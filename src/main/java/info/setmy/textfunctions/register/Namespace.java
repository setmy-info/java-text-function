package info.setmy.textfunctions.register;

import info.setmy.textfunctions.functions.Return;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Namespace {

    private final String name;

    private final KeywordFunctionMapper keywordFunctionMapper = KeywordFunctionMapper.newInstance(this);

    private final TemplateFunctionMapper templateFunctionMapper = TemplateFunctionMapper.newInstance(this);

    public Namespace(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void register(final DataTypeRegistration dataTypeRegistration) {
        final String keyword = dataTypeRegistration.getOptionalKeyword().orElse(null);
        final Function<String, Object> function = dataTypeRegistration.getOptionalFunction().orElse(null);
        final List<String> synonyms = dataTypeRegistration.getSynonymsList();

        keywordFunctionMapper.put(keyword, function);
        if (!dataTypeRegistration.getSynonymsList().isEmpty()) {
            keywordFunctionMapper.register(keyword, synonyms);
        }
    }

    public void register(final FunctionDeclaration functionDeclaration) {
        templateFunctionMapper.register(
            functionDeclaration.getOptionalFunctionTemplate().orElse(null),
            functionDeclaration.getOptionalTextFunction().orElse(null)
        );
    }

    public Return call(final String functionCallString) {
        return templateFunctionMapper.call(functionCallString);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Namespace namespace = (Namespace) o;
        return Objects.equals(name, namespace.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public KeywordFunctionMapper getKeywordFunctionMapper() {
        return keywordFunctionMapper;
    }
}
