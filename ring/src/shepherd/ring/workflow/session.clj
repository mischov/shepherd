(ns shepherd.ring.workflow.session
  (:require [shepherd.protocols :as shepherd]))


(defn parse-identity
  [request]

  (get request :identity))


(defrecord SessionWorkflow
  [unauthenticated unauthorized]

  shepherd/Authentication
  (parse-credentials
    [_ request]
    (get-in request [:session :identity]))

  (authenticate-credentials
    [_ request credentials]
    (if credentials
      (assoc request :identity credentials)
      request))

  shepherd/Authorization
  (parse-identity
    [_ request]
    (parse-identity request))

  (handle-unauthenticated
    [_ request]
    (if unauthenticated
      (unauthenticated request)
      {:status 401
       :body "Unauthorized."}))

  (handle-unauthorized
    [_ request identity]
    (if unauthorized
      (unauthorized request identity)
      {:status 403
       :body "Permission denied."})))


(defn session-workflow
  [{:keys [unauthenticated unauthorized] :as init}]

  (map->SessionWorkflow init))
