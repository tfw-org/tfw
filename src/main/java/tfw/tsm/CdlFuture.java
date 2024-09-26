package tfw.tsm;

public class CdlFuture<T> implements TfwFuture<T> {
    private boolean done = false;
    private T result = null;

    @Override
    public synchronized T get() throws Exception {
        if (!done) {
            wait();
        }

        return result;
    }

    @Override
    public synchronized T get(long timeoutMillis) throws Exception {
        if (!done) {
            wait(timeoutMillis);
        }

        return result;
    }

    @Override
    public synchronized boolean isDone() {
        return done;
    }

    @Override
    public synchronized void setResultAndRelease(T result) {
        if (done) {
            throw new IllegalStateException("Result has already been set!");
        }

        this.result = result;
        this.done = true;

        notifyAll();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
