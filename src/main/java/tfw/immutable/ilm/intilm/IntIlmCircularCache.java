package tfw.immutable.ilm.intilm;

import java.io.IOException;
import tfw.check.Argument;

public class IntIlmCircularCache {
    private IntIlmCircularCache() {}

    public static IntIlm create(IntIlm intIlm, int numRows) throws IOException {
        Argument.assertNotNull(intIlm, "intIlm");
        Argument.assertGreaterThan(numRows, 0, "numRows");

        return new MyIntIlm(intIlm, numRows);
    }

    private static class MyIntIlm extends AbstractIntIlm {
        private final IntIlm intIlm;
        private final int cacheLength;
        private int[] buffer = new int[0];
        private long cacheStart = 0;
        private long cacheEnd = 0;
        private final int maxRows;

        public MyIntIlm(IntIlm intIlm, int numRows) throws IOException {
            this.intIlm = intIlm;
            this.maxRows = numRows;

            cacheLength = (int) intIlm.width() * maxRows;
        }

        @Override
        protected long widthImpl() throws IOException {
            return intIlm.width();
        }

        @Override
        protected long heightImpl() throws IOException {
            return intIlm.height();
        }

        @Override
        protected synchronized void getImpl(
                int[] array, int offset, long rowStart, long colStart, int rowCount, int colCount) throws IOException {
            if (cacheStart == 0 && cacheEnd == 0 || rowStart > cacheEnd || rowStart + rowCount < cacheStart) {
                if (buffer.length == 0) {
                    buffer = new int[cacheLength];
                }

                int rowStartOffset = (int) (rowStart % maxRows);
                int rowsToGet = intIlm.height() > rowStart + maxRows ? maxRows : (int) (intIlm.height() - rowStart);

                if (intIlm.height() <= maxRows) {
                    intIlm.get(buffer, 0, 0, colStart, (int) intIlm.height(), colCount);
                    cacheStart = 0;
                    cacheEnd = intIlm.height();

                    for (int i = 0; i < rowCount; i++) {
                        for (int j = 0; j < colCount; j++) {
                            array[offset + i * colCount + j] = buffer[((i + rowStartOffset) % maxRows) * colCount + j];
                        }
                    }

                    return;
                }

                if (rowStart % maxRows == 0) {
                    intIlm.get(buffer, rowStartOffset * colCount, rowStart, colStart, rowsToGet, colCount);
                } else {
                    int first = (int) (rowStart % maxRows);
                    int firstLength = maxRows - first;

                    if (firstLength > rowCount) {
                        firstLength = rowCount;
                    }

                    intIlm.get(buffer, first * colCount, rowStart, colStart, firstLength, colCount);

                    if (firstLength + first > rowCount) {
                        first = rowCount - firstLength;
                    }
                    if (first > 0) {
                        intIlm.get(buffer, 0, rowStart + firstLength, colStart, first, colCount);
                    }
                }

                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < colCount; j++) {
                        array[offset + i * colCount + j] = buffer[((i + rowStartOffset) % maxRows) * colCount + j];
                    }
                }

                cacheStart = rowStart;
                cacheEnd = rowStart + maxRows;

                return;
            }

            if (cacheStart <= rowStart && rowStart + rowCount <= cacheEnd) {
                int rowStartOffset = (int) (rowStart % maxRows);

                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < colCount; j++) {
                        array[offset + i * colCount + j] = buffer[((i + rowStartOffset) % maxRows) * colCount + j];
                    }
                }
            }

            if (cacheStart <= rowStart && rowStart <= cacheEnd) {
                int newRowCount = (int) (rowStart + rowCount - cacheEnd);
                int rowStartOffset = (int) (rowStart % maxRows);
                int newRowStartOffset = (int) (cacheEnd % maxRows);

                newRowCount =
                        cacheEnd + newRowCount < intIlm.height() ? newRowCount : (int) (intIlm.height() - cacheEnd);

                if (newRowStartOffset + newRowCount <= maxRows) {
                    intIlm.get(buffer, newRowStartOffset * colCount, cacheEnd, colStart, newRowCount, colCount);
                } else {
                    int countA = maxRows - newRowStartOffset;
                    int countB = newRowCount - countA;

                    intIlm.get(buffer, newRowStartOffset * colCount, cacheEnd, colStart, countA, colCount);
                    intIlm.get(buffer, 0, cacheEnd + countA, colStart, countB, colCount);
                }

                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < colCount; j++) {
                        array[offset + i * colCount + j] = buffer[((i + rowStartOffset) % maxRows) * colCount + j];
                    }
                }

                cacheEnd += newRowCount;
                cacheStart += newRowCount;

                return;
            }

            if (cacheStart <= rowStart + rowCount && rowStart + rowCount <= cacheEnd) {
                int newRowCount = (int) (cacheStart - rowStart);
                int rowStartOffset = (int) (rowStart % maxRows);
                int newRowEndOffset = (int) (cacheStart % maxRows);

                if (rowStartOffset + newRowCount <= maxRows) {
                    intIlm.get(
                            buffer,
                            rowStartOffset * colCount,
                            cacheStart - newRowCount,
                            colStart,
                            newRowCount,
                            colCount);
                } else {
                    int end = newRowEndOffset - newRowCount + maxRows;
                    int countA = maxRows - end;
                    int countB = newRowCount - countA;

                    intIlm.get(buffer, end * colCount, rowStart, colStart, countA, colCount);

                    if (countB > 0) {
                        intIlm.get(buffer, 0, rowStart + countA, colStart, countB, colCount);
                    } else {
                        System.out.println("maxRows = " + maxRows);
                        System.out.println("countA = " + countA);
                        System.out.println("countB = " + countB);
                        System.out.println("newRowCount = " + newRowCount);
                        System.out.println("newRowEndOffset = " + newRowEndOffset);
                        System.out.println("rowStartOffset = " + rowStartOffset);
                    }

                    for (int i = 0; i < rowCount; i++) {
                        for (int j = 0; j < colCount; j++) {
                            array[offset + i * colCount + j] = buffer[((i + rowStartOffset) % maxRows) * colCount + j];
                        }
                    }

                    cacheEnd -= newRowCount;
                    cacheStart -= newRowCount;
                }

                return;
            }
        }
    }
}
