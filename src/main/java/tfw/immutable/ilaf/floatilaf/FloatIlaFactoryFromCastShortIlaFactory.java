package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaFromCastShortIla;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;

public class FloatIlaFactoryFromCastShortIlaFactory {
    private FloatIlaFactoryFromCastShortIlaFactory() {}

    public static FloatIlaFactory create(ShortIlaFactory shortIlaFactory, final int bufferSize) {
        Argument.assertNotNull(shortIlaFactory, "shortIlaFactory");

        return () -> FloatIlaFromCastShortIla.create(shortIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
