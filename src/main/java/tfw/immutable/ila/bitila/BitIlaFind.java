package tfw.immutable.ila.bitila;

import tfw.check.Argument;

public final class BitIlaFind {
    private BitIlaFind() {}

    public static long find(BitIla bitIla, long offset, long length, boolean[] pattern, long[] patternLocations) {
        Argument.assertNotNull(bitIla, "bitIla");
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotNull(pattern, "pattern");
        Argument.assertNotLessThan(pattern.length, 1, "pattern.length");
        Argument.assertNotNull(patternLocations, "patternLocations");
        Argument.assertNotLessThan(patternLocations.length, 1, "patternLocations.length");

        return -1;
    }

    public static long find(long[] array, long offset, long length, boolean[] pattern, long[] patternLocations) {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotNull(pattern, "pattern");
        Argument.assertNotLessThan(pattern.length, 1, "pattern.length");
        Argument.assertNotNull(patternLocations, "patternLocations");
        Argument.assertNotLessThan(patternLocations.length, 1, "patternLocations.length");

        final int patLen = pattern.length;
        final long allOnes = -1L;

        long len = Long.SIZE;
        long newLen = 0;
        long position = -1;

        if (length > 0 && length >= patLen) {
            if (patLen <= 0) {
                position = offset;
            } else {
                long maxPos = 0;

                for (int i = 0; i < patternLocations.length; i++) {
                    final long l = patternLocations[i];

                    if (l < 0) {
                        throw new IllegalArgumentException();
                    }
                    if (l > maxPos) {
                        maxPos = l;
                    }
                }

                final long bufLen = maxPos + 1;
                final long numBits = length - bufLen + 1;

                while (newLen < numBits && position < 0) {
                    if (newLen + len > numBits) {
                        len = numBits - newLen;
                    }

                    final long invLen = Long.SIZE - len;

                    long frag = allOnes << invLen;

                    if (len > 0) {
                        int i = 0;

                        while (frag != 0 && i < patLen) {
                            final long off = offset + patternLocations[i];
                            final int idx = (int) (off / Long.SIZE);
                            final int offsetMod64 = (int) (off % Long.SIZE);

                            long temp = array[idx] << offsetMod64;

                            if (offsetMod64 + len > Long.SIZE) {
                                temp |= array[idx + 1] >>> Long.SIZE - offsetMod64;
                            }
                            temp = temp >>> invLen;
                            temp = temp << invLen;

                            if (!pattern[i]) {
                                temp = ~temp;
                            }
                            frag &= temp;
                            i++;
                        }
                        if (frag != 0) {
                            int j = Long.SIZE - 1;
                            while ((frag >> j & 1L) == 0) {
                                j--;
                            }
                            position = offset + Long.SIZE - 1 - j;
                        }
                        offset += len;
                        newLen += len;
                    }
                }
            }
        }

        return position;
    }
}
