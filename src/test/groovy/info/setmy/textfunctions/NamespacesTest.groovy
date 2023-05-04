package info.setmy.textfunctions

import info.setmy.textfunctions.functions.Parameter
import info.setmy.textfunctions.functions.Parameters
import info.setmy.textfunctions.functions.Return
import info.setmy.textfunctions.register.DataTypeRegistration
import info.setmy.textfunctions.register.FunctionDeclaration
import info.setmy.textfunctions.register.Namespaces
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static info.setmy.textfunctions.functions.Return.newReturn
import static org.assertj.core.api.Assertions.assertThat
import static org.junit.jupiter.api.Assertions.assertThrows

class NamespacesTest {

    public static final String FUNCTION_TEMPLATE_STRING = "This is function template string with values {string}, {string} and {custom}."
    public static final String FUNCTION_CALL_STRING = "This is function template string with values AT PLACEHOLDER PLACES, AND ANOTHER and CUSTOM DATA TYPE."
    public static final String FUNCTION_TEMPLATE_DATA_TYPES =
        "Character {char}" +
            " and short {short}" +
            " and integer {int}" +
            " and long {long}" +
            " and float {float}" +
            " and double {double}" +
            " and boolean {boolean} as true" +
            " and boolean {boolean} as false" +
            " and date time {dateTime}" +
            " and big decimal {bigDecimal} sum."
    public static final String FUNCTION_CALL_DATA_TYPES =
        "Character A" +
            " and short 123" +
            " and integer 1234" +
            " and long 12345" +
            " and float 123456.789" +
            " and double 1234567.89" +
            " and boolean true as true" +
            " and boolean false as false" +
            " and date time 31.12.1976 23.59.59" +
            " and big decimal 12345678.9 sum."

    Namespaces namespaces

    @BeforeEach
    void before() {
        namespaces = new Namespaces()
    }

    @Test
    void functionAndCall() {
        namespaces.declare(
            DataTypeRegistration.builder()
                .namespaceName(null) // Goes to default namespace
                .keyword("custom")
                .synonyms("cust", "cus")
                .function { "#" + it + "#" }
                .build()
        )

        namespaces.declare(
            FunctionDeclaration.builder()
                .functionTemplate(FUNCTION_TEMPLATE_STRING)
                .function {
                    final Parameters parameters = it.getParameters()
                    assert parameters.arePresent() == true
                    assert parameters.isPresent() == true
                    assert parameters.isEmpty() == false
                    final String a = (String) parameters[0].get().getOptionalValue().orElse("x")
                    final String b = (String) parameters[1].get().getOptionalValue().orElse("y")
                    final String c = (String) parameters.getParameter(2).getValue("z")
                    return newReturn(a + " " + b + " " + c)
                }
                .build()
        )

        final Return returnValue = namespaces.call(null, FUNCTION_CALL_STRING)
        assertThat(returnValue[0].get().value).isEqualTo("AT PLACEHOLDER PLACES AND ANOTHER #CUSTOM DATA TYPE#")
        assertThat(returnValue[0].get().getValue()).isEqualTo("AT PLACEHOLDER PLACES AND ANOTHER #CUSTOM DATA TYPE#")
        assertThat(returnValue[0].get().getValue("default value")).isEqualTo("AT PLACEHOLDER PLACES AND ANOTHER #CUSTOM DATA TYPE#")
        assertThat(returnValue.getReturnValue(0).value).isEqualTo("AT PLACEHOLDER PLACES AND ANOTHER #CUSTOM DATA TYPE#")
        assertThat(returnValue.getReturnValue(0).getValue()).isEqualTo("AT PLACEHOLDER PLACES AND ANOTHER #CUSTOM DATA TYPE#")
        assertThat(returnValue.getReturnValue(0).getValue("default value")).isEqualTo("AT PLACEHOLDER PLACES AND ANOTHER #CUSTOM DATA TYPE#")
    }

    @Test
    void nonRegisteredFunctionCall() {
        def exception = assertThrows(FunctionNotFoundException, () -> {
            namespaces.call null, "This function is not registered for calling."
        }, "Should throw exception");
        assert exception.getMessage() == "No matching function for: This function is not registered for calling."
    }

    @Test
    void differentDataTypes() {
        namespaces.declare(
            FunctionDeclaration.builder()
                .functionTemplate(FUNCTION_TEMPLATE_DATA_TYPES)
                .function {
                    final Parameters parameters = it.getParameters()
                    assert parameters.arePresent() == true
                    assert parameters.isEmpty() == false
                    return newReturn(
                        parameters[0].get().getOptionalValue().orElse(null).toString() + " " +
                            parameters[1].get().getOptionalValue().orElse(null) + " " +
                            parameters[2].get().getOptionalValue().orElse(null) + " " +
                            parameters[3].get().getOptionalValue().orElse(null) + " " +
                            parameters[4].get().getOptionalValue().orElse(null) + " " +
                            parameters[5].get().getOptionalValue().orElse(null) + " " +
                            parameters[6].get().getOptionalValue().orElse(null) + " " +
                            parameters[7].get().value + " " +
                            parameters[8].get().getValue(null) + " " +
                            parameters[9].get().getValue()
                    )
                }
                .build()
        )

        final Return returnValue = namespaces.call(null, FUNCTION_CALL_DATA_TYPES)
        assert returnValue.isPresent() == true
        assert returnValue.arePresent() == true
        assert returnValue.isEmpty() == false
        assert returnValue.getReturnValue(0).value == "A 123 1234 12345 123456.79 1234567.89 true false 1976-12-31T23:59:59 12345678.9"
    }

    @Test
    void defaultNamespace() {
        namespaces.setNamespace("NON-GLOBAL")
        namespaces.declare(
            FunctionDeclaration.builder()
                .functionTemplate("Call to {string}")
                .function {
                    final Parameters parameters = it.getParameters()
                    return newReturn(
                        "Should call to " + parameters[0].get().getOptionalValue().orElse("Me").toString()
                    )
                }
                .build()
        )

        assert namespaces.call(null, "Call to Me")[0].get().getOptionalValue()
            .get() == "Should call to Me"
        assert namespaces.call("NON-GLOBAL", "Call to You")[0].get().value == "Should call to You"
    }

    @Test
    void exampleForREADME() {
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

        Return returnValue = namespaces.call "Say Hello World!"
        assert returnValue.isEmpty() == true
    }
}

