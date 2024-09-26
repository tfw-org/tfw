package tfw.tsm;

/**
 * A Transaction exception handler for testing.
 */
class TestTransactionExceptionHandler implements TransactionExceptionHandler {
    public Exception exp;

    @Override
    public void handle(Exception exp) {
        this.exp = exp;
        // exp.printStackTrace();
    }
}
