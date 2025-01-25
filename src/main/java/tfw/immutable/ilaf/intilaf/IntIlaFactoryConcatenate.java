package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaConcatenate;

public class IntIlaFactoryConcatenate {
    private IntIlaFactoryConcatenate() {}

    public static IntIlaFactory create(IntIlaFactory leftFactory, IntIlaFactory rightFactory) {
        return new IntIlaFactoryImpl(leftFactory, rightFactory);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final IntIlaFactory leftFactory;
        private final IntIlaFactory rightFactory;

        public IntIlaFactoryImpl(final IntIlaFactory leftFactory, IntIlaFactory rightFactory) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
        }

        @Override
        public IntIla create() {
            return IntIlaConcatenate.create(leftFactory.create(), rightFactory.create());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
