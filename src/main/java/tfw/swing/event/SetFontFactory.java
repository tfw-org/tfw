package tfw.swing.event;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JComponent;
import tfw.awt.ecd.FontECD;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;

public class SetFontFactory {
    private SetFontFactory() {}

    public static Commit create(String name, FontECD fontECD, Component component, Initiator[] initiators) {
        if (component instanceof JComponent) {
            return new JComponentSetFontCommit(name, fontECD, (JComponent) component, initiators);
        }

        return null;
    }

    private static class JComponentSetFontCommit extends Commit {
        private final FontECD fontECD;
        private final JComponent jComponent;

        public JComponentSetFontCommit(String name, FontECD fontECD, JComponent jComponent, Initiator[] initiators) {
            super("SetFontCommit[" + name + "]", new ObjectECD[] {fontECD}, null, initiators);

            this.fontECD = fontECD;
            this.jComponent = jComponent;
        }

        @Override
        protected void commit() {
            final Font font = (Font) get(fontECD);

            EventQueue.invokeLater(() -> jComponent.setFont(font));
        }
    }
}
