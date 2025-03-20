package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaInsert;

public class ShortIlaFactoryInsert {
    private ShortIlaFactoryInsert() {}

    public static ShortIlaFactory create(ShortIlaFactory ilaFactory, long index, short value) {
        return new ShortIlaFactoryImpl(ilaFactory, index, value);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final ShortIlaFactory ilaFactory;
        private final long index;
        private final short value;

        public ShortIlaFactoryImpl(final ShortIlaFactory ilaFactory, long index, short value) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.index = index;
            this.value = value;
        }

        @Override
        public ShortIla create() {
            return ShortIlaInsert.create(ilaFactory.create(), index, value);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
