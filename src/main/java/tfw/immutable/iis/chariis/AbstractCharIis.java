package tfw.immutable.iis.chariis;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iis.AbstractIis;

/**
 * This class is an optional abstract class that implements CharIis and provides
 * some common parameter validation.
 */
public abstract class AbstractCharIis extends AbstractIis implements CharIis {
    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the chars.
     * @param offset into the array to put the first char.
     * @param length of chars to put into the array.
     * @return the number of chars put into the array.
     * @throws IOException if something happens while trying to read the chars.
     */
    protected abstract int readImpl(char[] array, int offset, int length) throws IOException;

    @Override
    public final int read(final char[] array, final int offset, final int length) throws IOException {
        final BigInteger r = readCheck(array, offset, length);

        return r == null ? readImpl(array, offset, length) : r.intValue();
    }
}
// AUTO GENERATED FROM TEMPLATE
