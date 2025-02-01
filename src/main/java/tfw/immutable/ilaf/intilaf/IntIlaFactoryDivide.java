package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaDivide;

public class IntIlaFactoryDivide {
    private IntIlaFactoryDivide() {}

    public static IntIlaFactory create(
            final IntIlaFactory leftFactory, final IntIlaFactory rightFactory, final int bufferSize) {
        return new IntIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final IntIlaFactory leftFactory;
        private final IntIlaFactory rightFactory;
        private final int bufferSize;

        public IntIlaFactoryImpl(
                final IntIlaFactory leftFactory, final IntIlaFactory rightFactory, final int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public IntIla create() {
            return IntIlaDivide.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
