package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFill;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

class MultiplexerTest {
    private StringECD valueECD = new StringECD("value");

    private ObjectIlaECD multiValueECD = new ObjectIlaECD("multiValue");

    private ObjectIlaECD multiMultiValueECD = new ObjectIlaECD("multiMultiValue");

    @Test
    void testMultiplexerWithIntermediateBranch() throws Exception {
        StatelessTriggerECD triggerECD = new StatelessTriggerECD("trigger");
        RootFactory rf = new RootFactory();
        rf.addEventChannel(triggerECD);

        // THE FOLLOWING LINE SHOULD NOT HAVE A SECOND ARGUMENT.  THE SECOND
        // ARGUMENT IS PUT IN TO SOLVE THE "multistate == null not allowed!"
        // EXCEPTION!
        rf.addEventChannel(multiValueECD, ObjectIlaFill.create(null, 2));
        TestTransactionExceptionHandler exceptionHandler = new TestTransactionExceptionHandler();
        rf.setTransactionExceptionHandler(exceptionHandler);
        // rf.setLogging(true);

        BasicTransactionQueue queue = new BasicTransactionQueue();

        Root root = rf.create("MultiplexerTestRoot", queue);

        MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
        mbf.addMultiplexer(valueECD, multiValueECD);

        MultiplexedBranch multiBranch = mbf.create("Multiplexer");

        BranchFactory subBranchFactory = new BranchFactory();
        Branch subBranch0 = subBranchFactory.create("subBranch0");
        Branch subBranch1 = subBranchFactory.create("subBranch1");
        multiBranch.add(subBranch0, 0);
        multiBranch.add(subBranch1, 1);

        Initiator initiator0 = new Initiator("InitiatorValue0", valueECD);
        ValueCommit valueCommit0 = new ValueCommit("CommitValue0", valueECD);

        subBranch0.add(initiator0);
        subBranch0.add(valueCommit0);

        root.add(multiBranch);
        initiator0.set(valueECD, "0");
        queue.waitTilEmpty();
        assertEquals("0", valueCommit0.value, "value 0 not correct");

        Initiator initiator1 = new Initiator("InitiatorValue1", valueECD);
        ValueCommit valueCommit1 = new ValueCommit("CommitValue1", valueECD);
        subBranch1.add(initiator1);
        subBranch1.add(valueCommit1);

        initiator1.set(valueECD, "1");
        queue.waitTilEmpty();
        assertEquals("0", valueCommit0.value, "value 0 not correct");
        assertEquals("1", valueCommit1.value, "value 1 not correct");

        Initiator multiInitiator = new Initiator("MultiValueInitiator", multiValueECD);
        root.add(multiInitiator);

        String[] strings = new String[] {"zero", "one"};

        ObjectIla<Object> obj = ObjectIlaFromArray.create(strings);
        multiInitiator.set(multiValueECD, obj);
        queue.waitTilEmpty();

        assertEquals(strings[0], valueCommit0.value, "value 0 not correct");
        assertEquals(strings[1], valueCommit1.value, "value 1 not correct");

        MultiValueCommit mvCommit = new MultiValueCommit("MultiValueCommit", multiValueECD);
        root.add(mvCommit);

        initiator0.set(valueECD, "0");
        queue.waitTilEmpty();

        final Object[] multiValueArray0 = new Object[(int) mvCommit.value.length()];
        mvCommit.value.get(multiValueArray0, 0, 0, multiValueArray0.length);

        assertEquals("0", valueCommit0.value, "value 0 not correct");
        assertEquals("one", valueCommit1.value, "value 1 not correct");
        assertEquals("0", multiValueArray0[0], "multiValue[0] not correct");
        assertEquals("one", multiValueArray0[1], "multiValue[1] not correct");

        MyTriggeredConverter tc0 = new MyTriggeredConverter("tc0", triggerECD, "tc0", valueECD);
        MyTriggeredConverter tc1 = new MyTriggeredConverter("tc1", triggerECD, "tc1", valueECD);

        Initiator triggerInitiator = new Initiator("trigger", triggerECD);
        root.add(triggerInitiator);

        subBranch0.add(tc0);
        subBranch1.add(tc1);

        triggerInitiator.trigger(triggerECD);
        queue.waitTilEmpty();

        final Object[] multiValueArray1 = new Object[(int) mvCommit.value.length()];
        mvCommit.value.get(multiValueArray1, 0, 0, multiValueArray1.length);

        assertEquals("tc0", valueCommit0.value, "value 0 not correct");
        assertEquals("tc1", valueCommit1.value, "value 1 not correct");
        assertEquals("tc0", multiValueArray1[0], "multiValue[0] not correct");
        assertEquals("tc1", multiValueArray1[1], "multiValue[1] not correct");
        // We remove one of the component to cause a new transaction in order
        // to make sure that no exceptions are thrown...
        subBranch0.remove(valueCommit0);
        triggerInitiator.trigger(triggerECD);
        queue.waitTilEmpty();
        assertNull(exceptionHandler.exp, "Unexpected exception thrown during testing");

        subBranch0.add(valueCommit0);
        String tc0Value = "tc0-1";
        String tc1Value = "tc1-1";
        tc0.value = tc0Value;
        tc1.value = tc1Value;
        triggerInitiator.trigger(triggerECD);
        queue.waitTilEmpty();

        final Object[] multiValueArray2 = new Object[(int) mvCommit.value.length()];
        mvCommit.value.get(multiValueArray2, 0, 0, multiValueArray2.length);

        assertEquals(tc0Value, multiValueArray2[0], "multiValue[0] not correct");
        assertEquals(tc1Value, multiValueArray2[1], "multiValue[1] not correct");

        assertNull(exceptionHandler.exp, "Unexpected exception thrown during testing");
        // Now we will remove the multiplexer...and make sure everything still
        // works.
        root.remove(multiBranch);
        queue.waitTilEmpty();
        multiInitiator.set(multiValueECD, obj);
        queue.waitTilEmpty();

        final Object[] multiValueArray3 = new Object[(int) mvCommit.value.length()];
        final Object[] objArray3 = new Object[(int) obj.length()];
        mvCommit.value.get(multiValueArray3, 0, 0, multiValueArray3.length);
        obj.get(objArray3, 0, 0, objArray3.length);

        assertEquals(objArray3[0], multiValueArray3[0], "multiValue[0] not correct");
        assertEquals(objArray3[1], multiValueArray3[1], "multiValue[1] not correct");
        // Now we put the multiplexer back and make sure it still works.
        root.add(multiBranch);
        queue.waitTilEmpty();

        final Object[] objArray4 = new Object[(int) obj.length()];
        obj.get(objArray4, 0, 0, objArray4.length);

        assertEquals(objArray4[0], valueCommit0.value, "value 0 not correct");
        assertEquals(objArray4[1], valueCommit1.value, "value 1 not correct");
    }

