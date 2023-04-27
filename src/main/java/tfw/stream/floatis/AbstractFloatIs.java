package tfw.stream.floatis;

import java.io.IOException;
import tfw.check.Argument;

/**
 * This class is an optional abstract class that implements FloatIs and provides
 * some common parameter validation.
 */
public abstract class AbstractFloatIs implements FloatIs {
	/**
	 * This method replaces the actual read method and is called after the parameter validation.
	 * 
	 * @param array that will hold the floats.
	 * @param offset into the array to put the first float.
	 * @param length of floats to put into the array.
	 * @return the number of floats put into the array.
	 * @throws IOException if something happens while trying to read the floats.
	 */
	protected abstract int readImpl(float[] array, int offset, int length) throws IOException;
	
	@Override
	public final int read(final float[] array, final int offset, final int length) throws IOException {
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
