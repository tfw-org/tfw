package tfw.swing;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import tfw.check.Argument;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;

public class JButtonBB extends JButton implements BranchBox {
    private static final long serialVersionUID = 1L;
    private final transient Branch branch;

    public JButtonBB(Branch branch) {
        Argument.assertNotNull(branch, "branch");

        this.branch = branch;
    }

    public void addActionListenerToBoth(final ActionListener actionListener) {
        SwingUtil.addObjectToBranch(actionListener, branch);
        addActionListener(actionListener);
    }

    public void removeActionListenerFromBoth(final ActionListener actionListener) {
        SwingUtil.removeObjectFromBranch(actionListener, branch);
        removeActionListener(actionListener);
    }

    @Override
    public final Branch getBranch() {
        return branch;
    }

    public static JButtonBBBuilder builder() {
        return new JButtonBBBuilder();
    }
}
