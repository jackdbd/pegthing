# Peg Thing

[![Build Status](https://travis-ci.com/jackdbd/pegthing.svg?branch=master)](https://travis-ci.org/jackdbd/pegthing)

A game developed for the Functional Programming chapter of [Clojure for the Brave and True](https://www.braveclojure.com/functional-programming/).

![Peg Thing](https://www.braveclojure.com/assets/images/cftbat/functional-programming/peg-thing-starting.png "Peg Thing").

![Demo of Peg Thing](https://raw.githubusercontent.com/jackdbd/pegthing/master/images/demo.png "Demo of Peg Thing")

![Dependency hierarchy graph](https://raw.githubusercontent.com/jackdbd/pegthing/master/images/ns-hierarchy.png "Dependency hierarchy graph generated with lein-hiera")

## Usage

This project uses the [Clojure tools CLI](https://clojure.org/reference/deps_and_cli) and [tools.build](https://clojure.org/guides/tools_build).

Play the game:

```sh
clj -X:play
```

## Build

In order to build the uberjar using [tools.build](https://clojure.org/guides/tools_build) you will need Clojure CLI 1.10.3.933 or higher. Download the latest version from [here](https://clojure.org/guides/getting_started#_clojure_installer_and_cli_tools).

```sh
curl -O https://download.clojure.org/install/linux-install-1.10.3.1075.sh
chmod +x linux-install-1.10.3.1075.sh
sudo ./linux-install-1.10.3.1075.sh
```

Running a tool with `-T` will create a classpath that does not include the project `:paths` and `:deps`. Using `-T:build` will use only the `:paths` and `:deps` from the `:build` alias.

Build the uberjar using tools.build:

```sh
clj -T:build uber
# or, equivalently
clj -X:uberjar
```

Then run the standalone uberjar:

```sh
java -jar target/pegthing.core-1.2.9-standalone.jar
```

## Tests

Run all tests with [kaocha](https://github.com/lambdaisland/kaocha):

```sh
bin/kaocha

# for a more verbose output, use this reporter
bin/kaocha --reporter kaocha.report/documentation
```

## Containerize the app

Create the container image using [pack](https://github.com/buildpacks/pack) and the [Paketo Tiny Builder](https://github.com/paketo-buildpacks/tiny-builder) (`BP` stands for Buildpack).

```sh
pack build pegthing:latest --builder paketobuildpacks/builder:tiny \
  --env BP_JVM_TYPE=JRE \
  --env BP_JVM_VERSION=11 \
  --env BP_CLJ_TOOLS_BUILD_ARGUMENTS="-T:build uber"
```

Configuration with build-time environment variables:

- [Paketo BellSoft Liberica Buildpack](https://github.com/paketo-buildpacks/bellsoft-liberica#configuration)
- [Paketo Clojure Tools Buildpack](https://github.com/paketo-buildpacks/clojure-tools/blob/main/README.md#configuration)

Play the game inside a container:

```sh
docker run --rm --interactive --tty pegthing:latest
```

Inspect the generated container image with [dive](https://github.com/wagoodman/dive). For example you could try building the container image with `--env BP_JVM_TYPE=JDK` first, and `--env BP_JVM_TYPE=JRE` second. You will see that the container image containing the JRE is much smaller than the one containing the JDK.

```sh
dive pegthing:latest
```
