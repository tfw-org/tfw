package tfw.immutable.iisf.objectiisf;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.objectiis.ObjectIis;
import tfw.immutable.iis.objectiis.ObjectIisFromObjectIla;
import tfw.immutable.ila.objectila.ObjectIla;

public final class ObjectIisFactoryFromObjectIla {
    private ObjectIisFactoryFromObjectIla() {}

    public static <T> ObjectIisFactory<T> create(final ObjectIla<T> ila) {
        return new ObjectIisFactoryImpl<>(ila);
    }

    private static class ObjectIisFactoryImpl<T> implements ObjectIisFactory<T> {
        private final ObjectIla<T> ila;

        public ObjectIisFactoryImpl(final ObjectIla<T> ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public ObjectIis<T> create() throws IOException {
            return ObjectIisFromObjectIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
