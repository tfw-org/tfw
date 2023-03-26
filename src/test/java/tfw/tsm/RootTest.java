package tfw.tsm;

import junit.framework.TestCase;
import tfw.tsm.Root;

/**
 * 
 */
public class RootTest extends TestCase {
	public void testIsRooted(){
		Root root = new Root("test");
		assertTrue("isRooted() returned false", root.isRooted());
	}
}
