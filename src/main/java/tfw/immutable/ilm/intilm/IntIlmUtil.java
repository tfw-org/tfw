package tfw.immutable.ilm.intilm;

import java.io.IOException;
import tfw.check.Argument;

public final class IntIlmUtil {
    private IntIlmUtil() {}

    public static int[] toArray(final IntIlm intIlm) throws IOException {
        Argument.assertNotGreaterThan(intIlm.width(), Integer.MAX_VALUE, "width()", "native array size");
        Argument.assertNotGreaterThan(intIlm.height(), Integer.MAX_VALUE, "height()", "native array size");

        return toArray(intIlm, 0, 0, (int) intIlm.height(), (int) intIlm.width());
    }

    public static int[] toArray(
            final IntIlm intIlm, final long rowStart, final long columnStart, final int rowCount, int colCount)
            throws IOException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        int[] result = new int[rowCount * colCount];

        intIlm.toArray(result, 0, rowStart, columnStart, rowCount, colCount);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
