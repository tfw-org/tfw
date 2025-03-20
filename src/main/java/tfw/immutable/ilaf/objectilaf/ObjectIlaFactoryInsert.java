package tfw.immutable.ilaf.objectilaf;

import tfw.check.Argument;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaInsert;

public class ObjectIlaFactoryInsert {
    private ObjectIlaFactoryInsert() {}

    public static <T> ObjectIlaFactory<T> create(ObjectIlaFactory<T> ilaFactory, long index, T value) {
        return new ObjectIlaFactoryImpl<>(ilaFactory, index, value);
    }

    private static class ObjectIlaFactoryImpl<T> implements ObjectIlaFactory<T> {
        private final ObjectIlaFactory<T> ilaFactory;
        private final long index;
        private final T value;

        public ObjectIlaFactoryImpl(final ObjectIlaFactory<T> ilaFactory, long index, T value) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.index = index;
            this.value = value;
        }

        @Override
        public ObjectIla<T> create() {
            return ObjectIlaInsert.create(ilaFactory.create(), index, value);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
