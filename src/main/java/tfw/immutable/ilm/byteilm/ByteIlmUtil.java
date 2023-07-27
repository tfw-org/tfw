package tfw.immutable.ilm.byteilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ByteIlmUtil {
    private ByteIlmUtil() {}

    public static byte[] toArray(final ByteIlm byteIlm) throws DataInvalidException {
        Argument.assertNotGreaterThan(byteIlm.width(), Integer.MAX_VALUE, "width()", "native array size");
        Argument.assertNotGreaterThan(byteIlm.height(), Integer.MAX_VALUE, "height()", "native array size");

        return toArray(byteIlm, 0, 0, (int) byteIlm.height(), (int) byteIlm.width());
    }

    public static byte[] toArray(
            final ByteIlm byteIlm, final long rowStart, final long columnStart, final int rowCount, int colCount)
            throws DataInvalidException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        byte[] result = new byte[rowCount * colCount];

        byteIlm.toArray(result, 0, rowStart, columnStart, rowCount, colCount);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
