package tfw.immutable.ilaf.intilaf;

import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFill;

public class IntIlaFactoryFill {
    private IntIlaFactoryFill() {}

    public static IntIlaFactory create(final int value, final long length) {
        return new IntIlaFactoryImpl(value, length);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final int value;
        private final long length;

        public IntIlaFactoryImpl(final int value, final long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        public IntIla create() {
            return IntIlaFill.create(value, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
