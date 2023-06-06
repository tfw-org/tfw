package tfw.tsm;

final class RollbackException extends RuntimeException {
    public RollbackException() {
        super("Exception thrown outside of transaction");
    }
}
