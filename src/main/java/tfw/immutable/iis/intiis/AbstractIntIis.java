package tfw.immutable.iis.intiis;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iis.AbstractIis;

/**
 * This class is an optional abstract class that implements IntIis and provides
 * some common parameter validation.
 */
public abstract class AbstractIntIis extends AbstractIis implements IntIis {
    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the ints.
     * @param offset into the array to put the first int.
     * @param length of ints to put into the array.
     * @return the number of ints put into the array.
     * @throws IOException if something happens while trying to read the ints.
     */
    protected abstract int readImpl(int[] array, int offset, int length) throws IOException;

    @Override
    public final int read(final int[] array, final int offset, final int length) throws IOException {
        final BigInteger r = readCheck(array, offset, length);

        return r == null ? readImpl(array, offset, length) : r.intValue();
    }
}
// AUTO GENERATED FROM TEMPLATE
