package info.setmy.textfunctions.register;

import info.setmy.textfunctions.FunctionNotFoundException;
import info.setmy.textfunctions.LambdaReturn;
import info.setmy.textfunctions.functions.Context;
import info.setmy.textfunctions.functions.Parameter;
import info.setmy.textfunctions.functions.Parameters;
import info.setmy.textfunctions.functions.Return;
import info.setmy.textfunctions.functions.TextFunction;
import info.setmy.textfunctions.tokens.Template;
import info.setmy.textfunctions.tokens.TextToken;
import info.setmy.textfunctions.tokens.TokenType;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

@Log4j2
public class TemplateFunctionMapper {

    public static TemplateFunctionMapper newInstance(final Namespace namespace) {
        final TemplateFunctionMapper templateFunctionMapper = new TemplateFunctionMapper(namespace);
        return templateFunctionMapper;
    }

    private final Map<Template, TextFunction> templateFunctions = new ConcurrentHashMap<>();

    public TemplateFunctionMapper(final Namespace namespace) {
        this.namespace = namespace;
    }

    private final Namespace namespace;

    public Return call(final String text) {
        final LambdaReturn<Return> optionalReturn = new LambdaReturn(new Return());
        final List<Map.Entry<Template, TextFunction>> foundFunctions = templateFunctions.entrySet().stream()
            .filter(templateTemplateFunctionEntry -> templateTemplateFunctionEntry.getKey().match(text))
            .collect(toList());
        if (foundFunctions.isEmpty()) {
            throw new FunctionNotFoundException("No matching function for: " + text);
        }
        foundFunctions.forEach(templateTemplateFunctionEntry -> {
            log.info(
                "Found placeholder for: {} {}",
                text,
                templateTemplateFunctionEntry.getKey().getTemplateString()
            );
            final List<TextToken> templateTokens = templateTemplateFunctionEntry.getKey().parse(text);
            final LambdaReturn<Integer> optionalCounter = new LambdaReturn(0);
            final Parameters parameters = new Parameters();
            templateTokens.stream()
                .filter(textToken -> textToken.is(TokenType.PLACEHOLDER))
                .forEach(textToken -> {
                    parameters.add(
                        new Parameter(
                            // TODO : decide better form for parameter key: names coming from type template, ...
                            "parameter-" + optionalCounter.getValue().orElse(0),
                            textToken.getValue()
                        )
                    );
                    optionalCounter.setValue(optionalCounter.getValue().orElse(0) + 1);
                });
            final Return returnValue = templateTemplateFunctionEntry.getValue().apply(
                new Context(namespace, parameters)
            );
            optionalReturn.setValue(returnValue);
        });
        return optionalReturn.getValue().get();
    }

    public void register(final String templateString, final TextFunction function) {
        templateFunctions.put(toTemplate(templateString), function);
    }

    private Template toTemplate(final String templateString) {
        return new Template(namespace, templateString)
            .tokenize();
    }

    public Optional<Template> getTemplate(final String templateString) {
        return templateFunctions.keySet().stream()
            .filter(template -> template.isTemplate(templateString))
            .findFirst();
    }
}
