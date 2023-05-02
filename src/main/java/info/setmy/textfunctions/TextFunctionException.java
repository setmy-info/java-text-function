package info.setmy.textfunctions;

public class TextFunctionException extends RuntimeException {

    public TextFunctionException() {
    }

    public TextFunctionException(final String message) {
        super(message);
    }

    public TextFunctionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TextFunctionException(final Throwable cause) {
        super(cause);
    }

    public TextFunctionException(
        final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
