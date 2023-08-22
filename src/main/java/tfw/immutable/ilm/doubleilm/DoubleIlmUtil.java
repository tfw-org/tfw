package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.check.Argument;

public final class DoubleIlmUtil {
    private DoubleIlmUtil() {}

    public static double[] toArray(final DoubleIlm doubleIlm) throws IOException {
        Argument.assertNotGreaterThan(doubleIlm.width(), Integer.MAX_VALUE, "width()", "native array size");
        Argument.assertNotGreaterThan(doubleIlm.height(), Integer.MAX_VALUE, "height()", "native array size");

        return toArray(doubleIlm, 0, 0, (int) doubleIlm.height(), (int) doubleIlm.width());
    }

    public static double[] toArray(
            final DoubleIlm doubleIlm, final long rowStart, final long columnStart, final int rowCount, int colCount)
            throws IOException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        double[] result = new double[rowCount * colCount];

        doubleIlm.toArray(result, 0, rowStart, columnStart, rowCount, colCount);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
