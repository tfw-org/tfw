package tfw.immutable.ilm.byteilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class ByteIlmCircularCache {
    private ByteIlmCircularCache() {}

    public static ByteIlm create(ByteIlm byteIlm, int numRows) {
        Argument.assertNotNull(byteIlm, "byteIlm");
        Argument.assertGreaterThan(numRows, 0, "numRows");

        return new MyByteIlm(byteIlm, numRows);
    }

    private static class MyByteIlm extends AbstractByteIlm {
        private final ByteIlm byteIlm;
        private final int cacheLength;
        private byte[] buffer = new byte[0];
        private long cacheStart = 0;
        private long cacheEnd = 0;
        private final int cStride = 1;
        private final int rStride;
        private final int maxRows;

        public MyByteIlm(ByteIlm byteIlm, int numRows) {
            super(byteIlm.width(), byteIlm.height());

            this.byteIlm = byteIlm;
            this.maxRows = numRows;

            cacheLength = (int) byteIlm.width() * maxRows;
            rStride = (int) byteIlm.width();
        }

        @Override
        protected synchronized void toArrayImpl(
                byte[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws DataInvalidException {
            if (cacheStart == 0 && cacheEnd == 0 || rowStart > cacheEnd || rowStart + rowCount < cacheStart) {
                if (buffer.length == 0) {
                    buffer = new byte[cacheLength];
                }

                int rowStartOffset = (int) (rowStart % maxRows);
                int rowsToGet = byteIlm.height() > rowStart + maxRows ? maxRows : (int) (byteIlm.height() - rowStart);

                if (byteIlm.height() <= maxRows) {
                    byteIlm.toArray(buffer, 0, rStride, cStride, 0, colStart, (int) byteIlm.height(), colCount);
                    cacheStart = 0;
                    cacheEnd = byteIlm.height();

                    for (int i = 0; i < rowCount; i++) {
                        for (int j = 0; j < colCount; j++) {
                            array[offset + i * rowStride + j * colStride] =
                                    buffer[((i + rowStartOffset) % maxRows) * rStride + j * cStride];
                        }
                    }

                    return;
                }

                if (rowStart % maxRows == 0) {
                    byteIlm.toArray(
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

                    byteIlm.toArray(
                            buffer, first * rStride, rStride, cStride, rowStart, colStart, firstLength, colCount);

                    if (firstLength + first > rowCount) {
                        first = rowCount - firstLength;
                    }
                    if (first > 0) {
                        byteIlm.toArray(buffer, 0, rStride, cStride, rowStart + firstLength, colStart, first, colCount);
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
            }

            if (cacheStart <= rowStart && rowStart <= cacheEnd) {
                int newRowCount = (int) (rowStart + rowCount - cacheEnd);
                int rowStartOffset = (int) (rowStart % maxRows);
                int newRowStartOffset = (int) (cacheEnd % maxRows);

                newRowCount =
                        cacheEnd + newRowCount < byteIlm.height() ? newRowCount : (int) (byteIlm.height() - cacheEnd);

                if (newRowStartOffset + newRowCount <= maxRows) {
                    byteIlm.toArray(
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

                    byteIlm.toArray(
                            buffer,
                            newRowStartOffset * rStride,
                            rStride,
                            cStride,
                            cacheEnd,
                            colStart,
                            countA,
                            colCount);
                    byteIlm.toArray(buffer, 0, rStride, cStride, cacheEnd + countA, colStart, countB, colCount);
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
                    byteIlm.toArray(
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

                    byteIlm.toArray(buffer, end * rStride, rStride, cStride, rowStart, colStart, countA, colCount);

                    if (countB > 0) {
                        byteIlm.toArray(buffer, 0, rStride, cStride, rowStart + countA, colStart, countB, colCount);
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
