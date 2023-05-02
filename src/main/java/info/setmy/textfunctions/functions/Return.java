package info.setmy.textfunctions.functions;

import info.setmy.textfunctions.LambdaReturn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.stream;

public class Return {

    private final List<ReturnValue> returnList = new ArrayList<>();

    public static Return newReturn() {
        return new Return();
    }

    public static Return newReturn(final Object... objects) {
        final Return result = new Return();
        final LambdaReturn<Integer> i = new LambdaReturn<>(0);
        stream(objects)
            .forEach(object -> {
                int iValue = i.getValue().get();
                result.returnList.add(new ReturnValue("return-" + iValue, object));
                iValue++;
                i.setValue(iValue);
            });
        return result;
    }

    public List<ReturnValue> getReturnList() {
        return Collections.unmodifiableList(returnList);
    }

    public ReturnValue get(final int index) {
        return returnList.get(index);
    }

    public boolean isPresent() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return returnList.isEmpty();
    }
}
