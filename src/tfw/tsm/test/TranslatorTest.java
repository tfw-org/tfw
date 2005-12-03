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

import java.lang.reflect.InvocationTargetException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Branch;
import tfw.tsm.BranchFactory;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TransactionExceptionHandler;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StringECD;


public class TranslatorTest extends TestCase
{
    private final String answer = "Hello World";
    private String result = null;
    private ObjectECD portA = new StringECD("a");
    private ObjectECD portB = new StringECD("b");
    private ObjectECD[] eventChannels = new ObjectECD[]
        {
            portA
        };
    private Initiator initiator = new Initiator("Initiator", eventChannels);
    private Commit commit = new Commit("Commit", eventChannels)
        {
            protected void commit()
            {
                result = (String) get(portA);
            }
        };

    public void testTranslation()
        throws InterruptedException, InvocationTargetException
    {
        RootFactory rf = new RootFactory();
        rf.addEventChannel(portB);

        MyExceptionHandler handler = new MyExceptionHandler();
        rf.setTransactionExceptionHandler(handler);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root topBranch = rf.create("Top Branch", queue);

        BranchFactory bf = new BranchFactory();
        bf.addTranslation(portA, portB);

        Branch middleBranch1 = bf.create("Middle Branch commit");
        middleBranch1.add(commit);

        bf.clear();
        bf.addTranslation(portA, portB);

        Branch middleBranch2 = bf.create("Middle Branch initiator");
        middleBranch2.add(initiator);

        topBranch.add(middleBranch1);
        topBranch.add(middleBranch2);

        //Visualize.print(topBranch);
        initiator.set(portA, answer);
        queue.waitTilEmpty();
        assertEquals("initial connections", answer, result);

        String newAnswer = "Good bye world";
        initiator.set(portA, newAnswer);
        queue.waitTilEmpty();
        assertEquals("initial connections", newAnswer, result);

        middleBranch1.remove(commit);
        result = null;
        initiator.set(portA, answer);
        queue.waitTilEmpty();
        assertEquals("disconnect sink", null, result);

        result = null;
        middleBranch1.add(commit);
        queue.waitTilEmpty();
        checkHandler(handler);

        // No need to send...should fire on connect.
        // initiator.set("a", answer);
        assertEquals("fire on sink reconnect", answer, result);

        middleBranch2.remove(initiator);
        result = null;

        // Fire source unconnected.
        initiator.set(portA, answer);
        queue.waitTilEmpty();
        assertNotNull("send on disconnected initiator didn't throw exception",
            handler.exception);
        assertEquals("source removal", null, result);

        result = null;
        handler.exception = null;
        middleBranch2.add(initiator);
        queue.waitTilEmpty();
        assertEquals("source fire on reconnect", answer, result);

        checkHandler(handler);
    }

    public void testIncompatableTranslation(){
        BranchFactory bf = new BranchFactory();
        StringECD stringECD = new StringECD("StringECD");
        IntegerECD integerECD1 = new IntegerECD("integerECD1", 1, 5);
        IntegerECD integerECD2 = new IntegerECD("integerECD2", 0, 6);
        try{
            bf.addTranslation(integerECD1, integerECD2);
            fail("addTranslation() accepted incompatible ecds");
        } catch (IllegalArgumentException expected){
            //System.out.println(expected);
        }
        try{
            bf.addTranslation(integerECD2, integerECD1);
            fail("addTranslation() accepted incompatible ecds");
        } catch (IllegalArgumentException expected){
            //System.out.println(expected);
        }
    }
    private void checkHandler(MyExceptionHandler handler)
    {
        String message = "No exception";

        if (handler.exception != null)
        {
            handler.exception.printStackTrace();
            message = handler.exception.getMessage();
        }

        assertNull("Exception - " + message, handler.exception);
    }

    public static Test suite()
    {
        return new TestSuite(TranslatorTest.class);
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }

    private class MyExceptionHandler implements TransactionExceptionHandler
    {
        Exception exception = null;

        public void handle(Exception exception)
        {
        	//exception.printStackTrace();
            this.exception = exception;
        }
    }
}
