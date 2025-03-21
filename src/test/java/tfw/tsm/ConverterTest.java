package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

final class ConverterTest {
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
        @Override
        protected void convert() {
            state = get();
            convertA = (String) get(porta);
            convertB = (String) get(portb);
        }

        @Override
        protected void debugConvert() {
            state = get();
            debugConvertA = (String) get(porta);
            debugConvertB = (String) get(portb);
        }
    };

    @Test
    void converterTest() {
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
        checkAndClearValues(null, null, aValue, null, aValue, null, null);
        initiator.set(portb, bValue);
        queue.waitTilEmpty();
        checkAndClearValues(null, null, aValue, bValue, aValue, bValue, null);
        initiator.set(portc, cValue);
        queue.waitTilEmpty();
        checkAndClearValues(null, null, null, null, aValue, bValue, null);
        aValue = "a2";
        initiator.set(porta, aValue);
        queue.waitTilEmpty();
        checkAndClearValues(aValue, bValue, null, null, aValue, bValue, cValue);
    }

    private void checkAndClearValues(
            String aValue,
            String bValue,
            String aDebugValue,
            String bDebugValue,
            String aState,
            String bState,
            String cState) {
        assertThat(aValue).isEqualTo(convertA);
        assertThat(bValue).isEqualTo(convertB);
        assertThat(aDebugValue).isEqualTo(debugConvertA);
        assertThat(bDebugValue).isEqualTo(debugConvertB);
        assertThat(aState).isEqualTo(state.get(porta));
        assertThat(bState).isEqualTo(state.get(portb));
        assertThat(cState).isEqualTo(state.get(portc));
        convertA = null;
        convertB = null;
        debugConvertA = null;
        debugConvertB = null;
    }

    @Test
    void constructorTest() {
        assertThatThrownBy(() -> new TestConverter(null, triggeringSinks, nonTriggeringSinks, sources))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> new TestConverter("test", null, nonTriggeringSinks, sources))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("triggeringSinks == null not allowed!");
        assertThatThrownBy(() -> new TestConverter("test", new ObjectECD[] {null}, nonTriggeringSinks, sources))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("triggeringSinks[0] == null not allowed!");
        assertThatThrownBy(() -> new TestConverter("test", triggeringSinks, new ObjectECD[] {null}, sources))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("nonTriggeringSinks[0] == null not allowed!");
        assertThatThrownBy(() -> new TestConverter("test", triggeringSinks, nonTriggeringSinks, new ObjectECD[] {null}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sources[0] == null not allowed!");
        assertThatThrownBy(() -> new TestConverter("test", triggeringSinks, triggeringSinks, sources))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Multiple sinks detected for event channel 'a'");
    }

    private static class TestConverter extends Converter {
        public TestConverter(
                String name, ObjectECD[] triggeringSinks, ObjectECD[] nonTriggeringSinks, ObjectECD[] sources) {
            super(name, triggeringSinks, nonTriggeringSinks, sources);
        }

        @Override
        protected void convert() {}
    }
}
