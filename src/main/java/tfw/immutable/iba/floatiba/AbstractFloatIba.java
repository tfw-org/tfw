package tfw.immutable.iba.floatiba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.check.Argument;
import tfw.immutable.iba.AbstractIba;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

public abstract class AbstractFloatIba extends AbstractIba implements FloatIba {
    protected abstract void getImpl(final float[] array, int arrayOffset, BigInteger ibaStart, int length)
            throws IOException;

    @Override
    public final void get(float[] array, int arrayOffset, BigInteger ibaStart, int length) throws IOException {
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
