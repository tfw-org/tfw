package tfw.swing;

import java.awt.Container;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.event.InternalFrameListener;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.TreeComponent;

public class JInternalFrameBB extends JInternalFrame implements BranchBox {
    private final Branch branch;

    public JInternalFrameBB(String name) {
        this(new Branch("JPanelBB[" + name + "]"));
    }

    public JInternalFrameBB(Branch branch) {
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

    public final void addInternalFrameListenerToBoth(InternalFrameListener listener) {
        addInternalFrameListener(listener);

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
