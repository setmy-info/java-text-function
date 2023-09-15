package info.setmy.textfunctions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Getter
@RequiredArgsConstructor
public class KeyValue<L, R> {

    private final L key;

    private final R value;

    public R getValue(final R defaultValue) {
        return getOptionalValue().orElse(defaultValue);
    }

    public Optional<L> getOptionalKey() {
        return ofNullable(key);
    }

    public Optional<R> getOptionalValue() {
        return ofNullable(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KeyValue<?, ?> keyValue = (KeyValue<?, ?>) o;
        return Objects.equals(key, keyValue.key) && Objects.equals(value, keyValue.value);
    }
}
