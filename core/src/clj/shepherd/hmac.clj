(ns shepherd.hmac
  (:require [pandect.core :as p]))

(def hmac-md5
  "Values can be strings, byte arrays, or files.

   Format:
     (hmac-md5 raw-value secret-value)"
  p/md5-hmac)

(def hmac-sha1 p/sha1-hmac)
(def hmac-sha256 p/sha256-hmac)
(def hmac-sha384 p/sha384-hmac)
(def hmac-sha512 p/sha512-hmac)

(def hmac-sha3-256 p/sha3-256-hmac)
(def hmac-sha3-384 p/sha3-384-hmac)
(def hmac-sha3-512 p/sha3-512-hmac)

(def hmac-ripemd128 p/ripemd128-hmac)
(def hmac-ripemd160 p/ripemd160-hmac)
(def hmac-ripemd256 p/ripemd256-hmac)
(def hmac-ripemd320 p/ripemd320-hmac)

(def hmac-tiger p/tiger-hmac)

(def hmac-whirlpool p/whirlpool-hmac)
