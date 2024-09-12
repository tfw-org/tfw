package tfw.immutable.ila;

public class IlaTestDimensions {
    private static int defaultIlaLength = 17;
    private static int defaultOffsetLength = 7; // min 0
    private static int defaultMaxStride = 5; // min 1

    public static int defaultIlaLength() {
        return defaultIlaLength;
    }

    public static int defaultOffsetLength() {
        return defaultOffsetLength;
    }

    public static int defaultMaxStride() {
        return defaultMaxStride;
    }
}
