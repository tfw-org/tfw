package tfw.swing;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.TreeComponent;

public class JDialogBB extends JDialog implements BranchBox {
    private static final long serialVersionUID = 1L;
    private final Branch branch;

    public JDialogBB(String name, Dialog owner, String title, boolean modal) {
        this(new Branch("JPanelBB[" + name + "]"), owner, title, modal);
    }

    public JDialogBB(Branch branch, Dialog owner, String title, boolean modal) {
        this.branch = branch;
    }

    public JDialogBB(String name, Frame owner, String title, boolean modal) {
        this(new Branch("JPanelBB[" + name + "]"), owner, title, modal);
    }

    public JDialogBB(Branch branch, Frame owner, String title, boolean modal) {
        this.branch = branch;
    }

    public final void setContentPaneForBoth(Container contentPane) {
        if (getContentPane() instanceof BranchBox) {
            branch.remove((BranchBox) getContentPane());
        }
        setContentPane(contentPane);
        branch.add((BranchBox) contentPane);
    }

    public final void setJMenuBarForBoth(JMenuBar menuBar) {
        setJMenuBar(menuBar);
        branch.add((BranchBox) menuBar);
    }

    public final void addWindowListenerToBoth(WindowListener listener) {
        addWindowListener(listener);

        if (listener instanceof BranchBox) {
            branch.add((BranchBox) listener);
        } else if (listener instanceof TreeComponent) {
            branch.add((TreeComponent) listener);
        } else {
            throw new IllegalArgumentException("listener != BranchBox || TreeComponent not allowed!");
        }
    }

    public final Branch getBranch() {
        return (branch);
    }
}
