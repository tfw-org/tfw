package tfw.immutable.ilm.booleanilm;

import java.io.IOException;
import tfw.check.Argument;

public final class BooleanIlmUtil {
    private BooleanIlmUtil() {}

    public static boolean[] toArray(final BooleanIlm booleanIlm) throws IOException {
        Argument.assertNotGreaterThan(booleanIlm.width(), Integer.MAX_VALUE, "width()", "native array size");
        Argument.assertNotGreaterThan(booleanIlm.height(), Integer.MAX_VALUE, "height()", "native array size");

        return toArray(booleanIlm, 0, 0, (int) booleanIlm.height(), (int) booleanIlm.width());
    }

    public static boolean[] toArray(
            final BooleanIlm booleanIlm, final long rowStart, final long columnStart, final int rowCount, int colCount)
            throws IOException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        boolean[] result = new boolean[rowCount * colCount];

        booleanIlm.get(result, 0, rowStart, columnStart, rowCount, colCount);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
