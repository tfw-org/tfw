package tfw.immutable.iis.doubleiis;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iis.AbstractIis;

/**
 * This class is an optional abstract class that implements DoubleIis and provides
 * some common parameter validation.
 */
public abstract class AbstractDoubleIis extends AbstractIis implements DoubleIis {
    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the doubles.
     * @param offset into the array to put the first double.
     * @param length of doubles to put into the array.
     * @return the number of doubles put into the array.
     * @throws IOException if something happens while trying to read the doubles.
     */
    protected abstract int readImpl(double[] array, int offset, int length) throws IOException;

    @Override
    public final int read(final double[] array, final int offset, final int length) throws IOException {
        final BigInteger r = readCheck(array, offset, length);

        return r == null ? readImpl(array, offset, length) : r.intValue();
    }
}
// AUTO GENERATED FROM TEMPLATE
