package tfw.stream.doubleis;

import java.io.Closeable;
import java.io.IOException;

/**
 * This interface defines a Double Input Stream (DoubleIs).
 */
public interface DoubleIs extends Closeable
{
	/**
	 * This method is analogous to InputStream.read(byte[], int, int).
	 * 
	 * @param array to put the doubles.
	 * @param offset into the array to put the first double.
	 * @param length the number of doubles to put into the array.
	 * @return the number of doubles read or -1 if at end of stream.
	 * @throws IOException if there is a problem reading the doubles.
	 */
	int read(double[] array, int offset, int length) throws IOException;
	
	/**
	 * This method is analogous to InputStream.skip(long).
	 * 
	 * @param n the number of doubles to skip.
	 * @return the number of doubles skipped or -1 if at end of stream.
	 * @throws IOException if there is a problem skipping the doubles.
	 */
	long skip(long n) throws IOException;
}
