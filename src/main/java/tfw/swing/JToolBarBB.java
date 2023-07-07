package tfw.swing;

import java.awt.Component;
import javax.swing.JToolBar;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;

public class JToolBarBB extends JToolBar implements BranchBox {
    private static final long serialVersionUID = 1L;
    private final Branch branch;

    public JToolBarBB(String name) {
        this(new Branch(name));
    }

    public JToolBarBB(Branch branch) {
        this.branch = branch;
    }

    public final Component addToBoth(Component comp) {
        branch.add((BranchBox) comp);
        return (add(comp));
    }

    public final Component addToBoth(Component comp, int index) {
        branch.add((BranchBox) comp);
        return (add(comp, index));
    }

    public final void addToBoth(Component comp, Object constraints) {
        branch.add((BranchBox) comp);
        add(comp, constraints);
    }

    public final void addToBoth(Component comp, Object constraints, int index) {
        branch.add((BranchBox) comp);
        add(comp, constraints, index);
    }

    public final Component addToBoth(String name, Component comp) {
        branch.add((BranchBox) comp);
        return (add(name, comp));
    }

    public final void removeFromBoth(Component comp) {
        branch.remove((BranchBox) comp);
        remove(comp);
    }

    public final void removeFromBoth(int index) {
        branch.remove((BranchBox) getComponent(index));
        remove(index);
    }

    public final void removeAllFromBoth() {
        //		branch.removeAll();
        removeAll();
    }

    public Branch getBranch() {
        return (branch);
    }
}
