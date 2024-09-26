package tfw.tsm;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TsmFuture<T> implements Future<T> {
    private boolean done = false;
    private T result = null;

    @Override
    public synchronized T get() throws InterruptedException {
        if (!done) {
            wait();
        }

        return result;
    }

    @Override
    public synchronized T get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        if (!done) {
            wait(unit.toMillis(timeout));
        }

        return result;
    }

    @Override
    public synchronized boolean isDone() {
        return done;
    }

    synchronized void setResultAndRelease(T result) {
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
