(ns shepherd.ring.middleware
  (:require [shepherd.protocols :as shepherd]))


(defn wrap-authentication
  "Ring middleware that enables your
   authentication workflow."
  [handler workflow]

  (fn [request]
    (let [creds (shepherd/parse-credentials workflow request)]
      (if creds
        (let [req (shepherd/authenticate workflow request creds)]
          (handler req))
        (handler request)))))


(defn wrap-authorization
  "Ring middleware that enables your
   authorization workflow."
  [handler workflow]

  (fn [request]
    (let [id (shepherd/parse-identity workflow request)]
      (if (shepherd/authorized? workflow request id)
        (handler request)
        (shepherd/unauthorized workflow request id)))))


(defn wrap-auth
  "Ring middleware adding both authentication and authorization."

  ([handler workflow] (wrap-auth handler workflow workflow))
  ([handler authn-workflow authr-workflow]

     (-> handler
         (wrap-authorization authr-workflow)
         (wrap-authentication authn-workflow))))
