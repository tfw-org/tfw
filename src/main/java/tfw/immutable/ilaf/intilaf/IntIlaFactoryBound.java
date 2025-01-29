package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaBound;

public class IntIlaFactoryBound {
    private IntIlaFactoryBound() {}

    public static IntIlaFactory create(IntIlaFactory ilaFactory, int minimum, int maximum) {
        return new IntIlaFactoryImpl(ilaFactory, minimum, maximum);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final IntIlaFactory ilaFactory;
        private final int minimum;
        private final int maximum;

        public IntIlaFactoryImpl(final IntIlaFactory ilaFactory, int minimum, int maximum) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        public IntIla create() {
            return IntIlaBound.create(ilaFactory.create(), minimum, maximum);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
