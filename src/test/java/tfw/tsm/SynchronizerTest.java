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
package tfw.tsm;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import tfw.tsm.AlwaysChangeRule;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Commit;
import tfw.tsm.Converter;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.Synchronizer;
import tfw.tsm.TransactionExceptionHandler;
import tfw.tsm.ecd.CharacterECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;


public class SynchronizerTest extends TestCase
{
    private ObjectECD source = new StringECD("source");
    private ObjectECD sink = new StringECD("sink");
    private ObjectECD a1Port = new CharacterECD("a1");
    private ObjectECD a2Port = new CharacterECD("a2");
    private ObjectECD b1Port = new CharacterECD("b1");
    private ObjectECD b2Port = new CharacterECD("b2");
    private ObjectECD[] aChans = new ObjectECD[]
        {
            a1Port, a2Port
        };
    private ObjectECD[] bChans = new ObjectECD[]
        {
            b1Port, b2Port
        };
    private ObjectECD[] sinks = new ObjectECD[]{ sink };
    private ObjectECD[] sources = new ObjectECD[]
        {
            source
        };
    private char commitA1 = ' ';
    private char commitA2 = ' ';
    private char commitB1 = ' ';
    private char commitB2 = ' ';
    private boolean aToBFired = false;
    private boolean bToAFired = false;
    private Initiator initiator = new Initiator("Initiator",
            new ObjectECD[]{ a1Port, a2Port, b1Port, b2Port, source });
    private Synchronizer converter = new Synchronizer("TwoWay",
            new ObjectECD[]{ a1Port, a2Port },
            new ObjectECD[]{ b1Port, b2Port }, sinks, sources)
        {
            protected void convertAToB()
            {
                //System.out.println("convertAToB");
                aToBFired = true;

                Character ch = (Character) get(a1Port);

                if (ch != null)
                {
                    set(b1Port,
                        new Character(Character.toUpperCase(ch.charValue())));
                }

                ch = (Character) get(a2Port);

                if (ch != null)
                {
                    set(b2Port,
                        new Character(Character.toUpperCase(ch.charValue())));
                }

                set(source, "cause an error");
            }

            protected void convertBToA()
            {
                //System.out.println("convertBToA");
                bToAFired = true;

                Character ch = (Character) get(b1Port);

                if (ch != null)
                {
                    set(a1Port,
                        new Character(Character.toLowerCase(ch.charValue())));
                }

                ch = (Character) get(b2Port);

                if (ch != null)
                {
                    set(a2Port,
                        new Character(Character.toLowerCase(ch.charValue())));
                }

                set(source, "cause an error");
            }
        };

    private Commit commit = new Commit("Commit",
            new ObjectECD[]{ a1Port, a2Port, b1Port, b2Port })
        {
            protected void commit()
            {
                Character ch = (Character) get(a1Port);
                commitA1 = ((Character) get(a1Port)).charValue();
                ch = (Character) get(a2Port);
                commitA2 = ((Character) get(a2Port)).charValue();
                ch = (Character) get(b1Port);
                commitB1 = ((Character) get(b1Port)).charValue();
                ch = (Character) get(b2Port);
                commitB2 = ((Character) get(b2Port)).charValue();
            }

            protected void debugCommit()
            {
                Character ch = (Character) get(a1Port);

                if (ch != null)
                {
                    commitA1 = ((Character) get(a1Port)).charValue();
                }

                ch = (Character) get(a2Port);

                if (ch != null)
                {
                    commitA2 = ((Character) get(a2Port)).charValue();
                }

                ch = (Character) get(b1Port);

                if (ch != null)
                {
                    commitB1 = ((Character) get(b1Port)).charValue();
                }

                ch = (Character) get(b2Port);

                if (ch != null)
                {
                    commitB2 = ((Character) get(b2Port)).charValue();
                }
            }
        };

    private Converter rollback = new Converter("rollback",
            new ObjectECD[]{ a1Port },
            new ObjectECD[]{ b1Port })
        {
            protected void convert()
            {
                //System.out.println("Executing Rollback");
                initiator.set(b1Port, new Character('b'));
                rollback();
            }
        };

