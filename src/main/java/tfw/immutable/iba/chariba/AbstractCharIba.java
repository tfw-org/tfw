package tfw.immutable.iba.chariba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.AbstractIba;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

public abstract class AbstractCharIba extends AbstractIba implements CharIba {
    protected abstract void getImpl(final char[] array, int arrayOffset, BigInteger ibaStart, int length)
            throws IOException;

    @Override
    public final void get(char[] array, int arrayOffset, BigInteger ibaStart, int length) throws IOException {
        checkClosed();

        Arguments.checkNotNull(array, "array");
        ImmutableBigIntegerArrayUtil.validateGetParameters(array.length, arrayOffset, ibaStart, length(), length);

        if (length == 0) {
            return;
        }

        getImpl(array, arrayOffset, ibaStart, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
