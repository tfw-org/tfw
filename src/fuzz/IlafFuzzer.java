import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;
import tfw.immutable.ilaf.longilaf.LongIlaFactoryFromArray;

public final class IlafFuzzer {

    private static final int MAX_LENGTH = 512;
    private static final long SENTINEL = 0x5a5a5a5a5a5a5a5aL;

    private IlafFuzzer() {
    }

    public static void fuzzerTestOneInput(FuzzedDataProvider data)
            throws Exception {

        /*
         * Generate the backing array.
         *
         * Once this array is passed to LongIlaFactoryFromArray.create(),
         * we treat ownership as transferred to TFW and never modify it.
         */
        int length = data.consumeInt(0, MAX_LENGTH);

        long[] source = new long[length];

        for (int i = 0; i < length; i++) {
            source[i] = data.consumeLong();
        }

        /*
         * Exercise the ILAF factory.
         */
        LongIlaFactory factory =
                LongIlaFactoryFromArray.create(source);

        LongIla first = factory.create();
        LongIla second = factory.create();

        /*
         * Both instances should represent the same logical array.
         */
        assertEquals(
                source.length,
                first.length(),
                "first LongIla has incorrect length");

        assertEquals(
                source.length,
                second.length(),
                "second LongIla has incorrect length");

        /*
         * Verify individual element ranges.
         */
        int operations = data.consumeInt(1, 32);

        for (int operation = 0; operation < operations; operation++) {
            if (length == 0) {
                break;
            }

            int start = data.consumeInt(0, length - 1);
            int remaining = length - start;
            int count = data.consumeInt(1, remaining);

            verifyGet(first, source, start, count);
            verifyGet(second, source, start, count);
        }

        /*
         * For small arrays, exercise the entire array. This gives the
         * fuzzer a deterministic way to thoroughly check short inputs.
         */
        if (length <= 32) {
            verifyGet(first, source, 0, length);
            verifyGet(second, source, 0, length);
        }

        /*
         * Exercise zero-length reads when the API permits them.
         *
         * This is particularly useful for finding off-by-one errors at
         * the beginning and end of an array.
         */
        verifyEmptyGet(first, length);
        verifyEmptyGet(second, length);

        /*
         * The factory should be reusable: creating multiple LongIla
         * instances from the same factory should produce equivalent
         * logical contents.
         */
        verifyEntireArray(first, source);
        verifyEntireArray(second, source);
    }

    private static void verifyGet(
            LongIla ila,
            long[] source,
            int sourceOffset,
            int length) throws Exception {

        /*
         * Leave room before and after the requested destination range.
         * This lets us detect writes outside the requested region.
         */
        int destinationOffset = 3;

        long[] destination =
                new long[destinationOffset + length + 3];

        for (int i = 0; i < destination.length; i++) {
            destination[i] = SENTINEL;
        }

        ila.get(
                destination,
                destinationOffset,
                sourceOffset,
                length);

        /*
         * Verify the requested values.
         */
        for (int i = 0; i < length; i++) {
            long expected = source[sourceOffset + i];
            long actual = destination[destinationOffset + i];

            assertEquals(
                    expected,
                    actual,
                    "incorrect value at source index "
                            + (sourceOffset + i));
        }

        /*
         * Verify that nothing before the destination range changed.
         */
        for (int i = 0; i < destinationOffset; i++) {
            assertEquals(
                    SENTINEL,
                    destination[i],
                    "write occurred before destination range");
        }

        /*
         * Verify that nothing after the destination range changed.
         */
        int end = destinationOffset + length;

        for (int i = end; i < destination.length; i++) {
            assertEquals(
                    SENTINEL,
                    destination[i],
                    "write occurred after destination range");
        }
    }

    private static void verifyEmptyGet(
            LongIla ila,
            int sourceLength) throws Exception {

        /*
         * get(..., length=0) should not modify the destination.
         *
         * At sourceLength == 0, use source offset 0.
         * Otherwise use the end of the array.
         */
        long[] destination = {
                SENTINEL,
                SENTINEL,
                SENTINEL
        };

        int sourceOffset = sourceLength;

        ila.get(
                destination,
                1,
                sourceOffset,
                0);

        for (int i = 0; i < destination.length; i++) {
            assertEquals(
                    SENTINEL,
                    destination[i],
                    "zero-length get modified destination");
        }
    }

    private static void verifyEntireArray(
            LongIla ila,
            long[] source) throws Exception {

        long[] actual = new long[source.length];

        ila.get(
                actual,
                0,
                0,
                source.length);

        for (int i = 0; i < source.length; i++) {
            assertEquals(
                    source[i],
                    actual[i],
                    "incorrect value at index " + i);
        }
    }

    private static void assertEquals(
            long expected,
            long actual,
            String message) {

        if (expected != actual) {
            throw new AssertionError(
                    message
                            + ": expected "
                            + expected
                            + ", got "
                            + actual);
        }
    }
}
