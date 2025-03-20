package tfw.immutable.ilaf.booleanilaf;

import tfw.check.Argument;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaInsert;

public class BooleanIlaFactoryInsert {
    private BooleanIlaFactoryInsert() {}

    public static BooleanIlaFactory create(BooleanIlaFactory ilaFactory, long index, boolean value) {
        return new BooleanIlaFactoryImpl(ilaFactory, index, value);
    }

    private static class BooleanIlaFactoryImpl implements BooleanIlaFactory {
        private final BooleanIlaFactory ilaFactory;
        private final long index;
        private final boolean value;

        public BooleanIlaFactoryImpl(final BooleanIlaFactory ilaFactory, long index, boolean value) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.index = index;
            this.value = value;
        }

        @Override
        public BooleanIla create() {
            return BooleanIlaInsert.create(ilaFactory.create(), index, value);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
