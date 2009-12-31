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
package tfw.tsm.test;

import junit.framework.TestCase;

import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Converter;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TransactionExceptionHandler;

import tfw.tsm.ecd.StringECD;


/**
 *
 */
public class StateChangeCycleDelayTest extends TestCase
{
    private static int globalOrder = 0;
    StringECD ecI = new StringECD("ecI");
    StringECD ecA = new StringECD("ecA");
    StringECD ecB = new StringECD("ecB");
    StringECD ecC = new StringECD("ecC");
    StringECD ecD = new StringECD("ecD");

    public void testTwoStage()
    {
        RootFactory rf = new RootFactory();
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Initiator initiator = new Initiator("Test", ecI);
        MyConverter converterA = new MyConverter("ConverterA",
                new StringECD[]{ ecI }, ecA);
        MyConverter converterB = new MyConverter("ConverterB",
                new StringECD[]{ ecI, ecA }, ecB);
        rf.addEventChannel(ecI);
        rf.addEventChannel(ecA, "A initial");
        rf.addEventChannel(ecB, "B initial");

        Root root = rf.create("Test", queue);
        root.add(converterA);
        root.add(converterB);
        root.add(initiator);
        queue.waitTilEmpty();
        converterA.reset();
        converterB.reset();

        initiator.set(ecI, "Start");
        queue.waitTilEmpty();

        assertEquals("Converter A was called the wrong number of times", 1,
            converterA.getCount());
        assertEquals("Converter A was called in the wrong order", 1,
            converterA.getOrder());
        assertEquals("Converter B was called the wrong number of times", 1,
            converterB.getCount());
		assertEquals("Converter B was called in the wrong order", 2,
			converterB.getOrder());
    }

    public void testIndirectDependency()
    {
        RootFactory rf = new RootFactory();
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Initiator initiator = new Initiator("Test", ecI);
        MyConverter converterA = new MyConverter("ConverterA",
                new StringECD[]{ ecI }, ecA);
        MyConverter converterB = new MyConverter("ConverterB",
                new StringECD[]{ ecA }, ecB);
        MyConverter converterC = new MyConverter("ConverterC",
                new StringECD[]{ ecI, ecB }, ecC);
        rf.addEventChannel(ecI);
        rf.addEventChannel(ecA, "A initial");
        rf.addEventChannel(ecB, "B initial");
        rf.addEventChannel(ecC, "C initial");

        //rf.setLogging(true);
        Root root = rf.create("Test", queue);
        root.add(converterA);
        root.add(converterB);
        root.add(converterC);
        root.add(initiator);
        queue.waitTilEmpty();
        converterA.reset();
        converterB.reset();
        converterC.reset();

        initiator.set(ecI, "Start");
        queue.waitTilEmpty();

        assertEquals("Converter A was called the wrong number of times", 1,
            converterA.getCount());
		assertEquals("Converter A was called in the wrong order", 1,
			converterA.getOrder());
        assertEquals("Converter B was called the wrong number of times", 1,
            converterB.getCount());
		assertEquals("Converter B was called in the wrong order", 2,
			converterB.getOrder());
        assertEquals("Converter C was called the wrong number of times", 1,
            converterC.getCount());
		assertEquals("Converter C was called in the wrong order", 3,
			converterC.getOrder());
    }

    public void testDirectIndirectDependency()
    {
        RootFactory rf = new RootFactory();
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Initiator initiator = new Initiator("Test", ecI);
        MyConverter converterA = new MyConverter("ConverterA",
                new StringECD[]{ ecI }, ecA);
        MyConverter converterB = new MyConverter("ConverterB",
                new StringECD[]{ ecI, ecA }, ecB);
        MyConverter converterC = new MyConverter("ConverterC",
                new StringECD[]{ ecB }, ecC);
        MyConverter converterD = new MyConverter("ConverterD",
                new StringECD[]{ ecI, ecC }, ecD);
        rf.addEventChannel(ecI);
        rf.addEventChannel(ecA, "A initial");
        rf.addEventChannel(ecB, "B initial");
        rf.addEventChannel(ecC, "C initial");
        rf.addEventChannel(ecD, "D initial");

        //rf.setLogging(true);
        Root root = rf.create("Test", queue);
        root.add(converterA);
        root.add(converterB);
        root.add(converterC);
        root.add(converterD);
        root.add(initiator);
        queue.waitTilEmpty();
        converterA.reset();
        converterB.reset();
        converterC.reset();
        converterD.reset();

        initiator.set(ecI, "Start");
        queue.waitTilEmpty();

        assertEquals("Converter A was called the wrong number of times", 1,
            converterA.getCount());
		assertEquals("Converter A was called in the wrong order", 1,
			converterA.getOrder());
        assertEquals("Converter B was called the wrong number of times", 1,
            converterB.getCount());
		assertEquals("Converter B was called in the wrong order", 2,
			converterB.getOrder());
        assertEquals("Converter C was called the wrong number of times", 1,
            converterC.getCount());
		assertEquals("Converter C was called in the wrong order", 3,
			converterC.getOrder());
        assertEquals("Converter D was called the wrong number of times", 1,
            converterD.getCount());
		assertEquals("Converter D was called in the wrong order", 4,
			converterD.getOrder());
    }

