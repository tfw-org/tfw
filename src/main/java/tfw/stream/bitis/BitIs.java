package tfw.stream.bitis;

import java.io.Closeable;
import java.io.IOException;

/**
 * This interface defines a Bit Input Stream (BitIs).
 */
public interface BitIs extends Closeable
{
	/**
	 * This method is analogous to InputStream.read(byte[], int, int).
	 * 
	 * @param array to put the bits.
	 * @param offsetInBits into the array to put the first bit.
	 * @param lengthInBits the number of bits to put into the array.
	 * @return the number of bits read or -1 if at end of stream.
	 * @throws IOException if there is a problem reading the bits.
	 */
	int read(long[] array, int offsetInBits, int lengthInBits) throws IOException;
	
	/**
	 * This method is analogous to InputStream.skip(long).
	 * 
	 * @param nBits the number of bits to skip.
	 * @return the number of bits skipped or -1 if at end of stream.
	 * @throws IOException if there is a problem skipping the bits.
	 */
	long skip(long nBits) throws IOException;
}
