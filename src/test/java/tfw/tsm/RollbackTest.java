package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;
import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.RollbackECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.StringRollbackECD;

class RollbackTest {
    private final String VALID = "valid";

    private final String INVALID = "invalid";

    private final StringECD aECD = new StringECD("a");

    private final StringECD bECD = new StringECD("b");

    private final StringECD cECD = new StringECD("c");

    private final StringRollbackECD error1ECD = new StringRollbackECD("error1");

    private final StringRollbackECD error2ECD = new StringRollbackECD("error2");

    private final String aInitialState = "aInitialState";

    private final String bInitialState = "bInitialState";

    private final String cInitialState = "cInitialState";

    private String errorState1 = null;

    private String aCommitState = null;

    private String bCommitState = null;

    private String cCommitState = null;

    private EventChannelState aErrorState = new EventChannelState(error1ECD, "A is invalid");

    private EventChannelState cErrorState = new EventChannelState(error1ECD, "C is invalid");

    private final Initiator initiator = new Initiator("Initiator", new StringECD[] {aECD, bECD, cECD});

    private final Validator aValidator =
            new Validator("A Validator", new StringECD[] {aECD}, new StringRollbackECD[] {error1ECD}) {
                @Override
                protected void validateState() {
                    String state = (String) get(aECD);

                    // System.out.println("validating a: " + state);
                    if (state.equals("invalid")) {
                        rollback(error1ECD, aErrorState.getState());
                    }
                }
            };

    private Validator bValidator =
            new Validator("B Validator", new StringECD[] {bECD}, new StringRollbackECD[] {error1ECD}) {
                @Override
                protected void validateState() {
                    String state = (String) get(bECD);

                    // System.out.println("validating b: " + state);
                    if (state.equals("invalid")) {
                        // rollback(new EventChannelState[]
                        // {
                        // aRollbackState, bRollbackState, cRollbackState,
                        // bErrorState
                        // });
                    }
                }
            };

    private Validator cValidator =
            new Validator("C Validator", new StringECD[] {cECD}, new StringRollbackECD[] {error1ECD}) {
                @Override
                protected void validateState() {
                    String state = (String) get(cECD);

                    //            System.out.println("validating c: " + state);
                    if (state.equals("invalid")) {
                        rollback(error1ECD, cErrorState.getState());
                    }
                }
            };

    private Commit commit = new Commit("Commit", new ObjectECD[] {aECD, bECD, cECD}) {
        @Override
        protected void commit() {
            // System.out.println("commit.commit() reached");
            aCommitState = (String) get(aECD);
            bCommitState = (String) get(bECD);
            cCommitState = (String) get(cECD);
        }

        @Override
        protected void debugCommit() {
            //            System.out.println("commit.debugCommit() called" + get());
        }
    };

    private Commit errorHandler1 = new Commit("Error Handler 1", new ObjectECD[] {error1ECD}) {
        @Override
        protected void commit() {
            //            System.out.println("errorHandler1.commit()");
            errorState1 = (String) get(error1ECD);
        }

        @Override
        protected void debugCommit() {
            // System.out.println("errorHandler1.debugCommit()");
        }
    };

