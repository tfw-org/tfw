package tfw.immutable.ilm.shortilm;

import java.io.IOException;
import tfw.check.Argument;

public final class ShortIlmUtil {
    private ShortIlmUtil() {}

    public static short[] toArray(final ShortIlm shortIlm) throws IOException {
        Argument.assertNotGreaterThan(shortIlm.width(), Integer.MAX_VALUE, "width()", "native array size");
        Argument.assertNotGreaterThan(shortIlm.height(), Integer.MAX_VALUE, "height()", "native array size");

        return toArray(shortIlm, 0, 0, (int) shortIlm.height(), (int) shortIlm.width());
    }

    public static short[] toArray(
            final ShortIlm shortIlm, final long rowStart, final long columnStart, final int rowCount, int colCount)
            throws IOException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        short[] result = new short[rowCount * colCount];

        shortIlm.get(result, 0, rowStart, columnStart, rowCount, colCount);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
