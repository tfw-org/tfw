package tfw.tsm;

public class DeferredTransactionState implements TransactionState {
	public DeferredTransactionState() {
		// Not sure why this class does nothing.
	}
	
	@Override
	public TfwFuture<Throwable> getResultFuture() {
		return null;
	}

	@Override
	public TfwFuture<Long> getTransactionIdFuture() {
		return null;
	}
}
