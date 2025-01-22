package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.CharacterECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

final class SynchronizerTest {
    private ObjectECD source = new StringECD("source");
    private ObjectECD sink = new StringECD("sink");
    private ObjectECD a1Port = new CharacterECD("a1");
    private ObjectECD a2Port = new CharacterECD("a2");
    private ObjectECD b1Port = new CharacterECD("b1");
    private ObjectECD b2Port = new CharacterECD("b2");
    private ObjectECD[] aChans = new ObjectECD[] {a1Port, a2Port};
    private ObjectECD[] bChans = new ObjectECD[] {b1Port, b2Port};
    private ObjectECD[] sinks = new ObjectECD[] {sink};
    private ObjectECD[] sources = new ObjectECD[] {source};
    private char commitA1 = ' ';
    private char commitA2 = ' ';
    private char commitB1 = ' ';
    private char commitB2 = ' ';
    private boolean aToBFired = false;
    private boolean bToAFired = false;
    private Initiator initiator = new Initiator("Initiator", new ObjectECD[] {a1Port, a2Port, b1Port, b2Port, source});
    private Synchronizer converter =
            new Synchronizer(
                    "TwoWay", new ObjectECD[] {a1Port, a2Port}, new ObjectECD[] {b1Port, b2Port}, sinks, sources) {

                @Override
                protected void convertAToB() {
                    aToBFired = true;

                    Character ch = (Character) get(a1Port);

                    if (ch != null) {
                        set(b1Port, Character.toUpperCase(ch.charValue()));
                    }

                    ch = (Character) get(a2Port);

                    if (ch != null) {
                        set(b2Port, Character.toUpperCase(ch.charValue()));
                    }

                    set(source, "cause an error");
                }

                @Override
                protected void convertBToA() {
                    bToAFired = true;

                    Character ch = (Character) get(b1Port);

                    if (ch != null) {
                        set(a1Port, Character.toLowerCase(ch.charValue()));
                    }

                    ch = (Character) get(b2Port);

                    if (ch != null) {
                        set(a2Port, Character.toLowerCase(ch.charValue()));
                    }

                    set(source, "cause an error");
                }
            };

    private Commit commit = new Commit("Commit", new ObjectECD[] {a1Port, a2Port, b1Port, b2Port}) {
        @Override
        protected void commit() {
            commitA1 = ((Character) get(a1Port)).charValue();
            commitA2 = ((Character) get(a2Port)).charValue();
            commitB1 = ((Character) get(b1Port)).charValue();
            commitB2 = ((Character) get(b2Port)).charValue();
        }

        @Override
        protected void debugCommit() {
            Character ch = (Character) get(a1Port);

            if (ch != null) {
                commitA1 = ((Character) get(a1Port)).charValue();
            }

            ch = (Character) get(a2Port);

            if (ch != null) {
                commitA2 = ((Character) get(a2Port)).charValue();
            }

            ch = (Character) get(b1Port);

            if (ch != null) {
                commitB1 = ((Character) get(b1Port)).charValue();
            }

            ch = (Character) get(b2Port);

            if (ch != null) {
                commitB2 = ((Character) get(b2Port)).charValue();
            }
        }
    };

    private Converter rollback = new Converter("rollback", new ObjectECD[] {a1Port}, new ObjectECD[] {b1Port}) {
        @Override
        protected void convert() {
            initiator.set(b1Port, 'b');
            rollback();
        }
    };

    private Exception exception = null;
    private boolean fired = false;
    private Converter causeAtoBtoAError =
            new Converter("causeAtoBToAerror", new ObjectECD[] {source}, new ObjectECD[] {b1Port}) {
                @Override
                protected void convert() {
                    if (fired) {
                        return;
                    }

                    fired = true;

                    set(b1Port, 'E');
                }
            };

    private Converter causeBtoAtoBError =
            new Converter("causeAtoBToAerror", new ObjectECD[] {source}, new ObjectECD[] {a1Port}) {
                @Override
                protected void convert() {
                    if (fired) {
                        return;
                    }

                    fired = true;

                    set(a1Port, 'e');
                }
            };

    private BasicTransactionQueue queue = new BasicTransactionQueue();

    public Root initializeRoot(TransactionExceptionHandler handler) {
        RootFactory rf = new RootFactory();

        rf.addEventChannel(a1Port, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(a2Port, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(b1Port, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(b2Port, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(source, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(sink, null, AlwaysChangeRule.RULE, null);
        if (handler != null) {
            rf.setTransactionExceptionHandler(handler);
        }

        Root root = rf.create("Root", queue);
        root.add(initiator);
        root.add(converter);
        root.add(commit);
        queue.waitTilEmpty();

        return root;
    }

    @Test
    void constructionTest() {
        assertThatThrownBy(() -> new TestTwoWay(null, aChans, bChans, sinks, sources))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> new TestTwoWay("Test", null, bChans, sinks, sources))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("aPortDescriptions == null not allowed!");
        assertThatThrownBy(() -> new TestTwoWay("Test", new ObjectECD[0], bChans, sinks, sources))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("aPortDescriptions.length == 0 not allowed");
        assertThatThrownBy(() -> new TestTwoWay("Test", new ObjectECD[] {null}, bChans, sinks, sources))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("aPortDescription[0]== null not allowed!");
        assertThatThrownBy(() -> new TestTwoWay("Test", aChans, null, sinks, sources))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bPortDescriptions == null not allowed!");
        assertThatThrownBy(() -> new TestTwoWay("Test", aChans, new ObjectECD[0], sinks, sources))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bPortDescriptions.length == 0 not allowed");
        assertThatThrownBy(() -> new TestTwoWay("Test", aChans, new ObjectECD[] {null}, sinks, sources))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bPortDescription[0]== null not allowed!");
        assertThatThrownBy(() -> new TestTwoWay("Test", aChans, bChans, new ObjectECD[] {null}, sources))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sinkEventChannels[0]== null not allowed!");
        assertThatThrownBy(() -> new TestTwoWay("Test", aChans, bChans, sinks, new ObjectECD[] {null}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sources[4]== null not allowed!");
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

    private static class TestTwoWay extends Synchronizer {
        public TestTwoWay(String name, ObjectECD[] aChans, ObjectECD[] bChans, ObjectECD[] sinks, ObjectECD[] sources) {
            super(name, aChans, bChans, sinks, sources);
        }

        @Override
        public void convertAToB() {}

        @Override
        public void convertBToA() {}
    }
}
