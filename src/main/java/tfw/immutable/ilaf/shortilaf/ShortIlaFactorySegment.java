package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public class ShortIlaFactorySegment {
    private ShortIlaFactorySegment() {}

    public static ShortIlaFactory create(ShortIlaFactory factory, final long start, final long length) {
        Argument.assertNotNull(factory, "factory");

        return () -> ShortIlaSegment.create(factory.create(), start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
