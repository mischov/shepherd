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


(defn parse-identity
  [request]
  
  (get request :identity))


(defrecord HttpBasicWorkflow
  [realm authenticate unauthenticated unauthorized]

  shepherd/Authentication
  (parse-credentials
    [_ request]
    (parse-http-basic-credentials request))

  (authenticate-credentials
    [_ request credentials]
    (let [id (when authenticate (authenticate credentials))]
      (if id
        (assoc request :identity id)
        request)))

  shepherd/Authorization
  (parse-identity
    [_ request]
    (parse-identity request))
  
  (handle-unauthenticated
    [_ request]
    (if unauthenticated
      (unauthenticated request)
      {:status 401
       :headers {"WWW-Authenticate" (str "Basic realm=\"" realm "\"")}
       :body "Unauthorized."}))

  (handle-unauthorized
    [_ request identity]
    (if unauthorized
      (unauthorized request identity)
      {:status 403
       :body "Permission denied."})))


(defn http-basic-workflow
  [{:keys [realm authenticate unauthenticated unauthorized]
    :or {realm "Secured"} :as init}]

  (map->HttpBasicWorkflow init))
