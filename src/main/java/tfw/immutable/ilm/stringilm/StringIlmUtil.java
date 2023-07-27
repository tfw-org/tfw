package tfw.immutable.ilm.stringilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class StringIlmUtil {
    private StringIlmUtil() {}

    public static String[] toArray(final StringIlm StringIlm) throws DataInvalidException {
        Argument.assertNotGreaterThan(StringIlm.width(), Integer.MAX_VALUE, "width()", "native array size");
        Argument.assertNotGreaterThan(StringIlm.height(), Integer.MAX_VALUE, "height()", "native array size");

        return toArray(StringIlm, 0, 0, (int) StringIlm.height(), (int) StringIlm.width());
    }

    public static String[] toArray(
            final StringIlm StringIlm, final long rowStart, final long columnStart, final int rowCount, int colCount)
            throws DataInvalidException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        String[] result = new String[rowCount * colCount];

        StringIlm.toArray(result, 0, rowStart, columnStart, rowCount, colCount);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
