package tfw.swing.event;

import java.awt.Component;
import java.awt.EventQueue;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;

public class SetEnabledFactory {
    private SetEnabledFactory() {}

    public static Commit create(String name, BooleanECD enabledECD, Component component, Initiator[] initiators) {
        return new ComponentSetEnabledCommit(name, enabledECD, component, initiators);
    }

    private static class ComponentSetEnabledCommit extends Commit {
        private final BooleanECD enabledECD;
        private final Component component;

        public ComponentSetEnabledCommit(
                String name, BooleanECD enabledECD, Component component, Initiator[] initiators) {
            super("SetEnabledCommit[" + name + "]", new ObjectECD[] {enabledECD}, null, initiators);

            this.enabledECD = enabledECD;
            this.component = component;
        }

        @Override
        protected void commit() {
            final boolean enabled = (Boolean) get(enabledECD);

            EventQueue.invokeLater(() -> component.setEnabled(enabled));
        }
    }
}
