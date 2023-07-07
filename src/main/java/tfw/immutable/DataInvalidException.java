package tfw.immutable;

public class DataInvalidException extends Exception {
    private static final long serialVersionUID = 1L;

    public DataInvalidException(String s) {
        super(s);
    }

    public DataInvalidException(String s, Throwable cause) {
        super(s, cause);
    }
}
