package tfw.immutable.ilaf.intilaf;

import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;

public class IntIlaFactoryFromArray {
    private IntIlaFactoryFromArray() {}

    public static IntIlaFactory create(final int[] array) {
        return new IntIlaFactoryImpl(array);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final int[] array;

        public IntIlaFactoryImpl(final int[] array) {
            this.array = array;
        }

        @Override
        public IntIla create() {
            return IntIlaFromArray.create(array);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
