package tfw.immutable.ila.longila;

import java.util.Random;

/**
 *
 * @immutables.types=numeric
 */
public final class LongIlaUtilCheck {
    private LongIlaUtilCheck() {
        // non-instantiable class
    }

    public static void checkAll(final LongIla actual, long epsilon) throws Exception {
        checkZeroArgImmutability(actual);
        checkTwoArgImmutability(actual, epsilon);
        checkTwoFourEquivalence(actual, epsilon);
    }

    public static void checkZeroArgImmutability(LongIla ila) throws Exception {
        final long firstLength = ila.length();
        final long[] firstArray = LongIlaUtil.toArray(ila);
        final long secondLength = ila.length();
        final long[] secondArray = LongIlaUtil.toArray(ila);
        final long thirdLength = ila.length();
        final long[] thirdArray = LongIlaUtil.toArray(ila);
        final long fourthLength = ila.length();

        if (firstArray.length != firstLength) throw new Exception("firstArray.length != firstLength");
        if (secondArray.length != secondLength) throw new Exception("secondArray.length != secondLength");
        if (thirdArray.length != thirdLength) throw new Exception("thirdArray.length != thirdLength");

        if (firstLength != secondLength) throw new Exception("firstLength != secondLength");
        if (secondLength != thirdLength) throw new Exception("secondLength != thirdLength");
        if (thirdLength != fourthLength) throw new Exception("thirdLength != fourthLength");

        final Random random = new Random(0);

        for (int ii = 0; ii < firstLength; ++ii) {
            secondArray[ii] = random.nextLong();
        }

        for (int ii = 0; ii < firstLength; ++ii) {
            if (firstArray[ii] != thirdArray[ii])
                throw new Exception("firstArray[" + ii + "] ("
                        + firstArray[ii] + ") != thirdArray["
                        + ii + "] (" + thirdArray[ii] + ")");
        }
    }

    // also performs zero-two equivalence
    public static void checkTwoArgImmutability(LongIla ila, long epsilon) throws Exception {
        final long eps = epsilon < 0.0 ? -epsilon : epsilon;
        final long neps = -eps;
        final int ilaLength = ila.length() <= Integer.MAX_VALUE ? (int) ila.length() : Integer.MAX_VALUE;
        final long[] baseline = LongIlaUtil.toArray(ila, 0, ilaLength);
        if (baseline.length != ilaLength) throw new Exception("baseline.length != ilaLength");
        for (int length = 1; length <= ilaLength; ++length) {
            for (long start = 0; start < ilaLength - length + 1; ++start) {
                final long[] subset = LongIlaUtil.toArray(ila, start, length);
                if (subset.length != length) throw new Exception("subset.length != length");
                for (int ii = 0; ii < subset.length; ++ii) {
                    long delta = (baseline[ii + (int) start] - subset[ii]);
                    if (!(neps <= delta && delta <= eps))
                        throw new Exception("subset[" + ii + "] ("
                                + subset[ii] + ") !~ baseline["
                                + (ii + start) + "] ("
                                + baseline[ii + (int) start]
                                + ") {length=" + length
                                + ",start=" + start + "}");
                }
            }
        }
    }

    public static void checkTwoFourEquivalence(LongIla ila, long epsilon) throws Exception {
        final long eps = epsilon < 0.0 ? -epsilon : epsilon;
        final long neps = -eps;
        final int ilaLength = ila.length() <= Integer.MAX_VALUE ? (int) ila.length() : Integer.MAX_VALUE;
        final long[] four = new long[ilaLength];
        for (int length = 1; length <= ilaLength; ++length) {
            for (long start = 0; start < ilaLength - length + 1; ++start) {
                final long[] two = LongIlaUtil.toArray(ila, start, length);
                ila.toArray(four, 0, start, length);
                for (int ii = 0; ii < length; ++ii) {
                    long delta = (four[ii] - two[ii]);
                    if (!(neps <= delta && delta <= eps))
                        throw new Exception("four[" + ii + "] ("
                                + four[ii] + ") !~ two["
                                + ii + "] ("
                                + two[ii]
                                + ") {length=" + length
                                + ",start=" + start + "}");
                }
            }
        }
    }

    public static void dump(String msg, long[] array) {
        System.out.println(msg + ":");
        for (int ii = 0; ii < array.length; ++ii) {
            System.out.print(" " + array[ii]);
        }
        System.out.println();
    }
}
// AUTO GENERATED FROM TEMPLATE
