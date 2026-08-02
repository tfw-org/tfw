package tfw.test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ThreadSafeTestHandler extends Handler {
    // CopyOnWriteArrayList is ideal for scenarios with frequent reads (assertions)
    // and relatively infrequent writes (log events), providing snapshot isolation.
    private final List<LogRecord> records = new CopyOnWriteArrayList<>();

    @Override
    public void publish(LogRecord record) {
        if (isLoggable(record)) {
            records.add(record);
        }
    }

    @Override
    public void flush() {}

    @Override
    public void close() {
        records.clear();
    }

    public List<LogRecord> getRecords() {
        // Returns a safe snapshot; iteration will not throw ConcurrentModificationException
        return records;
    }
}
