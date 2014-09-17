(ns shepherd.protocols)


(defprotocol Identity
  "Protocol for getting and setting request identity."

  (parse-identity [_ request]
    "Parse and return identity from request.")

  (attach-identity [_ request identity]
    "Attach identity to and return request."))


(defprotocol Authentication
  "Protocol for authenticating requests."
  
  (parse-credentials [_ request]
    "Parse and return credentials from request.")
  
  (authenticate-request [_ request credentials]
    "Attempt to authenticate the credentials parsed
     from the request.

     If the credentials are valid, returns the request
     with an identity attached.

     If the credentials are invalid, returns the original
     request."))


(defprotocol Authorization
  "Protocol for handle failure."
  
  (handle-unauthenticated [_ request]
    "Handle requests that are authenticated and unauthorized.")
  
  (handle-unauthorized [_ request id]
    "Handle requests that are authenticated but unauthorized."))


;; Safe (non-authorizing) default implementations.
;;
;; Request is assumed to be map, and identity is
;; assumed to be attached to the :identity key
;; of the request. If either of these are not the
;; case your authorization workflow will need to
;; implement parse-identity.


(extend-protocol Identity
  java.lang.Object
  (parse-identity [_ request]
    (get request :identity)))


(extend-protocol Authentication
  java.lang.Object
  (parse-credentials [_ _]
    nil)

  (authenticate-request [_ request _]
    request))


(extend-protocol Authorization
  java.lang.Object
  (handle-unauthenticated [_ _]
    nil)
  
  (handle-unauthorized [_ _ _]
    nil))
