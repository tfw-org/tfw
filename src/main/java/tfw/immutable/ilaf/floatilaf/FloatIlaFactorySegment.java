package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaSegment;

public class FloatIlaFactorySegment {
    private FloatIlaFactorySegment() {}

    public static FloatIlaFactory create(FloatIlaFactory factory, final long start, final long length) {
        Argument.assertNotNull(factory, "factory");

        return () -> FloatIlaSegment.create(factory.create(), start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
