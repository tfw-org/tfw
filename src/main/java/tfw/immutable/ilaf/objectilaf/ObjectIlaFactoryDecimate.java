package tfw.immutable.ilaf.objectilaf;

import tfw.check.Argument;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaDecimate;

public class ObjectIlaFactoryDecimate {
    private ObjectIlaFactoryDecimate() {}

    public static <T> ObjectIlaFactory<T> create(ObjectIlaFactory<T> ilaFactory, long factor, T[] buffer) {
        return new ObjectIlaFactoryImpl<>(ilaFactory, factor, buffer);
    }

    private static class ObjectIlaFactoryImpl<T> implements ObjectIlaFactory<T> {
        private final ObjectIlaFactory<T> ilaFactory;
        private final long factor;
        private final T[] buffer;

        public ObjectIlaFactoryImpl(final ObjectIlaFactory<T> ilaFactory, long factor, T[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        public ObjectIla<T> create() {
            return ObjectIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
