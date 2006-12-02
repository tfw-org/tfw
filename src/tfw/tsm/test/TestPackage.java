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
import junit.framework.TestSuite;

public class TestPackage
{
	public static Test suite()
	{
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

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
	}
}
