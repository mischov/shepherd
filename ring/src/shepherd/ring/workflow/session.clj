(ns shepherd.ring.workflow.session
  (:require [shepherd.protocols :as shepherd]))


(defn parse-identity
  [request]

  (get request :identity))


(defrecord SessionWorkflow
  [unauthenticated unauthorized]

  shepherd/Identity
  (parse-identity
    [_ request]
    (parse-identity request))

  shepherd/Authentication
  (parse-credentials
    [_ request]
    (get-in request [:session :identity]))

  (authenticate-request
    [_ request credentials]
    (assoc request :identity credentials))

  shepherd/Authorization
  (handle-unauthenticated
    [_ request]
    (if unauthenticated
      (unauthenticated request)
      {:status 401
       :headers {}
       :body "Unauthorized."}))

  (handle-unauthorized
    [_ request identity]
    (if unauthorized
      (unauthorized request identity)
      {:status 403
       :headers {}
       :body "Permission denied."})))


(defn session-workflow
  "Requires additional middleware:

     ring.middleware.session."
  [{:keys [unauthenticated unauthorized] :as init}]

  (map->SessionWorkflow init))
