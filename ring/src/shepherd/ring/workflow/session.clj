(ns shepherd.ring.workflow.session
  (:require [shepherd.protocols :as shepherd]))


(defrecord SessionWorkflow
  [authr unauthr]

  shepherd/Authentication
  (parse-credentials
    [_ request]
    (get-in request [:session :identity]))

  (authenticate
    [_ request credentials]
    (if credentials
      (assoc request :identity credentials)
      request))

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
      (unauthr request identity)
      (if identity
        {:status 403
         :body "Permission denied."}
        {:status 401
         :body "Unauthorized."}))))


(defn create-session-workflow
  [{:keys [authr unauthr] :as init}]

  (map->SessionWorkflow init))
