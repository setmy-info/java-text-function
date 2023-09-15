package info.setmy.textfunctions

import info.setmy.textfunctions.functions.Parameters
import info.setmy.textfunctions.functions.Return
import info.setmy.textfunctions.register.DataTypeRegistration
import info.setmy.textfunctions.register.FunctionDeclaration
import info.setmy.textfunctions.register.Namespace
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.assertj.core.api.Assertions.assertThat
import static org.junit.jupiter.api.Assertions.assertThrows

class NamespaceTest {

    public static final String NAMESPACE_NAME = "some namespace"
    public static final String FUNCTION_TEMPLATE_STRING = "This is function template string with values {string}, {string} and {custom}."
    public static final String FUNCTION_CALL_STRING = "This is function template string with values AT PLACEHOLDER PLACES, AND ANOTHER and CUSTOM DATA TYPE."

    Namespace namespace

    @BeforeEach
    void before() {
        namespace = new Namespace(NAMESPACE_NAME)
    }

    @Test
    void functionAndCall() {
        namespace.register(DataTypeRegistration.builder()
            .keyword("custom")
            .synonyms("cust", "cus")
            .function { "#" + it + "#" }
            .build()
        )

        FunctionDeclaration functionDeclaration = FunctionDeclaration.builder()
            .functionTemplate(FUNCTION_TEMPLATE_STRING)
            .function {
                final Parameters parameters = it.getParameters()
                final String a = (String) parameters[0].get().getValue()
                final String b = (String) parameters[1].get().getValue("Y")
                final String c = (String) parameters[2].get().getOptionalValue().orElse("z")
                return Return.newReturn(a + " " + b + " " + c)
            }
            .build();

        namespace.register(functionDeclaration)

        final Return returnValue = namespace.call(FUNCTION_CALL_STRING)
        assertThat(returnValue[0].get().getValue()).isEqualTo("AT PLACEHOLDER PLACES AND ANOTHER #CUSTOM DATA TYPE#")
        assertThat(returnValue[0].get().getValue("xyz")).isEqualTo("AT PLACEHOLDER PLACES AND ANOTHER #CUSTOM DATA TYPE#")
        assertThat(returnValue[0].get().value).isEqualTo("AT PLACEHOLDER PLACES AND ANOTHER #CUSTOM DATA TYPE#")
    }

    @Test
    void nonRegisteredFunctionCall() {
        def exception = assertThrows(FunctionNotFoundException, () -> {
            namespace.call "This function is not registered for calling."
        }, "Should throw exception");
        assert exception.getMessage() == "No matching function for: This function is not registered for calling."
    }
}
