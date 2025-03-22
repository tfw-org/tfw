package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIlaFromCastFloatIla;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;

public class IntIlaFactoryFromCastFloatIlaFactory {
    private IntIlaFactoryFromCastFloatIlaFactory() {}

    public static IntIlaFactory create(FloatIlaFactory floatIlaFactory, final int bufferSize) {
        Argument.assertNotNull(floatIlaFactory, "floatIlaFactory");

        return () -> IntIlaFromCastFloatIla.create(floatIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
