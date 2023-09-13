package tfw.immutable.ilm.shortilm;

import java.io.IOException;
import java.util.Arrays;
import tfw.check.Argument;

public class StridedShortIlmCircularCache {
    private StridedShortIlmCircularCache() {}

    public static StridedShortIlm create(final StridedShortIlm stridedIlm, final int numRows, short[] buffer)
            throws IOException {
        Argument.assertNotNull(stridedIlm, "stridedIlm");
        Argument.assertGreaterThan(numRows, 0, "numRows");
        Argument.assertNotNull(buffer, "buffer");

        return new MyShortIlm(stridedIlm, numRows, buffer);
    }

    private static class MyShortIlm extends AbstractStridedShortIlm {
        private final StridedShortIlm stridedIlm;
        private final int cacheLength;
        private short[] buffer;
        private long cacheStart = 0;
        private long cacheEnd = 0;
        private final int cStride = 1;
        private final int rStride;
        private final int maxRows;

        public MyShortIlm(StridedShortIlm stridedIlm, int numRows, short[] buffer) throws IOException {
            this.stridedIlm = stridedIlm;
            this.maxRows = numRows;
            this.buffer = buffer;

            cacheLength = (int) stridedIlm.width() * maxRows;
            rStride = (int) stridedIlm.width();
        }

        @Override
        protected long widthImpl() throws IOException {
            return stridedIlm.width();
        }

        @Override
        protected long heightImpl() throws IOException {
            return stridedIlm.height();
        }

        @Override
        protected synchronized void getImpl(
                short[] array,
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
                    stridedIlm.get(buffer, 0, rStride, cStride, 0, colStart, (int) stridedIlm.height(), colCount);
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
                    stridedIlm.get(
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

                    stridedIlm.get(
                            buffer, first * rStride, rStride, cStride, rowStart, colStart, firstLength, colCount);

                    if (firstLength + first > rowCount) {
                        first = rowCount - firstLength;
                    }
                    if (first > 0) {
                        stridedIlm.get(buffer, 0, rStride, cStride, rowStart + firstLength, colStart, first, colCount);
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
                    stridedIlm.get(
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

                    stridedIlm.get(
                            buffer,
                            newRowStartOffset * rStride,
                            rStride,
                            cStride,
                            cacheEnd,
                            colStart,
                            countA,
                            colCount);
                    stridedIlm.get(buffer, 0, rStride, cStride, cacheEnd + countA, colStart, countB, colCount);
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
                    stridedIlm.get(
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

                    stridedIlm.get(buffer, end * rStride, rStride, cStride, rowStart, colStart, countA, colCount);

                    if (countB > 0) {
                        stridedIlm.get(buffer, 0, rStride, cStride, rowStart + countA, colStart, countB, colCount);
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
