package tfw.swing.event;

import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.AbstractButton;
import tfw.check.Argument;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

public class SetTextFactory {
    private SetTextFactory() {}

    public static Commit create(String name, StringECD textECD, Component component, Initiator[] initiators) {
        Argument.assertNotNull(name, "name");
        Argument.assertNotNull(textECD, "textECD");
        Argument.assertNotNull(component, "component");

        return new AbstractButtonSetTextCommit(name, textECD, (AbstractButton) component, initiators);
    }

    private static class AbstractButtonSetTextCommit extends Commit {
        private final StringECD textECD;
        private final AbstractButton abstractButton;

        public AbstractButtonSetTextCommit(
                String name, StringECD textECD, AbstractButton abstractButton, Initiator[] initiators) {
            super("SetTextCommit[" + name + "]", new ObjectECD[] {textECD}, null, initiators);

            this.textECD = textECD;
            this.abstractButton = abstractButton;
        }

        @Override
        protected void commit() {
            final String text = (String) get(textECD);

            EventQueue.invokeLater(() -> abstractButton.setText(text));
        }
    }
}
