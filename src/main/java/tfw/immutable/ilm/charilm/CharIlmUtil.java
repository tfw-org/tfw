package tfw.immutable.ilm.charilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class CharIlmUtil {
    private CharIlmUtil() {}

    public static char[] toArray(final CharIlm charIlm) throws DataInvalidException {
        Argument.assertNotGreaterThan(charIlm.width(), Integer.MAX_VALUE, "width()", "native array size");
        Argument.assertNotGreaterThan(charIlm.height(), Integer.MAX_VALUE, "height()", "native array size");

        return toArray(charIlm, 0, 0, (int) charIlm.height(), (int) charIlm.width());
    }

    public static char[] toArray(
            final CharIlm charIlm, final long rowStart, final long columnStart, final int rowCount, int colCount)
            throws DataInvalidException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        char[] result = new char[rowCount * colCount];

        charIlm.toArray(result, 0, rowStart, columnStart, rowCount, colCount);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
