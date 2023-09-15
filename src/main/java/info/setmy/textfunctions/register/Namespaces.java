package info.setmy.textfunctions.register;

import info.setmy.textfunctions.LambdaReturn;
import info.setmy.textfunctions.functions.Return;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public class Namespaces {

    public static final String GLOBAL = "global";

    private final Map<String, Namespace> namespaces = new ConcurrentHashMap<>();

    private final Namespace global = new Namespace(GLOBAL);

    private Namespace defaultNamespace;

    public Namespaces() {
        namespaces.put(GLOBAL, global);
        defaultNamespace = global;
    }

    public void setNamespace(final String namespaceName) {
        defaultNamespace = onDemandCreateAndGet(namespaceName).orElse(global);
    }

    public void declare(final DataTypeRegistration dataTypeRegistration) {
        onDemandCreateAndGet(dataTypeRegistration.getOptionalNamespaceName().orElse(defaultNamespace.getName()))
            .ifPresent(namespace -> namespace.register(dataTypeRegistration));
    }

    public void declare(final FunctionDeclaration functionDeclaration) {
        onDemandCreateAndGet(
            functionDeclaration.getOptionalNamespaceName().orElse(defaultNamespace.getName())
        ).ifPresent(
            namespace -> namespace.register(functionDeclaration)
        );
    }

    public Return call(final String functionText) {
        return call(defaultNamespace.getName(), functionText);
    }

    public Return call(final String namespaceName, final String functionText) {
        final LambdaReturn<Return> lambdaReturn = new LambdaReturn<>(new Return());
        onDemandCreateAndGet(namespaceName).ifPresent(
            namespace -> lambdaReturn.setValue(
                namespace.call(functionText)
            )
        );
        return lambdaReturn.getValue().get();
    }

    private Optional<Namespace> onDemandCreateAndGet(final String name) {
        final Optional<Namespace> optionalNamespace = get(name);
        if (optionalNamespace.isPresent()) {
            return optionalNamespace;
        }
        namespaces.put(name, new Namespace(name));
        return get(name);
    }

    private Optional<Namespace> get(final String name) {
        if (isNull(name)) {
            return of(defaultNamespace);
        }
        return ofNullable(namespaces.get(name));
    }
}
