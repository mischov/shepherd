(ns shepherd.codecs
  (:import [javax.xml.bind DatatypeConverter]))


;; While it is my goal and intention to rely on
;; external libraries as much as possible throughout
;; shepherd, there was no external library that uses
;; javax.xml.bind.DatatypeConverter for encoding/decoding.

;; My plan is to split this namespace out into its own
;; library in the near future and then just rely on that.


(defprotocol Base64
  (decode-base64 [v]
    "Decodes a base64 encoded value to a string.")
  (encode-base64 [v]
    "Base64 encodes a value to a string."))

(extend-protocol Base64
  (Class/forName "[B")
  (decode-base64
    [bytes]
    (String. (DatatypeConverter/parseBase64Binary (String. bytes "UTF-8"))
             "UTF-8"))

  (encode-base64
    [bytes]
    (DatatypeConverter/printBase64Binary bytes))
  
  String
  (decode-base64
    [string]
    (String. (DatatypeConverter/parseBase64Binary string) "UTF-8"))
  
  (encode-base64
    [string]
    (DatatypeConverter/printBase64Binary (.getBytes string))))


(defprotocol Hex
  (decode-hex [v]
    "Decodes a hex encoded value to a string.")
  (encode-hex [v]
    "Hex encodes a value to a string."))

(extend-protocol Hex
  (Class/forName "[B")
  (decode-hex
    [bytes]
    (String. (DatatypeConverter/parseHexBinary (String. bytes "UTF-8"))
             "UTF-8"))

  (encode-hex
    [bytes]
    (DatatypeConverter/printHexBinary bytes))
  
  String
  (decode-hex
    [string]
    (String. (DatatypeConverter/parseHexBinary string) "UTF-8"))
  
  (encode-hex
    [string]
    (DatatypeConverter/printHexBinary (.getBytes string))))

