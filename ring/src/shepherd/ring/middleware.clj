(ns shepherd.ring.middleware
  (:require [shepherd.protocols :as shepherd])
  (:import [shepherd.exceptions Unauthorized]))


(defn wrap-authentication
  "Ring middleware that enables your
   authentication workflow."
  [handler workflow]

  (fn [request]
    (if (shepherd/parse-identity workflow request)
      (handler request)
      (let [creds (shepherd/parse-credentials workflow request)]
        (if-not creds
          (handler request)
          (let [req (shepherd/authenticate-request workflow request creds)]
            (handler req)))))))


(defn wrap-authorization
  "Ring middleware that enables your
   authorization workflow."
  [handler workflow]

  (fn [request]
    (try (handler request)
         (catch Unauthorized e
           (if-let [id (shepherd/parse-identity workflow request)]
             (shepherd/handle-unauthorized workflow request id)
             (shepherd/handle-unauthenticated workflow request))))))


(defn wrap-auth
  "Ring middleware adding both authentication and authorization."

  ([handler workflow] (wrap-auth handler workflow workflow))
  ([handler authentication-workflow authorization-workflow]

     (-> handler
         (wrap-authorization authorization-workflow)
         (wrap-authentication authentication-workflow))))
