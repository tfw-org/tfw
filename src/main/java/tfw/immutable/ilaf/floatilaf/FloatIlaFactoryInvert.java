package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaInvert;

public class FloatIlaFactoryInvert {
    private FloatIlaFactoryInvert() {}

    public static FloatIlaFactory create(FloatIlaFactory ilaFactory) {
        Argument.assertNotNull(ilaFactory, "ilaFactory");

        return () -> FloatIlaInvert.create(ilaFactory.create());
    }
}
// AUTO GENERATED FROM TEMPLATE
