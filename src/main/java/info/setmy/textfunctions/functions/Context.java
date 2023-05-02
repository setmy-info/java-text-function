package info.setmy.textfunctions.functions;

import info.setmy.textfunctions.register.Namespace;

public class Context {

    private final Namespace namespace;

    public final Parameters parameters;

    public Context(final Namespace namespace, final Parameters parameters) {
        this.namespace = namespace;
        this.parameters = parameters;
    }

    public Parameters getParameters() {
        return parameters;
    }
}
