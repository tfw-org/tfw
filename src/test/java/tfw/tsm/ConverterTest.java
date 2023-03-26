package tfw.tsm;

import java.util.Map;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Converter;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;


public class ConverterTest extends TestCase
{
    private Map<ObjectECD, Object> state = null;
    private String convertA = null;
    private String convertB = null;
    private String debugConvertA = null;
    private String debugConvertB = null;
    private ObjectECD porta = new StringECD("a");
    private ObjectECD portb = new StringECD("b");
    private ObjectECD portc = new StringECD("c");
    private ObjectECD[] nonTriggeringSinks = new ObjectECD[]
        {
            portc
        };
    private ObjectECD[] triggeringSinks = new ObjectECD[]
        {
            porta, portb
        };
    private ObjectECD[] sources = new ObjectECD[]
        {
            porta, portb
        };
    private Converter converter = new Converter("Commit", triggeringSinks,
            nonTriggeringSinks, sources)
        {
            protected void convert()
            {
                state = get();
                convertA = (String) get(porta);
                convertB = (String) get(portb);
            }

            protected void debugConvert()
            {
                state = get();
                debugConvertA = (String) get(porta);
                debugConvertB = (String) get(portb);
            }
        };

    public void testConverter()
    {
        RootFactory rf = new RootFactory();
        rf.addEventChannel(porta);
        rf.addEventChannel(portb);
        rf.addEventChannel(portc);

        BasicTransactionQueue queue = new BasicTransactionQueue();

        Root root = rf.create("Test", queue);
        root.add(converter);

        Initiator initiator = new Initiator("test",
                new ObjectECD[]{ porta, portb, portc });
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
        aValue = "a2";
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
        debugConvertA = null;
        debugConvertB = null;
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
            new MyConverter("test", new ObjectECD[]{ null },
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
                new ObjectECD[]{ null }, sources);

            fail("Constructor accepted null element in non-triggering sinks");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new MyConverter("test", triggeringSinks, nonTriggeringSinks,
                new ObjectECD[]{ null });

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
            ObjectECD[] triggeringSinks,
            ObjectECD[] nonTriggeringSinks,
            ObjectECD[] sources)
        {
            super(name, triggeringSinks, nonTriggeringSinks, sources);
        }

        protected void convert()
        {
        }
    }
}
