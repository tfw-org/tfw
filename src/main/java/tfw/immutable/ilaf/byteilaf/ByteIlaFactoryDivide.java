package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaDivide;

public class ByteIlaFactoryDivide {
    private ByteIlaFactoryDivide() {}

    public static ByteIlaFactory create(
            final ByteIlaFactory leftFactory, final ByteIlaFactory rightFactory, final int bufferSize) {
        return new ByteIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final ByteIlaFactory leftFactory;
        private final ByteIlaFactory rightFactory;
        private final int bufferSize;

        public ByteIlaFactoryImpl(
                final ByteIlaFactory leftFactory, final ByteIlaFactory rightFactory, final int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ByteIla create() {
            return ByteIlaDivide.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
