package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaDecimate;

public class FloatIlaFactoryDecimate {
    private FloatIlaFactoryDecimate() {}

    public static FloatIlaFactory create(FloatIlaFactory ilaFactory, long factor, float[] buffer) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> FloatIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
    }
}
// AUTO GENERATED FROM TEMPLATE
