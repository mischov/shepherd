(ns shepherd.ring.workflow.http-basic
  (:require [shepherd.protocols :as shepherd]
            [shepherd.codecs :refer [decode-base64]]
            [clojure.string :refer [split]]))


(defn parse-http-basic-credentials
  [request]

  (when-let [auth-header (get-in request [:headers "authorization"])]
    (let [[_ auth-str] (re-find #"^Basic (.+)$" auth-header)
          [username password] (split (decode-base64 auth-str) #":")]
      {:username username :password password})))


(defrecord HttpBasicWorkflow
  [realm authn authr unauthr]

  shepherd/Authentication
  (parse-credentials
    [_ request]
    (parse-http-basic-credentials request))

  (authenticate
    [_ request credentials]
    (let [id (when authn (authn credentials))]
      (if id
        (assoc request :identity id)
        request)))

  shepherd/Authorization
  (parse-identity
    [_ request]
    (get request :identity))
  
  (authorized?
    [_ request identity]
    (when authr (authr request identity)))

  (unauthorized
    [_ request identity]
    (if unauthr
      (unauthr request identity realm)
      (if identity
        {:status 403
         :body "Permission denied."}
        {:status 401
         :headers {"WWW-Authenticate" (str "Basic realm=\"" realm "\"")}
         :body "Unauthorized."}))))


(defn create-http-basic-workflow
  [{:keys [realm authn authr unauthr] :or {realm "Secured"} :as init}]

  (map->HttpBasicWorkflow init))
