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

import junit.framework.TestCase;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Branch;
import tfw.tsm.Commit;
import tfw.tsm.Converter;
import tfw.tsm.Initiator;
import tfw.tsm.RootFactory;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;


/**
 *
 */
public class CommitTest extends TestCase
{
    private final EventChannelDescription portA = new StringECD("A");
    private final EventChannelDescription portB = new StringECD("B");
    private final EventChannelDescription portC = new StringECD("C");
    private final EventChannelDescription portD = new StringECD("D");
    private final Initiator initA = new Initiator("initiator a",
            new EventChannelDescription[]{ portA });
    private final Initiator initB = new Initiator("initiator b",
            new EventChannelDescription[]{ portB });
    private final Initiator initC = new Initiator("initiator c",
            new EventChannelDescription[]{ portC });
    private final Initiator initD = new Initiator("initiator d",
            new EventChannelDescription[]{ portD });
    private final MyCommit mycommit = new MyCommit("Test Commit",
            new EventChannelDescription[]{ portA, portB },
            new EventChannelDescription[]{ portC, portD },
            new Initiator[]{ initA, initB });

    public void testConstructor()
    {
        try
        {
            new MyCommit(null, new EventChannelDescription[]{ portA }, null,
                null);
            fail("Constructor accepted null name");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyCommit("MyCommit", null,
                new EventChannelDescription[]{ portA }, null);
            fail("Constructor accepted null triggerSinks");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyCommit("MyCommit", new EventChannelDescription[]{  },
                new EventChannelDescription[]{ portA }, null);
            fail("Constructor accepted empty triggerSinks");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyCommit("MyCommit",
                new EventChannelDescription[]{ portA, null }, null, null);
            fail("Constructor accepted null triggerSinks element");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyCommit("MyCommit", new EventChannelDescription[]{ portA },
                new EventChannelDescription[]{ null }, null);
            fail("Constructor accepted null nonTriggerSinks element");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyCommit("MyCommit", new EventChannelDescription[]{ portA },
                null, new Initiator[]{ null });
            fail("Constructor accepted null initiator element");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyCommit("MyCommit",
                new EventChannelDescription[]{ new StatelessTriggerECD("test") },
                null, null);
            fail("Constructor accepted stateless ecd as trigger");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyCommit("MyCommit", new EventChannelDescription[]{ portA },
                new EventChannelDescription[]{ new StatelessTriggerECD("test") },
                null);
            fail("Constructor accepted stateless ecd as non-triggerable");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
    }

    public void testTriggerBehavior() throws Exception
    {
        RootFactory rf = new RootFactory();
        rf.addTerminator(portA, "avalue");
        rf.addTerminator(portB, "bvalue");
        rf.addTerminator(portC, "cvalue");
        rf.addTerminator(portD, "dvalue");

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Branch branch = rf.create("TestBranch", queue);
        branch.add(initA);
        branch.add(initB);
        branch.add(initC);
        branch.add(initD);
        branch.add(mycommit);
        mycommit.clear();
        initC.set(portC, "cvalue");
        queue.waitTilEmpty();
        assertFalse("commit() was called on non-trigger sink!",
            mycommit.commitFired);
        assertFalse("debugCommit() was called on non-trigger sink!",
            mycommit.debugCommitFired);

        initA.set(portA, "avalue");
        queue.waitTilEmpty();
        assertFalse("commit() was called on an initiator sink!",
            mycommit.commitFired);
        assertFalse("debugCommit()  was called on an initiator sink!",
            mycommit.debugCommitFired);

        mycommit.clear();
        branch.add(new SetAOnA("SetAOnA", portA));
        initA.set(portA, "avalue");
		queue.waitTilEmpty();
        assertTrue("commit() was not called!", mycommit.commitFired);
        assertEquals("portA value wrong!", "true", mycommit.portAState);

        mycommit.clear();
        branch.add(new SetAOnC("SetAOnC", portC, portA));

        //Visualize.print(branch);
        initC.set(portC, "anything");
		queue.waitTilEmpty();
        assertTrue("commit() was not called!", mycommit.commitFired);
        assertEquals("portA value wrong!", "false", mycommit.portAState);
    }

    private class SetAOnA extends Converter
    {
        private final EventChannelDescription portA;

        public SetAOnA(String name, EventChannelDescription portA)
        {
            super(name, new EventChannelDescription[]{ portA },
                new EventChannelDescription[]{ portA });
            this.portA = portA;
        }

        public void convert()
        {
            String value = (String) get(portA);

            if (!value.equals("true") && !value.equals("false"))
            {
                set(portA, "true");
            }
        }
    }

    private class SetAOnC extends Converter
    {
        private final EventChannelDescription portA;
        private final EventChannelDescription portC;

        public SetAOnC(String name, EventChannelDescription portC,
            EventChannelDescription portA)
        {
            super(name, new EventChannelDescription[]{ portC },
                new EventChannelDescription[]{ portA });
            this.portA = portA;
            this.portC = portC;
        }

        public void convert()
        {
            set(portA, "false");
        }
    }

    private class MyCommit extends Commit
    {
        public boolean commitFired = false;
        public boolean debugCommitFired = false;
        public String portAState = null;
        public String portBState = null;
        public String portCState = null;
        public String portDState = null;

        public MyCommit(String name, EventChannelDescription[] triggerSinks,
            EventChannelDescription[] nonTriggerSinks, Initiator[] initiators)
        {
            super(name, triggerSinks, nonTriggerSinks, initiators);
        }

        private void setState()
        {
            portAState = (String) get(portA);
            portBState = (String) get(portA);
            portCState = (String) get(portA);
            portDState = (String) get(portA);
        }

        public void clear()
        {
            commitFired = false;
            debugCommitFired = false;
            portAState = null;
            portBState = null;
            portCState = null;
            portDState = null;
        }

        public void commit()
        {
            commitFired = true;
            setState();
        }

        public void debugCommit()
        {
            debugCommitFired = true;
            setState();
        }
    }
}
