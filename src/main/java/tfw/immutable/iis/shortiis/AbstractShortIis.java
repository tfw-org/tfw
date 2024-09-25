package tfw.immutable.iis.shortiis;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iis.AbstractIis;

/**
 * This class is an optional abstract class that implements ShortIis and provides
 * some common parameter validation.
 */
public abstract class AbstractShortIis extends AbstractIis implements ShortIis {
    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the shorts.
     * @param offset into the array to put the first short.
     * @param length of shorts to put into the array.
     * @return the number of shorts put into the array.
     * @throws IOException if something happens while trying to read the shorts.
     */
    protected abstract int readImpl(short[] array, int offset, int length) throws IOException;

    @Override
    public final int read(final short[] array, final int offset, final int length) throws IOException {
        final BigInteger r = readCheck(array, offset, length);

        return r == null ? readImpl(array, offset, length) : r.intValue();
    }
}
// AUTO GENERATED FROM TEMPLATE
