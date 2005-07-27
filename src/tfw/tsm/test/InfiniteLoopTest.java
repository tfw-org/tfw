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
import tfw.tsm.Commit;
import tfw.tsm.Converter;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StringECD;


/**
 *
 */
public class InfiniteLoopTest extends TestCase
{
    private static int count = 0;
    private static final StringECD porta = new StringECD("a");
    private static final StringECD portb = new StringECD("b");
    private static final StringECD portc = new StringECD("c");
    String cvalue = null;
    private boolean loop = true;
    private boolean transactionStarted = false;
    private final Converter trigger = new Converter("TriggeredConverter", new StringECD[]{portc},
            null, null)
        {
            protected void convert()
            {
                cvalue = (String) get(portc);
            }
        };

    private Converter convertAtoB = new Converter("A to B Converter",
            new EventChannelDescription[]{ porta }, new EventChannelDescription[]{ portb })
        {
            protected void convert()
            {
                transactionStarted = true;
                //System.out.println("a to b " + count++);

                if (loop)
                {
                    set(portb, "from a to b");
                }
            }
        };

    private Converter convertBtoA = new Converter("B to A Converter",
            new EventChannelDescription[]{ portb }, new EventChannelDescription[]{ porta })
        {
            protected void convert()
            {
                //System.out.println("b to a " + count++);

                if (loop)
                {
                    set(porta, "from b to a");
                }
            }
        };

    private Initiator initiator = new Initiator("Infinite loop initiator",
            new EventChannelDescription[]{ porta, portc });
    public boolean isCommit = false;
    boolean isDebugCommit = false;
    String avalue = null;
    String bvalue = null;
    private final Commit commit = new Commit("Infinite loop commit",
            new EventChannelDescription[]{ porta, portb })
        {
            protected void commit()
            {
                isCommit = true;
                avalue = (String) get(porta);
                bvalue = (String) get(portb);
            }

            protected void debugCommit()
            {
                isDebugCommit = true;
                avalue = (String) get(porta);
                bvalue = (String) get(portb);
            }
        };

    public void testInfiniteLoop() throws Exception
    {
        RootFactory rf = new RootFactory();
        BasicTransactionQueue queue = new BasicTransactionQueue();
//        SynchronousAWTTransactionQueue syncQueue = new SynchronousAWTTransactionQueue();
        rf.addEventChannel(porta);
        rf.addEventChannel(portb);
        rf.addEventChannel(portc);

        Root root = rf.create("Infinite loop test", queue);
        root.add(initiator);
        root.add(convertAtoB);

        root.add(convertBtoA);
        root.add(trigger);
        root.add(commit);
        initiator.set(porta, "kick off value");
        //queue.waitTilEmpty();
        assertFalse("commit was called", isCommit);
        assertFalse("debugCommit was called", isDebugCommit);

        String newTransaction = "generating new transaction";

        while (transactionStarted == false)
        {
            Thread.sleep(10);
        }

        initiator.set(portc, newTransaction);
        
        // Give it a chance to break through
        // Note that there is no guarantee that we
        // sleep long enough...
        Thread.sleep(50);

        assertNull("initiator broke into the infinite transaction.", cvalue);
        
        // terminate the infinite loop transaction...
        loop = false;
        
        // Give the follow on transaction a chance to 
        // to execute...
        queue.waitTilEmpty();
        assertEquals("intiator did cause value to set after loop was broken",
            newTransaction, cvalue);
    }
}