    @Test
    void testMultiLayerMultiplexing() throws Exception {

        String v0_0 = "Value0.0";
        String v0_1 = "Value0.1";
        String v1_0 = "Value1.0";
        String v1_1 = "Value1.1";

        ObjectIla<Object> vmmo = createMultiMultiValue(v0_0, v0_1, v1_0, v1_1);
        // TODO add multiMultiInitiator and multiMultiCommit...

        RootFactory rf = new RootFactory();
        rf.addEventChannel(multiMultiValueECD, vmmo);
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("MultiplexerTestRoot", queue);

        MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
        mbf.addMultiplexer(multiValueECD, multiMultiValueECD);
        MultiplexedBranch multiMultiBranch = mbf.create("MultiMultiBranch");

        mbf = new MultiplexedBranchFactory();
        mbf.addMultiplexer(valueECD, multiValueECD);
        MultiplexedBranch multiBranchZero = mbf.create("MultiBranchZero");

        mbf = new MultiplexedBranchFactory();
        mbf.addMultiplexer(valueECD, multiValueECD);
        MultiplexedBranch multiBranchOne = mbf.create("MultiBranchOne");

        multiMultiBranch.add(multiBranchZero, 0);
        multiMultiBranch.add(multiBranchOne, 1);

        Initiator initiator0_0 = new Initiator("Value0_0", valueECD);
        ValueCommit valueCommit0_0 = new ValueCommit("Value 0_0", valueECD);
        Initiator initiator0_1 = new Initiator("Value0_1", valueECD);
        ValueCommit valueCommit0_1 = new ValueCommit("Value 0_1", valueECD);
        multiBranchZero.add(initiator0_0, 0);
        multiBranchZero.add(valueCommit0_0, 0);
        multiBranchZero.add(initiator0_1, 1);
        multiBranchZero.add(valueCommit0_1, 1);

        Initiator initiator1_0 = new Initiator("Value1_0", valueECD);
        ValueCommit valueCommit1_0 = new ValueCommit("Value 1_0", valueECD);
        Initiator initiator1_1 = new Initiator("Value1_1", valueECD);
        ValueCommit valueCommit1_1 = new ValueCommit("Value 1_1", valueECD);
        multiBranchOne.add(initiator1_0, 0);
        multiBranchOne.add(valueCommit1_0, 0);
        multiBranchOne.add(initiator1_1, 1);
        multiBranchOne.add(valueCommit1_1, 1);

        MultiValueCommit mmValueCommit = new MultiValueCommit("multiMultiValueCommit", multiMultiValueECD);

        root.add(multiMultiBranch);
        root.add(mmValueCommit);

        queue.waitTilEmpty();
        checkState(
                vmmo,
                mmValueCommit.value,
                valueCommit0_0.value,
                valueCommit0_1.value,
                valueCommit1_0.value,
                valueCommit1_1.value);

        v1_1 = "hello 1.1";
        vmmo = createMultiMultiValue(v0_0, v0_1, v1_0, v1_1);
        initiator1_1.set(valueECD, v1_1);
        queue.waitTilEmpty();
        checkState(
                vmmo,
                mmValueCommit.value,
                valueCommit0_0.value,
                valueCommit0_1.value,
                valueCommit1_0.value,
                valueCommit1_1.value);
    }

