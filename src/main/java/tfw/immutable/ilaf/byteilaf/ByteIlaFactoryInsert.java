package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaInsert;

public class ByteIlaFactoryInsert {
    private ByteIlaFactoryInsert() {}

    public static ByteIlaFactory create(ByteIlaFactory ilaFactory, long index, byte value) {
        return new ByteIlaFactoryImpl(ilaFactory, index, value);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final ByteIlaFactory ilaFactory;
        private final long index;
        private final byte value;

        public ByteIlaFactoryImpl(final ByteIlaFactory ilaFactory, long index, byte value) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.index = index;
            this.value = value;
        }

        @Override
        public ByteIla create() {
            return ByteIlaInsert.create(ilaFactory.create(), index, value);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
