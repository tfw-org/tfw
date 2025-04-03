package tfw.swing.event;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import tfw.tsm.Initiator;
import tfw.tsm.StateQueueFactory;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StringECD;

public class DocumentListenerFactory {
    private DocumentListenerFactory() {}

    public static DocumentListener create(
            final String name, final StringECD textECD, final StateQueueFactory stateQueueFactory) {
        return new DocumentInitiator(name, textECD, stateQueueFactory);
    }

    private static class DocumentInitiator extends Initiator implements DocumentListener {
        private final StringECD textECD;

        public DocumentInitiator(
                final String name, final StringECD textECD, final StateQueueFactory stateQueueFactory) {
            super(name, new EventChannelDescription[] {textECD}, stateQueueFactory);

            this.textECD = textECD;
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            sendText(documentEvent);
        }

        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            sendText(documentEvent);
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            sendText(documentEvent);
        }

        private void sendText(DocumentEvent documentEvent) {
            try {
                final Document document = documentEvent.getDocument();

                set(textECD, document.getText(0, document.getLength()));
            } catch (BadLocationException ble) {
                // Do nothing.
            }
        }
    }
}
