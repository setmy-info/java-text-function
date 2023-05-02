package info.setmy.textfunctions;

import info.setmy.textfunctions.register.KeywordFunctionMapper;
import info.setmy.textfunctions.register.Namespace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KeywordMapperFunctionTest {

    public static final String SOME_NON_EXISTING_KEYWORD = "some.non.existing.keyword";

    KeywordFunctionMapper keywordFunctionMapper;

    Namespace namespace;

    @BeforeEach
    public void before() {
        namespace = new Namespace("default");
        keywordFunctionMapper = KeywordFunctionMapper.newInstance(namespace);
    }

    @Test
    public void nonExistingFunction() {
        assertThat(keywordFunctionMapper.get(SOME_NON_EXISTING_KEYWORD)).isNull();
        assertThat(keywordFunctionMapper.convert(SOME_NON_EXISTING_KEYWORD, "something")).isEmpty();
    }

    @Test
    public void existingFunction() {
        assertThat(keywordFunctionMapper.get("int")).isNotNull();
        assertThat(keywordFunctionMapper.convert("int", "123")).get().isEqualTo(123);
        assertThat(keywordFunctionMapper.convert("Integer", "123")).get().isEqualTo(123);
    }
}
