package tfw.immutable.ilaf.objectilaf;

import tfw.check.Argument;
import tfw.immutable.ila.objectila.ObjectIlaSegment;

public class ObjectIlaFactorySegment {
    private ObjectIlaFactorySegment() {}

    public static <T> ObjectIlaFactory<T> create(ObjectIlaFactory<T> factory, final long start, final long length) {
        Argument.assertNotNull(factory, "factory");

        return () -> ObjectIlaSegment.create(factory.create(), start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
