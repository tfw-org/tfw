package tfw.tsm;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestPackage {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(BasicTransactionQueueTest.class);
        suite.addTestSuite(BranchFactoryTest.class);
        suite.addTestSuite(CascadeTest.class);
        suite.addTestSuite(CommitTest.class);
        suite.addTestSuite(ConverterTest.class);
        suite.addTestSuite(DefaultStateQueueFactoryTest.class);
        suite.addTestSuite(DifferentObjectRuleTest.class);
        suite.addTestSuite(DotEqualsRuleTest.class);
        suite.addTestSuite(EventChannelStateTest.class);
        suite.addTestSuite(GetPreviousStateTest.class);
        suite.addTestSuite(ImportExportTreeStateTest.class);
        suite.addTestSuite(InfiniteLoopTest.class);
        suite.addTestSuite(InitiatorTest.class);
        suite.addTestSuite(MultiplexedBranchFactoryTest.class);
        suite.addTestSuite(MultiplexerConstructionTest.class);
        suite.addTestSuite(MultiplexerSynchronizerTest.class);
        suite.addTestSuite(MultiplexerTest.class);
        suite.addTestSuite(OneDeepStateQueueFactoryTest.class);
        suite.addTestSuite(PortTerminationTest.class);
        suite.addTestSuite(RollbackTest.class);
        suite.addTestSuite(RootTest.class);
        suite.addTestSuite(SetStateTest.class);
        suite.addTestSuite(StateChangeCycleDelayTest.class);
        suite.addTestSuite(StateLessECDTest.class);
        suite.addTestSuite(SynchronizerTest.class);
        //		suite.addTestSuite(TestTransactionExceptionHandler.class);
        suite.addTestSuite(TranslatorTest.class);
        suite.addTestSuite(TreeStateBufferTest.class);
        suite.addTestSuite(TreeStateTest.class);
        suite.addTestSuite(TriggeredConverterTest.class);
        suite.addTestSuite(ValidatorTest.class);
        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
