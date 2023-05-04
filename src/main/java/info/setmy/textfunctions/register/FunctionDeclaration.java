package info.setmy.textfunctions.register;

import info.setmy.textfunctions.functions.TextFunction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FunctionDeclaration {

    private Optional<String> optionalNamespaceName;

    private Optional<String> optionalFunctionTemplate;

    private Optional<TextFunction> optionalTextFunction;

    public static class FunctionDeclarationBuilder {

        public FunctionDeclarationBuilder namespaceName(final String namespaceName) {
            this.optionalNamespaceName = ofNullable(namespaceName);
            return this;
        }

        public FunctionDeclarationBuilder functionTemplate(final String functionTemplate) {
            this.optionalFunctionTemplate = ofNullable(functionTemplate);
            return this;
        }

        public FunctionDeclarationBuilder function(final TextFunction textFunction) {
            this.optionalTextFunction = ofNullable(textFunction);
            return this;
        }
    }
}
