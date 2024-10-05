package tfw.swing.event;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.JComponent;
import tfw.awt.ecd.ColorECD;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;

public class SetForegroundFactory {
    private SetForegroundFactory() {}

    public static Commit create(String name, ColorECD colorECD, Component component, Initiator[] initiators) {
        if (component instanceof JComponent) {
            return new JComponentSetForegroundCommit(name, colorECD, (JComponent) component, initiators);
        }

        return null;
    }

    private static class JComponentSetForegroundCommit extends Commit {
        private final ColorECD colorECD;
        private final JComponent jComponent;

        public JComponentSetForegroundCommit(
                String name, ColorECD colorECD, JComponent jComponent, Initiator[] initiators) {
            super("SetForegroundCommit[" + name + "]", new ObjectECD[] {colorECD}, null, initiators);

            this.colorECD = colorECD;
            this.jComponent = jComponent;
        }

        @Override
        protected void commit() {
            final Color color = (Color) get(colorECD);

            EventQueue.invokeLater(() -> jComponent.setForeground(color));
        }
    }
}
