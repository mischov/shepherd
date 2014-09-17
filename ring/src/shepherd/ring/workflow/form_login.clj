(ns shepherd.ring.workflow.form-login
  (:require [shepherd.protocols :as shepherd]))


(defrecord FormLoginWorkflow
  [username-key password-key authenticate login-uri]

  shepherd/Identity
  (parse-identity
    [_ request]
    (get request :identity))

  shepherd/Authentication
  (parse-credentials
    [_ {:keys [form-params uri] :as request}]
    (let [username (get form-params (or username-key "username"))
          password (get form-params (or password-key "password"))]
      (when (and (= login-uri uri) username password)
        {:username username :password password})))

  (authenticate-request
    [_ request credentials]
    (if-let [id (when authenticate (authenticate credentials))]
      (assoc request :identity id)
      request)))


(defn form-login-workflow
  "Key :login-uri used to make sure only valid login requests
   can issue identity. Values are read from :form-params key
   of request to ensure data was posted.

   Workflow only adds identity to request. This identity needs
   to be persisted somehow. One possible solution is to have the
   login handler issue a session cookie with the identity.

   Requires additional middleware:

     ring.middleware.params"
  [{:keys [username-key password-key authenticate login-uri] :as init}]

  (map->FormLoginWorkflow init))
