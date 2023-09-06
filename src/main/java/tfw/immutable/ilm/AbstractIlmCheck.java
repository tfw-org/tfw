package tfw.immutable.ilm;

import tfw.check.Argument;

public class AbstractIlmCheck {
    private AbstractIlmCheck() {}

    public static final void boundsCheck(
            long width,
            long height,
            int arrayLength,
            int offset,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount) {
        Argument.assertNotLessThan(arrayLength, 0, "arrayLength");
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertLessThan(offset, arrayLength, "offset", "arrayLength");
        Argument.assertNotLessThan(rowStart, 0, "rowStart");
        Argument.assertNotLessThan(colStart, 0, "colStart");
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        if (rowStart + rowCount > height) {
            throw new IllegalArgumentException("rowStart + rowCount > height" + " (=" + (rowStart + rowCount > height)
                    + ") > height not allowed!");
        }
        if (colStart + colCount > width) {
            throw new IllegalArgumentException(
                    "colStart + colCount > width" + " (=" + (colStart + colCount > width) + ") > width not allowed!");
        }

        Argument.assertNotGreaterThan(colStart + colCount, width, "colStart+colCount", "width");
    }
}
