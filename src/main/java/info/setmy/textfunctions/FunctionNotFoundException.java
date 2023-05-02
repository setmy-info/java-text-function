package info.setmy.textfunctions;

public class FunctionNotFoundException extends TextFunctionException {

    public FunctionNotFoundException() {
    }

    public FunctionNotFoundException(final String message) {
        super(message);
    }

    public FunctionNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FunctionNotFoundException(final Throwable cause) {
        super(cause);
    }

    public FunctionNotFoundException(
        final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
