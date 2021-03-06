# Shepherd

> That ain't a Shepherd.

Shepherd provides a solid foundation upon which to build secure Clojure applications.

## Contents

- [Features](#features)
- [Libraries](#libraries)
- [Installation](#installation)
- [Why?](#why)
- [Status](#status)
- [Quickstart](#quickstart)
- [TODO](#todo)

## Features

Shepherd currently features:

- Pashword hashing and comparison withBCrypt, SCrypt, and PBKDF2.
- Hashing with MD5, SHA512, and various other algorithms.
- HMACing with those same hashing algorithms.
- Base64 and Hex encoding and decoding.
- Protocols for Authorization and Authentication workflows.
- Middleware and basic/example implementations of workflows for Ring apps.

[**Back To Top ⇧**](#contents)

## Libraries

- `shepherd/core` - Foundation upon which security solutions can be built.
- `shepherd/ring` - Functionality for securing Ring apps.

[**Back To Top ⇧**](#contents)

## Installation

To install one of the above libraries (`shepherd/core` for example), add the following dependency to your `project.clj` file:

```clojure
[shepherd/core "0.0.2"]
```

To install all of the Shepherd libraries, add:

```clojure
[shepherd "0.0.2"]
```

[**Back To Top ⇧**](#contents)

## Why?

Rolling your own security for Clojure apps means fewer people testing your code, less expertise to draw on, and more work for yourself. Unfortunately, the Clojurian tendancy towards composing small, narrowly-purposed libraries means that many times you do have to roll your own.

Clojure's more integrated security libraries - namely Friend, Buddy, and Cylon - are improving this situation, but there is room for further improvement.

1. Friend and Cylon are relatively focused or rigid in their approaches, but many of the fundamental pieces of these two libraries are similar, and there is room for a more modular library upon which both libraries could be built.

2. There exist a number of tightly-focused, high-quality libraries - such as crypto-password and pandect - which integrated security libraries should be building on top of. The existing solutions tend towards implementing many of those things themselves.

Shepherd provides an integrated layer upon which further libraries can be built, and is itself built upon existing libraries where possible.

[**Back To Top ⇧**](#contents)

## Status

Shepherd is in **early alpha** and still being designed.

It is not recommended to use Shepherd in production.

[**Back To Top ⇧**](#contents)

## Quickstart

The [workflows](https://github.com/mischov/shepherd/tree/master/ring/src/shepherd/ring/workflow) in shepherd/ring and the [examples](https://github.com/mischov/shepherd-examples) would be a good starting place until I can get a proper quickstart together.

[**Back To Top ⇧**](#contents) 

## TODO

Shepherd is still very much being implemented. Some upcoming features may include, but are certainly not limited to:

- Documentation and Tutorials (by which a library lives or dies).
- More Ring workflows for common functionality like Oauth.
- (Possibly) Allowing multiple workflows.
- (Possibly) Component workflows.

If you have any futher suggestions, please contact me or raise an issue.

[**Back To Top ⇧**](#contents)

## License

Copyright © 2014 Mischov

Distributed under the Eclipse Public License.
