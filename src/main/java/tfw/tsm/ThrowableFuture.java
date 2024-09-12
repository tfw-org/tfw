package tfw.tsm;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class ThrowableFuture implements Future<Object> {
    private final Future<Throwable> baseFuture;

    public ThrowableFuture(Future<Throwable> baseFuture) {
        this.baseFuture = baseFuture;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return baseFuture.cancel(mayInterruptIfRunning);
    }

    public Object get() throws InterruptedException, ExecutionException {
        Throwable result = baseFuture.get();

        if (result != null) {
            throw new ExecutionException(result);
        }

        return null;
    }

    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        Throwable result = baseFuture.get(timeout, unit);

        if (result != null) {
            throw new ExecutionException(result);
        }

        return null;
    }

    public boolean isCancelled() {
        return baseFuture.isCancelled();
    }

    public boolean isDone() {
        return baseFuture.isDone();
    }
}
