(ns shepherd.authorization
  (import [shepherd.exceptions Unauthorized]))


(defn throw-unauthorized
  []

  (throw (Unauthorized.)))
