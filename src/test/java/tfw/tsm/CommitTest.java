package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

/**
 *
 */
class CommitTest {
    private final ObjectECD portA = new StringECD("A");
    private final ObjectECD portB = new StringECD("B");
    private final ObjectECD portC = new StringECD("C");
    private final ObjectECD portD = new StringECD("D");
    private final Initiator initA = new Initiator("initiator a", new ObjectECD[] {portA});
    private final Initiator initB = new Initiator("initiator b", new ObjectECD[] {portB});
    private final Initiator initC = new Initiator("initiator c", new ObjectECD[] {portC});
    private final Initiator initD = new Initiator("initiator d", new ObjectECD[] {portD});
    private final TestCommit testCommit = new TestCommit(
            "Test Commit", new ObjectECD[] {portA, portB}, new ObjectECD[] {portC, portD}, new Initiator[] {initA, initB
            });

    public void testConstructor() {
        try {
            new TestCommit(null, new ObjectECD[] {portA}, null, null);
            fail("Constructor accepted null name");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new TestCommit("TestCommit", null, new ObjectECD[] {portA}, null);
            fail("Constructor accepted null triggerSinks");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new TestCommit("TestCommit", new ObjectECD[] {}, new ObjectECD[] {portA}, null);
            fail("Constructor accepted empty triggerSinks");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new TestCommit("TestCommit", new ObjectECD[] {portA, null}, null, null);
            fail("Constructor accepted null triggerSinks element");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new TestCommit("TestCommit", new ObjectECD[] {portA}, new ObjectECD[] {null}, null);
            fail("Constructor accepted null nonTriggerSinks element");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new TestCommit("TestCommit", new ObjectECD[] {portA}, null, new Initiator[] {null});
            fail("Constructor accepted null initiator element");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    @Test
    void testTriggerBehavior() throws Exception {
        RootFactory rf = new RootFactory();
        rf.addEventChannel(portA, "avalue");
        rf.addEventChannel(portB, "bvalue");
        rf.addEventChannel(portC, "cvalue");
        rf.addEventChannel(portD, "dvalue");

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Branch branch = rf.create("TestBranch", queue);
        branch.add(initA);
        branch.add(initB);
        branch.add(initC);
        branch.add(initD);
        branch.add(testCommit);
        testCommit.clear();
        initC.set(portC, "cvalue");
        queue.waitTilEmpty();
        assertFalse(testCommit.commitFired, "commit() was called on non-trigger sink!");
        assertFalse(testCommit.debugCommitFired, "debugCommit() was called on non-trigger sink!");

        initA.set(portA, "avalue");
        queue.waitTilEmpty();
        assertFalse(testCommit.commitFired, "commit() was called on an initiator sink!");
        assertFalse(testCommit.debugCommitFired, "debugCommit()  was called on an initiator sink!");

        testCommit.clear();
        branch.add(new SetAOnA("SetAOnA", portA));
        initA.set(portA, "avalue");
        queue.waitTilEmpty();
        assertTrue(testCommit.commitFired, "commit() was not called!");
        assertEquals("true", testCommit.portAState, "portA value wrong!");

        testCommit.clear();
        branch.add(new SetAOnC("SetAOnC", portC, portA));

        // Visualize.print(branch);
        initC.set(portC, "anything");
        queue.waitTilEmpty();
        assertTrue(testCommit.commitFired, "commit() was not called!");
        assertEquals("false", testCommit.portAState, "portA value wrong!");
    }

    private static class SetAOnA extends Converter {
        private final ObjectECD portA;

        public SetAOnA(String name, ObjectECD portA) {
            super(name, new ObjectECD[] {portA}, new ObjectECD[] {portA});
            this.portA = portA;
        }

        @Override
        public void convert() {
            String value = (String) get(portA);

            if (!value.equals("true") && !value.equals("false")) {
                set(portA, "true");
            }
        }
    }

    private static class SetAOnC extends Converter {
        private final ObjectECD portA;

        public SetAOnC(String name, ObjectECD portC, ObjectECD portA) {
            super(name, new ObjectECD[] {portC}, new ObjectECD[] {portA});
            this.portA = portA;
        }

        @Override
        public void convert() {
            set(portA, "false");
        }
    }

    private class TestCommit extends Commit {
        public boolean commitFired = false;
        public boolean debugCommitFired = false;
        public String portAState = null;
        public String portBState = null;
        public String portCState = null;
        public String portDState = null;

        public TestCommit(String name, ObjectECD[] triggerSinks, ObjectECD[] nonTriggerSinks, Initiator[] initiators) {
            super(name, triggerSinks, nonTriggerSinks, initiators);
        }

        private void setState() {
            portAState = (String) get(portA);
            portBState = (String) get(portA);
            portCState = (String) get(portA);
            portDState = (String) get(portA);
        }

        public void clear() {
            commitFired = false;
            debugCommitFired = false;
            portAState = null;
            portBState = null;
            portCState = null;
            portDState = null;
        }

        @Override
        public void commit() {
            commitFired = true;
            setState();
        }

        @Override
        public void debugCommit() {
            debugCommitFired = true;
            setState();
        }
    }
}
