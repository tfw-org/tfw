package tfw.immutable.iis.booleaniis;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iis.AbstractIis;

/**
 * This class is an optional abstract class that implements BooleanIis and provides
 * some common parameter validation.
 */
public abstract class AbstractBooleanIis extends AbstractIis implements BooleanIis {
    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the booleans.
     * @param offset into the array to put the first boolean.
     * @param length of booleans to put into the array.
     * @return the number of booleans put into the array.
     * @throws IOException if something happens while trying to read the booleans.
     */
    protected abstract int readImpl(boolean[] array, int offset, int length) throws IOException;

    @Override
    public final int read(final boolean[] array, final int offset, final int length) throws IOException {
        final BigInteger r = readCheck(array, offset, length);

        return r == null ? readImpl(array, offset, length) : r.intValue();
    }
}
// AUTO GENERATED FROM TEMPLATE
