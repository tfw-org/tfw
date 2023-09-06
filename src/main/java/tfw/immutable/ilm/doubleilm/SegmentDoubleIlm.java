package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.check.Argument;

public class SegmentDoubleIlm {
    private SegmentDoubleIlm() {}

    public static DoubleIlm create(
            DoubleIlm doubleIlm, long startingRow, long startingColumn, long numberOfRows, long numberOfColumns)
            throws IOException {
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
        private final long numberOfRows;
        private final long numberOfColumns;

        public MyDoubleIlm(
                DoubleIlm doubleIlm, long startingRow, long startingColumn, long numberOfRows, long numberOfColumns) {
            this.doubleIlm = doubleIlm;
            this.startingRow = startingRow;
            this.startingColumn = startingColumn;
            this.numberOfRows = numberOfRows;
            this.numberOfColumns = numberOfColumns;
        }

        @Override
        protected long widthImpl() {
            return numberOfColumns;
        }

        @Override
        protected long heightImpl() {
            return numberOfRows;
        }

        @Override
        protected void toArrayImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            doubleIlm.toArray(array, offset, startingRow + rowStart, startingColumn + colStart, rowCount, colCount);
        }
    }
}
