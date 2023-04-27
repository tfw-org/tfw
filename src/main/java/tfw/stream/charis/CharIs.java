package tfw.stream.charis;

import java.io.Closeable;
import java.io.IOException;

/**
 * This interface defines a Char Input Stream (CharIs).
 */
public interface CharIs extends Closeable
{
	/**
	 * This method is analogous to InputStream.read(byte[], int, int).
	 * 
	 * @param array to put the chars.
	 * @param offset into the array to put the first char.
	 * @param length the number of chars to put into the array.
	 * @return the number of chars read or -1 if at end of stream.
	 * @throws IOException if there is a problem reading the chars.
	 */
	int read(char[] array, int offset, int length) throws IOException;
	
	/**
	 * This method is analogous to InputStream.skip(long).
	 * 
	 * @param n the number of chars to skip.
	 * @return the number of chars skipped or -1 if at end of stream.
	 * @throws IOException if there is a problem skipping the chars.
	 */
	long skip(long n) throws IOException;
}
