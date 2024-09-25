package tfw.immutable.iis.floatiis;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iis.AbstractIis;

/**
 * This class is an optional abstract class that implements FloatIis and provides
 * some common parameter validation.
 */
public abstract class AbstractFloatIis extends AbstractIis implements FloatIis {
    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the floats.
     * @param offset into the array to put the first float.
     * @param length of floats to put into the array.
     * @return the number of floats put into the array.
     * @throws IOException if something happens while trying to read the floats.
     */
    protected abstract int readImpl(float[] array, int offset, int length) throws IOException;

    @Override
    public final int read(final float[] array, final int offset, final int length) throws IOException {
        final BigInteger r = readCheck(array, offset, length);

        return r == null ? readImpl(array, offset, length) : r.intValue();
    }
}
// AUTO GENERATED FROM TEMPLATE
