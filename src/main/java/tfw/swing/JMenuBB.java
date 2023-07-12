package tfw.swing;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;

public class JMenuBB extends JMenu implements BranchBox {
    private static final long serialVersionUID = 1L;
    private final Branch branch;

    public JMenuBB(String name) {
        this(new Branch("JMenuBB[" + name + "]"));
    }

    public JMenuBB(Branch branch) {
        this.branch = branch;
    }

    public JMenuItem addToBoth(JMenuItem menuItem) {
        branch.add((BranchBox) menuItem);

        return add(menuItem);
    }

    public final Branch getBranch() {
        return branch;
    }
}
