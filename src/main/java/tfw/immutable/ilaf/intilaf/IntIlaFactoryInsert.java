package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaInsert;

public class IntIlaFactoryInsert {
    private IntIlaFactoryInsert() {}

    public static IntIlaFactory create(IntIlaFactory ilaFactory, long index, int value) {
        return new IntIlaFactoryImpl(ilaFactory, index, value);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final IntIlaFactory ilaFactory;
        private final long index;
        private final int value;

        public IntIlaFactoryImpl(final IntIlaFactory ilaFactory, long index, int value) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.index = index;
            this.value = value;
        }

        @Override
        public IntIla create() {
            return IntIlaInsert.create(ilaFactory.create(), index, value);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
