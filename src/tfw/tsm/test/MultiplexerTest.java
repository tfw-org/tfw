/*
 * The Framework Project Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; witout even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package tfw.tsm.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Branch;
import tfw.tsm.BranchFactory;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.MultiplexedBranch;
import tfw.tsm.MultiplexedBranchFactory;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class MultiplexerTest extends TestCase
{
    private StringECD valueECD = new StringECD("value");

    private ObjectIlaECD multiValueECD = new ObjectIlaECD("multiValue");

    private ObjectIlaECD multiMultiValueECD = new ObjectIlaECD(
            "multiMultiValue");

    public void testMultiplexerWithIntermediateBranch() throws Exception
    {
        StatelessTriggerECD triggerECD = new StatelessTriggerECD("trigger");
        RootFactory rf = new RootFactory();
        rf.addEventChannel(triggerECD);
        rf.addEventChannel(multiValueECD);
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
        assertEquals("value 0 not correct", "0", valueCommit0.value);

        Initiator initiator1 = new Initiator("InitiatorValue1", valueECD);
        ValueCommit valueCommit1 = new ValueCommit("CommitValue1", valueECD);
        subBranch1.add(initiator1);
        subBranch1.add(valueCommit1);

        initiator1.set(valueECD, "1");
        queue.waitTilEmpty();
        assertEquals("value 0 not correct", "0", valueCommit0.value);
        assertEquals("value 1 not correct", "1", valueCommit1.value);

        Initiator multiInitiator = new Initiator("MultiValueInitiator",
                multiValueECD);
        root.add(multiInitiator);

        String[] strings = new String[] { "zero", "one" };

        ObjectIla obj = ObjectIlaFromArray.create(strings);
        multiInitiator.set(multiValueECD, obj);
        queue.waitTilEmpty();

        assertEquals("value 0 not correct", strings[0], valueCommit0.value);
        assertEquals("value 1 not correct", strings[1], valueCommit1.value);

        MultiValueCommit mvCommit = new MultiValueCommit("MultiValueCommit",
                multiValueECD);
        root.add(mvCommit);

        initiator0.set(valueECD, "0");
        queue.waitTilEmpty();
        assertEquals("value 0 not correct", "0", valueCommit0.value);
        assertEquals("value 1 not correct", "one", valueCommit1.value);
        assertEquals("multiValue[0] not correct", "0",
                mvCommit.value.toArray()[0]);
        assertEquals("multiValue[1] not correct", "one", mvCommit.value
                .toArray()[1]);

        MyTriggeredConverter tc0 = new MyTriggeredConverter("tc0", triggerECD,
                "tc0", valueECD);
        MyTriggeredConverter tc1 = new MyTriggeredConverter("tc1", triggerECD,
                "tc1", valueECD);

        Initiator triggerInitiator = new Initiator("trigger", triggerECD);
        root.add(triggerInitiator);

        subBranch0.add(tc0);
        subBranch1.add(tc1);

        triggerInitiator.trigger(triggerECD);
        queue.waitTilEmpty();
        assertEquals("value 0 not correct", "tc0", valueCommit0.value);
        assertEquals("value 1 not correct", "tc1", valueCommit1.value);
        assertEquals("multiValue[0] not correct", "tc0", mvCommit.value
                .toArray()[0]);
        assertEquals("multiValue[1] not correct", "tc1", mvCommit.value
                .toArray()[1]);
        // We remove one of the component to cause a new transaction in order
        // to make sure that no exceptions are thrown...
        subBranch0.remove(valueCommit0);
        triggerInitiator.trigger(triggerECD);
        queue.waitTilEmpty();
        assertNull("Unexpected exception thrown during testing",
                exceptionHandler.exp);

        subBranch0.add(valueCommit0);
        String tc0Value = "tc0-1";
        String tc1Value = "tc1-1";
        tc0.value = tc0Value;
        tc1.value = tc1Value;
        triggerInitiator.trigger(triggerECD);
        queue.waitTilEmpty();
        assertEquals("multiValue[0] not correct", tc0Value, mvCommit.value
                .toArray()[0]);
        assertEquals("multiValue[1] not correct", tc1Value, mvCommit.value
                .toArray()[1]);

        assertNull("Unexpected exception thrown during testing",
                exceptionHandler.exp);
        // Now we will remove the multiplexer...and make sure everything still
        // works.
        root.remove(multiBranch);
        queue.waitTilEmpty();
        multiInitiator.set(multiValueECD, obj);
        queue.waitTilEmpty();
        assertEquals("multiValue[0] not correct", obj.toArray()[0],
                mvCommit.value.toArray()[0]);
        assertEquals("multiValue[1] not correct", obj.toArray()[1],
                mvCommit.value.toArray()[1]);
        // Now we put the multiplexer back and make sure it still works.
        root.add(multiBranch);
        queue.waitTilEmpty();
        assertEquals("value 0 not correct", obj.toArray()[0],
                valueCommit0.value);
        assertEquals("value 1 not correct", obj.toArray()[1],
                valueCommit1.value);
    }

    public void testMultiLayerMultiplexing() throws Exception
    {

        String v0_0 = "Value0.0";
        String v0_1 = "Value0.1";
        String v1_0 = "Value1.0";
        String v1_1 = "Value1.1";

        ObjectIla vmmo = createMultiMultiValue(v0_0, v0_1, v1_0, v1_1);
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

        MultiValueCommit mmValueCommit = new MultiValueCommit(
                "multiMultiValueCommit", multiMultiValueECD);

        root.add(multiMultiBranch);
        root.add(mmValueCommit);

        queue.waitTilEmpty();
        checkState(vmmo, mmValueCommit.value, valueCommit0_0.value,
                valueCommit0_1.value, valueCommit1_0.value,
                valueCommit1_1.value);

        v1_1 = "hello 1.1";
        vmmo = createMultiMultiValue(v0_0, v0_1, v1_0, v1_1);
        initiator1_1.set(valueECD, v1_1);
        queue.waitTilEmpty();
        checkState(vmmo, mmValueCommit.value, valueCommit0_0.value,
                valueCommit0_1.value, valueCommit1_0.value,
                valueCommit1_1.value);
    }

    public void testMultipleMultiplexers()
    {
        String slot0 = "slot0";

        RootFactory rf = new RootFactory();
        rf.addEventChannel(multiValueECD, ObjectIlaFromArray
                .create(new String[] { slot0 }));
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("MultiplexerTestRoot", queue);

        MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
        mbf.addMultiplexer(valueECD, multiValueECD);
        MultiplexedBranch multiBranch0 = mbf.create("MultiBranch0");
        ValueCommit vcMb0S0 = new ValueCommit("ValueCommitBranch0Slot0",
                valueECD);
        multiBranch0.add(vcMb0S0, 0);
        Initiator initiator0_0 = new Initiator("branch0Slot0Initiator",
                valueECD);
        multiBranch0.add(initiator0_0, 0);

        MultiplexedBranch multiBranch1 = mbf.create("MultiBranch1");
        ValueCommit vcMb1S0 = new ValueCommit("ValueCommitBranch1Slot0",
                valueECD);
        multiBranch1.add(vcMb1S0, 0);

        root.add(multiBranch0);
        root.add(multiBranch1);
        queue.waitTilEmpty();
        assertEquals("branch0Slot0", slot0, vcMb0S0.value);
        assertEquals("branch1Slot0", slot0, vcMb1S0.value);

        slot0 = "xyz";
        initiator0_0.set(valueECD, slot0);
        queue.waitTilEmpty();
        assertEquals("branch0Slot0", slot0, vcMb0S0.value);
        assertEquals("branch1Slot0", slot0, vcMb1S0.value);
    }

    private ObjectIla createMultiMultiValue(String v0_0, String v0_1,
            String v1_0, String v1_1)
    {
        String[] vm0 = new String[] { v0_0, v0_1 };
        String[] vm1 = new String[] { v1_0, v1_1 };

        ObjectIla vmo0 = ObjectIlaFromArray.create(vm0);
        ObjectIla vmo1 = ObjectIlaFromArray.create(vm1);

        ObjectIla vmmo = ObjectIlaFromArray
                .create(new ObjectIla[] { vmo0, vmo1 });
        return vmmo;
    }

    private void checkState(ObjectIla mmAnswer, ObjectIla mmResult,
            String v0_0, String v0_1, String v1_0, String v1_1)
            throws Exception
    {
        Object[][] mma = toArray(mmAnswer);
        assertEquals("[0][0]", mma[0][0], v0_0);
        assertEquals("[0][1]", mma[0][1], v0_1);
        assertEquals("[1][0]", mma[1][0], v1_0);
        assertEquals("[1][1]", mma[1][1], v1_1);

        Object[][] mmr = toArray(mmResult);
        assertEquals("[0][0]", mma[0][0], mmr[0][0]);
        assertEquals("[0][1]", mma[0][1], mmr[0][1]);
        assertEquals("[1][0]", mma[1][0], mmr[1][0]);
        assertEquals("[1][1]", mma[1][1], mmr[1][1]);
    }

    private Object[][] toArray(ObjectIla objs) throws Exception
    {
        Object[][] mmArray = new Object[2][];
        Object[] mma = objs.toArray();
        mmArray[0] = ((ObjectIla) mma[0]).toArray();
        mmArray[1] = ((ObjectIla) mma[1]).toArray();
        return mmArray;
    }

    public static Test suite()
    {
        return new TestSuite(MultiplexerTest.class);
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }

    private class MyTriggeredConverter extends TriggeredConverter
    {
        private String value;

        private final StringECD outputECD;

        public MyTriggeredConverter(String name,
                StatelessTriggerECD triggerECD, String value,
                StringECD outputECD)
        {
            super(name, triggerECD, null, new StringECD[] { outputECD });
            this.value = value;
            this.outputECD = outputECD;
        }

        /*
         * (non-Javadoc)
         * 
         * @see tfw.tsm.TriggeredConverter#convert()
         */
        protected void convert()
        {
            set(outputECD, value);
        }
    }

    private class ValueCommit extends Commit
    {
        public String value;

        public final ObjectECD valueECD;

        public ValueCommit(String name, ObjectECD valueECD)
        {
            super(name, new ObjectECD[] { valueECD });
            this.valueECD = valueECD;
        }

        public void commit()
        {
            value = (String) get(valueECD);
        }
    }

    private class MultiValueCommit extends Commit
    {
        public ObjectIla value;

        public final ObjectECD valueECD;

        public MultiValueCommit(String name, ObjectECD valueECD)
        {
            super(name, new ObjectECD[] { valueECD });
            this.valueECD = valueECD;
        }

        public void commit()
        {
            value = (ObjectIla) get(valueECD);
        }
    }
}