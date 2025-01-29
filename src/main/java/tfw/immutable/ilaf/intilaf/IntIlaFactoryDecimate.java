package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaDecimate;

public class IntIlaFactoryDecimate {
    private IntIlaFactoryDecimate() {}

    public static IntIlaFactory create(IntIlaFactory ilaFactory, long factor, int[] buffer) {
        return new IntIlaFactoryImpl(ilaFactory, factor, buffer);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final IntIlaFactory ilaFactory;
        private final long factor;
        private final int[] buffer;

        public IntIlaFactoryImpl(final IntIlaFactory ilaFactory, long factor, int[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        public IntIla create() {
            return IntIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