    @Test
    void testRollbackArguments() {
        TestRollbackHandler handler = new TestRollbackHandler();

        try {
            handler.testRollback(null, new Object());
            fail("rollback() accepted null event channel description");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            handler.testRollback(error1ECD, null);
            fail("rollback() accepted null state");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            handler.testRollback(null);
            fail("rollback() accepted null event channel state");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            handler.testRollback(new EventChannelState[] {null});

            fail("rollback() accepted event channel state with null element");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    @Test
    void testConverter() throws Exception {
        RootFactory rf = new RootFactory();
        rf.addEventChannel(error1ECD);
        rf.addEventChannel(error2ECD);
        rf.addEventChannel(aECD, aInitialState);
        rf.addEventChannel(bECD, bInitialState);
        rf.addEventChannel(cECD, cInitialState);

        // rf.setLogging(true);
        BasicTransactionQueue queue = new BasicTransactionQueue();

        // rf.setLogging(true);
        Root root = rf.create("Test branch", queue);
        root.add(initiator);
        root.add(aValidator);
        root.add(bValidator);
        root.add(cValidator);
        root.add(commit);
        root.add(errorHandler1);

        initiator.set(aECD, "valid");
        initiator.set(bECD, "valid");
        initiator.set(cECD, "invalid");
        queue.waitTilEmpty();

        // System.out.println("got here");
        assertEquals("valid", aCommitState, "A Commit");
        assertEquals("valid", bCommitState, "B Commit");
        assertEquals(cInitialState, cCommitState, "C Commit");
        assertEquals(cErrorState.getState(), errorState1, "error Commit");

        errorState1 = null;
        initiator.set(cECD, "valid");
        queue.waitTilEmpty();
        assertEquals("valid", aCommitState, "A Commit");
        assertEquals("valid", bCommitState, "B Commit");
        assertEquals("valid", cCommitState, "C Commit");
        assertEquals(null, errorState1, "error Commit");

        initiator.set(aECD, "invalid");
        queue.waitTilEmpty();
        assertEquals("valid", aCommitState, "A Commit");
        assertEquals("valid", bCommitState, "B Commit");
        assertEquals("valid", cCommitState, "C Commit");
        assertEquals(aErrorState.getState(), errorState1, "error Commit");

        errorState1 = null;
        initiator.set(bECD, "invalid");
        queue.waitTilEmpty();

        // assertEquals("A Commit", aRollbackState.getState(), aCommitState);
        // assertEquals("B Commit", bRollbackState.getState(), bCommitState);
        // assertEquals("C Commit", cRollbackState.getState(), cCommitState);
        // assertEquals("error Commit", aErrorState.getState(), errorState1);
    }

    @Test
    void testSimpleRollback() {
        String errorMsg = "An error occurred";
        TestCommit aCommit = new TestCommit(aECD, null);
        TestCommit errorCommit = new TestCommit(error1ECD, null);
        Validator validator = new TestValidator(aECD, error1ECD, errorMsg);
        Initiator initiator = new Initiator("Test initiator", aECD);
        RootFactory rf = new RootFactory();
        rf.addEventChannel(aECD);
        rf.addEventChannel(error1ECD);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("SimpleRollbackTest", queue);
        root.add(aCommit);
        root.add(errorCommit);
        root.add(validator);
        root.add(initiator);
        queue.waitTilEmpty();

        initiator.set(aECD, VALID);
        queue.waitTilEmpty();
        assertEquals(VALID, aCommit.commitValue, "valid value failed");
        assertEquals(null, errorCommit.commitValue, "Error commit called when no error");

        aCommit.commitValue = null;
        initiator.set(aECD, INVALID);
        queue.waitTilEmpty();
        assertEquals(null, aCommit.commitValue, "Invalid value reached commit");
        assertEquals(errorMsg, errorCommit.commitValue, "Error commit not reached");
    }

    @Test
    void testDaisyChainedMultiCycleRollback() {
        String cErrorMsg = "An error occurred";
        Initiator initiator = new Initiator("Test initiator", aECD);
        TestCommit aCommit = new TestCommit(aECD, null);
        TestCommit bCommit = new TestCommit(bECD, null);
        TestCommit cCommit = new TestCommit(cECD, null);
        TestConverter aConverter = new TestConverter("A converter", aECD, bECD);
        TestConverter bConverter = new TestConverter("B converter", bECD, cECD);
        Validator cValidator = new TestValidator(cECD, error1ECD, cErrorMsg);
        TestCommit errorCommit = new TestCommit(error1ECD, new StringECD[] {aECD, bECD, cECD});

        RootFactory rf = new RootFactory();
        rf.addEventChannel(aECD);
        rf.addEventChannel(bECD);
        rf.addEventChannel(cECD);
        rf.addEventChannel(error1ECD);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("SimpleRollbackTest", queue);
        root.add(aCommit);
        root.add(bCommit);
        root.add(cCommit);
        root.add(aConverter);
        root.add(bConverter);
        root.add(errorCommit);
        root.add(cValidator);
        root.add(initiator);
        queue.waitTilEmpty();

        initiator.set(aECD, VALID);
        queue.waitTilEmpty();
        assertEquals(VALID, aConverter.inValue, "aConverter not reached");
        assertEquals(VALID, aConverter.inValue, "bConverter not reached");
        assertEquals(VALID, aCommit.commitValue, "aCommit not reached");
        assertEquals(VALID, bCommit.commitValue, "bCommit not reached");
        assertEquals(VALID, cCommit.commitValue, "cCommit not reached");
        assertEquals(null, errorCommit.commitValue, "errorCommit reached");

        initiator.set(aECD, INVALID);
        queue.waitTilEmpty();
        assertEquals(INVALID, aConverter.inValue, "aConverter not reached");
        assertEquals(INVALID, aConverter.inValue, "bConverter not reached");
        assertEquals(VALID, aCommit.commitValue, "aCommit not reached");
        assertEquals(VALID, bCommit.commitValue, "bCommit not reached");
        assertEquals(VALID, cCommit.commitValue, "cCommit not reached");
        assertEquals(cErrorMsg, errorCommit.commitValue, "errorCommit reached");
        assertEquals(VALID, errorCommit.stateMap.get(aECD), "aECD has wrong value");
        assertEquals(VALID, errorCommit.stateMap.get(bECD), "bECD has wrong value");
        assertEquals(VALID, errorCommit.stateMap.get(cECD), "cECD has wrong value");
    }

    @Test
    void testMultiValueRollback() {
        String error1msg = "Error notification on error channel one";
        String error2msg = "Error notification on error channel two";
        EventChannelState[] rollbackState = new EventChannelState[] {
            new EventChannelState(error1ECD, error1msg), new EventChannelState(error2ECD, error2msg)
        };

        Initiator initiator = new Initiator("Test initiator", aECD);
        Validator aValidator = new TestValidator(aECD, new RollbackECD[] {error1ECD, error2ECD}, rollbackState);
        TestCommit errorCommit1 = new TestCommit(error1ECD, null);
        TestCommit errorCommit2 = new TestCommit(error2ECD, null);

        RootFactory rf = new RootFactory();
        rf.addEventChannel(aECD);
        rf.addEventChannel(error1ECD);
        rf.addEventChannel(error2ECD);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("SimpleRollbackTest", queue);
        root.add(aValidator);
        root.add(initiator);
        root.add(errorCommit1);
        root.add(errorCommit2);
        queue.waitTilEmpty();

        initiator.set(aECD, VALID);
        queue.waitTilEmpty();
        assertNull(errorCommit1.commitValue, "errorCommit1 received value on valid input");
        assertNull(errorCommit2.commitValue, "errorCommit2 received value on valid input");

        initiator.set(aECD, INVALID);
        queue.waitTilEmpty();
        assertEquals(error1msg, errorCommit1.commitValue, "errorCommit1 received wrong value on invalid input");
        assertEquals(error2msg, errorCommit2.commitValue, "errorCommit2 received wrong value on invalid input");
    }

    private static class TestValidator extends Validator {
        private final StringECD triggerECD;

        private final StringRollbackECD rollbackECD;

        private final String errorMsg;

        private final EventChannelState[] rollbackState;

        public TestValidator(StringECD triggerECD, RollbackECD[] rollbackECDs, EventChannelState[] rollbackState) {
            super("Test Validator " + triggerECD.getEventChannelName(), new StringECD[] {triggerECD}, rollbackECDs);
            this.triggerECD = triggerECD;
            this.errorMsg = null;
            this.rollbackECD = null;
            this.rollbackState = rollbackState;
        }

        public TestValidator(StringECD triggerECD, StringRollbackECD rollbackECD, String errorMsg) {
            super(
                    "Test Validator " + triggerECD.getEventChannelName(),
                    new StringECD[] {triggerECD},
                    new RollbackECD[] {rollbackECD});
            this.triggerECD = triggerECD;
            this.rollbackECD = rollbackECD;
            this.errorMsg = errorMsg;
            this.rollbackState = null;
        }

        @Override
        public void validateState() {
            String state = (String) get(triggerECD);

            if (state.equals("invalid")) {
                if (rollbackECD != null) {
                    rollback(rollbackECD, errorMsg);
                } else {
                    rollback(rollbackState);
                }
            }
        }
    }

    private static class TestCommit extends Commit {
        String commitValue = null;

        Map<ObjectECD, Object> stateMap = null;

        private final ObjectECD trigger;

        public TestCommit(ObjectECD trigger, ObjectECD[] nontriggers) {
            super("Test Commit " + trigger.getEventChannelName(), new ObjectECD[] {trigger}, nontriggers, null);
            this.trigger = trigger;
        }

        @Override
        public void commit() {
            stateMap = this.get();
            commitValue = (String) get(trigger);
        }
    }

    private class TestRollbackHandler extends Validator {
        public TestRollbackHandler() {
            super("Test", new ObjectECD[] {aECD}, new RollbackECD[] {error1ECD});
        }

        @Override
        public void validateState() {}

        public void testRollback(RollbackECD sourceECD, Object state) {
            rollback(sourceECD, state);
        }

        public void testRollback(EventChannelState[] ecs) {
            rollback(ecs);
        }
    }

    private static class TestConverter extends Converter {
        private final StringECD input;

        private final StringECD output;

        private String inValue;

        public TestConverter(String name, StringECD input, StringECD output) {
            super(name, new StringECD[] {input}, new StringECD[] {output});
            this.input = input;
            this.output = output;
        }

        @Override
        protected void convert() {
            this.inValue = (String) get(input);
            set(output, this.inValue);
        }
    }
}
