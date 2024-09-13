package tfw.immutable.iis.objectiis;

import tfw.immutable.ila.objectila.ObjectIla;

public final class ObjectIisFactoryFromObjectIla {
    public static <T> ObjectIisFactory<T> create(final ObjectIla<T> ila) {
        return new ObjectIisFactoryImpl<>(ila);
    }

    private static class ObjectIisFactoryImpl<T> implements ObjectIisFactory<T> {
        private final ObjectIla<T> ila;

        public ObjectIisFactoryImpl(final ObjectIla<T> ila) {
            this.ila = ila;
        }

        @Override
        public ObjectIis<T> create() {
            return ObjectIisFromObjectIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
