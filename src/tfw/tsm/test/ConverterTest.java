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
import tfw.tsm.Converter;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.StateMap;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class ConverterTest extends TestCase
{
    private StateMap state = null;
    private final String answer = "Hello World";
    private String convertA = null;
    private String convertB = null;
    private String convertC = null;
    private String debugConvertA = null;
    private String debugConvertB = null;
    private String debugConvertC = null;
    private EventChannelDescription porta = new StringECD("a");
    private EventChannelDescription portb = new StringECD("b");
    private EventChannelDescription portc = new StringECD("c");
    private EventChannelDescription[] nonTriggeringSinks = new EventChannelDescription[]
        {
            portc
        };
    private EventChannelDescription[] triggeringSinks = new EventChannelDescription[]
        {
            porta, portb
        };
    private EventChannelDescription[] sources = new EventChannelDescription[]
        {
            porta, portb
        };
    private Initiator initiator = new Initiator("Initiator", sources);
    private Converter converter = new Converter("Commit", triggeringSinks,
            nonTriggeringSinks, sources)
        {
            protected void convert()
            {
                state = get();
                convertA = (String) get(porta);
                convertB = (String) get(portb);
                convertC = (String) get(portc);
            }

            protected void debugConvert()
            {
                state = get();
                debugConvertA = (String) get(porta);
                debugConvertB = (String) get(portb);
                debugConvertC = (String) get(portc);
            }
        };

    public void testConverter()
    {
        RootFactory rf = new RootFactory();
        rf.addTerminator(porta);
        rf.addTerminator(portb);
        rf.addTerminator(portc);

        BasicTransactionQueue queue = new BasicTransactionQueue();

        Root root = rf.create("Test", queue);
        root.add(converter);

        Initiator initiator = new Initiator("test",
                new EventChannelDescription[]{ porta, portb, portc });
        root.add(initiator);

        String aValue = "a";
        String bValue = "b";
        String cValue = "c";
        initiator.set(porta, aValue);
        queue.waitTilEmpty();
        checkAndClearValues(null, null, null, aValue, null, null, aValue, null,
            null);
        initiator.set(portb, bValue);
        queue.waitTilEmpty();
        checkAndClearValues(null, null, null, aValue, bValue, null, aValue,
            bValue, null);
        initiator.set(portc, cValue);
        queue.waitTilEmpty();
        checkAndClearValues(null, null, null, null, null, null, aValue, bValue,
            null);
        initiator.set(porta, aValue);
        queue.waitTilEmpty();
        checkAndClearValues(aValue, bValue, cValue, null, null, null, aValue,
            bValue, cValue);
    }

    private void checkAndClearValues(String aValue, String bValue,
        String cValue, String aDebugValue, String bDebugValue,
        String cDebugValue, String aState, String bState, String cState)
    {
        assertEquals("convertA has wrong value", aValue, convertA);
        assertEquals("convertB has wrong value", bValue, convertB);
        assertEquals("debugConvertA has wrong value", aDebugValue, debugConvertA);
        assertEquals("debugConvertB has wrong value", bDebugValue, debugConvertB);
        assertEquals("state map has wrong value for porta", aState,
            state.get(porta));
        assertEquals("state map has wrong value for portb", bState,
            state.get(portb));
        assertEquals("state map has wrong value for portc", cState,
            state.get(portc));
        convertA = null;
        convertB = null;
        convertC = null;
        debugConvertA = null;
        debugConvertB = null;
        debugConvertC = null;
    }

    public void testConstructor()
    {
        try
        {
            new MyConverter(null, triggeringSinks, nonTriggeringSinks, sources);
            fail("Constructor accepted null name");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyConverter("test", null, nonTriggeringSinks, sources);
            fail("Constructor accepted null triggering sinks");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyConverter("test", new EventChannelDescription[]{ null },
                nonTriggeringSinks, sources);

            fail("Constructor accepted null element in triggering sinks");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyConverter("test", triggeringSinks,
                new EventChannelDescription[]{ null }, sources);

            fail("Constructor accepted null element in non-triggering sinks");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyConverter("test", triggeringSinks, nonTriggeringSinks,
                new EventChannelDescription[]{ null });

            fail("Constructor accepted null element in sources");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }


        try
        {
            new MyConverter("test", triggeringSinks, triggeringSinks, sources);

            fail("Constructor accepted duplicate sinks");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        StatelessTriggerECD[] statelessTriggers = new StatelessTriggerECD[]
            {
                new StatelessTriggerECD("test")
            };

        try
        {
            new MyConverter("test", statelessTriggers, nonTriggeringSinks,
                sources);

            fail("Constructor accepted stateless triggers as a trigger");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyConverter("test", triggeringSinks, statelessTriggers,
                sources);

            fail(
                "Constructor accepted stateless triggers as a nonTriggering sink");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyConverter("test", triggeringSinks, nonTriggeringSinks,
                statelessTriggers);

            fail(
                "Constructor accepted stateless triggers as a nonTriggering sink");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
    }

    public static Test suite()
    {
        return new TestSuite(ConverterTest.class);
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }

    private class MyConverter extends Converter
    {
        public MyConverter(String name,
            EventChannelDescription[] triggeringSinks,
            EventChannelDescription[] nonTriggeringSinks,
            EventChannelDescription[] sources)
        {
            super(name, triggeringSinks, nonTriggeringSinks, sources);
        }

        protected void convert()
        {
        }
    }
}
