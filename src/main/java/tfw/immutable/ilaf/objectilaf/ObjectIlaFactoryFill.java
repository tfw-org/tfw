package tfw.immutable.ilaf.objectilaf;

import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFill;

public class ObjectIlaFactoryFill {
    private ObjectIlaFactoryFill() {}

    public static <T> ObjectIlaFactory<T> create(final T value, final long length) {
        return new ObjectIlaFactoryImpl<>(value, length);
    }

    private static class ObjectIlaFactoryImpl<T> implements ObjectIlaFactory<T> {
        private final T value;
        private final long length;

        public ObjectIlaFactoryImpl(final T value, final long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        public ObjectIla<T> create() {
            return ObjectIlaFill.create(value, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
