package tfw.stream.objectis;

import java.io.Closeable;
import java.io.IOException;

/**
 * This interface defines a Object Input Stream (ObjectIs).
 */
public interface ObjectIs<T> extends Closeable
{
	/**
	 * This method is analogous to InputStream.read(byte[], int, int).
	 * 
	 * @param array to put the objects.
	 * @param offset into the array to put the first object.
	 * @param length the number of objects to put into the array.
	 * @return the number of objects read or -1 if at end of stream.
	 * @throws IOException if there is a problem reading the objects.
	 */
	int read(T[] array, int offset, int length) throws IOException;
	
	/**
	 * This method is analogous to InputStream.skip(long).
	 * 
	 * @param n the number of objects to skip.
	 * @return the number of objects skipped or -1 if at end of stream.
	 * @throws IOException if there is a problem skipping the objects.
	 */
	long skip(long n) throws IOException;
}
