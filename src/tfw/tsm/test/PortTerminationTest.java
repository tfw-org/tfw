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
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TransactionExceptionHandler;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StringECD;

import junit.framework.TestCase;


/**
 * Test to make sure rooted components are terminated.
 */
public class PortTerminationTest extends TestCase
{
    static Exception expected = null;

    public void testUnTerminatedPort()
    {
        EventChannelDescription ecd = new StringECD("Test");
        RootFactory rf = new RootFactory();
		rf.setTransactionExceptionHandler(new TransactionExceptionHandler()
			{
				public void handle(Exception exception)
				{
					PortTerminationTest.expected = exception;
				}
			});

        //rf.setLogging(true);
        BasicTransactionQueue queue = new BasicTransactionQueue();
		Root root = rf.create("test", queue);

        Commit commit = new Commit("test", new EventChannelDescription[]{ ecd })
            {
                public void commit()
                {
                }
            };

        root.add(commit);
        queue.waitTilEmpty();

        boolean failed = false;

        if (expected == null)
        {
            failed = true;

            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        assertNotNull("Root.add() accepted child with un-terminated ports!",
            expected);
        //assertFalse("waitTilEmpty() failed", failed);
    }
}
