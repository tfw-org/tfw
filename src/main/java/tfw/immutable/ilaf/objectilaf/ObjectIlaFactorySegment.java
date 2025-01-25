package tfw.immutable.ilaf.objectilaf;

import tfw.check.Argument;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaSegment;

public class ObjectIlaFactorySegment {
    private ObjectIlaFactorySegment() {}

    public static <T> ObjectIlaFactory<T> create(ObjectIlaFactory<T> factory, final long start, final long length) {
        return new ObjectIlaFactoryImpl<>(factory, start, length);
    }

    private static class ObjectIlaFactoryImpl<T> implements ObjectIlaFactory<T> {
        private final ObjectIlaFactory<T> factory;
        private final long start;
        private final long length;

        public ObjectIlaFactoryImpl(final ObjectIlaFactory<T> factory, final long start, final long length) {
            Argument.assertNotNull(factory, "factory");

            this.factory = factory;
            this.start = start;
            this.length = length;
        }

        @Override
        public ObjectIla<T> create() {
            return ObjectIlaSegment.create(factory.create(), start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
