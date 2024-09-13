package tfw.immutable.iis.byteiis;

import tfw.check.Argument;

public class ByteIisFactoryFromInputStreamFactory {
    private ByteIisFactoryFromInputStreamFactory() {}

    public static ByteIisFactory create(final InputStreamFactory inputStreamFactory) {
        return new ByteIisFactoryImpl(inputStreamFactory);
    }

    private static class ByteIisFactoryImpl implements ByteIisFactory {
        public final InputStreamFactory inputStreamFactory;

        public ByteIisFactoryImpl(final InputStreamFactory inputStreamFactory) {
            Argument.assertNotNull(inputStreamFactory, "inputStreamFactory");

            this.inputStreamFactory = inputStreamFactory;
        }

        @Override
        public ByteIis create() {
            return ByteIisFromInputStream.create(inputStreamFactory.create());
        }
    }
}
