# java-text-functions

Call functions with parameters as human-readable text. Parsed to tokens and function parameters.

Human text mapped to "functions" with customizable function parameter type conversion.

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
namespaces.register(
    FunctionDeclaration.builder()
        .namespaceName(null) // Goes to global or default namespace
        .functionTemplate("Say {string}") // Template text as function with parameter
        .function {
            Parameters parameters = it.getParameters()
            Optional<Parameter> optionalParameter = parameters.get(0)
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

## TODO

1. Language support for a) text functions, b) some data transform/convert (boolean: yes, no, jah, ei, ...).
1. Data validation (for security) before conversion/transform and possibility to add after validators.
1. Implement it fully in Groovy, to get latest groovy enhancements at lover version Java.
1. Seting default namespace.
1. date and time formats passing to converter.
1. TODO list in code .
1. Looks like pitest doesn't work.
1. Tokens parsing can be a more fancy (event based, ...) and in separate library
