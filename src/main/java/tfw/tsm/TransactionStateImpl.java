package tfw.tsm;

public class TransactionStateImpl implements TransactionState {
    private final CdlFuture<Throwable> resultFuture;
    private final CdlFuture<Long> transactionIdFuture;

    public TransactionStateImpl() {
        resultFuture = new CdlFuture<Throwable>();
        transactionIdFuture = new CdlFuture<Long>();
    }

    @Override
    public TfwFuture<Throwable> getResultFuture() {
        return resultFuture;
    }

    @Override
    public TfwFuture<Long> getTransactionIdFuture() {
        return transactionIdFuture;
    }
}
