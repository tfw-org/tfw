package tfw.fuzz;

public final class IlaFuzzHarness<A, I> {
    private static final long SENTINEL = 0x5a5a5a5a5a5a5a5aL;

    private final IlaFuzzSpec<A, I> spec;

    public IlaFuzzHarness(IlaFuzzSpec<A, I> spec) {
        this.spec = spec;
    }

    public void fuzz(com.code_intelligence.jazzer.api.FuzzedDataProvider data) throws Exception {
        IlaFuzzInput input = IlaFuzzInput.consume(data);

        IlaArrayAdapter<A> adapter = spec.adapter();

        A source = adapter.create(input.sourceLength());

        adapter.initialize(source);

        /*
         * Test the factory's null argument handling.
         *
         * The current FactoryFromArray.create(null) itself returns
         * a factory; the exception occurs when that factory creates
         * the ILA. Therefore spec.create(null) is the correct test.
         */
        verifyNullCreate();

        I ila;

        try {
            ila = spec.create(source);
        } catch (Throwable t) {
            throw failure(input, "create(array) unexpectedly failed", t);
        }

        verifyLength(ila, input);

        /*
         * Verify that the created ILA actually represents the source
         * array when there is something to read.
         */
        if (input.sourceLength() > 0) {
            verifyElement(ila, source, 0);
        }

        /*
         * Exercise the exact fuzzed get() operation.
         */
        verifyFuzzedGet(ila, source, input);

        /*
         * Exercise a null destination separately.
         *
         * For length == 0 this must be accepted by the current API,
         * because Abstract*Ila returns before checking null.
         *
         * For length != 0 it must produce IllegalArgumentException.
         */
        verifyNullDestination(ila, input);
    }

    private void verifyNullCreate() {
        try {
            spec.create(null);

            throw new AssertionError(spec.name() + ": create(null) was accepted");

        } catch (IllegalArgumentException expected) {
            /*
             * Correct.
             */
        } catch (AssertionError e) {
            throw e;

        } catch (Throwable t) {
            throw new AssertionError(
                    spec.name()
                            + ": create(null) threw "
                            + t.getClass().getName()
                            + " instead of IllegalArgumentException",
                    t);
        }
    }

    private void verifyLength(I ila, IlaFuzzInput input) throws Exception {
        long actual = spec.length(ila);

        if (actual != input.sourceLength()) {
            throw failure(input, "incorrect ILA length: expected=" + input.sourceLength() + ", actual=" + actual, null);
        }
    }

    private void verifyElement(I ila, A source, int index) throws Exception {
        IlaArrayAdapter<A> adapter = spec.adapter();

        A destination = adapter.create(1);

        adapter.initialize(destination);

        spec.get(ila, destination, 0, index, 1);

        adapter.assertElementEquals(source, index, destination, 0);
    }

    private void verifyFuzzedGet(I ila, A source, IlaFuzzInput input) {
        IlaArrayAdapter<A> adapter = spec.adapter();

        boolean valid = isValidGet(
                input.sourceLength(), input.destinationLength(), input.offset(), input.start(), input.length());

        A destination = adapter.create(input.destinationLength());

        adapter.initialize(destination);

        A before = adapter.copy(destination);

        try {
            spec.get(ila, destination, input.offset(), input.start(), input.length());

            if (!valid) {
                throw failure(input, "get() accepted invalid arguments", null);
            }

            verifySuccessfulGet(source, before, destination, input);

        } catch (IllegalArgumentException e) {

            if (valid) {
                throw failure(input, "get() rejected valid arguments", e);
            }

            /*
             * A failed argument check must happen before arraycopy,
             * so the destination must remain untouched.
             */
            verifyUnchanged(before, destination, input);

        } catch (Throwable t) {

            throw failure(input, "get() threw " + t.getClass().getName(), t);
        }
    }

    private void verifyNullDestination(I ila, IlaFuzzInput input) {
        try {
            spec.get(ila, null, input.offset(), input.start(), input.length());

            if (input.length() != 0) {
                throw failure(input, "get(null, ..., length != 0) " + "was accepted", null);
            }

        } catch (IllegalArgumentException e) {

            if (input.length() == 0) {
                throw failure(input, "get(null, ..., length == 0) " + "was rejected", e);
            }

        } catch (Throwable t) {

            throw failure(input, "get(null, ...) threw " + t.getClass().getName(), t);
        }
    }

    private void verifySuccessfulGet(A source, A before, A destination, IlaFuzzInput input) {
        IlaArrayAdapter<A> adapter = spec.adapter();

        /*
         * The current implementation treats length == 0 as an
         * unconditional no-op.
         */
        if (input.length() == 0) {
            verifyUnchanged(before, destination, input);
            return;
        }

        /*
         * A successful operation necessarily has a start that fits
         * in a Java array because sourceLength is bounded.
         */
        int sourceIndex = Math.toIntExact(input.start());

        for (int i = 0; i < input.length(); i++) {

            adapter.assertElementEquals(source, sourceIndex + i, destination, input.offset() + i);
        }

        /*
         * Nothing outside the requested destination range may change.
         */
        int copyStart = input.offset();

        int copyEnd = copyStart + input.length();

        for (int i = 0; i < input.destinationLength(); i++) {

            if (i >= copyStart && i < copyEnd) {
                continue;
            }

            adapter.assertElementEquals(before, i, destination, i);
        }
    }

    private void verifyUnchanged(A before, A destination, IlaFuzzInput input) {
        IlaArrayAdapter<A> adapter = spec.adapter();

        for (int i = 0; i < input.destinationLength(); i++) {

            adapter.assertElementEquals(before, i, destination, i);
        }
    }

    /*
     * This deliberately mirrors ImmutableLongArrayUtil.boundsCheck(),
     * including the special zero-length behavior of Abstract*Ila.
     */
    private static boolean isValidGet(long ilaLength, int arrayLength, int offset, long start, int length) {
        /*
         * Abstract*Ila.get() returns before ANY argument checking
         * when length == 0.
         */
        if (length == 0) {
            return true;
        }

        if (ilaLength < 0) {
            return false;
        }

        if (arrayLength < 0) {
            return false;
        }

        if (offset < 0) {
            return false;
        }

        if (start < 0) {
            return false;
        }

        if (length < 0) {
            return false;
        }

        if (offset >= arrayLength) {
            return false;
        }

        if (start >= ilaLength) {
            return false;
        }

        /*
         * Widen BEFORE adding.
         */
        if ((long) offset + (long) length > (long) arrayLength) {
            return false;
        }

        if (start + (long) length > ilaLength) {
            return false;
        }

        return true;
    }

    private void assertSentinel(A array, int index) {
        /*
         * This method is intentionally unused for the generic case.
         * The adapter owns element comparison because primitive arrays
         * have no common generic element type.
         */
    }

    private AssertionError failure(IlaFuzzInput input, String message, Throwable cause) {
        String fullMessage = spec.name() + ": " + message + " [" + input + "]";

        if (cause == null) {
            return new AssertionError(fullMessage);
        }

        return new AssertionError(fullMessage, cause);
    }
}
