package tfw.tsm;

import java.util.concurrent.Future;

class TransactionStateImpl implements TransactionState
{
	private final CdlFuture<Throwable> resultFuture;
	private final CdlFuture<Long> transactionIdFuture;
	
	public TransactionStateImpl()
	{
		resultFuture = new CdlFuture<Throwable>();
		transactionIdFuture = new CdlFuture<Long>();
	}
	
	@Override
	public Future<?> getResultFuture()
	{
		return(resultFuture);
	}

	@Override
	public Future<Long> getTransactionIdFuture()
	{
		return(transactionIdFuture);
	}
	
	CdlFuture<Throwable> getCdlResultFuture()
	{
		return(resultFuture);
	}
	
	CdlFuture<Long> getCdlTransactionIdFuture()
	{
		return(transactionIdFuture);
	}
}