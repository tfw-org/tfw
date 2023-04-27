package tfw.stream.bitis;

import java.io.IOException;
import tfw.check.Argument;

/**
 * This class is an optional abstract class that implements BitIs and provides
 * some common parameter validation.
 */
public abstract class AbstractBitIs implements BitIs {
	/**
	 * This method replaces the actual read method and is called after the parameter validation.
	 * 
	 * @param array that will hold the bits.
	 * @param offsetInBits into the array to put the first bit.
	 * @param lengthInBits of bits to put into the array.
	 * @return the number of bits put into the array.
	 * @throws IOException if something happens while trying to read the bits.
	 */
	protected abstract int readImpl(long[] array, int offsetInBits, int lengthInBits) throws IOException;
	
	@Override
	public final int read(final long[] array, final int offsetInBits, final int lengthInBits) throws IOException {
		Argument.assertNotNull(array, "array");
		Argument.assertNotLessThan(offsetInBits, 0, "offset");
		Argument.assertNotLessThan(lengthInBits, 0, "length");
		Argument.assertNotGreaterThan(lengthInBits, array.length - offsetInBits, "length", "array.length - offset");
		
		if (lengthInBits == 0) {
			return 0;
		}
		
		return readImpl(array, offsetInBits, lengthInBits);
	}
}
