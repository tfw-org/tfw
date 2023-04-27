package tfw.stream.byteis;

import java.io.IOException;
import tfw.check.Argument;

/**
 * This class is an optional abstract class that implements ByteIs and provides
 * some common parameter validation.
 */
public abstract class AbstractByteIs implements ByteIs {
	/**
	 * This method replaces the actual read method and is called after the parameter validation.
	 * 
	 * @param array that will hold the bytes.
	 * @param offset into the array to put the first byte.
	 * @param length of bytes to put into the array.
	 * @return the number of bytes put into the array.
	 * @throws IOException if something happens while trying to read the bytes.
	 */
	protected abstract int readImpl(byte[] array, int offset, int length) throws IOException;
	
	@Override
	public final int read(final byte[] array, final int offset, final int length) throws IOException {
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
