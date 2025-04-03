package tfw.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.StatelessTriggerECD;

public class TestDocumentListenerTreeComponent extends Initiator implements DocumentListener {
    public TestDocumentListenerTreeComponent(final StatelessTriggerECD statelessTriggerECD) {
        super("TestDocumentListenerTreeComponent", statelessTriggerECD);
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        throw new UnsupportedOperationException("Unimplemented method 'changedUpdate'");
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        throw new UnsupportedOperationException("Unimplemented method 'insertUpdate'");
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        throw new UnsupportedOperationException("Unimplemented method 'removeUpdate'");
    }
}
