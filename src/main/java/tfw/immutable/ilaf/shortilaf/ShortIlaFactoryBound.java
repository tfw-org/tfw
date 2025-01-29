package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaBound;

public class ShortIlaFactoryBound {
    private ShortIlaFactoryBound() {}

    public static ShortIlaFactory create(ShortIlaFactory ilaFactory, short minimum, short maximum) {
        return new ShortIlaFactoryImpl(ilaFactory, minimum, maximum);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final ShortIlaFactory ilaFactory;
        private final short minimum;
        private final short maximum;

        public ShortIlaFactoryImpl(final ShortIlaFactory ilaFactory, short minimum, short maximum) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        public ShortIla create() {
            return ShortIlaBound.create(ilaFactory.create(), minimum, maximum);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
