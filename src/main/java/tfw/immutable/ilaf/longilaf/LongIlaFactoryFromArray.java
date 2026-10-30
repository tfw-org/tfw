package tfw.immutable.ilaf.longilaf;

import tfw.immutable.ila.longila.LongIlaFromArray;

public class LongIlaFactoryFromArray {
    private LongIlaFactoryFromArray() {}

    /**
     * Creates a factory that produces immutable arrays backed by the specified
     * array.
     *
     * <p>The supplied array is not copied. Ownership of the array is transferred
     * to the returned factory, and the caller must not access or modify the
     * array after this method returns. Every ILA created by the factory uses
     * the same backing array.
     *
     * <p>If the caller needs to retain and use the original array, a clone
     * should be passed instead.
     *
     * @param array the array whose ownership is transferred to the returned factory
     * @return a factory that produces ILAs backed by the supplied array
     */
    public static LongIlaFactory create(final long[] array) {
        return () -> LongIlaFromArray.create(array);
    }
}
// AUTO GENERATED FROM TEMPLATE
