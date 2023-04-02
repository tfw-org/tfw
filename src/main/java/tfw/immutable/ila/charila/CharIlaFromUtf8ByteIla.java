package tfw.immutable.ila.charila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;

public final class CharIlaFromUtf8ByteIla {
	private CharIlaFromUtf8ByteIla() {
	}

	public static CharIla create(final ByteIla utf8ByteIla, final int byteBufferLength, final int maxIndexTableLength)
			throws DataInvalidException {
		return new MyCharIla(utf8ByteIla, byteBufferLength, maxIndexTableLength);
	}

	private static class MyCharIla implements CharIla {
		private final ByteIla utf8ByteIla;
		private final byte[] byteBuffer;
		private final long charDelta;
		private final long[] byteIndexTable;
		private final long charLength;

		private long nextCharIndex = -1;
		private long nextByteIndex = -1;

		public MyCharIla(final ByteIla utf8ByteIla, final int byteBufferLength, final int indexTableLength)
				throws DataInvalidException {
			this.utf8ByteIla = utf8ByteIla;
			this.byteBuffer = new byte[CharIlaFromUtf8ByteIlaUtil.calculateByteBufferLength(byteBufferLength)];
			this.charDelta = CharIlaFromUtf8ByteIlaUtil.calculateCharDelta(indexTableLength, utf8ByteIla.length());
			this.byteIndexTable = new long[CharIlaFromUtf8ByteIlaUtil.calculateByteIndexTableLength(indexTableLength,
					utf8ByteIla.length(), charDelta)];
			this.charLength = CharIlaFromUtf8ByteIlaUtil.calculateCharLengthAndFillByteIndexTable(utf8ByteIla,
					byteBuffer, charDelta, byteIndexTable);
		}

		@Override
		public long length() {
			return charLength;
		}

		@Override
		public void toArray(char[] array, int offset, long start, int length) throws DataInvalidException {
			final int byteIndexTableIndex = (int) (start / charDelta);
			final long startByteIndex = byteIndexTable[byteIndexTableIndex];
			final long startCharIndex = byteIndexTableIndex * charDelta;
			final int lastArrayIndex = offset + length - 1;

			int utf8State = 0;
			int utf8Character = 0;
			int arrayIndex = offset;
			long byteIndex = (start == nextCharIndex) ? nextByteIndex
					: CharIlaFromUtf8ByteIlaUtil.seekForward(utf8ByteIla, byteBuffer, startCharIndex, startByteIndex,
							start);

			while (arrayIndex <= lastArrayIndex) {
				final long remainingBytes = utf8ByteIla.length() - byteIndex;
				final int bytesToGet = (int) ((byteBuffer.length < remainingBytes) ? byteBuffer.length
						: remainingBytes);

				utf8ByteIla.toArray(byteBuffer, 0, byteIndex, bytesToGet);

				for (int byteBufferIndex = 0; (byteBufferIndex < bytesToGet)
						&& (arrayIndex <= lastArrayIndex); byteBufferIndex++, byteIndex++) {
					final byte b = byteBuffer[byteBufferIndex];

					if (b > 0) {
						array[arrayIndex++] = (char) b;
					} else {
						final int ub = b + 256;
						final int type = CharIlaFromUtf8ByteIlaUtil.HOEHRMANN_LOOKUP_TABLE[ub];

						utf8Character = (utf8State != CharIlaFromUtf8ByteIlaUtil.HOEHRMANN_UTF8_ACCEPT)
								? (ub & 0x3F) | (utf8Character << 6)
								: (0xFF >> type) & (ub);

						utf8State = CharIlaFromUtf8ByteIlaUtil.HOEHRMANN_LOOKUP_TABLE2[utf8State + type];

						if (utf8State == CharIlaFromUtf8ByteIlaUtil.HOEHRMANN_UTF8_ACCEPT) {
							if (utf8Character > 0xFFFF) {
								array[arrayIndex++] = (char) (0xD7C0 + (utf8Character >> 10));
								array[arrayIndex++] = (char) (0xDC00 + (utf8Character & 0x3FF));
							} else {
								array[arrayIndex++] = (char) utf8Character;
							}
						}
					}
				}
			}

			nextCharIndex = start + length;
			nextByteIndex = byteIndex;
		}

		public final char[] toArray() throws DataInvalidException {
			if (length() > (long) Integer.MAX_VALUE)
				throw new ArrayIndexOutOfBoundsException("Ila too large for native array");

			return toArray((long) 0, (int) length());
		}

		public final char[] toArray(long start, int length) throws DataInvalidException {
			char[] result = new char[length];

			toArray(result, 0, start, length);

			return result;
		}

		@Override
		public void toArray(char[] array, int offset, int stride, long start, int length) throws DataInvalidException {
			// TODO Auto-generated method stub

		}
	}
}
