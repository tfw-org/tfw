package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaAdd;

public class ByteIlaFactoryAdd {
    private ByteIlaFactoryAdd() {}

    public static ByteIlaFactory create(ByteIlaFactory leftFactory, ByteIlaFactory rightFactory, int bufferSize) {
        return new ByteIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final ByteIlaFactory leftFactory;
        private final ByteIlaFactory rightFactory;
        private final int bufferSize;

        public ByteIlaFactoryImpl(ByteIlaFactory leftFactory, ByteIlaFactory rightFactory, int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ByteIla create() {
            return ByteIlaAdd.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
