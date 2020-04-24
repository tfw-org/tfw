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

import tfw.tsm.BasicTransactionQueue;

import junit.framework.TestCase;


/**
 *
 */
public class BasicTransactionQueueTest extends TestCase
{
    public void testAdd()
    {
        BasicTransactionQueue queue = new BasicTransactionQueue();

        try
        {
            queue.invokeLater(null);
            fail("add() accepted null runnable");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
    }
    private int count = 0;
    private class TestRunnable implements Runnable{

    	public boolean done = false;
        public int cnt = -1;
        public final int sleepTime;
        
        public TestRunnable(int sleepTime){
            this.sleepTime = sleepTime;
        }
    	public void run(){
			try {
				Thread.sleep(this.sleepTime);
				done = true;
                this.cnt = ++count;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
    public void testAddNWait(){
		BasicTransactionQueue queue = new BasicTransactionQueue();
		TestRunnable ts = new TestRunnable(500);
		
		queue.invokeLater(ts);
		queue.waitTilEmpty();

		assertTrue("Not done after waitTilEmpty()", ts.done);
    }
    
    public void testInvokeAndWait() throws Exception {
        BasicTransactionQueue queue = new BasicTransactionQueue();
        try{
            queue.invokeAndWait(null);
            fail("invokeAndWait() accepted null");
        } catch (IllegalArgumentException expected){
            //System.out.println(expected);
        }
        TestRunnable tr1 = new TestRunnable(20);
        TestRunnable tr2 = new TestRunnable(0);
        TestRunnable tr3 = new TestRunnable(5);
        queue.invokeLater(tr1);
        queue.invokeAndWait(tr2);
        assertEquals("tr2.cnt",2, tr2.cnt);
        queue.invokeLater(tr3);
        queue.waitTilEmpty();
        
        assertEquals("tr1.cnt",1, tr1.cnt);
        assertEquals("tr2.cnt",2, tr2.cnt);
        assertEquals("tr3.cnt",3, tr3.cnt);
    }
}
