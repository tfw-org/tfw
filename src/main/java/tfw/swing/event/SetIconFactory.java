package tfw.swing.event;

import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import tfw.swing.ecd.IconECD;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;

public class SetIconFactory {
    private SetIconFactory() {}

    public static Commit create(String name, IconECD iconECD, Component component, Initiator[] initiators) {
        if (component instanceof AbstractButton) {
            return new AbstractButtonSetIconCommit(name, iconECD, (AbstractButton) component, initiators);
        }

        return null;
    }

    private static class AbstractButtonSetIconCommit extends Commit {
        private final IconECD iconECD;
        private final AbstractButton abstractButton;

        public AbstractButtonSetIconCommit(
                String name, IconECD iconECD, AbstractButton abstractButton, Initiator[] initiators) {
            super("SetIconCommit[" + name + "]", new ObjectECD[] {iconECD}, null, initiators);

            this.iconECD = iconECD;
            this.abstractButton = abstractButton;
        }

        @Override
        protected void commit() {
            final Icon icon = (Icon) get(iconECD);

            EventQueue.invokeLater(() -> abstractButton.setIcon(icon));
        }
    }
}
