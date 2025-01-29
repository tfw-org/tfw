package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaAdd;

public class LongIlaFactoryAdd {
    private LongIlaFactoryAdd() {}

    public static LongIlaFactory create(LongIlaFactory leftFactory, LongIlaFactory rightFactory, int bufferSize) {
        return new LongIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final LongIlaFactory leftFactory;
        private final LongIlaFactory rightFactory;
        private final int bufferSize;

        public LongIlaFactoryImpl(LongIlaFactory leftFactory, LongIlaFactory rightFactory, int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public LongIla create() {
            return LongIlaAdd.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
