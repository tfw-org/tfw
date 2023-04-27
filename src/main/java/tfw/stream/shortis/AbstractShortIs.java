package tfw.stream.shortis;

import java.io.IOException;
import tfw.check.Argument;

/**
 * This class is an optional abstract class that implements ShortIs and provides
 * some common parameter validation.
 */
public abstract class AbstractShortIs implements ShortIs {
	/**
	 * This method replaces the actual read method and is called after the parameter validation.
	 * 
	 * @param array that will hold the shorts.
	 * @param offset into the array to put the first short.
	 * @param length of shorts to put into the array.
	 * @return the number of shorts put into the array.
	 * @throws IOException if something happens while trying to read the shorts.
	 */
	protected abstract int readImpl(short[] array, int offset, int length) throws IOException;
	
	@Override
	public final int read(final short[] array, final int offset, final int length) throws IOException {
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
