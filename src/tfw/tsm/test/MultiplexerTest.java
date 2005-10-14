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
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class MultiplexerTest extends TestCase
{
    private StringECD valueECD = new StringECD("value");

    private ObjectIlaECD multiValueECD = new ObjectIlaECD("multiValue");

    // public void testMultiplexing() throws Exception
    // {
    // StatelessTriggerECD triggerECD = new StatelessTriggerECD("trigger");
    // RootFactory rf = new RootFactory();
    // rf.addEventChannel(triggerECD);
    // rf.addEventChannel(multiValueECD);
    // //rf.setLogging(true);
    //        
    // BasicTransactionQueue queue = new BasicTransactionQueue();
    //
    // Root root = rf.create("MultiplexerTestRoot", queue);
    //
    // MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
    // mbf.addMultiplexer(valueECD, multiValueECD);
    //
    // MultiplexedBranch multiBranch = mbf.create("Multiplexer");
    //
    // Initiator initiator0 = new Initiator("Value0", valueECD);
    // ValueCommit valueCommit0 = new ValueCommit("Value 0", valueECD);
    //
    // multiBranch.add(initiator0, 0);
    // multiBranch.add(valueCommit0, 0);
    //
    // root.add(multiBranch);
    // initiator0.set(valueECD, "0");
    // queue.waitTilEmpty();
    // assertEquals("value 0 not correct", "0", valueCommit0.value);
    //
    // Initiator initiator1 = new Initiator("Value1", valueECD);
    // ValueCommit valueCommit1 = new ValueCommit("Value 1", valueECD);
    // multiBranch.add(initiator1, 1);
    // multiBranch.add(valueCommit1, 1);
    //
    // initiator1.set(valueECD, "1");
    // queue.waitTilEmpty();
    // assertEquals("value 0 not correct", "0", valueCommit0.value);
    // assertEquals("value 1 not correct", "1", valueCommit1.value);
    //
    // Initiator multiInitiator = new Initiator("MultiValueInitiator",
    // multiValueECD);
    // root.add(multiInitiator);
    //
    // String[] strings = new String[] { "zero", "one" };
    //
    // ObjectIla obj = ObjectIlaFromArray.create(strings);
    // multiInitiator.set(multiValueECD, obj);
    // queue.waitTilEmpty();
    //
    // assertEquals("value 0 not correct", strings[0], valueCommit0.value);
    // assertEquals("value 1 not correct", strings[1], valueCommit1.value);
    //
    // MultiValueCommit mvCommit = new MultiValueCommit("MultiValueCommit",
    // multiValueECD);
    // root.add(mvCommit);
    //
    // initiator0.set(valueECD, "0");
    // queue.waitTilEmpty();
    // assertEquals("value 0 not correct", "0", valueCommit0.value);
    // assertEquals("value 1 not correct", "one", valueCommit1.value);
    // assertEquals("multiValue[0] not correct", "0",
    // mvCommit.value.toArray()[0]);
    // assertEquals("multiValue[1] not correct", "one", mvCommit.value
    // .toArray()[1]);
    //
    // //TODO: add ProcessorSource test case.
    // MyTriggeredConverter tc0 = new MyTriggeredConverter("tc0", triggerECD,
    // "tc0", valueECD);
    // MyTriggeredConverter tc1 = new MyTriggeredConverter("tc1", triggerECD,
    // "tc1", valueECD);
    //        
    // Initiator triggerInitiator = new Initiator("trigger", triggerECD);
    // root.add(triggerInitiator);
    //        
    // multiBranch.add(tc0, 0);
    // multiBranch.add(tc1, 1);
    //         
    // triggerInitiator.trigger(triggerECD);
    // queue.waitTilEmpty();
    // assertEquals("value 0 not correct", "tc0", valueCommit0.value);
    // assertEquals("value 1 not correct", "tc1", valueCommit1.value);
    // assertEquals("multiValue[0] not correct", "tc0",
    // mvCommit.value.toArray()[0]);
    // assertEquals("multiValue[1] not correct", "tc1", mvCommit.value
    // .toArray()[1]);
    //        
    // // Test exception in multiple state changes in the same
    // // multiplex sub-channel...
    //        
    // // multiBranch.remove(tc1);
    // // multiBranch.add(tc1,0);
    // // triggerInitiator.trigger(triggerECD);
    // }

    public void testMultiplerWithIntermediateBranch() throws Exception
    {
        StatelessTriggerECD triggerECD = new StatelessTriggerECD("trigger");
        RootFactory rf = new RootFactory();
        rf.addEventChannel(triggerECD);
        rf.addEventChannel(multiValueECD);
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

        Initiator initiator0 = new Initiator("Value0", valueECD);
        ValueCommit valueCommit0 = new ValueCommit("Value 0", valueECD);

        subBranch0.add(initiator0);
        subBranch0.add(valueCommit0);

        root.add(multiBranch);
        initiator0.set(valueECD, "0");
        queue.waitTilEmpty();
        assertEquals("value 0 not correct", "0", valueCommit0.value);

        Initiator initiator1 = new Initiator("Value1", valueECD);
        ValueCommit valueCommit1 = new ValueCommit("Value 1", valueECD);
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

        System.err.println("\n***********\n");
        triggerInitiator.trigger(triggerECD);
        queue.waitTilEmpty();
        assertEquals("value 0 not correct", "tc0", valueCommit0.value);
        assertEquals("value 1 not correct", "tc1", valueCommit1.value);
        assertEquals("multiValue[0] not correct", "tc0", mvCommit.value
                .toArray()[0]);
        assertEquals("multiValue[1] not correct", "tc1", mvCommit.value
                .toArray()[1]);
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
        private final String value;

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

        public final EventChannelDescription valueECD;

        public ValueCommit(String name, EventChannelDescription valueECD)
        {
            super(name, new EventChannelDescription[] { valueECD });
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

        public final EventChannelDescription valueECD;

        public MultiValueCommit(String name, EventChannelDescription valueECD)
        {
            super(name, new EventChannelDescription[] { valueECD });
            this.valueECD = valueECD;
        }

        public void commit()
        {
            value = (ObjectIla) get(valueECD);
        }
    }
}