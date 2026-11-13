package tfw.immutable.ilaf.doubleilaf;

import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

public class DoubleIlaFactoryFromArray {
    private DoubleIlaFactoryFromArray() {}

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
    public static DoubleIlaFactory create(final double[] array) {
        return () -> DoubleIlaFromArray.create(array);
    }
}
// AUTO GENERATED FROM TEMPLATE
