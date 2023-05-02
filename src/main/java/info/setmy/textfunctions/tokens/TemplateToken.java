package info.setmy.textfunctions.tokens;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@Builder
@RequiredArgsConstructor
public class TemplateToken {

    private final TokenType tokenType;

    private final String value;

    public String getValueInnerText() {
        return value.substring(1, value.length() - 1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenType, value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TemplateToken templateToken = (TemplateToken) o;
        return tokenType == templateToken.tokenType && Objects.equals(value, templateToken.value);
    }
}
