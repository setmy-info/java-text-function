# java-text-function

Call functions with parameters as human-readable text. Parsed to tokens and function parameters.

Human text mapped to "functions" with customizable function parameter type conversion.

Named it as text functions (TF).

Current library/component remains a low-level component on which other components can be planned and built.

## Example

```groovy
Return returnValue = namespaces.call "Say Hello World!"
assert returnValue.isEmpty() == true
```

Prints out:

```text
Hello World!
```

To get that, functions have to be registered (into namespace):

```groovy
namespaces.declare(
    FunctionDeclaration.builder()
        .functionTemplate("Say {string}") // Template text as function with parameter
        .function {// Function body
            Parameters parameters = it.getParameters()
            assert parameters.arePresent() == true
            Optional<Parameter> optionalParameter = parameters[0]
            if (optionalParameter.isPresent()) {
                Parameter parameter = optionalParameter.get()
                String parameterString = parameter.getOptionalValue().orElse("")
                System.out.println(parameterString)
                newReturn()
            }
        }
        .build()
)
```

## Build

* [JDK 8](https://www.azul.com/downloads/?version=java-8-lts&package=jdk#zulu)

* mvnw clean install

* mvnw org.pitest:pitest-maven:mutationCoverage site:site

## Development version starting

```shell
./mvnw release:update-versions -DautoVersionSubmodules=true -DdevelopmentVersion=1.1.0-SNAPSHOT
```

## Release

```shell
./mvnw release:prepare
```

```shell
./mvnw clean install && ./mvnw org.pitest:pitest-maven:mutationCoverage site:site
```

```shell
./mvnw deploy -Psign
```

## TODOs and ideas

Some TODOs, ideas, blueprints, maybe for building other components.

1. Language support for a) text functions, b) some data transform/convert (boolean: yes, no, jah, ei, ...).
1. Data validation (for security) before conversion/transform and possibility to add after validators.
1. Migrate code to Groovy. To get the capabilities and features of the newest (Groovy) language with lover version of
   Java.
1. date and time formats passing to converter.
1. Auto converters - type detectors (date, datetime).
1. TODO list in code.
1. Looks like pitest doesn't work.
1. Tokens parsing can be a more fancy (event based, ...) and in separate library.
1. Integrate with mvn release:perform
1. Plan to combine functions input and output connections like in any lang. Perhaps by function parameter keys, return
   value keys. Also, possible to handle with graph theories (**JGraphT**, GraphStream, Apache Commons Math, **Apache
   Commons Graph**, Colt, Jung). Function graphs, parameter graphs?
1. Numeric ID-s for functions, map by numbers to quicker function getting. Some kind of cache.
1. Placeholder parsing to add more possibilities (name for function parameter, type, datetime formats, ...)
1. TF-s to DB. Function body (implementation) can be already in Java and Groovy or similar - can't hold (
   easily) in DB. Maybe integrate with Clojure - function body as data.
1. Compile TF "libraries/books/chapters/paragraphs" to other languages. Write in TF-s and compile it to Java for
   example.
1. Use Codox (codox-maven-plugin) for Clojure documentation generation. Or Marginalia or Autodoc. 