    private Exception exception = null;
    private boolean fired = false;
    private Converter causeAtoBtoAError = new Converter("causeAtoBToAerror",
            new ObjectECD[]{ source },
            new ObjectECD[]{ b1Port })
        {
            protected void convert()
            {
                if (fired)
                {
                    return;
                }

                fired = true;

                //System.err.println("\nset b1 to cause error\n");
                set(b1Port, new Character('E'));
            }
        };

    private Converter causeBtoAtoBError = new Converter("causeAtoBToAerror",
            new ObjectECD[]{ source },
            new ObjectECD[]{ a1Port })
        {
            protected void convert()
            {
                if (fired)
                {
                    return;
                }

                fired = true;

                //System.out.println("set a1 to cause error");
                set(a1Port, new Character('e'));
            }
        };

    private BasicTransactionQueue queue = new BasicTransactionQueue();

    public SynchronizerTest(String test)
    {
        super(test);
    }

    public SynchronizerTest()
    {
        super();
    }

    public Root initializeRoot(TransactionExceptionHandler handler)
    {
        RootFactory rf = new RootFactory();

        //rf.setLogging(true);
        rf.addEventChannel(a1Port, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(a2Port, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(b1Port, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(b2Port, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(source, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(sink, null, AlwaysChangeRule.RULE, null);
        if (handler != null){
        	rf.setTransactionExceptionHandler(handler);
        }
		
        Root root = rf.create("Root", queue);
        root.add(initiator);
        root.add(converter);
        root.add(commit);
        queue.waitTilEmpty();
        
        return root;
    }

    public void testConstruction()
    {
        try
        {
            new TestTwoWay(null, aChans, bChans, sinks, sources);
            fail("Constructor accepted null name");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new TestTwoWay("Test", null, bChans, sinks, sources);
            fail("Constructor accepted null 'A' channels");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new TestTwoWay("Test", new ObjectECD[0], bChans,
                sinks, sources);
            fail("Constructor accepted empty 'A' channels");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new TestTwoWay("Test", new ObjectECD[]{ null },
                bChans, sinks, sources);
            fail("Constructor accepted 'A' channel array with null value");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new TestTwoWay("Test", aChans, null, sinks, sources);
            fail("Constructor accepted null 'B' channels");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new TestTwoWay("Test", aChans, new ObjectECD[0],
                sinks, sources);
            fail("Constructor accepted empty 'B' channels");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new TestTwoWay("Test", aChans,
                new ObjectECD[]{ null }, sinks, sources);
            fail("Constructor accepted 'B' channel array with null value");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new TestTwoWay("Test", aChans, bChans,
                new ObjectECD[]{ null }, sources);
            fail("Constructor accepted sinks channel array with null value");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new TestTwoWay("Test", aChans, bChans, sinks,
                new ObjectECD[]{ null });
            fail("Constructor accepted sources channel array with null value");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
    }

/*
    public void testConvertAToB() throws Exception
    {
        this.aToBFired = false;
        initializeRoot(null);
        initiator.set(a1Port, new Character('a'));
        queue.waitTilEmpty();
        assertEquals("CommitA1", 'a', commitA1);
        assertEquals("CommitA2", ' ', commitA2);
        assertEquals("CommitB1", ' ', commitB1);
        assertEquals("CommitB2", ' ', commitB2);
        assertFalse("convertAToB() called with a2 not set", this.aToBFired);

        initiator.set(a2Port, new Character('b'));
        queue.waitTilEmpty();
        assertEquals("CommitA1", 'a', commitA1);
        assertEquals("CommitA2", 'b', commitA2);
        assertEquals("CommitB1", 'A', commitB1);
        assertEquals("CommitB2", 'B', commitB2);
        assertTrue("convertAToB() not called with both channels set",
            this.aToBFired);
        this.aToBFired = false;
        initiator.set(source, "alskjf");
        queue.waitTilEmpty();
        assertFalse("convertAToB() called on non-triggering channel",
            this.aToBFired);
    }

    public void testConvertBToA()
    {
        this.bToAFired = false;
        initializeRoot(null);
        initiator.set(b1Port, new Character('A'));
        queue.waitTilEmpty();
        assertEquals("CommitA1", ' ', commitA1);
        assertEquals("CommitA2", ' ', commitA2);
        assertEquals("CommitB1", 'A', commitB1);
        assertEquals("CommitB2", ' ', commitB2);
        assertFalse("convertBToA() called with b2 not set", this.bToAFired);

        initiator.set(b2Port, new Character('B'));
        queue.waitTilEmpty();
        assertEquals("CommitA1", 'a', commitA1);
        assertEquals("CommitA2", 'b', commitA2);
        assertEquals("CommitB1", 'A', commitB1);
        assertEquals("CommitB2", 'B', commitB2);
        assertTrue("convertBToA() not called with both channels set",
            this.bToAFired);
        this.bToAFired = false;
        initiator.set(source, "alskjf");
        queue.waitTilEmpty();
        assertFalse("convertAToB() called on non-triggering channel",
            this.bToAFired);
    }
*/
    //    	 This test doesn't work because it doesn't cause the state to 
    //    	 change in the same transaction.
 /*   public void testAtoBtoAError() throws Exception
    {
        initiator.set(a1Port, new Character('a'));
        initiator.set(a2Port, new Character('b'));
        Root root = initializeRoot(new TransactionExceptionHandler()
		{
			public void handle(Exception exception)
			{
				SynchronizerTest.this.exception = exception;

				//exception.printStackTrace();
			}
		});
        root.add(causeAtoBtoAError);
        this.exception = null;


        queue.waitTilEmpty();
        fired = false;

        //System.out.println("initiate error");
        initiator.set(a1Port, new Character('g'));
        queue.waitTilEmpty();

        if (exception == null)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        //System.out.println(exception);
        assertNotNull("Failed to detect 'a' to 'b' to 'a' conversion", exception);
    }
*/
/*    public void testBtoAtoBError() throws Exception
    {
        initiator.set(b1Port, new Character('A'));
        initiator.set(b2Port, new Character('B'));
        Root root = initializeRoot(new TransactionExceptionHandler()
		{
			public void handle(Exception exception)
			{
				SynchronizerTest.this.exception = exception;

				//exception.printStackTrace();
			}
		});
        root.add(causeBtoAtoBError);

        queue.waitTilEmpty();
        this.exception = null;
        fired = false;

        //System.out.println("initiator error");
        initiator.set(b1Port, new Character('G'));
        queue.waitTilEmpty();

        if (exception == null)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        //System.out.println(exception);
        assertNotNull("Failed to detect 'b' to 'a' to 'b' conversion", exception);
    }
*/
    //    public void testRollback()
    //    {
    //        branch.add(rollback);
    //        SynchronizeThread.sync();
    //        initiator.set(a1Port, new Character('a'));
    //        SynchronizeThread.sync();
    //
    //        try
    //        {
    //            initiator.set(b2Port, new Character('B'));
    //            SynchronizeThread.sync();
    //        }
    //        catch (IllegalStateException unexpected)
    //        {
    //            fail(
    //                "Synchronizer didn't clean up it's internal state on rollback");
    //        }
    //    }
    //    /* this test does not work because exception is thrown in another thread
    //            public void testConvertConflict()
    //                    throws InterruptedException, InvocationTargetException
    //            {
    //                    BranchFactory nf = new BranchFactory();
    //                    nf.addTerminator(a1Port);
    //                    nf.addTerminator(a2Port);
    //                    nf.addTerminator(b1Port);
    //                    nf.addTerminator(b2Port);
    //                    nf.setRoot(true);
    //                    Branch branch = nf.create("Test branch");
    //                    branch.add(initiator);
    //                    branch.add(converter);
    //                    branch.add(commit);
    //                    SynchronizeThread.sync();
    //
    //                    initiator.set(a1Port, new Character('a'));
    //                    try
    //                    {
    //                            initiator.set(b1Port, new Character('A'));
    //                            fail("Synchronizer accept state change in both"
    //                                            +" A and B event set");
    //                    }
    //                    catch(IllegalStateException expected)
    //                    {
    //                    }
    //            }
    //    */
    public static Test suite()
    {
        //    	TestSuite suite = new TestSuite();
        //    	suite.addTest(new SynchronizerTest("testAtoBtoAError"));
        //    	return suite;
        return new TestSuite(SynchronizerTest.class);
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }

    private class TestTwoWay extends Synchronizer
    {
        public TestTwoWay(String name, ObjectECD[] aChans,
            ObjectECD[] bChans, ObjectECD[] sinks,
            ObjectECD[] sources)
        {
            super(name, aChans, bChans, sinks, sources);
        }

        public void convertAToB()
        {
        }

        public void convertBToA()
        {
        }
    }
}
