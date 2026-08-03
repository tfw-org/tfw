package tfw.test;

import java.util.Comparator;
import java.util.logging.LogRecord;

/**
 * Compares LogRecord instances based solely on their log level and message.
 * All other fields (timestamp, thread ID, source class, etc.) are ignored.
 */
public class LogRecordComparator implements Comparator<LogRecord> {

    @Override
    public int compare(LogRecord r1, LogRecord r2) {
        if (r1 == r2) {
            return 0;
        }
        if (r1 == null) {
            return -1;
        }
        if (r2 == null) {
            return 1;
        }

        // Compare Level
        int levelCompare =
                Integer.compare(r1.getLevel().intValue(), r2.getLevel().intValue());
        if (levelCompare != 0) {
            return levelCompare;
        }

        // Compare Message (handle nulls)
        String msg1 = r1.getMessage();
        String msg2 = r2.getMessage();

        if (msg1 == null && msg2 == null) {
            return 0;
        }
        if (msg1 == null) {
            return -1;
        }
        if (msg2 == null) {
            return 1;
        }

        return msg1.compareTo(msg2);
    }
}
