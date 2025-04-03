package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;

public class DoubleIlaFactorySegment {
    private DoubleIlaFactorySegment() {}

    public static DoubleIlaFactory create(DoubleIlaFactory factory, final long start, final long length) {
        Argument.assertNotNull(factory, "factory");

        return () -> DoubleIlaSegment.create(factory.create(), start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
