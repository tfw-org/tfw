package tfw.tsm;

import junit.framework.TestCase;

/**
 *
 */
public class RootTest extends TestCase {
    public void testIsRooted() {
        RootFactory rootFactory = new RootFactory();
        Root root = rootFactory.create("test", new BasicTransactionQueue());
        assertTrue("isRooted() returned false", root.isRooted());
    }
}
