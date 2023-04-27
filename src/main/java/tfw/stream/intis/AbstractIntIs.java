package tfw.stream.intis;

import java.io.IOException;
import tfw.check.Argument;

/**
 * This class is an optional abstract class that implements IntIs and provides
 * some common parameter validation.
 */
public abstract class AbstractIntIs implements IntIs {
	/**
	 * This method replaces the actual read method and is called after the parameter validation.
	 * 
	 * @param array that will hold the ints.
	 * @param offset into the array to put the first int.
	 * @param length of ints to put into the array.
	 * @return the number of ints put into the array.
	 * @throws IOException if something happens while trying to read the ints.
	 */
	protected abstract int readImpl(int[] array, int offset, int length) throws IOException;
	
	@Override
	public final int read(final int[] array, final int offset, final int length) throws IOException {
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
