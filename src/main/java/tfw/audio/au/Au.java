package tfw.audio.au;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaConcatenate;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ila.byteila.ByteIlaSegment;
import tfw.immutable.ila.byteila.ByteIlaUtil;

/**
 * The Au class represents a valid AU formatted set of bytes along with
 * allowing easy access to the AU header fields and audio data.
 */
public final class Au {
    /**
     * A long representing the string ".snd".
     */
    public static final long SUN_MAGIC_NUMBER = 0x2e736e64;

    /**
     * A long representing the string "\000ds." (byte reversed).
     */
    public static final long DEC_MAGIC_NUMBER = 0x2e736400;

    /**
     * A long representing the string "dns." signifying that everything should
     * be read in "little endian" format (i.e. byte reversed).
     */
    public static final long REV_SUN_MAGIC_NUMBER = 0x646e732e;

    /**
     * A long representing the string ".sd\000" (byte reversed) signifying that
     * everything should be read in "little endian" format (i.e. byte reversed).
     */
    public static final long REV_DEC_MAGIC_NUMBER = 0x0064732e;

    public static final long ISDN_U_LAW_8_BIT = 1;
    public static final long LINEAR_8_BIT = 2;
    public static final long LINEAR_16_BIT = 3;
    public static final long LINEAR_24_BIT = 4;
    public static final long LINEAR_32_BIT = 5;
    public static final long IEEE_FLOATING_POINT_32_BIT = 6;
    public static final long IEEE_FLOATING_POINT_64_BIT = 7;
    public static final long CCITT_G_721_4_BIT_ADPCM = 23;
    public static final long CCITT_G_722_ADPCM = 24;
    public static final long CCITT_G_723_3_BIT_ADPCM = 25;
    public static final long CCITT_G_723_5_BIT_ADPCM = 26;
    public static final long ISDN_A_LAW_8_BIT = 27;
    public static final long UNKNOWN_DATA_SIZE = -1;

    private static final long MINIMUM_HEADER_LENGTH = 24;

    /**
     * A long that contains the AU header identifier constant.  This can be
     * either SUN_MAGIC_NUMBER (the one defined by the AU specification) or
     * DEC_MAGIC_NUMBER (done in the spirit of compatibility).
     */
    public final long magicNumber;

    /**
     * A long that contains the offset, in bytes, from the start of the file
     * to the beginning of the audio data.
     */
    public final long offset;

    /**
     * A long that contains the length, in bytes, of the audio data.  If this
     * length is unknown when the header is written, then it will be set to
     * UNKNOWN_DATA_SIZE.
     */
    public final long dataSize;

    /**
     * A long that is set to one of the ISDN, LINEAR, IEEE or CCITT constants
     * defined in this file.  The linear formats are signed integers, centered
     * at zero.  The floating-point formats are signed, zero-centered and
     * normalized to the unit value (-1.0 &lt;= x &lt;= 1.0).
     */
    public final long encoding;

    /**
     * A long that contains the audio sampling rate in samples per second.
     */
    public final long sampleRate;

    /**
     * A long that contains the number of interleaved data channels.
     */
    public final long numberOfChannels;

    /**
     * A ByteIla containing the optional annotation bytes.
     */
    public final ByteIla annotation;

    /**
     * A ByteIla containing the AU formatted bytes (i.e. AU header and audio
     * data).
     */
    public final ByteIla auByteData;

    /**
     * A ByteIla containing only the audio data bytes.
     */
    public final ByteIla audioData;

    /**
     * Constructs a newly allocated Au object from a ByteIla.
     *
     * @param byteIla the byteIla containing the AU formatted bytes.
     * @throws IOException if the byteIla has invalid data.
     * @throws IOException if a valid AU header is not found.
     */
    public Au(ByteIla byteIla) throws IOException {
        Argument.assertNotNull(byteIla, "byteIla");

        if (byteIla.length() < MINIMUM_HEADER_LENGTH) {
            throw new IOException("length < " + MINIMUM_HEADER_LENGTH + " not allowed!");
        }
        Argument.assertNotLessThan(byteIla.length(), MINIMUM_HEADER_LENGTH, "byteIla.length()");

        auByteData = byteIla;

        magicNumber = unsignedIntFromBytes(ByteIlaUtil.toArray(byteIla, 0, 4), 0, SUN_MAGIC_NUMBER);

        if (magicNumber != SUN_MAGIC_NUMBER
                && magicNumber != DEC_MAGIC_NUMBER
                && magicNumber != REV_SUN_MAGIC_NUMBER
                && magicNumber != REV_DEC_MAGIC_NUMBER) {
            throw new IOException("Unrecognized AU Magic Number!");
        }

        byte[] header = ByteIlaUtil.toArray(byteIla, 4, 20);

        offset = unsignedIntFromBytes(header, 0, magicNumber);

        if (offset < MINIMUM_HEADER_LENGTH) {
            throw new IOException("offset(" + offset + ") < " + MINIMUM_HEADER_LENGTH + " not allowed!");
        }
        if (offset > byteIla.length()) {
            throw new IOException("offset(" + offset + ") > length(" + byteIla.length() + ") not allowed!");
        }

        dataSize = unsignedIntFromBytes(header, 4, magicNumber);
        encoding = unsignedIntFromBytes(header, 8, magicNumber);
        sampleRate = unsignedIntFromBytes(header, 12, magicNumber);
        numberOfChannels = unsignedIntFromBytes(header, 16, magicNumber);
        annotation = ByteIlaSegment.create(byteIla, 24, offset - 24);
        audioData = ByteIlaSegment.create(byteIla, offset, byteIla.length() - offset);

        checkEncoding(encoding);
    }

