package tfw.swing.event;

import java.awt.EventQueue;
import javax.swing.AbstractButton;
import javax.swing.text.JTextComponent;
import tfw.check.Argument;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

public class SetTextFactory {
    private SetTextFactory() {}

    public static Commit create(String name, StringECD textECD, AbstractButton abstractButton, Initiator[] initiators) {
        Argument.assertNotNull(name, "name");
        Argument.assertNotNull(textECD, "textECD");
        Argument.assertNotNull(abstractButton, "abstractButton");

        return new AbstractButtonSetTextCommit(name, textECD, abstractButton, initiators);
    }

    public static Commit create(String name, StringECD textECD, JTextComponent jTextComponent, Initiator[] initiators) {
        Argument.assertNotNull(name, "name");
        Argument.assertNotNull(textECD, "textECD");
        Argument.assertNotNull(jTextComponent, "jTextComponent");

        return new JTextComponentSetTextCommit(name, textECD, jTextComponent, initiators);
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

    private static class JTextComponentSetTextCommit extends Commit {
        private final StringECD textECD;
        private final JTextComponent jTextCompoennt;

        public JTextComponentSetTextCommit(
                String name, StringECD textECD, JTextComponent jTextCompoennt, Initiator[] initiators) {
            super("SetTextCommit[" + name + "]", new ObjectECD[] {textECD}, null, initiators);

            this.textECD = textECD;
            this.jTextCompoennt = jTextCompoennt;
        }

        @Override
        protected void commit() {
            final String text = (String) get(textECD);

            EventQueue.invokeLater(() -> jTextCompoennt.setText(text));
        }
    }
}
