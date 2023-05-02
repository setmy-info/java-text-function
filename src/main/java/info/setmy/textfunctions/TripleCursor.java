package info.setmy.textfunctions;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class TripleCursor<T> {

    private final List<T> list;

    private Optional<T> optionalPrevious = empty();

    private Optional<T> optionalCurrent = empty();

    private Optional<T> optionalNext = empty();

    private int currentIndex;

    public TripleCursor(final List<T> list) {
        this.list = unmodifiableList(list);
        init();
    }

    public TripleCursor init() {
        if (this.list.size() == 0) {
            return this;
        }
        optionalCurrent = of(this.list.get(currentIndex));
        if (this.list.size() > 1) {
            optionalNext = of(this.list.get(currentIndex + 1));
        }
        return this;
    }

    public boolean haveCurrent() {
        return optionalCurrent.isPresent();
    }

    public void next() {
        currentIndex++;
        optionalPrevious = optionalCurrent;
        optionalCurrent = optionalNext;
        if (list.size() > currentIndex + 1) {
            optionalNext = of(list.get(currentIndex + 1));
        } else {
            optionalNext = empty();
        }
    }

    public Optional<T> getOptionalPrevious() {
        return optionalPrevious;
    }

    public void setOptionalPrevious(final Optional<T> optionalPrevious) {
        this.optionalPrevious = optionalPrevious;
    }

    public Optional<T> getOptionalCurrent() {
        return optionalCurrent;
    }

    public void setOptionalCurrent(final Optional<T> optionalCurrent) {
        this.optionalCurrent = optionalCurrent;
    }

    public Optional<T> getOptionalNext() {
        return optionalNext;
    }

    public void setOptionalNext(final Optional<T> optionalNext) {
        this.optionalNext = optionalNext;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public List<T> getList() {
        return list;
    }
}
