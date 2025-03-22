package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIlaFromCastFloatIla;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;

public class CharIlaFactoryFromCastFloatIlaFactory {
    private CharIlaFactoryFromCastFloatIlaFactory() {}

    public static CharIlaFactory create(FloatIlaFactory floatIlaFactory, final int bufferSize) {
        Argument.assertNotNull(floatIlaFactory, "floatIlaFactory");

        return () -> CharIlaFromCastFloatIla.create(floatIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
