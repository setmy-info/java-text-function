package info.setmy.textfunctions.clojure;

import info.setmy.textfunctions.TextFunctionException;

public class ClojureException extends TextFunctionException {

    ClojureException() {
    }

    ClojureException(final String message) {
        super(message);
    }

    ClojureException(final String message, final Throwable cause) {
        super(message, cause);
    }

    ClojureException(final Throwable cause) {
        super(cause);
    }

    ClojureException(
        final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
