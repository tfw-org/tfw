package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;

public final class StridedDoubleIla {
    private final DoubleIla ila;
    private final double[] buffer;

    public StridedDoubleIla(final DoubleIla ila, final double[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        this.ila = ila;
        this.buffer = buffer;
    }

    public long length() throws IOException {
        return ila.length();
    }

    public void get(final double[] array, final int offset, final int stride, final long start, final int length)
            throws IOException {
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertNotGreaterThanOrEquals(offset, array.length, "offset", "array.length");
        Argument.assertNotEquals(stride, 0, "stride");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        if (stride < 0) {
            Argument.assertNotLessThan(offset + (length - 1) * stride, 0, "offset+(length-1)*stride");
        } else {
            Argument.assertNotGreaterThan(
                    offset + (length - 1) * stride + 1, array.length, "offset+(length-1)*stride+1", "array.length");
        }
        Argument.assertNotGreaterThan(start + length, ila.length(), "start+length", "Ila.length()");

        final DoubleIla segmentDoubleIla =
                start == 0 && length == ila.length() ? ila : DoubleIlaSegment.create(ila, start, length);
        final DoubleIlaIterator bii = new DoubleIlaIterator(segmentDoubleIla, buffer.clone());

        for (int i = 0, ii = offset; i < length; i++, ii += stride) {
            array[ii] = bii.next();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
