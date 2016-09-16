package by.bsu.dektiarev.exception;

/**
 * Created by USER on 16.09.2016.
 */
    public class CommandNotFoundException extends ActionExecutionException {

    private static final long serialVersionUID = 526887225833217984L;

    public CommandNotFoundException() {
    }

    public CommandNotFoundException(String message) {
        super(message);
    }

    public CommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandNotFoundException(Throwable cause) {
        super(cause);
    }
}
