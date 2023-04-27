package tfw.stream.objectis;

/**
 * This interface defines a factory that creates ObjectIs objects.
 */
public interface ObjectIsFactory<T> {
	/**
	 * Create a ObjectIs object.
	 * 
	 * @return the ObjectIs object.
	 */
	ObjectIs<T> create();
}
