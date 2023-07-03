package tfw.immutable.ila.shortila;

import java.util.Random;

/**
 *
 * @immutables.types=numeric
 */
public final class ShortIlaUtilCheck {
    private ShortIlaUtilCheck() {
        // non-instantiable class
    }

    public static void checkAll(final ShortIla actual, short epsilon) throws Exception {
        checkZeroArgImmutability(actual);
        checkTwoArgImmutability(actual, epsilon);
        checkTwoFourEquivalence(actual, epsilon);
    }

    public static void checkZeroArgImmutability(ShortIla ila) throws Exception {
        final long firstLength = ila.length();
        final short[] firstArray = ShortIlaUtil.toArray(ila);
        final long secondLength = ila.length();
        final short[] secondArray = ShortIlaUtil.toArray(ila);
        final long thirdLength = ila.length();
        final short[] thirdArray = ShortIlaUtil.toArray(ila);
        final long fourthLength = ila.length();

        if (firstArray.length != firstLength) throw new Exception("firstArray.length != firstLength");
        if (secondArray.length != secondLength) throw new Exception("secondArray.length != secondLength");
        if (thirdArray.length != thirdLength) throw new Exception("thirdArray.length != thirdLength");

        if (firstLength != secondLength) throw new Exception("firstLength != secondLength");
        if (secondLength != thirdLength) throw new Exception("secondLength != thirdLength");
        if (thirdLength != fourthLength) throw new Exception("thirdLength != fourthLength");

        final Random random = new Random(0);

        for (int ii = 0; ii < firstLength; ++ii) {
            secondArray[ii] = (short) random.nextInt();
        }

        for (int ii = 0; ii < firstLength; ++ii) {
            if (firstArray[ii] != thirdArray[ii])
                throw new Exception("firstArray[" + ii + "] ("
                        + firstArray[ii] + ") != thirdArray["
                        + ii + "] (" + thirdArray[ii] + ")");
        }
    }

    // also performs zero-two equivalence
    public static void checkTwoArgImmutability(ShortIla ila, short epsilon) throws Exception {
        final short eps = epsilon < 0.0 ? (short) -epsilon : epsilon;
        final short neps = (short) -eps;
        final int ilaLength = ila.length() <= Integer.MAX_VALUE ? (int) ila.length() : Integer.MAX_VALUE;
        final short[] baseline = ShortIlaUtil.toArray(ila, 0, ilaLength);
        if (baseline.length != ilaLength) throw new Exception("baseline.length != ilaLength");
        for (int length = 1; length <= ilaLength; ++length) {
            for (long start = 0; start < ilaLength - length + 1; ++start) {
                final short[] subset = ShortIlaUtil.toArray(ila, start, length);
                if (subset.length != length) throw new Exception("subset.length != length");
                for (int ii = 0; ii < subset.length; ++ii) {
                    short delta = (short) (baseline[ii + (int) start] - subset[ii]);
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

    public static void checkTwoFourEquivalence(ShortIla ila, short epsilon) throws Exception {
        final short eps = epsilon < 0.0 ? (short) -epsilon : epsilon;
        final short neps = (short) -eps;
        final int ilaLength = ila.length() <= Integer.MAX_VALUE ? (int) ila.length() : Integer.MAX_VALUE;
        final short[] four = new short[ilaLength];
        for (int length = 1; length <= ilaLength; ++length) {
            for (long start = 0; start < ilaLength - length + 1; ++start) {
                final short[] two = ShortIlaUtil.toArray(ila, start, length);
                ila.toArray(four, 0, start, length);
                for (int ii = 0; ii < length; ++ii) {
                    short delta = (short) (four[ii] - two[ii]);
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

    public static void dump(String msg, short[] array) {
        System.out.println(msg + ":");
        for (int ii = 0; ii < array.length; ++ii) {
            System.out.print(" " + array[ii]);
        }
        System.out.println();
    }
}
// AUTO GENERATED FROM TEMPLATE
