package tfw.audio.au;

import tfw.audio.shortila.LinearShortIlaFromALawByteIla;
import tfw.audio.shortila.LinearShortIlaFromMuLawByteIla;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaSwap;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastByteIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastFloatIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastIntIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastShortIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromLongIla;
import tfw.immutable.ila.doubleila.DoubleIlaScalarMultiply;
import tfw.immutable.ila.floatila.FloatIlaFromIntIla;
import tfw.immutable.ila.intila.IntIlaFromByteIla;
import tfw.immutable.ila.longila.LongIlaFromByteIla;
import tfw.immutable.ila.shortila.ShortIlaFromByteIla;

public class NormalizedDoubleIlaFromAuAudioData {
    private NormalizedDoubleIlaFromAuAudioData() {}

    public static DoubleIla create(ByteIla auAudioData, long auMagicNumber, long auEncoding) {
        if (auEncoding == Au.ISDN_U_LAW_8_BIT) {
            return DoubleIlaScalarMultiply.create(
                    DoubleIlaFromCastShortIla.create(LinearShortIlaFromMuLawByteIla.create(auAudioData)),
                    1.0 / (Math.pow(2.0, 15.0) - 1.0));
        } else if (auEncoding == Au.LINEAR_8_BIT) {
            return DoubleIlaScalarMultiply.create(
                    DoubleIlaFromCastByteIla.create(auAudioData), 1.0 / (Math.pow(2.0, 7.0) - 1.0));
        } else if (auEncoding == Au.LINEAR_16_BIT) {
            if (auMagicNumber == Au.REV_SUN_MAGIC_NUMBER || auMagicNumber == Au.REV_DEC_MAGIC_NUMBER) {
                auAudioData = ByteIlaSwap.create(auAudioData, 2);
            }

            return DoubleIlaScalarMultiply.create(
                    DoubleIlaFromCastShortIla.create(ShortIlaFromByteIla.create(auAudioData)),
                    1.0 / (Math.pow(2.0, 15.0) - 1.0));
        } else if (auEncoding == Au.LINEAR_32_BIT) {
            if (auMagicNumber == Au.REV_SUN_MAGIC_NUMBER || auMagicNumber == Au.REV_DEC_MAGIC_NUMBER) {
                auAudioData = ByteIlaSwap.create(auAudioData, 4);
            }

            return DoubleIlaScalarMultiply.create(
                    DoubleIlaFromCastIntIla.create(IntIlaFromByteIla.create(auAudioData)),
                    1.0 / (Math.pow(2.0, 31.0) - 1.0));
        } else if (auEncoding == Au.IEEE_FLOATING_POINT_32_BIT) {
            if (auMagicNumber == Au.REV_SUN_MAGIC_NUMBER || auMagicNumber == Au.REV_DEC_MAGIC_NUMBER) {
                auAudioData = ByteIlaSwap.create(auAudioData, 4);
            }

            return DoubleIlaFromCastFloatIla.create(FloatIlaFromIntIla.create(IntIlaFromByteIla.create(auAudioData)));
        } else if (auEncoding == Au.IEEE_FLOATING_POINT_64_BIT) {
            if (auMagicNumber == Au.REV_SUN_MAGIC_NUMBER || auMagicNumber == Au.REV_DEC_MAGIC_NUMBER) {
                auAudioData = ByteIlaSwap.create(auAudioData, 8);
            }

            return DoubleIlaFromLongIla.create(LongIlaFromByteIla.create(auAudioData));
        } else if (auEncoding == Au.ISDN_A_LAW_8_BIT) {
            return DoubleIlaScalarMultiply.create(
                    DoubleIlaFromCastShortIla.create(LinearShortIlaFromALawByteIla.create(auAudioData)),
                    1.0 / (Math.pow(2.0, 15.0) - 1.0));
        }

        throw new IllegalArgumentException("Unsupported Au encoding type: " + auEncoding);
    }
}
