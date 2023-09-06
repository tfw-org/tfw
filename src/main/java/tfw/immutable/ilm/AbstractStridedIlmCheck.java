package tfw.immutable.ilm;

import tfw.check.Argument;

public final class AbstractStridedIlmCheck {
    private AbstractStridedIlmCheck() {}

    public static void boundsCheck(
            long width,
            long height,
            int arrayLength,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount) {
        Argument.assertNotLessThan(arrayLength, 0, "arrayLength");

        if (width == 0 || height == 0 || arrayLength == 0) {
            return;
        }

        Argument.assertNotLessThan(arrayLength, 0, "arrayLength");
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertLessThan(offset, arrayLength, "offset", "arrayLength");
        Argument.assertNotEquals(rowStride, 0, "rowStride");
        Argument.assertNotEquals(colStride, 0, "colStride");
        Argument.assertNotLessThan(rowStart, 0, "rowStart");
        Argument.assertNotLessThan(colStart, 0, "colStart");
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        if (offset + (rowCount - 1) * rowStride + (colCount - 1) * colStride + 1 > arrayLength) {
            throw new IllegalArgumentException("offset+(rowCount-1)*rowStride+(colCount-1)*colStride+1" + " (="
                    + (offset + (rowCount - 1) * rowStride + (colCount - 1) * colStride + 1)
                    + ") > " + "arrayLength"
                    + " (=" + arrayLength + ") not allowed!");
        }
        if (offset + (rowCount - 1) * rowStride + (colCount - 1) * colStride + 1 < 0) {
            throw new IllegalArgumentException("offset+(rowCount-1)*rowStride+(colCount-1)*colStride+1" + " (="
                    + (offset + (rowCount - 1) * rowStride + (colCount - 1) * colStride + 1) + ") < 0 not allowed!");
        }
        if (rowStart + rowCount > height) {
            throw new IllegalArgumentException("rowStart + rowCount > height" + " (=" + (rowStart + rowCount > height)
                    + ") > height not allowed!");
        }
        if (colStart + colCount > width) {
            throw new IllegalArgumentException(
                    "colStart + colCount > width" + " (=" + (colStart + colCount > width) + ") > width not allowed!");
        }

        Argument.assertNotGreaterThan(colStart + colCount, width, "colStart+colCount", "width");

        if (overlaps(rowCount, colCount, rowStride, colStride)) {}
    }

    private static boolean overlaps(int rowCount, int colCount, int rowStride, int colStride) {
        final int absRowStride = rowStride < 0 ? -rowStride : rowStride;
        final int absColStride = colStride < 0 ? -colStride : colStride;
        final int highestRowIndex = rowCount - 1;
        final int highestColIndex = colCount - 1;

        if (absRowStride * highestRowIndex < absColStride) {
            return false;
        } else if (absColStride * highestColIndex < absRowStride) {
            return false;
        } else {
            final int gcd = gcd(absRowStride, absColStride);
            final int rowConflictIndex = absColStride / gcd;
            final int colConflictIndex = absRowStride / gcd;

            return rowConflictIndex < rowCount && colConflictIndex < colCount;
        }
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }

        return a;
    }
}
