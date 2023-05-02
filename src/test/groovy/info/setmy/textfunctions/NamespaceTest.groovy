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

    public static final String NAMESPACE_NAME = "default namespace"
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

        namespace.register(
            FunctionDeclaration.builder()
                .functionTemplate(FUNCTION_TEMPLATE_STRING)
                .function {
                    final Parameters parameters = it.getParameters()
                    final String a = (String) parameters.get(0).get().getOptionalValue().orElse("x")
                    final String b = (String) parameters.get(1).get().getOptionalValue().orElse("y")
                    final String c = (String) parameters.get(2).get().getOptionalValue().orElse("z")
                    return Return.newReturn(a + " " + b + " " + c)
                }
                .build()
        )

        final Return returnValue = namespace.call(FUNCTION_CALL_STRING)
        assertThat(returnValue.get(0).getOptionalValue().get()).isEqualTo("AT PLACEHOLDER PLACES AND ANOTHER #CUSTOM DATA TYPE#")
    }

    @Test
    void nonRegisteredFunctionCall() {
        def exception = assertThrows(FunctionNotFoundException, () -> {
            namespace.call "This function is not registered for calling."
        }, "Should throw exception");
        assert exception.getMessage() == "No matching function for: This function is not registered for calling."
    }
}
