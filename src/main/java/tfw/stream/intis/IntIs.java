package tfw.stream.intis;

import java.io.Closeable;
import java.io.IOException;

/**
 * This interface defines a Int Input Stream (IntIs).
 */
public interface IntIs extends Closeable
{
	/**
	 * This method is analogous to InputStream.read(byte[], int, int).
	 * 
	 * @param array to put the ints.
	 * @param offset into the array to put the first int.
	 * @param length the number of ints to put into the array.
	 * @return the number of ints read or -1 if at end of stream.
	 * @throws IOException if there is a problem reading the ints.
	 */
	int read(int[] array, int offset, int length) throws IOException;
	
	/**
	 * This method is analogous to InputStream.skip(long).
	 * 
	 * @param n the number of ints to skip.
	 * @return the number of ints skipped or -1 if at end of stream.
	 * @throws IOException if there is a problem skipping the ints.
	 */
	long skip(long n) throws IOException;
}
