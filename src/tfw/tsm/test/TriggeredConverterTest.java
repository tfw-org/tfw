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
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.StatelessTriggerECD;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class TriggeredConverterTest extends TestCase
{
    private final String answer = "Hello World";
    private String triggerA = null;
    private String triggerB = null;
    private String debugTriggerA = null;
    private String debugTriggerB = null;
    private StringECD channel1 = new StringECD("1");
    private StringECD channel2 = new StringECD("2");
    private StatelessTriggerECD trigger = new StatelessTriggerECD("trigger");
    private EventChannelDescription[] sinks = new EventChannelDescription[]
        {
            channel1, channel2
        };
    private EventChannelDescription[] sources = new EventChannelDescription[]
        {
            channel1, channel2, trigger
        };
    private Initiator initiator = new Initiator("Initiator", sources);
    private TriggeredConverter triggeredConverter = new TriggeredConverter("TriggeredConverter", trigger,
            sinks, sources)
        {
            protected void convert()
            {
                //System.out.println("triggerAction");
                triggerA = (String) get(channel1);
                triggerB = (String) get(channel2);
            }

            protected void debugConvert()
            {
                //System.out.println("debugTriggerAction");
                debugTriggerA = (String) get(channel1);
                debugTriggerB = (String) get(channel2);
            }
        };

    public void testConverter() throws Exception
    {
        RootFactory rf = new RootFactory();
        rf.addEventChannel(channel1);
        rf.addEventChannel(channel2);
        rf.addEventChannel(trigger);

        BasicTransactionQueue queue = new BasicTransactionQueue();

        Root root = rf.create("Test branch", queue);
        root.add(initiator);
        root.add(triggeredConverter);

        initiator.set(channel1, answer);
        queue.waitTilEmpty();
        assertEquals("send a triggerA", null, triggerA);
        assertEquals("send a triggerB", null, triggerB);
        assertEquals("send a debugTriggerA", null, debugTriggerA);
        assertEquals("send a debugTriggerB", null, debugTriggerB);

        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertEquals("send a triggerA", null, triggerA);
        assertEquals("send a triggerB", null, triggerB);
        assertEquals("send a debugTriggerA", answer, debugTriggerA);
        assertEquals("send a debugTriggerB", null, debugTriggerB);

        debugTriggerA = null;
        initiator.set(channel2, answer);
        queue.waitTilEmpty();
        assertEquals("send a triggerA", null, triggerA);
        assertEquals("send a triggerB", null, triggerB);
        assertEquals("send a debugTriggerA", null, debugTriggerA);
        assertEquals("send a debugTriggerB", null, debugTriggerB);

        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertEquals("send a triggerA", answer, triggerA);
        assertEquals("send a triggerB", answer, triggerB);
        assertEquals("send a debugTriggerA", null, debugTriggerA);
        assertEquals("send a debugTriggerB", null, debugTriggerB);
    }
    

    public static Test suite()
    {
        return new TestSuite(TriggeredConverterTest.class);
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }
}
