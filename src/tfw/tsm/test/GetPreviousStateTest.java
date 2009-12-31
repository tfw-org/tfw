/*
 * Created on Jan 27, 2006
 *
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
 * without even the implied warranty of
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
import tfw.tsm.TransactionExceptionHandler;
import tfw.tsm.ecd.StringECD;

public class GetPreviousStateTest extends TestCase
{
    StringECD channel = new StringECD("channel");

    public void testIsStateChanged()
    {
        final String initialState = "initialState";
        final String stateChangeOne = "StateOne";
        final String stateChangeTwo = "StateTwo";
        RootFactory rf = new RootFactory();
        rf.setTransactionExceptionHandler(new TransactionExceptionHandler()
        {
            public void handle(Exception e)
            {
                e.printStackTrace();
                fail("Test failed with an exception: " + e.getMessage());
            }
        });
        rf.addEventChannel(channel, initialState);
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("TestRoot", queue);

        TestConverter converter = new TestConverter();
        TestCommit commit = new TestCommit();
        root.add(converter);
        root.add(commit);
        queue.waitTilEmpty();
        assertEquals("getPreviousCycleState() failed inital value = "
                + converter.value, initialState, converter.value);
        assertEquals("getPreviousTransactionState() failed initial value = "
                + commit.value, initialState, commit.value);

        Initiator initiator = new Initiator("initiator", channel);
        root.add(initiator);
        initiator.set(channel, stateChangeOne);
        queue.waitTilEmpty();
        assertEquals("getPreviousCycleState() failed inital value = "
                + converter.value, initialState, converter.value);
        assertEquals("getPreviousTransactionState() failed initial value = "
                + commit.value, initialState, commit.value);
        
        initiator.set(channel, stateChangeTwo);
        queue.waitTilEmpty();
        assertEquals("getPreviousCycleState() failed inital value = "
                + converter.value, stateChangeOne, converter.value);
        assertEquals("getPreviousTransactionState() failed initial value = "
                + commit.value, stateChangeOne, commit.value);
    }

    private class TestConverter extends Converter
    {
        private String value = null;

        public TestConverter()
        {
            super("TestConverter", new StringECD[] { channel },
                    new StringECD[] {});
        }

        protected void convert()
        {
            value = (String) this.getPreviousCycleState(channel);
        }
    }

    private class TestCommit extends Commit
    {
        private String value = null;

        public TestCommit()
        {
            super("TestCommit", new StringECD[] { channel });
        }

        protected void commit()
        {
            value = (String) this.getPreviousTransactionState(channel);
        }
    }
}
