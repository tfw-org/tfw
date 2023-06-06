package tfw.immutable.ilm.doubleilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class SegmentDoubleIlm {
    private SegmentDoubleIlm() {}

    public static DoubleIlm create(
            DoubleIlm doubleIlm, long startingRow, long startingColumn, long numberOfRows, long numberOfColumns) {
        Argument.assertNotNull(doubleIlm, "doubleIlm");
        Argument.assertNotLessThan(startingRow, 0, "startingRow");
        Argument.assertNotLessThan(startingColumn, 0, "startingColumn");
        Argument.assertNotLessThan(numberOfRows, 0, "numberOfRows");
        Argument.assertNotLessThan(numberOfColumns, 0, "numberOfColumns");
        Argument.assertNotGreaterThan(
                startingRow + numberOfRows, doubleIlm.height(), "startingRow + numberOfRows", "doubleIlm.height()");
        Argument.assertNotGreaterThan(
                startingColumn + numberOfColumns,
                doubleIlm.width(),
                "startingColumn + numberOfColumns,",
                "doubleIlm.width()");

        return new MyDoubleIlm(doubleIlm, startingRow, startingColumn, numberOfRows, numberOfColumns);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm {
        private final DoubleIlm doubleIlm;
        private final long startingRow;
        private final long startingColumn;

        public MyDoubleIlm(
                DoubleIlm doubleIlm, long startingRow, long startingColumn, long numberOfRows, long numberOfColumns) {
            super(numberOfColumns, numberOfRows);

            this.doubleIlm = doubleIlm;
            this.startingRow = startingRow;
            this.startingColumn = startingColumn;
        }

        @Override
        protected void toArrayImpl(
                double[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws DataInvalidException {
            doubleIlm.toArray(
                    array,
                    offset,
                    rowStride,
                    colStride,
                    startingRow + rowStart,
                    startingColumn + colStart,
                    rowCount,
                    colCount);
        }
    }
}
