package info.setmy.textfunctions.tokens;

import info.setmy.textfunctions.TripleCursor;
import info.setmy.textfunctions.register.Namespace;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.hash;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class Template {

    public static final char PLACEHOLDER_BEGIN_CHAR = '{';
    public static final char PLACEHOLDER_END_CHAR = '}';

    private final List<TemplateToken> templateTokenList = new ArrayList<>();

    private TokenBuilder tokenBuilder;

    private final Namespace namespace;

    private final String templateString;

    private final char[] characters;

    public Template(final Namespace namespace, final String templateString) {
        this.namespace = namespace;
        this.templateString = templateString;
        this.characters = templateString.toCharArray();
    }

    public Template tokenize() {
        for (int i = 0; i < characters.length; i++) {
            handleCharacter(characters[i]);
        }
        addCurrentTokenBuilder();
        return this;
    }

    private void handleCharacter(final char character) {
        switch (character) {
            case PLACEHOLDER_BEGIN_CHAR:
                handleBeginChar(character);
                break;
            case PLACEHOLDER_END_CHAR:
                handleEndChar(character);
                break;
            default:
                handleTextChar(character);
        }
    }

    private void handleTextChar(final char character) {
        if (tokenBuilder == null) {
            tokenBuilder = new TokenBuilder(TokenType.TEXT);
        }
        tokenBuilder.add(character);
    }

    private void handleBeginChar(final char character) {
        addCurrentTokenBuilder();
        tokenBuilder = new TokenBuilder(TokenType.PLACEHOLDER)
            .add(character);
    }

    private void handleEndChar(final char character) {
        tokenBuilder.add(character);
        addCurrentTokenBuilder();
        tokenBuilder = null;
    }

    private void addCurrentTokenBuilder() {
        if (nonNull(tokenBuilder)) {
            add(new TemplateToken(tokenBuilder.getTokenType(), tokenBuilder.toString()));
        }
    }

    public boolean add(final TemplateToken templateToken) {
        return templateTokenList.add(templateToken);
    }

    public boolean isTemplate(final String templateString) {
        return this.templateString.equals(templateString);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof Template) {
            final Template template = (Template) o;
            return Objects.equals(templateString, template.templateString);
        }
        return false;
    }

    public String getTemplateString() {
        return templateString;
    }

    @Override
    public int hashCode() {
        return hash(templateTokenList);
    }

    public List<TextToken> parse(final String text) {
        final List<TextToken> result = new ArrayList<>();
        final TripleCursor<TemplateToken> tripleCursor = new TripleCursor<>(templateTokenList);
        final Data data = new Data(
            tripleCursor,
            result,
            text
        );
        while (tripleCursor.haveCurrent()) {
            handle(data);
            tripleCursor.next();
        }
        return result;
    }

    private void handle(final Data data) {
        switch (data.tripleCursor.getOptionalCurrent().get().getTokenType()) {
            case TEXT:
                handleUserText(data);
                break;
            case PLACEHOLDER:
                handlePlaceHolder(data);
                break;
            default:
                handleDefault(data);
        }
    }

    private void handleUserText(final Data data) {
        final TripleCursor<TemplateToken> tripleCursor = data.tripleCursor;
        final TemplateToken current = tripleCursor.getOptionalCurrent().get();
        data.result.add(
            new TextToken(
                current.getTokenType(),
                current.getValue()
            )
        );
        data.begin = data.begin + current.getValue().length();
    }

    private void handlePlaceHolder(final Data data) {
        final TripleCursor<TemplateToken> tripleCursor = data.tripleCursor;
        final TemplateToken current = tripleCursor.getOptionalCurrent().get();
        final int nextIndex = nextIndex(data);
        if (nextIndex >= 0) {
            final String placeholderValue = data.text.substring(data.begin, nextIndex);
            final String typeName = current.getValueInnerText();
            final Object newPlaceholderValue = namespace.getKeywordFunctionMapper()
                .convert(typeName, placeholderValue)
                .orElse(null);
            data.result.add(
                new TextToken(
                    current.getTokenType(),
                    newPlaceholderValue
                )
            );
        }
        data.begin = nextIndex;
    }

    private void handleDefault(final Data data) {
    }

    private int nextIndex(final Data data) {
        final TripleCursor<TemplateToken> tripleCursor = data.tripleCursor;
        int result;
        if (tripleCursor.getOptionalNext().isPresent()) {
            result = data.text.indexOf(tripleCursor.getOptionalNext().get().getValue());
        } else {
            result = data.text.length();
        }
        return result;
    }

    public boolean match(final String text) {
        boolean result = false;
        int expectedStringBegin = 0;
        for (TemplateToken templateToken : templateTokenList) {
            if (templateToken.getTokenType() == TokenType.TEXT) {
                result = false;
                final int stringBegin = text.indexOf(templateToken.getValue());
                if (stringBegin < expectedStringBegin) {
                    return result;
                } else {
                    result = true;
                }
                expectedStringBegin = stringBegin + templateToken.getValue().length();
            } else {
                expectedStringBegin++;//Placeholder takes at least one character string space
            }
        }
        return result;
    }

    private static class Data {

        public final TripleCursor<TemplateToken> tripleCursor;
        public final List<TextToken> result;
        public final String text;
        public int begin;

        Data(final TripleCursor<TemplateToken> tripleCursor, final List<TextToken> result, final String text) {
            this.tripleCursor = tripleCursor;
            this.result = result;
            this.text = text;
        }
    }
}
