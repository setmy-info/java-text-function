package info.setmy.textfunctions.tokens;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class TextToken {

    private final TokenType tokenType;
    private final Object value;

    public boolean is(final TokenType tokenType) {
        return this.tokenType == tokenType;
    }
}
