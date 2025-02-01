package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromCastIntIla;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;

public class ShortIlaFactoryFromCastIntIlaFactory {
    private ShortIlaFactoryFromCastIntIlaFactory() {}

    public static ShortIlaFactory create(IntIlaFactory intIlaFactory, final int bufferSize) {
        return new ShortIlaFactoryImpl(intIlaFactory, bufferSize);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final IntIlaFactory intIlaFactory;
        private final int bufferSize;

        public ShortIlaFactoryImpl(final IntIlaFactory intIlaFactory, final int bufferSize) {
            Argument.assertNotNull(intIlaFactory, "intIlaFactory");

            this.intIlaFactory = intIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ShortIla create() {
            return ShortIlaFromCastIntIla.create(intIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
