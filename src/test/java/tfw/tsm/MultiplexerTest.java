package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFill;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

final class MultiplexerTest {
    private StringECD valueECD = new StringECD("value");
    private ObjectIlaECD multiValueECD = new ObjectIlaECD("multiValue");
    private ObjectIlaECD multiMultiValueECD = new ObjectIlaECD("multiMultiValue");

    @Test
    void multiplexerWithIntermediateBranchTest() throws Exception {
        StatelessTriggerECD triggerECD = new StatelessTriggerECD("trigger");
        RootFactory rf = new RootFactory();
        rf.addEventChannel(triggerECD);

        // THE FOLLOWING LINE SHOULD NOT HAVE A SECOND ARGUMENT.  THE SECOND
        // ARGUMENT IS PUT IN TO SOLVE THE "multistate == null not allowed!"
        // EXCEPTION!
        rf.addEventChannel(multiValueECD, ObjectIlaFill.create(null, 2));
        TestTransactionExceptionHandler exceptionHandler = new TestTransactionExceptionHandler();
        rf.setTransactionExceptionHandler(exceptionHandler);

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
        assertThat(valueCommit0.value).isEqualTo("0");

        Initiator initiator1 = new Initiator("InitiatorValue1", valueECD);
        ValueCommit valueCommit1 = new ValueCommit("CommitValue1", valueECD);
        subBranch1.add(initiator1);
        subBranch1.add(valueCommit1);

        initiator1.set(valueECD, "1");
        queue.waitTilEmpty();
        assertThat(valueCommit0.value).isEqualTo("0");
        assertThat(valueCommit1.value).isEqualTo("1");

        Initiator multiInitiator = new Initiator("MultiValueInitiator", multiValueECD);
        root.add(multiInitiator);

        String[] strings = new String[] {"zero", "one"};

        ObjectIla<Object> obj = ObjectIlaFromArray.create(strings);
        multiInitiator.set(multiValueECD, obj);
        queue.waitTilEmpty();

        assertThat(strings[0]).isEqualTo(valueCommit0.value);
        assertThat(strings[1]).isEqualTo(valueCommit1.value);

        MultiValueCommit mvCommit = new MultiValueCommit("MultiValueCommit", multiValueECD);
        root.add(mvCommit);

        initiator0.set(valueECD, "0");
        queue.waitTilEmpty();

        final Object[] multiValueArray0 = new Object[(int) mvCommit.value.length()];
        mvCommit.value.get(multiValueArray0, 0, 0, multiValueArray0.length);

        assertThat(valueCommit0.value).isEqualTo("0");
        assertThat(valueCommit1.value).isEqualTo("one");
        assertThat(multiValueArray0[0]).isEqualTo("0");
        assertThat(multiValueArray0[1]).isEqualTo("one");

        TestTriggeredConverter tc0 = new TestTriggeredConverter("tc0", triggerECD, "tc0", valueECD);
        TestTriggeredConverter tc1 = new TestTriggeredConverter("tc1", triggerECD, "tc1", valueECD);

        Initiator triggerInitiator = new Initiator("trigger", triggerECD);
        root.add(triggerInitiator);

        subBranch0.add(tc0);
        subBranch1.add(tc1);

        triggerInitiator.trigger(triggerECD);
        queue.waitTilEmpty();

        final Object[] multiValueArray1 = new Object[(int) mvCommit.value.length()];
        mvCommit.value.get(multiValueArray1, 0, 0, multiValueArray1.length);

        assertThat(valueCommit0.value).isEqualTo("tc0");
        assertThat(valueCommit1.value).isEqualTo("tc1");
        assertThat(multiValueArray1[0]).isEqualTo("tc0");
        assertThat(multiValueArray1[1]).isEqualTo("tc1");
        // We remove one of the component to cause a new transaction in order
        // to make sure that no exceptions are thrown...
        subBranch0.remove(valueCommit0);
        triggerInitiator.trigger(triggerECD);
        queue.waitTilEmpty();
        assertThat(exceptionHandler.exp).isNull();

        subBranch0.add(valueCommit0);
        String tc0Value = "tc0-1";
        String tc1Value = "tc1-1";
        tc0.value = tc0Value;
        tc1.value = tc1Value;
        triggerInitiator.trigger(triggerECD);
        queue.waitTilEmpty();

        final Object[] multiValueArray2 = new Object[(int) mvCommit.value.length()];
        mvCommit.value.get(multiValueArray2, 0, 0, multiValueArray2.length);

        assertThat(tc0Value).isEqualTo(multiValueArray2[0]);
        assertThat(tc1Value).isEqualTo(multiValueArray2[1]);

        assertThat(exceptionHandler.exp).isNull();
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

        assertThat(objArray3[0]).isEqualTo(multiValueArray3[0]);
        assertThat(objArray3[1]).isEqualTo(multiValueArray3[1]);
        // Now we put the multiplexer back and make sure it still works.
        root.add(multiBranch);
        queue.waitTilEmpty();

        final Object[] objArray4 = new Object[(int) obj.length()];
        obj.get(objArray4, 0, 0, objArray4.length);

        assertThat(objArray4[0]).isEqualTo(valueCommit0.value);
        assertThat(objArray4[1]).isEqualTo(valueCommit1.value);
    }

