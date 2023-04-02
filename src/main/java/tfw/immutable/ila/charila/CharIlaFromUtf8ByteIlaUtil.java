package tfw.immutable.ila.charila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;

public final class CharIlaFromUtf8ByteIlaUtil {
	private CharIlaFromUtf8ByteIlaUtil() {
	}

	public static final int DEFAULT_BYTE_BUFFER_LENGTH = 1000;
	public static final int DEFAULT_MIN_CHAR_DELTA = 100;
	public static final int DEFAULT_MAX_INDEX_TABLE_LENGTH = 100000;

	static final int HOEHRMANN_UTF8_ACCEPT = 0;
	static final int[] HOEHRMANN_LOOKUP_TABLE = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 7, 7, 7, 7,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 2, 2, 2, 2, 2, 2,
			2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			3, 4, 3, 3, 11, 6, 6, 6, 5, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 0 };
	static final int[] HOEHRMANN_LOOKUP_TABLE2 = new int[] { 0, 12, 24, 36, 60, 96, 84, 12, 12, 12, 48, 72, 12, 12, 12,
			12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0, 12, 12, 12, 12, 12, 0, 12, 0, 12, 12, 12, 24, 12, 12, 12, 12, 12,
			24, 12, 24, 12, 12, 12, 12, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 12, 12, 24,
			12, 12, 12, 12, 12, 12, 12, 12, 12, 36, 12, 36, 12, 12, 12, 36, 12, 12, 12, 12, 12, 36, 12, 36, 12, 12, 12,
			36, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12 };

	public static int calculateByteBufferLength(final int byteBufferLength) {
		return (byteBufferLength > 0) ? byteBufferLength : DEFAULT_BYTE_BUFFER_LENGTH;
	}

	public static long calculateCharDelta(final int indexTableLength, final long utf8ByteIlaLength) {
		if (indexTableLength > 0) {
			return utf8ByteIlaLength / indexTableLength + ((utf8ByteIlaLength % indexTableLength == 0) ? 0 : 1);
		} else {
			final long minDeltaTableLength = utf8ByteIlaLength / DEFAULT_MIN_CHAR_DELTA;

			if (minDeltaTableLength <= DEFAULT_MAX_INDEX_TABLE_LENGTH) {
				return DEFAULT_MIN_CHAR_DELTA;
			} else {
				return utf8ByteIlaLength / DEFAULT_MAX_INDEX_TABLE_LENGTH
						+ ((utf8ByteIlaLength % DEFAULT_MAX_INDEX_TABLE_LENGTH == 0) ? 0 : 1);
			}
		}
	}

	public static int calculateByteIndexTableLength(final int indexTableLength, final long utf8ByteIlaLength,
			final long charDelta) {
		if (indexTableLength > 0) {
			return indexTableLength;
		} else {
			final long minDeltaTableLength = utf8ByteIlaLength / DEFAULT_MIN_CHAR_DELTA
					+ ((utf8ByteIlaLength % DEFAULT_MIN_CHAR_DELTA == 0) ? 0 : 1);

			if (minDeltaTableLength <= DEFAULT_MAX_INDEX_TABLE_LENGTH) {
				return (int) minDeltaTableLength;
			} else {
				return (int) (utf8ByteIlaLength / charDelta + ((utf8ByteIlaLength % charDelta == 0) ? 0 : 1));
			}
		}
	}

	public static long calculateCharLengthAndFillByteIndexTable(final ByteIla utf8ByteIla, final byte[] byteBuffer,
			final long charDelta, final long[] byteIndexTable) throws DataInvalidException {
		final long utf8ByteIlaLength = utf8ByteIla.length();

		long charLength = 0;
		int utf8State = 0;
		int utf8Character = 0;
		long firstByteIndex = 0;
		long charDeltaIndex = charDelta;
		int byteIndexTableIndex = 0;

		for (long byteIndex = 0; byteIndex < utf8ByteIlaLength;) {
			final long remainingBytes = utf8ByteIlaLength - byteIndex;
			final int bytesToGet = (int) ((byteBuffer.length <= remainingBytes) ? byteBuffer.length : remainingBytes);

			utf8ByteIla.toArray(byteBuffer, 0, byteIndex, bytesToGet);

			for (int byteBufferIndex = 0; byteBufferIndex < bytesToGet; byteBufferIndex++, byteIndex++) {
				final byte b = byteBuffer[byteBufferIndex];

				if (b > 0) {
					if (charDeltaIndex == charDelta) {
						byteIndexTable[byteIndexTableIndex++] = firstByteIndex;
						charDeltaIndex = 0;
					}

					charDeltaIndex++;
					charLength++;
					firstByteIndex = byteIndex + 1;
				} else {
					final int ub = b + 256;
					final int type = HOEHRMANN_LOOKUP_TABLE[ub];

					utf8Character = (utf8State != HOEHRMANN_UTF8_ACCEPT) ? (ub & 0x3F) | (utf8Character << 6)
							: (0xFF >> type) & (ub);
					utf8State = HOEHRMANN_LOOKUP_TABLE2[utf8State + type];

					if (utf8State == HOEHRMANN_UTF8_ACCEPT) {
						if (charDeltaIndex == charDelta) {
							byteIndexTable[byteIndexTableIndex++] = firstByteIndex;
							charDeltaIndex = 0;
						}
						if (utf8Character > 0xFFFF) {
							charDeltaIndex++;
							charLength++;

							if (charDeltaIndex == charDelta) {
								byteIndexTable[byteIndexTableIndex++] = firstByteIndex;
								charDeltaIndex = 0;
							}
						}

						charDeltaIndex++;
						charLength++;
						firstByteIndex = byteIndex + 1;
					}
				}
			}
		}

		return charLength;
	}

	public static long seekForward(final ByteIla utf8ByteIla, final byte[] byteBuffer, final long startCharIndex,
			final long startByteIndex, final long targetCharIndex) throws DataInvalidException {
		final long utf8ByteIlaLength = utf8ByteIla.length();

		long charIndex = startCharIndex;
		int utf8State = 0;
		int utf8Character = 0;
		long firstByteIndex = startByteIndex;

		for (long byteIndex = startByteIndex; byteIndex < utf8ByteIlaLength;) {
			final long remainingBytes = utf8ByteIlaLength - byteIndex;
			final int bytesToGet = (int) ((byteBuffer.length <= remainingBytes) ? byteBuffer.length : remainingBytes);

			utf8ByteIla.toArray(byteBuffer, 0, byteIndex, bytesToGet);

			for (int byteBufferIndex = 0; byteBufferIndex < bytesToGet; byteBufferIndex++, byteIndex++) {
				final byte b = byteBuffer[byteBufferIndex];

				if (b > 0) {
					if (charIndex == targetCharIndex) {
						return firstByteIndex;
					}

					charIndex++;
					firstByteIndex = byteIndex + 1;
				} else {
					final int ub = b + 256;
					final int type = HOEHRMANN_LOOKUP_TABLE[ub];

					utf8Character = (utf8State != HOEHRMANN_UTF8_ACCEPT) ? (ub & 0x3F) | (utf8Character << 6)
							: (0xFF >> type) & (ub);
					utf8State = HOEHRMANN_LOOKUP_TABLE2[utf8State + type];

					if (utf8State == HOEHRMANN_UTF8_ACCEPT) {
						if (charIndex == targetCharIndex) {
							return firstByteIndex;
						}
						if (utf8Character > 0xFFFF) {
							charIndex++;
							if (charIndex == targetCharIndex) {
								return firstByteIndex;
							}
						}
						charIndex++;
						firstByteIndex = byteIndex + 1;
					}
				}
			}
		}

		return -1;
	}
}
