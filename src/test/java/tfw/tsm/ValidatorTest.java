package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.RollbackECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.StringRollbackECD;

final class ValidatorTest {
    @Test
    void constructionTest() {
        ObjectECD[] sinks = new ObjectECD[] {new StringECD("Test")};

        assertThatThrownBy(() -> new TestValidator(null, sinks, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> new TestValidator("Test", new ObjectECD[] {null}, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("triggeringSinks[0]== null not allowed!");
        assertThatThrownBy(() -> new TestValidator("Test", sinks, new ObjectECD[] {null}, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("nonTriggeringSinks[0]== null not allowed!");
        assertThatThrownBy(() -> new TestValidator("Test", null, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("triggeringSinks == null not allowed!");
    }

    private final StringECD eventChannelAECD = new StringECD("ecA");
    private final StringECD eventChannelBECD = new StringECD("ecB");

    @Test
    void validatorTest() {
        RootFactory rf = new RootFactory();
        rf.addEventChannel(eventChannelAECD);
        rf.addEventChannel(eventChannelBECD);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("ValidatorTestRoot", queue);
        Initiator initiator =
                new Initiator("ValidatorTestInitiator", new StringECD[] {eventChannelAECD, eventChannelBECD});
        root.add(initiator);
        TestValidator validator = new TestValidator(
                "TestValidator", new StringECD[] {eventChannelAECD}, new StringECD[] {eventChannelBECD}, null);
        root.add(validator);
        String valueA = "valueA";
        initiator.set(eventChannelAECD, valueA);
        queue.waitTilEmpty();

        assertThat(validator.channelA).isNull();
        assertThat(validator.channelB).isNull();
        assertThat(valueA).isEqualTo(validator.debugChannelA);
        assertThat(validator.debugChannelB).isNull();

        validator.reset();
        valueA = "newValueA";
        String valueB = "valueB";
        initiator.set(eventChannelBECD, valueB);
        initiator.set(eventChannelAECD, valueA);
        queue.waitTilEmpty();
        assertThat(valueA).isEqualTo(validator.channelA);
        assertThat(valueB).isEqualTo(validator.channelB);
        assertThat(validator.debugChannelA).isNull();
        assertThat(validator.debugChannelB).isNull();
    }

    @Test
    void triggeredValidationTest() {
        StatelessTriggerECD trigger = new StatelessTriggerECD("trigger");
        final IntegerECD minECD = new IntegerECD("min");
        final IntegerECD maxECD = new IntegerECD("max");
        final StringRollbackECD error = new StringRollbackECD("error");
        RootFactory rf = new RootFactory();
        rf.addEventChannel(trigger);
        rf.addEventChannel(error);
        rf.addEventChannel(minECD, 0);
        rf.addEventChannel(maxECD, 1);
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("Test", queue);
        Initiator initiator = new Initiator("Initiator", new EventChannelDescription[] {trigger, minECD, maxECD});
        root.add(initiator);
        root.add(new Validator("TestValidator", trigger, new ObjectECD[] {minECD, maxECD}, new RollbackECD[] {error}) {
            @Override
            protected void validateState() {
                int min = ((Integer) get(minECD)).intValue();
                int max = ((Integer) get(maxECD)).intValue();
                if (min > max) {
                    rollback(error, "min must be less than or equal to max");
                }
            }
        });

        ErrorHandler errorHandler = new ErrorHandler(error);
        root.add(errorHandler);
        queue.waitTilEmpty();
        assertThat(errorHandler.errorMsg).isNull();

        initiator.set(minECD, 3);
        queue.waitTilEmpty();
        assertThat(errorHandler.errorMsg).isNull();

        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertThat(errorHandler.errorMsg).isNotNull();

        errorHandler.errorMsg = null;
        initiator.set(maxECD, 4);
        queue.waitTilEmpty();
        assertThat(errorHandler.errorMsg).isNull();
    }

    private static class ErrorHandler extends Commit {
        private final StringRollbackECD errorECD;

        String errorMsg = null;

        public ErrorHandler(StringRollbackECD errorECD) {
            super("ErrorHandler", new ObjectECD[] {errorECD});
            this.errorECD = errorECD;
        }

        @Override
        protected void commit() {
            this.errorMsg = (String) get(this.errorECD);
        }
    }

    private class TestValidator extends Validator {
        private String channelA = null;
        private String channelB = null;
        private String debugChannelA = null;
        private String debugChannelB = null;

        public TestValidator(
                String name, ObjectECD[] triggeringSinks, ObjectECD[] nonTriggeringSinks, RollbackECD[] initiators) {
            super(name, triggeringSinks, nonTriggeringSinks, initiators);
        }

        @Override
        protected void validateState() {
            channelA = (String) get(eventChannelAECD);
            channelB = (String) get(eventChannelBECD);
        }

        @Override
        protected void debugValidateState() {
            debugChannelA = (String) get(eventChannelAECD);
            debugChannelB = (String) get(eventChannelBECD);
        }

        public void reset() {
            this.channelA = null;
            this.channelB = null;
            this.debugChannelA = null;
            this.debugChannelB = null;
        }
    }
}
