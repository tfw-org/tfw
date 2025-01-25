package tfw.immutable.ilaf.objectilaf;

import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;

public class ObjectIlaFactoryFromArray {
    private ObjectIlaFactoryFromArray() {}

    public static <T> ObjectIlaFactory<T> create(final T[] array) {
        return new ObjectIlaFactoryImpl<>(array);
    }

    private static class ObjectIlaFactoryImpl<T> implements ObjectIlaFactory<T> {
        private final T[] array;

        public ObjectIlaFactoryImpl(final T[] array) {
            this.array = array;
        }

        @Override
        public ObjectIla<T> create() {
            return ObjectIlaFromArray.create(array);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
