package tfw.swing;

import javax.swing.JLabel;
import tfw.swing.label.SetTextCommit;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.ecd.StringECD;

public class JLabelBB extends JLabel implements BranchBox {
    private static final long serialVersionUID = 1L;
    private final Branch branch;

    public JLabelBB(String name, StringECD textECD) {
        this(new Branch("JLabelBB[" + name + "]"), textECD);
    }

    public JLabelBB(Branch branch, StringECD textECD) {
        this.branch = branch;

        branch.add(new SetTextCommit("JLabelBB", textECD, this, null));
    }

    public final Branch getBranch() {
        return (branch);
    }
}
