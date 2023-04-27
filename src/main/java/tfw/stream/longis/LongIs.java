package tfw.stream.longis;

import java.io.Closeable;
import java.io.IOException;

/**
 * This interface defines a Long Input Stream (LongIs).
 */
public interface LongIs extends Closeable
{
	/**
	 * This method is analogous to InputStream.read(byte[], int, int).
	 * 
	 * @param array to put the longs.
	 * @param offset into the array to put the first long.
	 * @param length the number of longs to put into the array.
	 * @return the number of longs read or -1 if at end of stream.
	 * @throws IOException if there is a problem reading the longs.
	 */
	int read(long[] array, int offset, int length) throws IOException;
	
	/**
	 * This method is analogous to InputStream.skip(long).
	 * 
	 * @param n the number of longs to skip.
	 * @return the number of longs skipped or -1 if at end of stream.
	 * @throws IOException if there is a problem skipping the longs.
	 */
	long skip(long n) throws IOException;
}
