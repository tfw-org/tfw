package tfw.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;

public class TestDocumentListenerBranchBox implements DocumentListener, BranchBox {
    private final Branch branch = new Branch("TestActionListenerBranch");

    @Override
    public Branch getBranch() {
        return branch;
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        throw new UnsupportedOperationException("Unimplemented method 'changedUpdate'");
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        throw new UnsupportedOperationException("Unimplemented method 'insertUpdate'");
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        throw new UnsupportedOperationException("Unimplemented method 'removeUpdate'");
    }
}
