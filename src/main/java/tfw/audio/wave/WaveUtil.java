package tfw.audio.wave;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaSegment;
import tfw.immutable.ila.byteila.ByteIlaSwap;
import tfw.immutable.ila.byteila.ByteIlaUtil;

public final class WaveUtil {
    private WaveUtil() {}

    public static int intFromSignedFourBytes(
            final ByteIla byteIla, final long offset, final boolean swap, final int bufferSize) throws IOException {
        final ByteIla swapByteIla = swap ? ByteIlaSwap.create(byteIla, 4, bufferSize) : byteIla;
        byte[] b = ByteIlaUtil.toArray(ByteIlaSegment.create(swapByteIla, offset, 4));
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        try {
            return dis.readInt();
        } catch (IOException ioe) {
            throw new IOException("intFromSignedFourBytes", ioe);
        }
    }

    public static int intFromUnsignedTwoBytes(final ByteIla byteIla, final long offset, final int bufferSize)
            throws IOException {
        final ByteIla swapByteIla = ByteIlaSwap.create(byteIla, 2, bufferSize);
        byte[] b = ByteIlaUtil.toArray(ByteIlaSegment.create(swapByteIla, offset, 2));
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        try {
            return dis.readUnsignedShort();
        } catch (IOException ioe) {
            throw new IOException("intFromUnsignedTwoBytes", ioe);
        }
    }
}
