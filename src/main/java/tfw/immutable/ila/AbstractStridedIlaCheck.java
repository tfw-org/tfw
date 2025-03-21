package tfw.immutable.ila;

import tfw.check.Argument;

public class AbstractStridedIlaCheck {
    private AbstractStridedIlaCheck() {}

    public static void boundsCheck(
            final long ilaLength,
            final int arrayLength,
            final int offset,
            final int stride,
            final long start,
            final int length) {
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertLessThan(offset, arrayLength, "offset", "array.length");
        Argument.assertNotEquals(stride, 0, "stride");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        if (stride < 0) {
            Argument.assertNotLessThan(offset + (length - 1) * stride, 0, "offset+(length-1)*stride");
        } else {
            Argument.assertNotGreaterThan(
                    offset + (length - 1) * stride + 1L, arrayLength, "offset+(length-1)*stride+1", "array.length");
        }
        Argument.assertNotGreaterThan(start + length, ilaLength, "start+length", "Ila.length()");
    }
}
