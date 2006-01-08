/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * witout even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.tsm.test;

import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Commit;
import tfw.tsm.Converter;
import tfw.tsm.EventChannelState;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.StateMap;
import tfw.tsm.Validator;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.RollbackECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.StringRollbackECD;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RollbackTest extends TestCase
{
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

    private String errorState2 = null;

    private String aCommitState = null;

    private String bCommitState = null;

    private String cCommitState = null;

    private EventChannelState aRollbackState = new EventChannelState(aECD,
            "A set after rolledback");

    private EventChannelState bRollbackState = new EventChannelState(bECD,
            "B set after rolledback");

    private EventChannelState cRollbackState = new EventChannelState(cECD,
            "C set after rolledback");

    private EventChannelState aErrorState = new EventChannelState(error1ECD,
            "A is invalid");

    private EventChannelState bErrorState = new EventChannelState(error1ECD,
            "B is invalid");

    private EventChannelState cErrorState = new EventChannelState(error1ECD,
            "C is invalid");

    private final Initiator initiator = new Initiator("Initiator",
            new StringECD[] { aECD, bECD, cECD });

    private final Validator aValidator = new Validator("A Validator",
            new StringECD[] { aECD }, new StringRollbackECD[] { error1ECD })
    {
        protected void validateState()
        {
            String state = (String) get(aECD);

            // System.out.println("validating a: " + state);
            if (state.equals("invalid"))
            {
                rollback((RollbackECD) aErrorState.getECD(), aErrorState
                        .getState());
            }
        }
    };

    private Validator bValidator = new Validator("B Validator",
            new StringECD[] { bECD }, new StringRollbackECD[] { error1ECD })
    {
        protected void validateState()
        {
            String state = (String) get(bECD);

            // System.out.println("validating b: " + state);
            if (state.equals("invalid"))
            {
                // rollback(new EventChannelState[]
                // {
                // aRollbackState, bRollbackState, cRollbackState,
                // bErrorState
                // });
            }
        }
    };

    private Validator cValidator = new Validator("C Validator",
            new StringECD[] { cECD }, new StringRollbackECD[] { error1ECD })
    {
        protected void validateState()
        {
            String state = (String) get(cECD);

            System.out.println("validating c: " + state);
            if (state.equals("invalid"))
            {
                rollback((RollbackECD) cErrorState.getECD(), cErrorState
                        .getState());
            }
        }
    };

    private Commit commit = new Commit("Commit", new ObjectECD[] { aECD, bECD,
            cECD })
    {
        protected void commit()
        {
            // System.out.println("commit.commit() reached");
            aCommitState = (String) get(aECD);
            bCommitState = (String) get(bECD);
            cCommitState = (String) get(cECD);
        }

        protected void debugCommit()
        {
            System.out.println("commit.debugCommit() called" + get());
        }
    };

    private Commit errorHandler1 = new Commit("Error Handler 1",
            new ObjectECD[] { error1ECD })
    {
        protected void commit()
        {
            System.out.println("errorHandler1.commit()");
            errorState1 = (String) get(error1ECD);
        }

        protected void debugCommit()
        {
            // System.out.println("errorHandler1.debugCommit()");
        }
    };

    private Commit errorHandler2 = new Commit("Error Handler 2",
            new ObjectECD[] { error2ECD })
    {
        protected void commit()
        {
            System.out.println("errorHandler2.commit()");
            errorState2 = (String) get(error2ECD);
        }

        protected void debugCommit()
        {
            // System.out.println("errorHandler2.debugCommit()");
        }
    };

    public void testRollbackArguments()
    {
        TestRollbackHandler handler = new TestRollbackHandler();

        try
        {
            handler.testRollback(null, new Object());
            fail("rollback() accepted null event channel description");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }

        try
        {
            handler.testRollback(error1ECD, null);
            fail("rollback() accepted null state");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }

        try
        {
            handler.testRollback(null);
            fail("rollback() accepted null event channel state");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }

        try
        {
            handler.testRollback(new EventChannelState[] { null });

            fail("rollback() accepted event channel state with null element");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }
    }

    public void testConverter() throws Exception
    {
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
        assertEquals("A Commit", "valid", aCommitState);
        assertEquals("B Commit", "valid", bCommitState);
        assertEquals("C Commit", cInitialState, cCommitState);
        assertEquals("error Commit", cErrorState.getState(), errorState1);

        errorState1 = null;
        initiator.set(cECD, "valid");
        queue.waitTilEmpty();
        assertEquals("A Commit", "valid", aCommitState);
        assertEquals("B Commit", "valid", bCommitState);
        assertEquals("C Commit", "valid", cCommitState);
        assertEquals("error Commit", null, errorState1);

        initiator.set(aECD, "invalid");
        queue.waitTilEmpty();
        assertEquals("A Commit", "valid", aCommitState);
        assertEquals("B Commit", "valid", bCommitState);
        assertEquals("C Commit", "valid", cCommitState);
        assertEquals("error Commit", aErrorState.getState(), errorState1);

        errorState1 = null;
        initiator.set(bECD, "invalid");
        queue.waitTilEmpty();

        // assertEquals("A Commit", aRollbackState.getState(), aCommitState);
        // assertEquals("B Commit", bRollbackState.getState(), bCommitState);
        // assertEquals("C Commit", cRollbackState.getState(), cCommitState);
        // assertEquals("error Commit", aErrorState.getState(), errorState1);
    }

    public void testSimpleRollback()
    {
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
        assertEquals("valid value failed", VALID, aCommit.commitValue);
        assertEquals("Error commit called when no error", null,
                errorCommit.commitValue);

        aCommit.commitValue = null;
        initiator.set(aECD, INVALID);
        queue.waitTilEmpty();
        assertEquals("Invalid value reached commit", null, aCommit.commitValue);
        assertEquals("Error commit not reached", errorMsg,
                errorCommit.commitValue);
    }

    public void testDaisyChainedMultiCycleRollback()
    {
        String cErrorMsg = "An error occurred";
        Initiator initiator = new Initiator("Test initiator", aECD);
        TestCommit aCommit = new TestCommit(aECD, null);
        TestCommit bCommit = new TestCommit(bECD, null);
        TestCommit cCommit = new TestCommit(cECD, null);
        TestConverter aConverter = new TestConverter("A converter", aECD, bECD);
        TestConverter bConverter = new TestConverter("B converter", bECD, cECD);
        Validator cValidator = new TestValidator(cECD, error1ECD, cErrorMsg);
        TestCommit errorCommit = new TestCommit(error1ECD, new StringECD[] {
                aECD, bECD, cECD });

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
        assertEquals("aConverter not reached", VALID, aConverter.inValue);
        assertEquals("bConverter not reached", VALID, aConverter.inValue);
        assertEquals("aCommit not reached", VALID, aCommit.commitValue);
        assertEquals("bCommit not reached", VALID, bCommit.commitValue);
        assertEquals("cCommit not reached", VALID, cCommit.commitValue);
        assertEquals("errorCommit reached", null, errorCommit.commitValue);

        initiator.set(aECD, INVALID);
        queue.waitTilEmpty();
        assertEquals("aConverter not reached", INVALID, aConverter.inValue);
        assertEquals("bConverter not reached", INVALID, aConverter.inValue);
        assertEquals("aCommit not reached", VALID, aCommit.commitValue);
        assertEquals("bCommit not reached", VALID, bCommit.commitValue);
        assertEquals("cCommit not reached", VALID, cCommit.commitValue);
        assertEquals("errorCommit reached", cErrorMsg, errorCommit.commitValue);
        assertEquals("aECD has wrong value", VALID, errorCommit.stateMap
                .get(aECD));
        assertEquals("bECD has wrong value", VALID, errorCommit.stateMap
                .get(bECD));
        assertEquals("cECD has wrong value", VALID, errorCommit.stateMap
                .get(cECD));
    }

    public void testMultiValueRollback()
    {
        String error1msg = "Error notification on error channel one";
        String error2msg = "Error notification on error channel two";
        EventChannelState[] rollbackState = new EventChannelState[] {
                new EventChannelState(error1ECD, error1msg),
                new EventChannelState(error2ECD, error2msg) };

        Initiator initiator = new Initiator("Test initiator", aECD);
        Validator aValidator = new TestValidator(aECD, rollbackState);
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
        assertNull("errorCommit1 received value on valid input",
                errorCommit1.commitValue);
        assertNull("errorCommit2 received value on valid input",
                errorCommit2.commitValue);

        initiator.set(aECD, INVALID);
        queue.waitTilEmpty();
        assertEquals("errorCommit1 received wrong value on invalid input",
                error1msg, errorCommit1.commitValue);
        assertEquals("errorCommit2 received wrong value on invalid input",
                error2msg, errorCommit2.commitValue);
    }

    public static Test suite()
    {
        return new TestSuite(RollbackTest.class);
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }

    private static RollbackECD[] getECDs(EventChannelState[] ecs)
    {
        RollbackECD[] ecds = new RollbackECD[ecs.length];

        for (int i = 0; i < ecs.length; i++)
        {
            ecds[i] = (RollbackECD) ecs[i].getECD();
        }

        return ecds;
    }

    private class TestValidator extends Validator
    {
        private final StringECD triggerECD;

        private final StringRollbackECD rollbackECD;

        private final String errorMsg;

        private final EventChannelState[] rollbackState;

        public TestValidator(StringECD triggerECD,
                EventChannelState[] rollbackState)
        {
            super("Test Validator " + triggerECD.getEventChannelName(),
                    new StringECD[] { triggerECD }, getECDs(rollbackState));
            this.triggerECD = triggerECD;
            this.errorMsg = null;
            this.rollbackECD = null;
            this.rollbackState = rollbackState;
        }

        public TestValidator(StringECD triggerECD,
                StringRollbackECD rollbackECD, String errorMsg)
        {
            super("Test Validator " + triggerECD.getEventChannelName(),
                    new StringECD[] { triggerECD },
                    new RollbackECD[] { rollbackECD });
            this.triggerECD = triggerECD;
            this.rollbackECD = rollbackECD;
            this.errorMsg = errorMsg;
            this.rollbackState = null;
        }

        public void validateState()
        {
            String state = (String) get(triggerECD);

            if (state.equals("invalid"))
            {
                if (rollbackECD != null)
                {
                    rollback(rollbackECD, errorMsg);
                }
                else
                {
                    rollback(rollbackState);
                }
            }
        }
    }

    private class TestCommit extends Commit
    {
        String commitValue = null;

        StateMap stateMap = null;

        private final ObjectECD trigger;

        public TestCommit(ObjectECD trigger, ObjectECD[] nontriggers)
        {
            super("Test Commit " + trigger.getEventChannelName(),
                    new ObjectECD[] { trigger }, nontriggers, null);
            this.trigger = trigger;
        }

        public void commit()
        {
            stateMap = this.get();
            commitValue = (String) get(trigger);
        }
    }

    private class TestRollbackHandler extends Validator
    {
        public TestRollbackHandler()
        {
            super("Test", new ObjectECD[] { aECD },
                    new RollbackECD[] { error1ECD });
        }

        public void validateState()
        {
        }

        public void testRollback(RollbackECD sourceECD, Object state)
        {
            rollback(sourceECD, state);
        }

        public void testRollback(EventChannelState[] ecs)
        {
            rollback(ecs);
        }
    }

    private class TestConverter extends Converter
    {
        private final StringECD input;

        private final StringECD output;

        private String inValue;

        public TestConverter(String name, StringECD input, StringECD output)
        {
            super(name, new StringECD[] { input }, new StringECD[] { output });
            this.input = input;
            this.output = output;
        }

        protected void convert()
        {
            this.inValue = (String) get(input);
            set(output, this.inValue);
        }
    }
}
