package tfw.swing.label;

import java.awt.EventQueue;
import javax.swing.JLabel;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

public class SetTextCommit extends Commit {
    private final StringECD textECD;
    private final JLabel label;

    public SetTextCommit(String name, StringECD textECD, JLabel label, Initiator[] initiators) {
        super("SetTextCommit[" + name + "]", new ObjectECD[] {textECD}, null, initiators);

        this.textECD = textECD;
        this.label = label;
    }

    protected void commit() {
        final String text = (String) get(textECD);

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                label.setText(text);
            }
        });
    }
}