    /**
     * Constructs a newly created, specification complient, Au object from
     * header information and audio data.
     * @param encoding encoding type as defined in Au class.
     * @param sampleRate audio sample rate in samples per second.
     * @param numberOfChannels number of interleaved channels.
     * @param annotation annotation bytes (length must be a multiple of 8).
     * @param audioData audio bytes.
     */
    public Au(long encoding, long sampleRate, long numberOfChannels, ByteIla annotation, ByteIla audioData)
            throws IOException {
        checkEncoding(encoding);
        Argument.assertGreaterThanOrEqualTo(sampleRate, 0, "sampleRate");
        Argument.assertGreaterThanOrEqualTo(numberOfChannels, 0, "numberOfChannels");
        Argument.assertNotNull(annotation, "annotation");
        Argument.assertEquals(annotation.length() % 8, 0, "annotation.length() % 8", "0");
        Argument.assertEquals(audioData.length() % numberOfChannels, 0, "audioData.length() % numberOfChannels", "0");
        Argument.assertNotNull(audioData, "audioData");

        byte[] header = new byte[(int) MINIMUM_HEADER_LENGTH];

        this.magicNumber = SUN_MAGIC_NUMBER;
        bytesFromUnsignedInt(header, 0, magicNumber);

        this.offset = MINIMUM_HEADER_LENGTH + annotation.length();
        bytesFromUnsignedInt(header, 4, offset);

        this.dataSize = audioData.length();
        bytesFromUnsignedInt(header, 8, dataSize);

        this.encoding = encoding;
        bytesFromUnsignedInt(header, 12, encoding);

        this.sampleRate = sampleRate;
        bytesFromUnsignedInt(header, 16, sampleRate);

        this.numberOfChannels = numberOfChannels;
        bytesFromUnsignedInt(header, 20, numberOfChannels);

        this.annotation = annotation;

        this.auByteData = ByteIlaConcatenate.create(
                ByteIlaConcatenate.create(ByteIlaFromArray.create(header), annotation), audioData);

        this.audioData = audioData;
    }

    private static void checkEncoding(long encoding) {
        if (encoding != ISDN_U_LAW_8_BIT
                && encoding != LINEAR_8_BIT
                && encoding != LINEAR_16_BIT
                && encoding != LINEAR_24_BIT
                && encoding != LINEAR_32_BIT
                && encoding != IEEE_FLOATING_POINT_32_BIT
                && encoding != IEEE_FLOATING_POINT_64_BIT
                && encoding != CCITT_G_721_4_BIT_ADPCM
                && encoding != CCITT_G_722_ADPCM
                && encoding != CCITT_G_723_3_BIT_ADPCM
                && encoding != CCITT_G_723_5_BIT_ADPCM
                && encoding != ISDN_A_LAW_8_BIT) {
            throw new IllegalArgumentException("Unknown encoding type: " + encoding);
        }
    }

    private static long unsignedIntFromBytes(byte[] bytes, int offset, long magicNumber) {
        if (magicNumber == SUN_MAGIC_NUMBER || magicNumber == DEC_MAGIC_NUMBER) {
            return ((bytes[offset] & 0xFF) << 24)
                    | ((bytes[offset + 1] & 0xFF) << 16)
                    | ((bytes[offset + 2] & 0xFF) << 8)
                    | ((bytes[offset + 3] & 0xFF));
        } else {
            return ((bytes[offset + 3] & 0xFF) << 24)
                    | ((bytes[offset + 2] & 0xFF) << 16)
                    | ((bytes[offset + 1] & 0xFF) << 8)
                    | ((bytes[offset] & 0xFF));
        }
    }

    private static void bytesFromUnsignedInt(byte[] bytes, int offset, long l) {
        bytes[offset] = (byte) ((l >> 24) & 0xFF);
        bytes[offset + 1] = (byte) ((l >> 16) & 0xFF);
        bytes[offset + 2] = (byte) ((l >> 8) & 0xFF);
        bytes[offset + 3] = (byte) (l & 0xFF);
    }
}
