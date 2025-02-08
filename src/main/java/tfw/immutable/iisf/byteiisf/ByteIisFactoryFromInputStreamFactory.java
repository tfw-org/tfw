package tfw.immutable.iisf.byteiisf;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.byteiis.ByteIis;
import tfw.immutable.iis.byteiis.ByteIisFromInputStream;
import tfw.immutable.iis.byteiis.InputStreamFactory;

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
        public ByteIis create() throws IOException {
            return ByteIisFromInputStream.create(inputStreamFactory.create());
        }
    }
}
