package tfw.immutable.iis.byteiis;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iis.AbstractIis;

/**
 * This class is an optional abstract class that implements ByteIis and provides
 * some common parameter validation.
 */
public abstract class AbstractByteIis extends AbstractIis implements ByteIis {
    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the bytes.
     * @param offset into the array to put the first byte.
     * @param length of bytes to put into the array.
     * @return the number of bytes put into the array.
     * @throws IOException if something happens while trying to read the bytes.
     */
    protected abstract int readImpl(byte[] array, int offset, int length) throws IOException;

    @Override
    public final int read(final byte[] array, final int offset, final int length) throws IOException {
        final BigInteger r = readCheck(array, offset, length);

        return r == null ? readImpl(array, offset, length) : r.intValue();
    }
}
// AUTO GENERATED FROM TEMPLATE
