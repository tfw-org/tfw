package tfw.immutable.ilm.longilm;

import java.io.IOException;
import tfw.check.Argument;

public final class LongIlmUtil {
    private LongIlmUtil() {}

    public static long[] toArray(final LongIlm longIlm) throws IOException {
        Argument.assertNotGreaterThan(longIlm.width(), Integer.MAX_VALUE, "width()", "native array size");
        Argument.assertNotGreaterThan(longIlm.height(), Integer.MAX_VALUE, "height()", "native array size");

        return toArray(longIlm, 0, 0, (int) longIlm.height(), (int) longIlm.width());
    }

    public static long[] toArray(
            final LongIlm longIlm, final long rowStart, final long columnStart, final int rowCount, int colCount)
            throws IOException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        long[] result = new long[rowCount * colCount];

        longIlm.get(result, 0, rowStart, columnStart, rowCount, colCount);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
