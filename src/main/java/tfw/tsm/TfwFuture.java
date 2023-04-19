package tfw.tsm;

public interface TfwFuture<T> {
	T get() throws Exception;
	T get(long timeoutInMillis) throws Exception;
	boolean isDone();
	boolean cancel(boolean mayInterruptIfRunning);
	boolean isCancelled();
	void setResultAndRelease(T result);
}
