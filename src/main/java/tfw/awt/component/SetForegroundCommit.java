package tfw.awt.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import tfw.awt.ecd.ColorECD;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;

public class SetForegroundCommit extends Commit {
    private final ColorECD colorECD;
    private final Component component;

    public SetForegroundCommit(String name, ColorECD colorECD, Component component, Initiator[] initiators) {
        super("SetForegroundCommit[" + name + "]", new ObjectECD[] {colorECD}, null, initiators);

        this.colorECD = colorECD;
        this.component = component;
    }

    @Override
    protected void commit() {
        final Color color = (Color) get(colorECD);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                component.setForeground(color);
            }
        });
    }
}
