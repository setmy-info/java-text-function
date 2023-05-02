package info.setmy.textfunctions;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class LambdaReturn<T> {

    private Optional<T> value = empty();

    public LambdaReturn() {
    }

    public LambdaReturn(final T value) {
        this.setValue(value);
    }

    public Optional<T> getValue() {
        return value;
    }

    public LambdaReturn<T> setValue(final T value) {
        this.setValue(ofNullable(value));
        return this;
    }

    public LambdaReturn<T> setValue(final Optional<T> value) {
        this.value = value;
        return this;
    }
}
