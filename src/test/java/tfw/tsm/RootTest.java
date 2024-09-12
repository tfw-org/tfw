package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 *
 */
class RootTest {
    @Test
    void testIsRooted() {
        RootFactory rootFactory = new RootFactory();
        Root root = rootFactory.create("test", new BasicTransactionQueue());
        assertTrue(root.isRooted(), "isRooted() returned false");
    }
}
