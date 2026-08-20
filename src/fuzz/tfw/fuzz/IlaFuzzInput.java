package tfw.fuzz;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class IlaFuzzInput {

    /*
     * Large enough to give the fuzzer useful array shapes without
     * allowing a single input to consume excessive memory.
     */
    public static final int MAX_ARRAY_LENGTH = 512;

    private final int sourceLength;
    private final int destinationLength;
    private final int offset;
    private final long start;
    private final int length;

    private IlaFuzzInput(int sourceLength, int destinationLength, int offset, long start, int length) {

        this.sourceLength = sourceLength;
        this.destinationLength = destinationLength;
        this.offset = offset;
        this.start = start;
        this.length = length;
    }

    public static IlaFuzzInput consume(FuzzedDataProvider data) {

        return new IlaFuzzInput(
                data.consumeInt(0, MAX_ARRAY_LENGTH),
                data.consumeInt(0, MAX_ARRAY_LENGTH),
                data.consumeInt(),
                data.consumeLong(),
                data.consumeInt());
    }

    public int sourceLength() {
        return sourceLength;
    }

    public int destinationLength() {
        return destinationLength;
    }

    public int offset() {
        return offset;
    }

    public long start() {
        return start;
    }

    public int length() {
        return length;
    }

    @Override
    public String toString() {
        return "sourceLength=" + sourceLength
                + ", destinationLength=" + destinationLength
                + ", offset=" + offset
                + ", start=" + start
                + ", length=" + length;
    }
}
