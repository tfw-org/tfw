package tfw.swing;

import javax.swing.JList;
import tfw.swing.list.SelectionAndListCommit;
import tfw.swing.list.SelectionInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ila.IntIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class JListBB extends JList<Object> implements BranchBox {
    private static final long serialVersionUID = 1L;
    private final Branch branch;

    public JListBB(
            String name,
            ObjectIlaECD listECD,
            ObjectIlaECD selectedItemsECD,
            IntIlaECD selectedIndexesECD,
            BooleanECD enabledECD) {
        this(new Branch(name), listECD, selectedItemsECD, selectedIndexesECD, enabledECD);
    }

    public JListBB(
            Branch branch,
            ObjectIlaECD listECD,
            ObjectIlaECD selectedItemsECD,
            IntIlaECD selectedIndexesECD,
            BooleanECD enabledECD) {
        this.branch = branch;

        SelectionInitiator selectionInitiator =
                new SelectionInitiator("JListBB", selectedItemsECD, selectedIndexesECD, this);

        addListSelectionListener(selectionInitiator);
        branch.add(selectionInitiator);

        SelectionAndListCommit selectionAndListCommit = new SelectionAndListCommit(
                "JListBB", listECD, selectedItemsECD, selectedIndexesECD, new Initiator[] {selectionInitiator}, this);

        branch.add(selectionAndListCommit);
    }

    @Override
    public final Branch getBranch() {
        return branch;
    }
}
