package info.setmy.textfunctions.clojure;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static lombok.AccessLevel.PRIVATE;

@Log4j2
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor"})
@NoArgsConstructor(access = PRIVATE)
public final class Init {

    public static void init() {
        log.info("Initializing Clojure");
        final ClojureScript clojureScript = ClojureScript.builder()
            .namespaceName("global.init")
            .scriptFileName("info/setmy/global/init.clj")
            .entryPoint("-init-entry")
            .arguments(new String[]{})
            .build();
        clojureScript.exec();
    }
}
