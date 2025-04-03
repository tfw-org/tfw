package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaBound;

public class FloatIlaFactoryBound {
    private FloatIlaFactoryBound() {}

    public static FloatIlaFactory create(FloatIlaFactory ilaFactory, float minimum, float maximum) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> FloatIlaBound.create(ilaFactory.create(), minimum, maximum);
    }
}
// AUTO GENERATED FROM TEMPLATE
