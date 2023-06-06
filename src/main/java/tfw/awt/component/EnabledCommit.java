package tfw.awt.component;

import java.awt.Component;
import java.awt.EventQueue;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;

public class EnabledCommit extends Commit {
    private final BooleanECD enabledECD;
    private final Component component;

    public EnabledCommit(String name, BooleanECD enabledECD, Component component, Initiator[] initiators) {
        super("EnabledCommit[" + name + "]", new ObjectECD[] {enabledECD}, null, initiators);

        this.enabledECD = enabledECD;
        this.component = component;
    }

    protected final void commit() {
        final boolean enabled = ((Boolean) get(enabledECD)).booleanValue();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                component.setEnabled(enabled);
            }
        });
    }
}
