package tfw.immutable.iis.longiis;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iis.AbstractIis;

/**
 * This class is an optional abstract class that implements LongIis and provides
 * some common parameter validation.
 */
public abstract class AbstractLongIis extends AbstractIis implements LongIis {
    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the longs.
     * @param offset into the array to put the first long.
     * @param length of longs to put into the array.
     * @return the number of longs put into the array.
     * @throws IOException if something happens while trying to read the longs.
     */
    protected abstract int readImpl(long[] array, int offset, int length) throws IOException;

    @Override
    public final int read(final long[] array, final int offset, final int length) throws IOException {
        final BigInteger r = readCheck(array, offset, length);

        return r == null ? readImpl(array, offset, length) : r.intValue();
    }
}
// AUTO GENERATED FROM TEMPLATE
