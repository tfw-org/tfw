package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.StringECD;

final class SetStateTest {
    /**
     * Verifies that an exception is thrown if the component attempts to set an
     * event channel twice in the same state change cyecle.
     */
    @Test
    void doubleSetTest() {
        StringECD ecd = new StringECD("stringECD");
        TestTransactionExceptionHandler exceptionHandler = new TestTransactionExceptionHandler();
        BasicTransactionQueue queue = new BasicTransactionQueue();

        final Root root = Root.builder()
                .setName("Test")
                .setTransactionQueue(queue)
                .setTransactionExceptionHandler(exceptionHandler)
                .addObjectECD(ecd, null)
                .build();

        root.add(new DoubleSetConverter(ecd));
        Initiator initiator = new Initiator("Initiator", ecd);
        root.add(initiator);
        initiator.set(ecd, "hello");
        queue.waitTilEmpty();

        assertThat(exceptionHandler.exp).isNotNull();
    }

    private static class DoubleSetConverter extends Converter {
        private final StringECD ecd;

        public DoubleSetConverter(StringECD ecd) {
            super("DoubleSetConverter", new StringECD[] {ecd}, new StringECD[] {ecd});
            this.ecd = ecd;
        }

        @Override
        protected void convert() {
            set(ecd, "value");
            // Attempt to set the same event channel twice...
            set(ecd, "newValue");
        }
    }
}
