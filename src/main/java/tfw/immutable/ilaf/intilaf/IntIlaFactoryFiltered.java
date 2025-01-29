package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFiltered;
import tfw.immutable.ila.intila.IntIlaFiltered.IntFilter;

public class IntIlaFactoryFiltered {
    private IntIlaFactoryFiltered() {}

    public static IntIlaFactory create(IntIlaFactory ilaFactory, IntFilter filter, int[] buffer) {
        return new IntIlaFactoryImpl(ilaFactory, filter, buffer);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final IntIlaFactory ilaFactory;
        private final IntFilter filter;
        private final int[] buffer;

        public IntIlaFactoryImpl(final IntIlaFactory ilaFactory, IntFilter filter, int[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        public IntIla create() {
            return IntIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
