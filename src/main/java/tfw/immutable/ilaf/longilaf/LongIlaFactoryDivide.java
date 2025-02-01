package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaDivide;

public class LongIlaFactoryDivide {
    private LongIlaFactoryDivide() {}

    public static LongIlaFactory create(
            final LongIlaFactory leftFactory, final LongIlaFactory rightFactory, final int bufferSize) {
        return new LongIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final LongIlaFactory leftFactory;
        private final LongIlaFactory rightFactory;
        private final int bufferSize;

        public LongIlaFactoryImpl(
                final LongIlaFactory leftFactory, final LongIlaFactory rightFactory, final int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public LongIla create() {
            return LongIlaDivide.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
