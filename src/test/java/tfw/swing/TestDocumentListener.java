package tfw.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TestDocumentListener implements DocumentListener {
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
