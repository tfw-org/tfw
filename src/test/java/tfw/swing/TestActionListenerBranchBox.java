package tfw.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;

public class TestActionListenerBranchBox implements ActionListener, BranchBox {
    private final Branch branch = new Branch("TestActionListenerBranch");

    @Override
    public Branch getBranch() {
        return branch;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
