package tfw.immutable.ilm.longilm;

import java.io.IOException;
import java.util.Arrays;
import tfw.check.Argument;

public class StridedLongIlmCircularCache {
    private StridedLongIlmCircularCache() {}

    public static StridedLongIlm create(StridedLongIlm stridedIlm, int numRows, long[] buffer) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");
        Argument.assertGreaterThan(numRows, 0, "numRows");
        Argument.assertNotNull(buffer, "buffer");

        return new MyLongIlm(stridedIlm, numRows, buffer);
    }

    private static class MyLongIlm extends AbstractStridedLongIlm {
        private final StridedLongIlm stridedIlm;
        private final int cacheLength;
        private long[] buffer;
        private long cacheStart = 0;
        private long cacheEnd = 0;
        private final int cStride = 1;
        private final int rStride;
        private final int maxRows;

        public MyLongIlm(StridedLongIlm stridedIlm, int numRows, long[] buffer) {
            super(stridedIlm.width(), stridedIlm.height());

            this.stridedIlm = stridedIlm;
            this.maxRows = numRows;
            this.buffer = buffer;

            cacheLength = (int) stridedIlm.width() * maxRows;
            rStride = (int) stridedIlm.width();
        }

        @Override
        protected synchronized void toArrayImpl(
                long[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws IOException {
            if (cacheStart == 0 && cacheEnd == 0 || rowStart > cacheEnd || rowStart + rowCount < cacheStart) {
                if (buffer.length == 0) {
                    buffer = Arrays.copyOf(buffer, cacheLength);
                }

                int rowStartOffset = (int) (rowStart % maxRows);
                int rowsToGet =
                        stridedIlm.height() > rowStart + maxRows ? maxRows : (int) (stridedIlm.height() - rowStart);

                if (stridedIlm.height() <= maxRows) {
                    stridedIlm.toArray(buffer, 0, rStride, cStride, 0, colStart, (int) stridedIlm.height(), colCount);
                    cacheStart = 0;
                    cacheEnd = stridedIlm.height();

                    for (int i = 0; i < rowCount; i++) {
                        for (int j = 0; j < colCount; j++) {
                            array[offset + i * rowStride + j * colStride] =
                                    buffer[((i + rowStartOffset) % maxRows) * rStride + j * cStride];
                        }
                    }

                    return;
                }

                if (rowStart % maxRows == 0) {
                    stridedIlm.toArray(
                            buffer,
                            rowStartOffset * rStride,
                            rStride,
                            cStride,
                            rowStart,
                            colStart,
                            rowsToGet,
                            colCount);
                } else {
                    int first = (int) (rowStart % maxRows);
                    int firstLength = maxRows - first;

                    if (firstLength > rowCount) {
                        firstLength = rowCount;
                    }

                    stridedIlm.toArray(
                            buffer, first * rStride, rStride, cStride, rowStart, colStart, firstLength, colCount);

                    if (firstLength + first > rowCount) {
                        first = rowCount - firstLength;
                    }
                    if (first > 0) {
                        stridedIlm.toArray(
                                buffer, 0, rStride, cStride, rowStart + firstLength, colStart, first, colCount);
                    }
                }

                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < colCount; j++) {
                        array[offset + i * rowStride + j * colStride] =
                                buffer[((i + rowStartOffset) % maxRows) * rStride + j * cStride];
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
                        array[offset + i * rowStride + j * colStride] =
                                buffer[((i + rowStartOffset) % maxRows) * rStride + j * cStride];
                    }
                }
                return;
            }

            if (cacheStart <= rowStart && rowStart <= cacheEnd) {
                int newRowCount = (int) (rowStart + rowCount - cacheEnd);
                int rowStartOffset = (int) (rowStart % maxRows);
                int newRowStartOffset = (int) (cacheEnd % maxRows);

                newRowCount = cacheEnd + newRowCount < stridedIlm.height()
                        ? newRowCount
                        : (int) (stridedIlm.height() - cacheEnd);

                if (newRowStartOffset + newRowCount <= maxRows) {
                    stridedIlm.toArray(
                            buffer,
                            newRowStartOffset * rStride,
                            rStride,
                            cStride,
                            cacheEnd,
                            colStart,
                            newRowCount,
                            colCount);
                } else {
                    int countA = maxRows - newRowStartOffset;
                    int countB = newRowCount - countA;

                    stridedIlm.toArray(
                            buffer,
                            newRowStartOffset * rStride,
                            rStride,
                            cStride,
                            cacheEnd,
                            colStart,
                            countA,
                            colCount);
                    stridedIlm.toArray(buffer, 0, rStride, cStride, cacheEnd + countA, colStart, countB, colCount);
                }

                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < colCount; j++) {
                        array[offset + i * rowStride + j * colStride] =
                                buffer[((i + rowStartOffset) % maxRows) * rStride + j * cStride];
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
                    stridedIlm.toArray(
                            buffer,
                            rowStartOffset * rStride,
                            rStride,
                            cStride,
                            cacheStart - newRowCount,
                            colStart,
                            newRowCount,
                            colCount);
                } else {
                    int end = newRowEndOffset - newRowCount + maxRows;
                    int countA = maxRows - end;
                    int countB = newRowCount - countA;

                    stridedIlm.toArray(buffer, end * rStride, rStride, cStride, rowStart, colStart, countA, colCount);

                    if (countB > 0) {
                        stridedIlm.toArray(buffer, 0, rStride, cStride, rowStart + countA, colStart, countB, colCount);
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
                            array[offset + i * rowStride + j * colStride] =
                                    buffer[((i + rowStartOffset) % maxRows) * rStride + j * cStride];
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
// AUTO GENERATED FROM TEMPLATE