    public void testCircularDependency()
    {
        RootFactory rf = new RootFactory();
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Initiator initiator = new Initiator("Test", ecI);
        MyConverter converterA = new MyConverter("ConverterA",
                new StringECD[]{ ecI }, ecA);
        MyConverter converterB = new MyConverter("ConverterB",
                new StringECD[]{ ecA }, ecB);
        MyConverter converterC = new MyConverter("ConverterC",
                new StringECD[]{ ecI, ecB }, ecA, false);
        rf.addEventChannel(ecI);
        rf.addEventChannel(ecA, "A initial");
        rf.addEventChannel(ecB, "B initial");
        rf.addEventChannel(ecC, "C initial");

        MyHandler handler = new MyHandler();
        rf.setTransactionExceptionHandler(handler);

        Root root = rf.create("Test", queue);
        root.add(converterA);
        root.add(converterB);
        root.add(converterC);
        root.add(initiator);
        queue.waitTilEmpty();
        converterA.reset();
        converterB.reset();
        converterC.reset();

        initiator.set(ecI, "Start");
        queue.waitTilEmpty();

        if (handler.exception != null)
        {
            fail("Unexpected exception: " + handler.exception.getMessage());
        }
    }
    
	public void testThreeWayDependency()
	{
		RootFactory rf = new RootFactory();
		BasicTransactionQueue queue = new BasicTransactionQueue();
		Initiator initiator = new Initiator("Test", ecI);
		MyConverter converterA = new MyConverter("ConverterA",
				new StringECD[]{ ecI }, ecA);
		MyConverter converterB = new MyConverter("ConverterB",
				new StringECD[]{ ecI, ecA }, ecB);
		MyConverter converterC = new MyConverter("ConverterC",
				new StringECD[]{ ecI, ecA, ecB }, ecC);
		rf.addEventChannel(ecI);
		rf.addEventChannel(ecA, "A initial");
		rf.addEventChannel(ecB, "B initial");
		rf.addEventChannel(ecC, "C initial");

		//rf.setLogging(true);
		Root root = rf.create("Test", queue);
		root.add(converterA);
		root.add(converterB);
		root.add(converterC);
		root.add(initiator);
		queue.waitTilEmpty();
		converterA.reset();
		converterB.reset();
		converterC.reset();

		initiator.set(ecI, "Start");
		queue.waitTilEmpty();

		assertEquals("Converter A was called the wrong number of times", 1,
			converterA.getCount());
		assertEquals("Converter A was called in the wrong order", 1,
			converterA.getOrder());
		assertEquals("Converter B was called the wrong number of times", 1,
			converterB.getCount());
		assertEquals("Converter B was called in the wrong order", 2,
			converterB.getOrder());
		assertEquals("Converter C was called the wrong number of times", 1,
			converterC.getCount());
		assertEquals("Converter C was called in the wrong order", 3,
			converterC.getOrder());
	}

    private class MyHandler implements TransactionExceptionHandler
    {
        private Exception exception = null;

        public void handle(Exception exception)
        {
            this.exception = exception;
        }
    }

    public class MyConverter extends Converter
    {
        private int count = 0;
        private int order = -1;
        private final StringECD output;
        private final boolean fire;

        public MyConverter(String name, StringECD[] inputs, StringECD output)
        {
            this(name, inputs, output, true);
        }

        public MyConverter(String name, StringECD[] inputs, StringECD output,
            boolean fire)
        {
            super(name, inputs, new StringECD[]{ output });
            this.output = output;
            this.fire = fire;
        }

        public int getCount()
        {
            return count;
        }

        public int getOrder()
        {
            return order;
        }

        public void reset()
        {
            this.count = 0;
            this.order = -1;
            globalOrder = 0;
        }

        public void convert()
        {
            count++;
            order = ++globalOrder;

            //System.out.println(this.getName() + " has been called " + count +
            //    " times");
            //System.out.println(this.get());
            //System.out.println();
            if (fire)
            {
                set(output, "" + count);
            }
        }
    }
}
