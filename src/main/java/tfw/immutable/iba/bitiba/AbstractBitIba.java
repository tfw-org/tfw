package tfw.immutable.iba.bitiba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.check.Argument;
import tfw.check.ClosedManager;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

public abstract class AbstractBitIba implements BitIba {
    protected abstract void closeImpl() throws IOException;

    protected abstract BigInteger lengthInBitsImpl() throws IOException;

    protected abstract void getImpl(long[] array, long arrayOffsetInBits, BigInteger ibaStartInBits, long lengthInBits)
            throws IOException;

    private final ClosedManager closedManager = new ClosedManager();

    @Override
    public final void close() throws IOException {
        if (closedManager.close()) {
            closeImpl();
        }
    }

    @Override
    public final BigInteger lengthInBits() throws IOException {
        closedManager.checkClosed("BitIba");

        return lengthInBitsImpl();
    }

    @Override
    public final void get(long[] array, long arrayOffsetInBits, BigInteger ibaStartInBits, long lengthInBits)
            throws IOException {
        closedManager.checkClosed("BitIba");

        Argument.assertNotNull(array, "array");
        ImmutableBigIntegerArrayUtil.validateGetParameters(
                array.length, arrayOffsetInBits, ibaStartInBits, lengthInBits(), lengthInBits);

        if (lengthInBits == 0) {
            return;
        }

        getImpl(array, arrayOffsetInBits, ibaStartInBits, lengthInBits);
    }
}
