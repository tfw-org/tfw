package tfw.immutable.iba.byteiba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.check.Argument;
import tfw.check.ClosedManager;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

public abstract class AbstractByteIba implements ByteIba {
    protected abstract void closeImpl() throws IOException;

    protected abstract BigInteger lengthImpl() throws IOException;

    protected abstract void getImpl(final byte[] array, int arrayOffset, BigInteger ibaStart, int length)
            throws IOException;

    private final ClosedManager closedManager = new ClosedManager();

    @Override
    public final void close() throws IOException {
        if (closedManager.close()) {
            closeImpl();
        }
    }

    @Override
    public final BigInteger length() throws IOException {
        closedManager.checkClosed("ByteIba");

        return lengthImpl();
    }

    @Override
    public final void get(byte[] array, int arrayOffset, BigInteger ibaStart, int length) throws IOException {
        closedManager.checkClosed("ByteIba");

        Argument.assertNotNull(array, "array");
        ImmutableBigIntegerArrayUtil.validateGetParameters(array.length, arrayOffset, ibaStart, length(), length);

        if (length == 0) {
            return;
        }

        getImpl(array, arrayOffset, ibaStart, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
