package tfw.immutable.iis.objectiis;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iis.AbstractIis;

/**
 * This class is an optional abstract class that implements ObjectIis and provides
 * some common parameter validation.
 */
public abstract class AbstractObjectIis<T> extends AbstractIis implements ObjectIis<T> {
    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the Objects.
     * @param offset into the array to put the first Object.
     * @param length of Objects to put into the array.
     * @return the number of Objects put into the array.
     * @throws IOException if something happens while trying to read the Objects.
     */
    protected abstract int readImpl(T[] array, int offset, int length) throws IOException;

    @Override
    public final int read(final T[] array, final int offset, final int length) throws IOException {
        final BigInteger r = readCheck(array, offset, length);

        return r == null ? readImpl(array, offset, length) : r.intValue();
    }
}
// AUTO GENERATED FROM TEMPLATE
