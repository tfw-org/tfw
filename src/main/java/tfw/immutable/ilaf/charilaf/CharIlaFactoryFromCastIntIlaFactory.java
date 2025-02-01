package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromCastIntIla;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;

public class CharIlaFactoryFromCastIntIlaFactory {
    private CharIlaFactoryFromCastIntIlaFactory() {}

    public static CharIlaFactory create(IntIlaFactory intIlaFactory, final int bufferSize) {
        return new CharIlaFactoryImpl(intIlaFactory, bufferSize);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final IntIlaFactory intIlaFactory;
        private final int bufferSize;

        public CharIlaFactoryImpl(final IntIlaFactory intIlaFactory, final int bufferSize) {
            Argument.assertNotNull(intIlaFactory, "intIlaFactory");

            this.intIlaFactory = intIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public CharIla create() {
            return CharIlaFromCastIntIla.create(intIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
