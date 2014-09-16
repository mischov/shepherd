(ns shepherd.protocols)


(defprotocol Authentication
  "Protocol for authentication workflows."
  
  (parse-credentials [_ request]
    "Parse and return credentials from request.")
  
  (authenticate-credentials [_ request credentials]
    "Attempt to authenticate the credentials parsed
     from the request.

     Returns request.

     If the credentials authenticate, appends identity
     to returned request."))


(defprotocol Authorization
  "Protocol for authorization workflows."

  (parse-identity [_ request]
    "Parse and return identity from request.

     Default implementation assumes request is a map
     and identity is attached to the :identity key of
     request. If one of these facts is not true,
     parse-identity will need to be implemented by your
     workflow.")

  (handle-unauthenticated [_ request]
    "Handle request that are unauthenticated and unauthorized.")
  
  (handle-unauthorized [_ request id]
    "Handle requests that are authenticated but unauthorized."))


;; Safe (non-authorizing) default implementations.
;;
;; Request is assumed to be map, and identity is
;; assumed to be attached to the :identity key
;; of the request. If either of these are not the
;; case your authorization workflow will need to
;; implement parse-identity.


(extend-protocol Authentication
  java.lang.Object
  (parse-credentials [_ _]
    nil)

  (authenticate-credentials [_ request _]
    request))


(extend-protocol Authorization
  java.lang.Object
  (parse-identity [_ request]
    (get request :identity))

  (handle-unauthenticated [_ _]
    nil)

  (handle-unauthorized [_ _ _]
    nil))
