package tfw.immutable.ilaf.floatilaf;

import tfw.immutable.ila.floatila.FloatIlaFromArray;

public class FloatIlaFactoryFromArray {
    private FloatIlaFactoryFromArray() {}

    public static FloatIlaFactory create(final float[] array) {
        return () -> FloatIlaFromArray.create(array);
    }
}
// AUTO GENERATED FROM TEMPLATE
