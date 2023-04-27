package tfw.stream.doubleis;

import java.io.IOException;
import tfw.check.Argument;

/**
 * This class is an optional abstract class that implements DoubleIs and provides
 * some common parameter validation.
 */
public abstract class AbstractDoubleIs implements DoubleIs {
	/**
	 * This method replaces the actual read method and is called after the parameter validation.
	 * 
	 * @param array that will hold the doubles.
	 * @param offset into the array to put the first double.
	 * @param length of doubles to put into the array.
	 * @return the number of doubles put into the array.
	 * @throws IOException if something happens while trying to read the doubles.
	 */
	protected abstract int readImpl(double[] array, int offset, int length) throws IOException;
	
	@Override
	public final int read(final double[] array, final int offset, final int length) throws IOException {
		Argument.assertNotNull(array, "array");
		Argument.assertNotLessThan(offset, 0, "offset");
		Argument.assertNotLessThan(length, 0, "length");
		Argument.assertNotGreaterThan(length, array.length - offset, "length", "array.length - offset");
		
		if (length == 0) {
			return 0;
		}
		
		return readImpl(array, offset, length);
	}
}
