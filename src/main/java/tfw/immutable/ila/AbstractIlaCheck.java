package tfw.immutable.ila;

import tfw.check.Argument;

public final class AbstractIlaCheck {
    public static final void boundsCheck(
            long ilaLength, int arrayLength, int offset, int stride, long start, int length) {
        Argument.assertNotLessThan(arrayLength, 0, "array.length");
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertNotGreaterThanOrEquals(offset, arrayLength, "offset", "array.length");
        Argument.assertNotEquals(stride, 0, "stride");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        if (stride < 0) {
            Argument.assertNotLessThan(offset + (length - 1) * stride, 0, "offset+(length-1)*stride");
        } else {
            Argument.assertNotGreaterThan(
                    offset + (length - 1) * stride + 1, arrayLength, "offset+(length-1)*stride+1", "array.length");
        }
        Argument.assertNotGreaterThan(start + length, ilaLength, "start+length", "Ila.length()");
    }
}
