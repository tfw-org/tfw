package tfw.immutable.ilaf.byteilaf;

import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;

public class ByteIlaFactoryFromArray {
    private ByteIlaFactoryFromArray() {}

    public static ByteIlaFactory create(final byte[] array) {
        return new ByteIlaFactoryImpl(array);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final byte[] array;

        public ByteIlaFactoryImpl(final byte[] array) {
            this.array = array;
        }

        @Override
        public ByteIla create() {
            return ByteIlaFromArray.create(array);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
