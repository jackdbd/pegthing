# Peg Thing

A game developed for the Functional Programming chapter of [Clojure for the Brave and True](https://www.braveclojure.com/functional-programming/).

![Peg Thing](https://www.braveclojure.com/assets/images/cftbat/functional-programming/peg-thing-starting.png "Peg Thing").

![Demo of Peg Thing](https://raw.githubusercontent.com/jackdbd/pegthing/master/images/demo.png "Demo of Peg Thing")

![Dependency hierarchy graph](https://raw.githubusercontent.com/jackdbd/pegthing/master/images/ns-hierarchy.png "Dependency hierarchy graph generated with lein-hiera")

## Usage

This project uses [Leiningen](https://leiningen.org/).

Play with:

```sh
lein run
```

## Build

Build the [uberjar](https://imagej.net/Uber-JAR):

```sh
lein uberjar
```

Then run the executable:

```sh
java -jar target/uberjar/pegthing-0.1.0-SNAPSHOT-standalone.jar
```

## Tests

Run all tests with:

```sh
lein test
```

## Other

The dependency hierarchy graph was generated with the Leiningen plugin [lein-hiera](https://github.com/greglook/lein-hiera). If you want to recreate it, run the following command (please note that you will need both lein-hiera and [Graphviz](https://graphviz.org/) installed).

```sh
lein hiera
```
