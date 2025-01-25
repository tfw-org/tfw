package tfw.immutable.ilaf.booleanilaf;

import tfw.check.Argument;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaConcatenate;

public class BooleanIlaFactoryConcatenate {
    private BooleanIlaFactoryConcatenate() {}

    public static BooleanIlaFactory create(BooleanIlaFactory leftFactory, BooleanIlaFactory rightFactory) {
        return new BooleanIlaFactoryImpl(leftFactory, rightFactory);
    }

    private static class BooleanIlaFactoryImpl implements BooleanIlaFactory {
        private final BooleanIlaFactory leftFactory;
        private final BooleanIlaFactory rightFactory;

        public BooleanIlaFactoryImpl(final BooleanIlaFactory leftFactory, BooleanIlaFactory rightFactory) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
        }

        @Override
        public BooleanIla create() {
            return BooleanIlaConcatenate.create(leftFactory.create(), rightFactory.create());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
