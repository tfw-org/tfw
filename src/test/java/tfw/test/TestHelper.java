package tfw.test;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestHelper {
    public static ThreadSafeTestHandler beforeEach(final Logger logger) {
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        ThreadSafeTestHandler testHandler = new ThreadSafeTestHandler();
        logger.addHandler(testHandler);

        return testHandler;
    }

    public static void afterEach(final Logger logger, final Handler handler) {
        if (logger != null && handler != null) {
            logger.removeHandler(handler);
            handler.close();
        }
    }
}
