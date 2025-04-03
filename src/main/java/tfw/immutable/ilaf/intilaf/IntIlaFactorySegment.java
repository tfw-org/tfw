package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIlaSegment;

public class IntIlaFactorySegment {
    private IntIlaFactorySegment() {}

    public static IntIlaFactory create(IntIlaFactory factory, final long start, final long length) {
        Argument.assertNotNull(factory, "factory");

        return () -> IntIlaSegment.create(factory.create(), start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
