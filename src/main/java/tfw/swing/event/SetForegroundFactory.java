package tfw.swing.event;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JComponent;
import tfw.awt.ecd.ColorECD;
import tfw.check.Argument;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;

public class SetForegroundFactory {
    private SetForegroundFactory() {}

    public static Commit create(String name, ColorECD colorECD, JComponent jComponent, Initiator[] initiators) {
        Argument.assertNotNull(name, "name");
        Argument.assertNotNull(colorECD, "colorECD");
        Argument.assertNotNull(jComponent, "jComponent");

        return new JComponentSetForegroundCommit(name, colorECD, jComponent, initiators);
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
