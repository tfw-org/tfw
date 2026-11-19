package tfw.immutable.iba.byteiba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.AbstractIba;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

public abstract class AbstractByteIba extends AbstractIba implements ByteIba {
    protected abstract void getImpl(final byte[] array, int arrayOffset, BigInteger ibaStart, int length)
            throws IOException;

    @Override
    public final void get(byte[] array, int arrayOffset, BigInteger ibaStart, int length) throws IOException {
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
