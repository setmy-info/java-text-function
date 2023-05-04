package info.setmy.textfunctions.clojure;

import clojure.lang.RT;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Builder
@RequiredArgsConstructor
public class ClojureScript {

    @Default
    private final String namespaceName = "global";

    private final String scriptFileName;

    @Default
    private final String entryPoint = "-main";

    @Default
    private final String[] arguments = {};

    void exec() {
        log.debug("Execute clojure");
        try {
            RT.loadResourceScript(scriptFileName);
            RT.var(namespaceName, entryPoint).invoke(arguments);
        } catch (Exception e) {
            log.error("Error with script '{}'.'{}'", namespaceName, scriptFileName);
            throw new ClojureException(e);
        }
    }
}
