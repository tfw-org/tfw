package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIlaSegment;

public class LongIlaFactorySegment {
    private LongIlaFactorySegment() {}

    public static LongIlaFactory create(LongIlaFactory factory, final long start, final long length) {
        Argument.assertNotNull(factory, "factory");

        return () -> LongIlaSegment.create(factory.create(), start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
