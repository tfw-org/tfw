package tfw.tsm;

final class RollbackException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RollbackException() {
        super("Exception thrown outside of transaction");
    }
}
