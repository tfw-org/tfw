package tfw.immutable.ilaf.floatilaf;

import tfw.immutable.ila.floatila.FloatIlaFill;

public class FloatIlaFactoryFill {
    private FloatIlaFactoryFill() {}

    public static FloatIlaFactory create(final float value, final long length) {
        return () -> FloatIlaFill.create(value, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
