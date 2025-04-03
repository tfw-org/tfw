package tfw.immutable.ilaf.booleanilaf;

import tfw.check.Argument;
import tfw.immutable.ila.booleanila.BooleanIlaSegment;

public class BooleanIlaFactorySegment {
    private BooleanIlaFactorySegment() {}

    public static BooleanIlaFactory create(BooleanIlaFactory factory, final long start, final long length) {
        Argument.assertNotNull(factory, "factory");

        return () -> BooleanIlaSegment.create(factory.create(), start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
