package by.bsu.dektiarev.exception;

import java.security.PrivilegedActionException;

/**
 * Created by USER on 15.06.2016.
 */
public class DatabaseConnectionException extends Exception {
    private static final long serialVersionUID = -8290132673672541702L;

    /**
     * Constructs a new by.bsu.dektiarev.exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public DatabaseConnectionException() {
    }

    /**
     * Constructs a new by.bsu.dektiarev.exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public DatabaseConnectionException(String message) {
        super(message);
    }

    /**
     * Constructs a new by.bsu.dektiarev.exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this by.bsu.dektiarev.exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new by.bsu.dektiarev.exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public DatabaseConnectionException(Throwable cause) {
        super(cause);
    }
}
