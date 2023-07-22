package tfw.immutable.ila.booleanila;

import java.util.Random;

public final class BooleanIlaUtilCheck {
    private BooleanIlaUtilCheck() {
        // non-instantiable class
    }

    public static void checkAll(BooleanIla actual, boolean epsilon) throws Exception {
        checkZeroArgImmutability(actual);
        checkTwoArgImmutability(actual, epsilon);
        checkTwoFourEquivalence(actual, epsilon);
    }

    public static void checkZeroArgImmutability(BooleanIla ila) throws Exception {
        final long firstLength = ila.length();
        final boolean[] firstArray = BooleanIlaUtil.toArray(ila);
        final long secondLength = ila.length();
        final boolean[] secondArray = BooleanIlaUtil.toArray(ila);
        final long thirdLength = ila.length();
        final boolean[] thirdArray = BooleanIlaUtil.toArray(ila);
        final long fourthLength = ila.length();

        if (firstArray.length != firstLength) throw new Exception("firstArray.length != firstLength");
        if (secondArray.length != secondLength) throw new Exception("secondArray.length != secondLength");
        if (thirdArray.length != thirdLength) throw new Exception("thirdArray.length != thirdLength");

        if (firstLength != secondLength) throw new Exception("firstLength != secondLength");
        if (secondLength != thirdLength) throw new Exception("secondLength != thirdLength");
        if (thirdLength != fourthLength) throw new Exception("thirdLength != fourthLength");

        final Random random = new Random(0);
        for (int ii = 0; ii < firstLength; ++ii) {
            secondArray[ii] = random.nextBoolean();
        }

        for (int ii = 0; ii < firstLength; ++ii) {
            if (firstArray[ii] != thirdArray[ii])
                throw new Exception("firstArray[" + ii + "] ("
                        + firstArray[ii] + ") != thirdArray["
                        + ii + "] (" + thirdArray[ii] + ")");
        }
    }

    // also performs zero-two equivalence
    public static void checkTwoArgImmutability(BooleanIla ila, boolean epsilon) throws Exception {
        if (epsilon != false) {
            throw new IllegalArgumentException("epsilon != " + (false) + " not allowed");
        } else {
            final int ilaLength = ila.length() <= Integer.MAX_VALUE ? (int) ila.length() : Integer.MAX_VALUE;
            final boolean[] baseline = BooleanIlaUtil.toArray(ila, 0, ilaLength);
            if (baseline.length != ilaLength) throw new Exception("baseline.length != ilaLength");
            for (int length = 1; length <= ilaLength; ++length) {
                for (long start = 0; start < ilaLength - length + 1; ++start) {
                    final boolean[] subset = BooleanIlaUtil.toArray(ila, start, length);
                    if (subset.length != length) throw new Exception("subset.length != length");
                    for (int ii = 0; ii < subset.length; ++ii) {
                        if (!(baseline[ii + (int) start] == subset[ii]))
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
    }

    public static void checkTwoFourEquivalence(BooleanIla ila, boolean epsilon) throws Exception {
        if (epsilon != false) {
            throw new IllegalArgumentException("epsilon != " + (false) + " not allowed");
        } else {
            final int ilaLength = ila.length() <= Integer.MAX_VALUE ? (int) ila.length() : Integer.MAX_VALUE;
            final boolean[] four = new boolean[ilaLength];
            for (int length = 1; length <= ilaLength; ++length) {
                for (long start = 0; start < ilaLength - length + 1; ++start) {
                    final boolean[] two = BooleanIlaUtil.toArray(ila, start, length);
                    ila.toArray(four, 0, start, length);
                    for (int ii = 0; ii < length; ++ii) {
                        if (!(four[ii] == two[ii]))
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
    }

    public static void dump(String msg, boolean[] array) {
        System.out.println(msg + ":");
        for (int ii = 0; ii < array.length; ++ii) {
            System.out.print(" " + array[ii]);
        }
        System.out.println();
    }
}
// AUTO GENERATED FROM TEMPLATE
