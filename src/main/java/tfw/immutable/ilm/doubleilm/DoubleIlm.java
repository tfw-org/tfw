package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface DoubleIlm extends ImmutableLongMatrix {
    void get(final double[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
