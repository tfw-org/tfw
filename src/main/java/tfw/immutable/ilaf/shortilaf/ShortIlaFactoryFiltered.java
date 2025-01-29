package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFiltered;
import tfw.immutable.ila.shortila.ShortIlaFiltered.ShortFilter;

public class ShortIlaFactoryFiltered {
    private ShortIlaFactoryFiltered() {}

    public static ShortIlaFactory create(ShortIlaFactory ilaFactory, ShortFilter filter, short[] buffer) {
        return new ShortIlaFactoryImpl(ilaFactory, filter, buffer);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final ShortIlaFactory ilaFactory;
        private final ShortFilter filter;
        private final short[] buffer;

        public ShortIlaFactoryImpl(final ShortIlaFactory ilaFactory, ShortFilter filter, short[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        public ShortIla create() {
            return ShortIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
