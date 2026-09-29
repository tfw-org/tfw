package tfw.immutable.ilaf.objectilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.objectila.ObjectIla;

public final class ObjectIlaFactoryConcatenateFuzzer {

    private ObjectIlaFactoryConcatenateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int leftLength = data.consumeInt(0, ObjectIlaFactoryFuzzerSupport.MAX_LENGTH);

        final int rightLength = data.consumeInt(0, ObjectIlaFactoryFuzzerSupport.MAX_LENGTH);

        final ObjectIlaFactoryFuzzerSupport.GetInput input = ObjectIlaFactoryFuzzerSupport.consumeGetInput(data);

        final Object[] left = ObjectIlaFactoryFuzzerSupport.createInitializedArray(leftLength, data);

        final Object[] right = ObjectIlaFactoryFuzzerSupport.createInitializedArray(rightLength, data);

        final ObjectIlaFactory leftFactory = ObjectIlaFactoryFromArray.create(left);

        final ObjectIlaFactory rightFactory = ObjectIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory);

        final ObjectIlaFactory concatenateFactory = ObjectIlaFactoryConcatenate.create(leftFactory, rightFactory);

        final int expectedLength = leftLength + rightLength;

        ObjectIlaFactoryFuzzerSupport.verifyIla(
                concatenateFactory.create(),
                expectedLength,
                input,
                index -> index < leftLength ? left[index] : right[index - leftLength],
                data);

        testLeftBoundary(concatenateFactory, left, leftLength);
    }

    private static void testArgumentValidation(final ObjectIlaFactory leftFactory, ObjectIlaFactory rightFactory)
            throws Exception {

        ObjectIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ObjectIlaFactoryConcatenate.create(null, rightFactory), "create() accepted null leftFactory");

        ObjectIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ObjectIlaFactoryConcatenate.create(leftFactory, null), "create() accepted null rightFactory");
    }

    private static void testLeftBoundary(ObjectIlaFactory concatenateFactory, Object[] left, int leftLength)
            throws Exception {

        if (leftLength == 0) {
            return;
        }

        final ObjectIla ila = concatenateFactory.create();

        try {
            final Object[] destination = new Object[1];

            /*
             * Read exactly the final element of the left ILA.
             *
             * This specifically exercises the boundary where the
             * right side has zero elements to contribute.
             */
            ila.get(destination, 0, leftLength - 1L, 1);

            ObjectIlaFactoryFuzzerSupport.assertEquals(left[leftLength - 1], destination[0], leftLength - 1);

        } finally {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
