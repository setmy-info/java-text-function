package info.setmy.textfunctions.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class Return {

    private final List<ReturnValue> returnList;

    public static Return newReturn() {
        return new Return();
    }

    public static Return newReturn(final Object object) {
        return newReturn("0", object);
    }

    public static Return newReturn(final String key, final Object object) {
        return newReturn(new ReturnValue(key, object));
    }

    public static Return newReturn(final ReturnValue[] returnValues) {
        if (isNull(returnValues)) {
            return new Return();
        }
        return new Return(new ArrayList<>(asList(returnValues)));
    }

    public Return() {
        this.returnList = new ArrayList<>();
    }

    public Return(final List<ReturnValue> returnList) {
        this.returnList = returnList;
    }

    public static Return newReturn(final ReturnValue returnValue) {
        final Return result = new Return();
        result.add(returnValue);
        return result;
    }

    public boolean add(final ReturnValue returnValue) {
        if (isNull(returnValue)) {
            return false;
        }
        return this.returnList.add(returnValue);
    }

    public List<ReturnValue> getReturnList() {
        return unmodifiableList(returnList);
    }


    public ReturnValue getReturnValue(final int index) {
        return this.getAt(index).orElse(null);
    }

    public Optional<ReturnValue> getAt(final int index) {
        if (index >= returnList.size()) {
            return empty();
        }
        return of(returnList.get(index));
    }


    public int size() {
        return returnList.size();
    }

    public boolean isPresent() {
        return arePresent();
    }

    public boolean arePresent() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return returnList.isEmpty();
    }
}
