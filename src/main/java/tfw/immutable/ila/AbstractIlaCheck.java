package tfw.immutable.ila;

import tfw.check.Argument;

public final class AbstractIlaCheck {
    public static void boundsCheck(long ilaLength, int arrayLength, int offset, long start, int length) {
        Argument.assertNotLessThan(arrayLength, 0, "array.length");
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertNotGreaterThanOrEquals(offset, arrayLength, "offset", "array.length");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ilaLength, "start+length", "Ila.length()");
    }
}
