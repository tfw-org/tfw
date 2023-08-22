package tfw.immutable.ilm.longilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class LongIlmUtil {
    private LongIlmUtil() {}

    public static long[] toArray(final LongIlm longIlm) throws DataInvalidException {
        Argument.assertNotGreaterThan(longIlm.width(), Integer.MAX_VALUE, "width()", "native array size");
        Argument.assertNotGreaterThan(longIlm.height(), Integer.MAX_VALUE, "height()", "native array size");

        return toArray(longIlm, 0, 0, (int) longIlm.height(), (int) longIlm.width());
    }

    public static long[] toArray(
            final LongIlm longIlm, final long rowStart, final long columnStart, final int rowCount, int colCount)
            throws DataInvalidException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        long[] result = new long[rowCount * colCount];

        longIlm.toArray(result, 0, rowStart, columnStart, rowCount, colCount);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE