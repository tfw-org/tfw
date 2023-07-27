package tfw.immutable.ilm.objectilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ObjectIlmUtil {
    private ObjectIlmUtil() {}

    public static Object[] toArray(final ObjectIlm ObjectIlm) throws DataInvalidException {
        Argument.assertNotGreaterThan(ObjectIlm.width(), Integer.MAX_VALUE, "width()", "native array size");
        Argument.assertNotGreaterThan(ObjectIlm.height(), Integer.MAX_VALUE, "height()", "native array size");

        return toArray(ObjectIlm, 0, 0, (int) ObjectIlm.height(), (int) ObjectIlm.width());
    }

    public static Object[] toArray(
            final ObjectIlm ObjectIlm, final long rowStart, final long columnStart, final int rowCount, int colCount)
            throws DataInvalidException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        Object[] result = new Object[rowCount * colCount];

        ObjectIlm.toArray(result, 0, rowStart, columnStart, rowCount, colCount);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