    @Test
    void testMultipleMultiplexers() {
        String slot0 = "slot0";

        RootFactory rf = new RootFactory();
        rf.addEventChannel(multiValueECD, ObjectIlaFromArray.create(new String[] {slot0}));
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("MultiplexerTestRoot", queue);

        MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
        mbf.addMultiplexer(valueECD, multiValueECD);
        MultiplexedBranch multiBranch0 = mbf.create("MultiBranch0");
        ValueCommit vcMb0S0 = new ValueCommit("ValueCommitBranch0Slot0", valueECD);
        multiBranch0.add(vcMb0S0, 0);
        Initiator initiator0_0 = new Initiator("branch0Slot0Initiator", valueECD);
        multiBranch0.add(initiator0_0, 0);

        MultiplexedBranch multiBranch1 = mbf.create("MultiBranch1");
        ValueCommit vcMb1S0 = new ValueCommit("ValueCommitBranch1Slot0", valueECD);
        multiBranch1.add(vcMb1S0, 0);

        root.add(multiBranch0);
        root.add(multiBranch1);
        queue.waitTilEmpty();
        assertEquals(slot0, vcMb0S0.value, "branch0Slot0");
        assertEquals(slot0, vcMb1S0.value, "branch1Slot0");

        slot0 = "xyz";
        initiator0_0.set(valueECD, slot0);
        queue.waitTilEmpty();
        assertEquals(slot0, vcMb0S0.value, "branch0Slot0");
        assertEquals(slot0, vcMb1S0.value, "branch1Slot0");
    }

    private ObjectIla<Object> createMultiMultiValue(String v0_0, String v0_1, String v1_0, String v1_1) {
        String[] vm0 = new String[] {v0_0, v0_1};
        String[] vm1 = new String[] {v1_0, v1_1};

        ObjectIla<Object> vmo0 = ObjectIlaFromArray.create(vm0);
        ObjectIla<Object> vmo1 = ObjectIlaFromArray.create(vm1);

        ObjectIla<Object> vmmo = ObjectIlaFromArray.create(new Object[] {vmo0, vmo1});
        return vmmo;
    }

    private void checkState(
            ObjectIla<Object> mmAnswer, ObjectIla<Object> mmResult, String v0_0, String v0_1, String v1_0, String v1_1)
            throws Exception {
        Object[][] mma = toArray(mmAnswer);
        assertEquals(mma[0][0], v0_0, "[0][0]");
        assertEquals(mma[0][1], v0_1, "[0][1]");
        assertEquals(mma[1][0], v1_0, "[1][0]");
        assertEquals(mma[1][1], v1_1, "[1][1]");

        Object[][] mmr = toArray(mmResult);
        assertEquals(mma[0][0], mmr[0][0], "[0][0]");
        assertEquals(mma[0][1], mmr[0][1], "[0][1]");
        assertEquals(mma[1][0], mmr[1][0], "[1][0]");
        assertEquals(mma[1][1], mmr[1][1], "[1][1]");
    }

    @SuppressWarnings("unchecked")
    private Object[][] toArray(ObjectIla<Object> objs) throws Exception {
        Object[][] mmArray = new Object[2][];

        Object[] mma = new Object[(int) objs.length()];
        objs.get(mma, 0, 0, mma.length);

        final ObjectIla<Object> mma0Ila = (ObjectIla<Object>) mma[0];
        final ObjectIla<Object> mma1Ila = (ObjectIla<Object>) mma[1];

        mmArray[0] = new Object[(int) mma0Ila.length()];
        mmArray[1] = new Object[(int) mma1Ila.length()];

        mma0Ila.get(mmArray[0], 0, 0, mmArray[0].length);
        mma1Ila.get(mmArray[1], 0, 0, mmArray[1].length);

        return mmArray;
    }

    private class MyTriggeredConverter extends TriggeredConverter {
        private String value;

        private final StringECD outputECD;

        public MyTriggeredConverter(String name, StatelessTriggerECD triggerECD, String value, StringECD outputECD) {
            super(name, triggerECD, null, new StringECD[] {outputECD});
            this.value = value;
            this.outputECD = outputECD;
        }

        /*
         * (non-Javadoc)
         *
         * @see tfw.tsm.TriggeredConverter#convert()
         */
        protected void convert() {
            set(outputECD, value);
        }
    }

    private class ValueCommit extends Commit {
        public String value;

        public final ObjectECD valueECD;

        public ValueCommit(String name, ObjectECD valueECD) {
            super(name, new ObjectECD[] {valueECD});
            this.valueECD = valueECD;
        }

        public void commit() {
            value = (String) get(valueECD);
        }
    }

    private class MultiValueCommit extends Commit {
        public ObjectIla<Object> value;

        public final ObjectECD valueECD;

        public MultiValueCommit(String name, ObjectECD valueECD) {
            super(name, new ObjectECD[] {valueECD});
            this.valueECD = valueECD;
        }

        @SuppressWarnings("unchecked")
        public void commit() {
            value = (ObjectIla<Object>) get(valueECD);
        }
    }
}
