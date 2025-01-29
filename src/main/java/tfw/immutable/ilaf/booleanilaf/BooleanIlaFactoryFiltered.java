package tfw.immutable.ilaf.booleanilaf;

import tfw.check.Argument;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFiltered;
import tfw.immutable.ila.booleanila.BooleanIlaFiltered.BooleanFilter;

public class BooleanIlaFactoryFiltered {
    private BooleanIlaFactoryFiltered() {}

    public static BooleanIlaFactory create(BooleanIlaFactory ilaFactory, BooleanFilter filter, boolean[] buffer) {
        return new BooleanIlaFactoryImpl(ilaFactory, filter, buffer);
    }

    private static class BooleanIlaFactoryImpl implements BooleanIlaFactory {
        private final BooleanIlaFactory ilaFactory;
        private final BooleanFilter filter;
        private final boolean[] buffer;

        public BooleanIlaFactoryImpl(final BooleanIlaFactory ilaFactory, BooleanFilter filter, boolean[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        public BooleanIla create() {
            return BooleanIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
