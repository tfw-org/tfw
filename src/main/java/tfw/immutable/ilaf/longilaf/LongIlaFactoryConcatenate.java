package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaConcatenate;

public class LongIlaFactoryConcatenate {
    private LongIlaFactoryConcatenate() {}

    public static LongIlaFactory create(LongIlaFactory leftFactory, LongIlaFactory rightFactory) {
        return new LongIlaFactoryImpl(leftFactory, rightFactory);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final LongIlaFactory leftFactory;
        private final LongIlaFactory rightFactory;

        public LongIlaFactoryImpl(final LongIlaFactory leftFactory, LongIlaFactory rightFactory) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
        }

        @Override
        public LongIla create() {
            return LongIlaConcatenate.create(leftFactory.create(), rightFactory.create());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
