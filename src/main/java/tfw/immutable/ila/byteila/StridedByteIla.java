package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class StridedByteIla {
    private final ByteIla ila;
    private final byte[] buffer;

    public StridedByteIla(final ByteIla ila, final byte[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        this.ila = ila;
        this.buffer = buffer;
    }

    public long length() throws IOException {
        return ila.length();
    }

    public void get(final byte[] array, final int offset, final int stride, final long start, final int length)
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

        final ByteIla segmentByteIla =
                start == 0 && length == ila.length() ? ila : ByteIlaSegment.create(ila, start, length);
        final ByteIlaIterator bii = new ByteIlaIterator(segmentByteIla, buffer.clone());

        for (int i = 0, ii = offset; i < length; i++, ii += stride) {
            array[ii] = bii.next();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
