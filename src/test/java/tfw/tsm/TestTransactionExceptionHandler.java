package tfw.tsm;

class TestTransactionExceptionHandler implements TransactionExceptionHandler {
    public Exception exp;

    @Override
    public void handle(Exception exp) {
        this.exp = exp;
    }
}
