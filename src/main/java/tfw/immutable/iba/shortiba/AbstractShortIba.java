package tfw.immutable.iba.shortiba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.check.Argument;
import tfw.immutable.iba.AbstractIba;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

public abstract class AbstractShortIba extends AbstractIba implements ShortIba {
    protected abstract void getImpl(final short[] array, int arrayOffset, BigInteger ibaStart, int length)
            throws IOException;

    @Override
    public final void get(short[] array, int arrayOffset, BigInteger ibaStart, int length) throws IOException {
        checkClosed();

        Argument.assertNotNull(array, "array");
        ImmutableBigIntegerArrayUtil.validateGetParameters(array.length, arrayOffset, ibaStart, length(), length);

        if (length == 0) {
            return;
        }

        getImpl(array, arrayOffset, ibaStart, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
