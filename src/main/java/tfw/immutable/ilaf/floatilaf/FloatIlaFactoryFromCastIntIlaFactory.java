package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaFromCastIntIla;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;

public class FloatIlaFactoryFromCastIntIlaFactory {
    private FloatIlaFactoryFromCastIntIlaFactory() {}

    public static FloatIlaFactory create(IntIlaFactory intIlaFactory, final int bufferSize) {
        Argument.assertNotNull(intIlaFactory, "intIlaFactory");

        return () -> FloatIlaFromCastIntIla.create(intIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
