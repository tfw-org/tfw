package tfw.immutable.ilm.doubleilm;

import java.util.Arrays;
import junit.framework.TestCase;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaRamp;

public class TakeSkipDoubleIlmTest extends TestCase {
	public void testTakeSkipDoubleIlm() throws Exception {
		DoubleIla doubleIla = DoubleIlaRamp.create(-10.0, 1.0, 20);
		double[] doubleArray = doubleIla.toArray();
		
		DoubleIlm takeSkip1 = TakeSkipDoubleIlm.create(doubleIla, 10, 10);
		double[] takeSkip1Array = takeSkip1.toArray();
		assertTrue(Arrays.equals(doubleArray, takeSkip1Array));
		assertEquals(takeSkip1.width(), 10);
		assertEquals(takeSkip1.height(), 2);
		
		DoubleIlm takeSkip2 = TakeSkipDoubleIlm.create(doubleIla, 4, 5);
		double[] takeSkip2Array = new double[16];
		System.arraycopy(doubleArray, 0, takeSkip2Array,  0,  4);
		System.arraycopy(doubleArray, 5, takeSkip2Array,  4,  4);
		System.arraycopy(doubleArray, 10, takeSkip2Array,  8,  4);
		System.arraycopy(doubleArray, 15, takeSkip2Array,  12,  4);
		assertTrue(Arrays.equals(takeSkip2Array, takeSkip2.toArray()));
		assertEquals(takeSkip2.width(), 4);
		assertEquals(takeSkip2.height(), 4);
		
		DoubleIlm takeSkip3 = TakeSkipDoubleIlm.create(doubleIla, 10, 5);
		double[] takeSkip3Array = new double[30];
		System.arraycopy(doubleArray, 0, takeSkip3Array,  0, 10);
		System.arraycopy(doubleArray, 5, takeSkip3Array,  10, 10);
		System.arraycopy(doubleArray, 10, takeSkip3Array,  20, 10);
		assertTrue(Arrays.equals(takeSkip3Array, takeSkip3.toArray()));
		assertEquals(takeSkip3.width(), 10);
		assertEquals(takeSkip3.height(), 3);
		
		DoubleIlm takeSkip4 = TakeSkipDoubleIlm.create(doubleIla, 10, 5);
		double[] takeSkip4Array = new double[9];
		System.arraycopy(doubleArray, 1, takeSkip4Array,  0,  3);
		System.arraycopy(doubleArray, 6, takeSkip4Array,  3,  3);
		System.arraycopy(doubleArray, 11, takeSkip4Array,  6,  3);
		assertTrue(Arrays.equals(takeSkip4Array, takeSkip4.toArray(0, 1, 3, 3)));
		assertEquals(takeSkip4.width(), 10);
		assertEquals(takeSkip4.height(), 3);
		
		DoubleIlm takeSkip5 = TakeSkipDoubleIlm.create(doubleIla, 10, 5);
		double[] takeSkip5Array = new double[] {
				0.0,  0.0, 0.0,  0.0, 0.0,  0.0,
				0.0, -9.0, 0.0, -8.0, 0.0, -7.0,
				0.0,  0.0, 0.0,  0.0, 0.0,  0.0,
				0.0, -4.0, 0.0, -3.0, 0.0, -2.0,
				0.0,  0.0, 0.0,  0.0, 0.0,  0.0,
				0.0,  1.0, 0.0,  2.0, 0.0,  3.0};
		double[] takeSkip5Test = new double[36];
		takeSkip5.toArray(takeSkip5Test, 7, 12, 2, 0, 1, 3, 3);
		assertTrue(Arrays.equals(takeSkip5Array, takeSkip5Test));
		assertEquals(takeSkip5.width(), 10);
		assertEquals(takeSkip5.height(), 3);
	}
}
