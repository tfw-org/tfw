package tfw.immutable.ilaf.objectilaf;

import tfw.check.Argument;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFiltered;
import tfw.immutable.ila.objectila.ObjectIlaFiltered.ObjectFilter;

public class ObjectIlaFactoryFiltered {
    private ObjectIlaFactoryFiltered() {}

    public static <T> ObjectIlaFactory<T> create(ObjectIlaFactory<T> ilaFactory, ObjectFilter<T> filter, T[] buffer) {
        return new ObjectIlaFactoryImpl<>(ilaFactory, filter, buffer);
    }

    private static class ObjectIlaFactoryImpl<T> implements ObjectIlaFactory<T> {
        private final ObjectIlaFactory<T> ilaFactory;
        private final ObjectFilter<T> filter;
        private final T[] buffer;

        public ObjectIlaFactoryImpl(final ObjectIlaFactory<T> ilaFactory, ObjectFilter<T> filter, T[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        public ObjectIla<T> create() {
            return ObjectIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
