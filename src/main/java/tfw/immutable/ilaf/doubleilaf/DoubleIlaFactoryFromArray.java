package tfw.immutable.ilaf.doubleilaf;

import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

public class DoubleIlaFactoryFromArray {
    private DoubleIlaFactoryFromArray() {}

    public static DoubleIlaFactory create(final double[] array) {
        return () -> DoubleIlaFromArray.create(array);
    }
}
// AUTO GENERATED FROM TEMPLATE
