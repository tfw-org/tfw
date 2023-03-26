package tfw.tsm;

import tfw.tsm.TransactionExceptionHandler;

/**
 * A Transaction exception handler for testing.
 */
class TestTransactionExceptionHandler implements
        TransactionExceptionHandler
{
    public Exception exp;

    public void handle(Exception exp)
    {
        this.exp = exp;
        //exp.printStackTrace();
    }
}