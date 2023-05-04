package info.setmy.textfunctions;

import info.setmy.textfunctions.register.Namespace;
import info.setmy.textfunctions.register.TemplateFunctionMapper;
import info.setmy.textfunctions.tokens.Template;
import info.setmy.textfunctions.tokens.TextToken;
import info.setmy.textfunctions.tokens.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static info.setmy.textfunctions.functions.Return.newReturn;
import static org.assertj.core.api.Assertions.assertThat;

public class TextFunctionMapperTest {

    public static final String ABCDEF = "abcdef";
    public static final String STRING = "{string}";
    public static final String STRING_DEF = "{string}def";
    public static final String ABC_STRING = "abc{string}";
    public static final String ABC_STRING_LMN = "abc{string}lmn";
    public static final String STRING_DEFGHIJK_STRING = "{string}defghijk{string}";
    public static final String TEMPLATE_STRING = "This is actually {string} call with parameters {string} and {string}.";

    TemplateFunctionMapper templateFunctionMapper;

    Namespace namespace;

    @BeforeEach
    public void before() {
        namespace = new Namespace("default");
        templateFunctionMapper = TemplateFunctionMapper.newInstance(namespace);
        templateFunctionMapper.register(ABCDEF, context -> {
            System.out.println(ABCDEF + " called with: " + context);
            return newReturn("x");
        });
        templateFunctionMapper.register(STRING, context -> {
            System.out.println(STRING + " called with: " + context);
            return newReturn(context.getParameters().getAt(0).get());
        });
        templateFunctionMapper.register(STRING_DEF, context -> {
            System.out.println(STRING_DEF + " called with: " + context);
            return newReturn(context.getParameters().getAt(0).get());
        });
        templateFunctionMapper.register(ABC_STRING, context -> {
            System.out.println(ABC_STRING + " called with: " + context);
            return newReturn(context.getParameters().getAt(0).get());
        });
        templateFunctionMapper.register(ABC_STRING_LMN, context -> {
            System.out.println(ABC_STRING_LMN + " called with: " + context);
            return newReturn(context.getParameters().getAt(0).get());
        });
        templateFunctionMapper.register(STRING_DEFGHIJK_STRING, context -> {
            System.out.println(STRING_DEFGHIJK_STRING + " called with: " + context);
            return newReturn(context.getParameters().getAt(0).get());
        });
        templateFunctionMapper.register(
            TEMPLATE_STRING,
            parameters ->
                newReturn(
                    parameters.getParameters().getAt(0).get() +
                        "/" + parameters.getParameters().getAt(1).get() +
                        "/" + parameters.getParameters().getAt(2).get()
                )
        );
    }

    @Test
    public void parse_noPlaceholders() {
        final Template template = templateFunctionMapper.getTemplate(ABCDEF).get();
        final List<TextToken> parsed = template.parse("abcdef");
        assertThat(parsed).isNotEmpty().hasSize(1);
        assertThat(parsed.get(0).getTokenType()).isEqualTo(TokenType.TEXT);
        assertThat(parsed.get(0).getValue()).isEqualTo("abcdef");
    }

    @Test
    public void parse_singlePlaceholder() {
        final Template template = templateFunctionMapper.getTemplate(STRING).get();
        final List<TextToken> parsed = template.parse("abcdef");
        assertThat(parsed).isNotEmpty().hasSize(1);
        assertThat(parsed.get(0).getTokenType()).isEqualTo(TokenType.PLACEHOLDER);
        assertThat(parsed.get(0).getValue()).isEqualTo("abcdef");
    }

    @Test
    public void parse_placeholderAtBegin() {
        final Template template = templateFunctionMapper.getTemplate(STRING_DEF).get();
        final List<TextToken> parsed = template.parse("abcdef");
        assertThat(parsed).isNotEmpty().hasSize(2);
        assertThat(parsed.get(0).getTokenType()).isEqualTo(TokenType.PLACEHOLDER);
        assertThat(parsed.get(0).getValue()).isEqualTo("abc");
        assertThat(parsed.get(1).getTokenType()).isEqualTo(TokenType.TEXT);
        assertThat(parsed.get(1).getValue()).isEqualTo("def");
    }

