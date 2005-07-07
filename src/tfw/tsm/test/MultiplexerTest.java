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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.MultiplexedBranchFactory;
import tfw.tsm.MultiplexedBranch;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectIlaECD;
import tfw.tsm.ecd.StringECD;


public class MultiplexerTest extends TestCase
{
    private EventChannelDescription valueECD = new StringECD("value");
    private ObjectIlaECD multiValueECD = new ObjectIlaECD("multiValue");

    public void testMultiplexing()
    {
        RootFactory rf = new RootFactory();
        rf.addTerminator(multiValueECD);
        BasicTransactionQueue queue = new BasicTransactionQueue();

        Root root = rf.create("MultiplexerTestRoot",queue);

        MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
        mbf.addMultiplexer(valueECD, multiValueECD);

        MultiplexedBranch multiBranch = mbf.create("Multiplexer");

        Initiator initiator0 = new Initiator("Value0", valueECD);
        ValueCommit valueCommit0 = new ValueCommit("Value 0", valueECD);

        multiBranch.add(initiator0, 0);
        multiBranch.add(valueCommit0, 0);

        root.add(multiBranch);
        initiator0.set(valueECD, "0");
		queue.waitTilEmpty();
        assertEquals("value 0 not correct", "0", valueCommit0.value);

        Initiator initiator1 = new Initiator("Value1", valueECD);
        ValueCommit valueCommit1 = new ValueCommit("Value 1", valueECD);
        multiBranch.add(initiator1, 1);
        multiBranch.add(valueCommit1, 1);

        initiator1.set(valueECD, "1");
		queue.waitTilEmpty();
        assertEquals("value 0 not correct", "0", valueCommit0.value);
        assertEquals("value 1 not correct", "1", valueCommit1.value);

        Initiator multiInitiator = new Initiator("MultiValue", multiValueECD);
        root.add(multiInitiator);

        String[] strings = new String[]{ "zero", "one" };

        ObjectIla obj = ObjectIlaFromArray.create(strings);
        multiInitiator.set(multiValueECD, obj);
		queue.waitTilEmpty();
        assertEquals("value 0 not correct", strings[0], valueCommit0.value);
        assertEquals("value 1 not correct", strings[1], valueCommit1.value);

        MultiValueCommit mvCommit = new MultiValueCommit("MultiValue",
                multiValueECD);
        root.add(mvCommit);

        initiator0.set(valueECD, "0");
		queue.waitTilEmpty();
        assertEquals("value 0 not correct", "0", valueCommit0.value);
        assertEquals("value 1 not correct", "one", valueCommit1.value);
        assertEquals("multiValue[0] not correct", "0",
            mvCommit.value.toArray()[0]);
        assertEquals("multiValue[1] not correct", "one",
            mvCommit.value.toArray()[1]);
            
        //TODO: add ProcessorSource test case.
    }

    public static Test suite()
    {
        return new TestSuite(MultiplexerTest.class);
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }

    private class ValueCommit extends Commit
    {
        public String value;
        public final EventChannelDescription valueECD;

        public ValueCommit(String name, EventChannelDescription valueECD)
        {
            super(name, new EventChannelDescription[]{ valueECD });
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
            super(name, new EventChannelDescription[]{ valueECD });
            this.valueECD = valueECD;
        }

        public void commit()
        {
            value = (ObjectIla) get(valueECD);
        }
    }
}
