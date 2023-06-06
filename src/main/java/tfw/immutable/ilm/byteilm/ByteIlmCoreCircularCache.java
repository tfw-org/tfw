package tfw.immutable.ilm.byteilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class ByteIlmCoreCircularCache {
    private ByteIlmCoreCircularCache() {}

    public static ByteIlm create(ByteIlm byteIlm, int numRows, ByteIlm byteIlmCoreCircularCache) {
        Argument.assertNotNull(byteIlm, "byteIlm");
        Argument.assertGreaterThan(numRows, 0, "numRows");

        return new MyByteIlm(byteIlm, numRows, byteIlmCoreCircularCache);
    }

    private static class MyByteIlm extends AbstractByteIlm {
        private final Core core;

        MyByteIlm(ByteIlm byteIlm, int numRows, ByteIlm byteIlmCoreCircularCache) {
            super(byteIlm.width(), byteIlm.height());

            if (byteIlmCoreCircularCache instanceof MyByteIlm) {
                core = ((MyByteIlm) byteIlmCoreCircularCache).core;
                core.setByteIlm(byteIlm);
            } else {
                core = new Core(byteIlm, numRows);
            }
        }

        @Override
        protected void toArrayImpl(
                byte[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws DataInvalidException {
            core.toArrayImpl(array, offset, rowStride, colStride, rowStart, colStart, rowCount, colCount);
        }
    }

    private static class Core {
        private final Object lock = new Object();
        private ByteIlm byteIlm;
        private final int cacheLength;
        private byte[] buffer = new byte[0];
        private long cacheStart = 0;
        private long cacheEnd = 0;
        private final int cStride = 1;
        private final int rStride;
        private final int maxRows;

        public Core(ByteIlm byteIlm, int numRows) {
            this.byteIlm = byteIlm;
            this.maxRows = numRows;

            cacheLength = (int) byteIlm.width() * maxRows;
            rStride = (int) byteIlm.width();
        }

        public void setByteIlm(ByteIlm byteIlm) {
            synchronized (lock) {
                this.byteIlm = byteIlm;
            }
        }

        public void toArrayImpl(
                byte[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws DataInvalidException {
            synchronized (lock) {
                if (buffer.length == 0) {
                    buffer = new byte[cacheLength];
                }
                if (cacheStart == 0 && cacheEnd == 0 || rowStart > cacheEnd || rowStart + rowCount < cacheStart) {
                    int rowStartOffset = (int) (rowStart % maxRows);

                    if (rowStart % maxRows == 0) {
                        byteIlm.toArray(
                                buffer,
                                rowStartOffset * rStride,
                                rStride,
                                cStride,
                                rowStart,
                                colStart,
                                rowCount,
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
                            byteIlm.toArray(
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
                    cacheEnd = rowStart + rowCount;

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

                    newRowCount = cacheEnd + newRowCount < byteIlm.height()
                            ? newRowCount
                            : (int) (byteIlm.height() - cacheEnd);

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

                    cacheEnd = rowStart + rowCount;
                    cacheStart = rowStart;

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

                        cacheEnd = rowStart + rowCount;
                        cacheStart = rowStart;
                    }

                    return;
                }
            }
        }
    }
}
