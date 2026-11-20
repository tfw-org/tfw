// booleaniba,byteiba,chariba,doubleiba,floatiba,intiba,longiba,objectiba,shortiba
package ${PACKAGE};

import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.AbstractIbaFromArray;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

public final class ${NAME}IbaFromArray {
    private ${NAME}IbaFromArray() {
        // non-instantiable class
    }

    public static ${TEMPLATE_SPACE}${NAME}Iba${TEMPLATE} create(${TYPE_OR_TEMPLATE}[] array) {
        return new ${NAME}IbaImpl${DIAMOND}(array);
    }

    private static class ${NAME}IbaImpl${TEMPLATE} extends AbstractIbaFromArray implements ${NAME}Iba${TEMPLATE} {
        private final ${TYPE_OR_TEMPLATE}[] array;

        private ${NAME}IbaImpl(${TYPE_OR_TEMPLATE}[] array) {
            super(checkAndReturnLength(array));

            this.array = array;
        }

        @Override
        public void get(${TYPE_OR_TEMPLATE}[] array, int offset, BigInteger start, int length) {
            checkClosed();

            Arguments.checkNotNull(array, "array");
            ImmutableBigIntegerArrayUtil.validateGetParameters(array.length, offset, start, arrayLength, length);

            if (length == 0) {
                return;
            }

            System.arraycopy(this.array, start.intValueExact(), array, offset, length);
        }

        private static ${TEMPLATE_SPACE}BigInteger checkAndReturnLength(${TYPE_OR_TEMPLATE}[] array) {
            Arguments.checkNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
