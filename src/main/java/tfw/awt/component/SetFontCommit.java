package tfw.awt.component;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import tfw.awt.ecd.FontECD;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;

public class SetFontCommit extends Commit {
    private final FontECD fontECD;
    private final Component component;

    public SetFontCommit(String name, FontECD fontECD, Component component, Initiator[] initiators) {
        super("SetFontCommit[" + name + "]", new ObjectECD[] {fontECD}, null, initiators);

        this.fontECD = fontECD;
        this.component = component;
    }

    protected void commit() {
        final Font font = (Font) get(fontECD);

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                component.setFont(font);
            }
        });
    }
}
