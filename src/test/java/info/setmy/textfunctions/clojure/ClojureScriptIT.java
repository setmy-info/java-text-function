package info.setmy.textfunctions.clojure;

import org.junit.jupiter.api.Test;

public class ClojureScriptIT {

    ClojureScript clojureScript;

    @Test
    void exec() {
        Init.init();
        clojureScript = ClojureScript.builder()
            .namespaceName("global.test")
            .scriptFileName("info/setmy/global/test.clj")
            .entryPoint("-main")
            .arguments(new String[]{"Imre", "Peeter"})
            .build();
        clojureScript.exec();
    }
}
