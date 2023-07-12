package tfw.audio.wave;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaSegment;
import tfw.immutable.ila.byteila.ByteIlaSwap;
import tfw.immutable.ila.byteila.ByteIlaUtil;

public final class WaveUtil {
    private WaveUtil() {}

    public static int intFromSignedFourBytes(ByteIla byteIla, long offset, boolean swap) throws DataInvalidException {
        if (swap) {
            byteIla = ByteIlaSwap.create(byteIla, 4);
        }
        byte[] b = ByteIlaUtil.toArray(ByteIlaSegment.create(byteIla, offset, 4));
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        try {
            return dis.readInt();
        } catch (IOException ioe) {
            throw new DataInvalidException("intFromSignedFourBytes", ioe);
        }
    }

    public static int intFromUnsignedTwoBytes(ByteIla byteIla, long offset) throws DataInvalidException {
        byteIla = ByteIlaSwap.create(byteIla, 2);
        byte[] b = ByteIlaUtil.toArray(ByteIlaSegment.create(byteIla, offset, 2));
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        try {
            return dis.readUnsignedShort();
        } catch (IOException ioe) {
            throw new DataInvalidException("intFromUnsignedTwoBytes", ioe);
        }
    }
}
