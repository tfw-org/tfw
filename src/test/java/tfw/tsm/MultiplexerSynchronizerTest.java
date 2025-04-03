package tfw.tsm;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.tsm.ecd.ila.ObjectIlaECD;

final class MultiplexerSynchronizerTest {

    private static class MultiCommit extends Commit {

        ObjectIla<Object> objectIla = null;

        private final ObjectIlaECD objectIlaECD;

        public MultiCommit(String name, ObjectIlaECD objectIlaECD) {
            super(name, new ObjectIlaECD[] {objectIlaECD});
            this.objectIlaECD = objectIlaECD;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void commit() {
            this.objectIla = (ObjectIla<Object>) get(this.objectIlaECD);
        }
    }

    @Test
    void multiplexedSynchronizerTest() throws Exception {
        /*
                StringECD stringECD = new StringECD("string");
                IntegerECD integerECD = new IntegerECD("integer");
                ObjectIlaECD multiStringECD = new ObjectIlaECD("multiString");
                ObjectIlaECD multiIntegerECD = new ObjectIlaECD("multiInteger");
                StringRollbackECD errorECD = new StringRollbackECD("error");

                Integer[] integerValues = new Integer[] { new Integer(0),
                        new Integer(1), new Integer(2) };
                ObjectIla initialInts = ObjectIlaFromArray.create(integerValues);
                RootFactory rf = new RootFactory();
                // rf.setLogging(true);

                // THE FOLLOWING LINE SHOULD NOT HAVE A SECOND ARGUMENT.  THE SECOND
                // ARGUMENT IS PUT IN TO SOLVE THE "multistate == null not allowed!"
                // EXCEPTION!
                rf.addEventChannel(multiStringECD, ObjectIlaFill.create(null, 3));
                rf.addEventChannel(multiIntegerECD, initialInts);
                rf.addEventChannel(errorECD);
                BasicTransactionQueue queue = new BasicTransactionQueue();
                Root root = rf.create("Test", queue);

                MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
                mbf.addMultiplexer(stringECD, multiStringECD);
                mbf.addMultiplexer(integerECD, multiIntegerECD);
                MultiplexedBranch mbranch = mbf.create("multiBranch");

        //        mbranch.add(new ObjectStringSynchronizer("sync 0", "sync0", integerECD,
        //                stringECD, errorECD, integerECD.getCodec()), 0);
        //        mbranch.add(new ObjectStringSynchronizer("sync 1", "sync1", integerECD,
        //                stringECD, errorECD, integerECD.getCodec()), 1);
        //        mbranch.add(new ObjectStringSynchronizer("sync 2", "sync2", integerECD,
        //                stringECD, errorECD, integerECD.getCodec()), 2);

                MultiCommit stringMultiCommit = new MultiCommit("StringMultiCommit",
                        multiStringECD);
                MultiCommit integerMultiCommit = new MultiCommit("IntegerMultiCommit",
                        multiIntegerECD);
                root.add(stringMultiCommit);
                root.add(integerMultiCommit);
                root.add(mbranch);
                queue.waitTilEmpty();

                assertNotNull("integerMultiCommit.objectIla",
                        integerMultiCommit.objectIla);
                assertNotNull("stringMultiCommit.objectIla",
                        stringMultiCommit.objectIla);
                String[] strValues = new String[(int) initialInts.length()];
                stringMultiCommit.objectIla.toArray(strValues, 0, 0, strValues.length);

                assertTrue("wrong string values", Arrays.equals(new String[] { "0",
                        "1", "2" }, strValues));

                assertTrue("wrong integer values", Arrays.equals(integerValues,
                        integerMultiCommit.objectIla.toArray()));

                Initiator initiator = new Initiator("myInitiator", new ObjectECD[] {
                        integerECD, stringECD });
                mbranch.add(initiator, 1);
                queue.waitTilEmpty();
                strValues[1] = "100";
                integerValues[1] = new Integer(100);
                initiator.set(stringECD, strValues[1]);
                queue.waitTilEmpty();
                assertTrue("wrong string values", Arrays.equals(strValues,
                        stringMultiCommit.objectIla.toArray()));
                assertTrue("wrong integer values", Arrays.equals(integerValues,
                        integerMultiCommit.objectIla.toArray()));
        */
    }
}