    @Test
    public void parse_placeholderAtEnd() {
        final Template template = templateFunctionMapper.getTemplate(ABC_STRING).get();
        final List<TextToken> parsed = template.parse("abcdef");
        assertThat(parsed).isNotEmpty().hasSize(2);
        assertThat(parsed.get(0).getTokenType()).isEqualTo(TokenType.TEXT);
        assertThat(parsed.get(0).getValue()).isEqualTo("abc");
        assertThat(parsed.get(1).getTokenType()).isEqualTo(TokenType.PLACEHOLDER);
        assertThat(parsed.get(1).getValue()).isEqualTo("def");
    }

    @Test
    public void parse_placeholderAtMiddle() {
        final Template template = templateFunctionMapper.getTemplate(ABC_STRING_LMN).get();
        final List<TextToken> parsed = template.parse("abcdefghijklmn");
        assertThat(parsed).isNotEmpty().hasSize(3);
        assertThat(parsed.get(0).getTokenType()).isEqualTo(TokenType.TEXT);
        assertThat(parsed.get(0).getValue()).isEqualTo("abc");
        assertThat(parsed.get(1).getTokenType()).isEqualTo(TokenType.PLACEHOLDER);
        assertThat(parsed.get(1).getValue()).isEqualTo("defghijk");
        assertThat(parsed.get(2).getTokenType()).isEqualTo(TokenType.TEXT);
        assertThat(parsed.get(2).getValue()).isEqualTo("lmn");
    }

    @Test
    public void parse_placeholderAtEdges() {
        final Template template = templateFunctionMapper.getTemplate(STRING_DEFGHIJK_STRING).get();
        final List<TextToken> parsed = template.parse("abcdefghijklmn");
        assertThat(parsed).isNotEmpty().hasSize(3);
        assertThat(parsed.get(0).getTokenType()).isEqualTo(TokenType.PLACEHOLDER);
        assertThat(parsed.get(0).getValue()).isEqualTo("abc");
        assertThat(parsed.get(1).getTokenType()).isEqualTo(TokenType.TEXT);
        assertThat(parsed.get(1).getValue()).isEqualTo("defghijk");
        assertThat(parsed.get(2).getTokenType()).isEqualTo(TokenType.PLACEHOLDER);
        assertThat(parsed.get(2).getValue()).isEqualTo("lmn");
    }

    @Test
    public void placeholdersDetection4() {
        final Optional<Template> optionalTemplate = templateFunctionMapper.getTemplate(TEMPLATE_STRING);
        assertThat(optionalTemplate).isPresent();
        final Template template = optionalTemplate.get();
        final List<TextToken> parsed = template.parse("This is actually user textual function call with parameters abc, def, ghi and ijk, lmn.");
        assertThat(parsed).isNotEmpty().hasSize(7);
        assertThat(parsed.get(0).getTokenType()).isEqualTo(TokenType.TEXT);
        assertThat(parsed.get(0).getValue()).isEqualTo("This is actually ");
        assertThat(parsed.get(1).getTokenType()).isEqualTo(TokenType.PLACEHOLDER);
        assertThat(parsed.get(1).getValue()).isEqualTo("user textual function");
        assertThat(parsed.get(2).getTokenType()).isEqualTo(TokenType.TEXT);
        assertThat(parsed.get(2).getValue()).isEqualTo(" call with parameters ");
        assertThat(parsed.get(3).getTokenType()).isEqualTo(TokenType.PLACEHOLDER);
        assertThat(parsed.get(3).getValue()).isEqualTo("abc, def, ghi");
        assertThat(parsed.get(4).getTokenType()).isEqualTo(TokenType.TEXT);
        assertThat(parsed.get(4).getValue()).isEqualTo(" and ");
        assertThat(parsed.get(5).getTokenType()).isEqualTo(TokenType.PLACEHOLDER);
        assertThat(parsed.get(5).getValue()).isEqualTo("ijk, lmn");
        assertThat(parsed.get(6).getTokenType()).isEqualTo(TokenType.TEXT);
        assertThat(parsed.get(6).getValue()).isEqualTo(".");
    }

    @Test
    public void call() {
        templateFunctionMapper.call("abcdef");
    }
}
