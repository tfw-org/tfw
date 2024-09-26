package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;
import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

class ConverterTest {
    private Map<ObjectECD, Object> state = null;
    private String convertA = null;
    private String convertB = null;
    private String debugConvertA = null;
    private String debugConvertB = null;
    private ObjectECD porta = new StringECD("a");
    private ObjectECD portb = new StringECD("b");
    private ObjectECD portc = new StringECD("c");
    private ObjectECD[] nonTriggeringSinks = new ObjectECD[] {portc};
    private ObjectECD[] triggeringSinks = new ObjectECD[] {porta, portb};
    private ObjectECD[] sources = new ObjectECD[] {porta, portb};
    private Converter converter = new Converter("Commit", triggeringSinks, nonTriggeringSinks, sources) {
        protected void convert() {
            state = get();
            convertA = (String) get(porta);
            convertB = (String) get(portb);
        }

        protected void debugConvert() {
            state = get();
            debugConvertA = (String) get(porta);
            debugConvertB = (String) get(portb);
        }
    };

    @Test
    void testConverter() {
        RootFactory rf = new RootFactory();
        rf.addEventChannel(porta);
        rf.addEventChannel(portb);
        rf.addEventChannel(portc);

        BasicTransactionQueue queue = new BasicTransactionQueue();

        Root root = rf.create("Test", queue);
        root.add(converter);

        Initiator initiator = new Initiator("test", new ObjectECD[] {porta, portb, portc});
        root.add(initiator);

        String aValue = "a";
        String bValue = "b";
        String cValue = "c";
        initiator.set(porta, aValue);
        queue.waitTilEmpty();
        checkAndClearValues(null, null, null, aValue, null, null, aValue, null, null);
        initiator.set(portb, bValue);
        queue.waitTilEmpty();
        checkAndClearValues(null, null, null, aValue, bValue, null, aValue, bValue, null);
        initiator.set(portc, cValue);
        queue.waitTilEmpty();
        checkAndClearValues(null, null, null, null, null, null, aValue, bValue, null);
        aValue = "a2";
        initiator.set(porta, aValue);
        queue.waitTilEmpty();
        checkAndClearValues(aValue, bValue, cValue, null, null, null, aValue, bValue, cValue);
    }

    private void checkAndClearValues(
            String aValue,
            String bValue,
            String cValue,
            String aDebugValue,
            String bDebugValue,
            String cDebugValue,
            String aState,
            String bState,
            String cState) {
        assertEquals(aValue, convertA, "convertA has wrong value");
        assertEquals(bValue, convertB, "convertB has wrong value");
        assertEquals(aDebugValue, debugConvertA, "debugConvertA has wrong value");
        assertEquals(bDebugValue, debugConvertB, "debugConvertB has wrong value");
        assertEquals(aState, state.get(porta), "state map has wrong value for porta");
        assertEquals(bState, state.get(portb), "state map has wrong value for portb");
        assertEquals(cState, state.get(portc), "state map has wrong value for portc");
        convertA = null;
        convertB = null;
        debugConvertA = null;
        debugConvertB = null;
    }

    @Test
    public void testConstructor() {
        try {
            new TestConverter(null, triggeringSinks, nonTriggeringSinks, sources);
            fail("Constructor accepted null name");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new TestConverter("test", null, nonTriggeringSinks, sources);
            fail("Constructor accepted null triggering sinks");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new TestConverter("test", new ObjectECD[] {null}, nonTriggeringSinks, sources);

            fail("Constructor accepted null element in triggering sinks");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new TestConverter("test", triggeringSinks, new ObjectECD[] {null}, sources);

            fail("Constructor accepted null element in non-triggering sinks");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new TestConverter("test", triggeringSinks, nonTriggeringSinks, new ObjectECD[] {null});

            fail("Constructor accepted null element in sources");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new TestConverter("test", triggeringSinks, triggeringSinks, sources);

            fail("Constructor accepted duplicate sinks");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    private static class TestConverter extends Converter {
        public TestConverter(
                String name, ObjectECD[] triggeringSinks, ObjectECD[] nonTriggeringSinks, ObjectECD[] sources) {
            super(name, triggeringSinks, nonTriggeringSinks, sources);
        }

        protected void convert() {}
    }
}
