package tfw.immutable.ilaf.objectilaf;

import tfw.check.Argument;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaInterleave;
import tfw.immutable.ilaf.ImmutableLongArrayFactoryUtil;

public class ObjectIlaFactoryInterleave {
    private ObjectIlaFactoryInterleave() {}

    public static <T> ObjectIlaFactory<T> create(final ObjectIlaFactory<T>[] ilaFactories, final T[] buffers) {
        return new ObjectIlaFactoryImpl<>(ilaFactories, buffers);
    }

    private static class ObjectIlaFactoryImpl<T> implements ObjectIlaFactory<T> {
        private final ObjectIlaFactory<T>[] ilaFactories;
        private final T[] buffers;

        public ObjectIlaFactoryImpl(final ObjectIlaFactory<T>[] ilaFactories, final T[] buffers) {
            Argument.assertNotNull(buffers, "buffers");

            this.ilaFactories = ImmutableLongArrayFactoryUtil.assertNotNullAndClone(ilaFactories, "ilaFactories");
            this.buffers = buffers;
        }

        @Override
        @SuppressWarnings("unchecked")
        public ObjectIla<T> create() {
            final ObjectIla<T>[] ilas = new ObjectIla[ilaFactories.length];

            for (int i = 0; i < ilas.length; i++) {
                ilas[i] = ilaFactories[i].create();
            }

            return ObjectIlaInterleave.create(ilas, buffers);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
