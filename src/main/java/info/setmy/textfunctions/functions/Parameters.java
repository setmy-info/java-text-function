package info.setmy.textfunctions.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class Parameters {

    private final List<Parameter> parameterList = new ArrayList<>();

    public boolean add(final Parameter parameter) {
        return parameterList.add(parameter);
    }

    public Parameter getParameter(final int index) {
        return this.getAt(index).orElse(null);
    }

    public Optional<Parameter> getAt(final int index) {
        if (index >= parameterList.size()) {
            return empty();
        }
        return of(parameterList.get(index));
    }

    public int size() {
        return parameterList.size();
    }

    public boolean isPresent() {
        return arePresent();
    }

    public boolean arePresent() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return parameterList.isEmpty();
    }
}
