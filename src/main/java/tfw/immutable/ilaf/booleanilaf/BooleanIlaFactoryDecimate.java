package tfw.immutable.ilaf.booleanilaf;

import tfw.check.Argument;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaDecimate;

public class BooleanIlaFactoryDecimate {
    private BooleanIlaFactoryDecimate() {}

    public static BooleanIlaFactory create(BooleanIlaFactory ilaFactory, long factor, boolean[] buffer) {
        return new BooleanIlaFactoryImpl(ilaFactory, factor, buffer);
    }

    private static class BooleanIlaFactoryImpl implements BooleanIlaFactory {
        private final BooleanIlaFactory ilaFactory;
        private final long factor;
        private final boolean[] buffer;

        public BooleanIlaFactoryImpl(final BooleanIlaFactory ilaFactory, long factor, boolean[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        public BooleanIla create() {
            return BooleanIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