    @Test
    void multiLayerMultiplexingTest() throws Exception {

        String v00 = "Value0.0";
        String v01 = "Value0.1";
        String v10 = "Value1.0";
        String v11 = "Value1.1";

        ObjectIla<Object> vmmo = createMultiMultiValue(v00, v01, v10, v11);
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

        Initiator initiator00 = new Initiator("Value0_0", valueECD);
        ValueCommit valueCommit00 = new ValueCommit("Value 0_0", valueECD);
        Initiator initiator01 = new Initiator("Value0_1", valueECD);
        ValueCommit valueCommit01 = new ValueCommit("Value 0_1", valueECD);
        multiBranchZero.add(initiator00, 0);
        multiBranchZero.add(valueCommit00, 0);
        multiBranchZero.add(initiator01, 1);
        multiBranchZero.add(valueCommit01, 1);

        Initiator initiator10 = new Initiator("Value1_0", valueECD);
        ValueCommit valueCommit10 = new ValueCommit("Value 1_0", valueECD);
        Initiator initiator11 = new Initiator("Value1_1", valueECD);
        ValueCommit valueCommit11 = new ValueCommit("Value 1_1", valueECD);
        multiBranchOne.add(initiator10, 0);
        multiBranchOne.add(valueCommit10, 0);
        multiBranchOne.add(initiator11, 1);
        multiBranchOne.add(valueCommit11, 1);

        MultiValueCommit mmValueCommit = new MultiValueCommit("multiMultiValueCommit", multiMultiValueECD);

        root.add(multiMultiBranch);
        root.add(mmValueCommit);

        queue.waitTilEmpty();
        checkState(
                vmmo,
                mmValueCommit.value,
                valueCommit00.value,
                valueCommit01.value,
                valueCommit10.value,
                valueCommit11.value);

        v11 = "hello 1.1";
        vmmo = createMultiMultiValue(v00, v01, v10, v11);
        initiator11.set(valueECD, v11);
        queue.waitTilEmpty();
        checkState(
                vmmo,
                mmValueCommit.value,
                valueCommit00.value,
                valueCommit01.value,
                valueCommit10.value,
                valueCommit11.value);
    }

    @Test
    void multipleMultiplexersTest() {
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
        Initiator initiator00 = new Initiator("branch0Slot0Initiator", valueECD);
        multiBranch0.add(initiator00, 0);

        MultiplexedBranch multiBranch1 = mbf.create("MultiBranch1");
        ValueCommit vcMb1S0 = new ValueCommit("ValueCommitBranch1Slot0", valueECD);
        multiBranch1.add(vcMb1S0, 0);

        root.add(multiBranch0);
        root.add(multiBranch1);
        queue.waitTilEmpty();
        assertThat(slot0).isEqualTo(vcMb0S0.value).isEqualTo(vcMb1S0.value);

        slot0 = "xyz";
        initiator00.set(valueECD, slot0);
        queue.waitTilEmpty();
        assertThat(slot0).isEqualTo(vcMb0S0.value).isEqualTo(vcMb1S0.value);
    }

    private ObjectIla<Object> createMultiMultiValue(String v00, String v01, String v10, String v11) {
        String[] vm0 = new String[] {v00, v01};
        String[] vm1 = new String[] {v10, v11};

        ObjectIla<Object> vmo0 = ObjectIlaFromArray.create(vm0);
        ObjectIla<Object> vmo1 = ObjectIlaFromArray.create(vm1);

        return ObjectIlaFromArray.create(new Object[] {vmo0, vmo1});
    }

    private void checkState(
            ObjectIla<Object> mmAnswer, ObjectIla<Object> mmResult, String v00, String v01, String v10, String v11)
            throws Exception {
        Object[][] mma = toArray(mmAnswer);
        assertThat(mma[0][0]).isEqualTo(v00);
        assertThat(mma[0][1]).isEqualTo(v01);
        assertThat(mma[1][0]).isEqualTo(v10);
        assertThat(mma[1][1]).isEqualTo(v11);

        Object[][] mmr = toArray(mmResult);
        assertThat(mma[0][0]).isEqualTo(mmr[0][0]);
        assertThat(mma[0][1]).isEqualTo(mmr[0][1]);
        assertThat(mma[1][0]).isEqualTo(mmr[1][0]);
        assertThat(mma[1][1]).isEqualTo(mmr[1][1]);
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

    private static class TestTriggeredConverter extends TriggeredConverter {
        private String value;

        private final StringECD outputECD;

        public TestTriggeredConverter(String name, StatelessTriggerECD triggerECD, String value, StringECD outputECD) {
            super(name, triggerECD, null, new StringECD[] {outputECD});
            this.value = value;
            this.outputECD = outputECD;
        }

        @Override
        protected void convert() {
            set(outputECD, value);
        }
    }

    private static class ValueCommit extends Commit {
        public String value;

        public final ObjectECD valueECD;

        public ValueCommit(String name, ObjectECD valueECD) {
            super(name, new ObjectECD[] {valueECD});
            this.valueECD = valueECD;
        }

        @Override
        public void commit() {
            value = (String) get(valueECD);
        }
    }

    private static class MultiValueCommit extends Commit {
        public ObjectIla<Object> value;

        public final ObjectECD valueECD;

        public MultiValueCommit(String name, ObjectECD valueECD) {
            super(name, new ObjectECD[] {valueECD});
            this.valueECD = valueECD;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void commit() {
            value = (ObjectIla<Object>) get(valueECD);
        }
    }
}
