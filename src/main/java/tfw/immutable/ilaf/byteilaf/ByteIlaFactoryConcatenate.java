package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaConcatenate;

public class ByteIlaFactoryConcatenate {
    private ByteIlaFactoryConcatenate() {}

    public static ByteIlaFactory create(ByteIlaFactory leftFactory, ByteIlaFactory rightFactory) {
        return new ByteIlaFactoryImpl(leftFactory, rightFactory);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final ByteIlaFactory leftFactory;
        private final ByteIlaFactory rightFactory;

        public ByteIlaFactoryImpl(final ByteIlaFactory leftFactory, ByteIlaFactory rightFactory) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
        }

        @Override
        public ByteIla create() {
            return ByteIlaConcatenate.create(leftFactory.create(), rightFactory.create());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
