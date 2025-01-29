package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaDecimate;

public class ShortIlaFactoryDecimate {
    private ShortIlaFactoryDecimate() {}

    public static ShortIlaFactory create(ShortIlaFactory ilaFactory, long factor, short[] buffer) {
        return new ShortIlaFactoryImpl(ilaFactory, factor, buffer);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final ShortIlaFactory ilaFactory;
        private final long factor;
        private final short[] buffer;

        public ShortIlaFactoryImpl(final ShortIlaFactory ilaFactory, long factor, short[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        public ShortIla create() {
            return ShortIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
