(ns shepherd.hash
  (:require [pandect.core]
            [potemkin :refer [import-vars]]))


;;
;;  All functions accept a single string, byte array, or file argument.
;;


(import-vars
 [pandect.core

   md5
  
   sha1
   sha256
   sha384
   sha512

   sha3-256
   sha3-384
   sha3-512

   ripemd128
   ripemd160
   ripemd256
   ripemd320

   tiger

   whirlpool])



