package info.setmy.textfunctions.register;

import info.setmy.textfunctions.functions.TextFunction;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class FunctionDeclaration {

    @Default
    private final Optional<String> optionalNamespaceName = empty();

    @Default
    private final Optional<String> optionalFunctionTemplate = empty();

    @Default
    private final Optional<TextFunction> optionalTextFunction = empty();

    public static class FunctionDeclarationBuilder {

        public FunctionDeclarationBuilder namespaceName(final String namespaceName) {
            this.optionalNamespaceName(ofNullable(namespaceName));
            return this;
        }

        public FunctionDeclarationBuilder functionTemplate(final String functionTemplate) {
            this.optionalFunctionTemplate(ofNullable(functionTemplate));
            return this;
        }

        public FunctionDeclarationBuilder function(final TextFunction textFunction) {
            this.optionalTextFunction(ofNullable(textFunction));
            return this;
        }
    }
}
