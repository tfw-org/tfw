package tfw.swing;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;

public class JPopupMenuBB extends JPopupMenu implements BranchBox {
    private static final long serialVersionUID = 1L;
    private final Branch branch;

    public JPopupMenuBB(String name) {
        this(new Branch("JPopupMenuBB[" + name + "]"));
    }

    public JPopupMenuBB(Branch branch) {
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
