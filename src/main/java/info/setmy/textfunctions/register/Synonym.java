package info.setmy.textfunctions.register;

import info.setmy.textfunctions.KeyValue;

import java.util.List;

public class Synonym extends KeyValue<String, List<String>> {

    public Synonym(final String key, final List<String> value) {
        super(key, value);
    }

    public boolean containsKey(final String key) {
        return getValue().contains(key);
    }
}
