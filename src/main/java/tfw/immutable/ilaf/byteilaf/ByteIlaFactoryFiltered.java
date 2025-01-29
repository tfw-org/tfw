package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFiltered;
import tfw.immutable.ila.byteila.ByteIlaFiltered.ByteFilter;

public class ByteIlaFactoryFiltered {
    private ByteIlaFactoryFiltered() {}

    public static ByteIlaFactory create(ByteIlaFactory ilaFactory, ByteFilter filter, byte[] buffer) {
        return new ByteIlaFactoryImpl(ilaFactory, filter, buffer);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final ByteIlaFactory ilaFactory;
        private final ByteFilter filter;
        private final byte[] buffer;

        public ByteIlaFactoryImpl(final ByteIlaFactory ilaFactory, ByteFilter filter, byte[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        public ByteIla create() {
            return ByteIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
