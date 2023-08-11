package tfw.immutable.ilm;

import tfw.check.Argument;

public abstract class AbstractIlm implements ImmutableLongMatrix {
    protected final long width;
    protected final long height;

    protected AbstractIlm(long width, long height) {
        Argument.assertNotLessThan(width, 0, "width");
        Argument.assertNotLessThan(height, 0, "height");

        if (width == 0 && height != 0) throw new IllegalArgumentException("width == 0 && height != 0 not allowed!");
        if (height == 0 && width != 0) throw new IllegalArgumentException("height == 0 && width != 0 not allowed!");

        this.width = width;
        this.height = height;
    }

    public final long width() {
        return width;
    }

    public final long height() {
        return height;
    }

    protected final void boundsCheck(
            int arrayLength, int offset, long rowStart, long colStart, int rowCount, int colCount) {
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
