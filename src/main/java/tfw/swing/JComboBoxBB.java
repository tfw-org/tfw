package tfw.swing;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComboBox;
import tfw.awt.component.EnabledCommit;
import tfw.swing.combobox.SelectionAndListCommit;
import tfw.swing.combobox.SelectionInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class JComboBoxBB extends JComboBox<Object> implements BranchBox {
    private static final long serialVersionUID = 1L;
    private final Branch branch;

    public JComboBoxBB(
            String name,
            ObjectIlaECD listECD,
            ObjectECD selectedItemECD,
            IntegerECD selectedIndexECD,
            BooleanECD enabledECD) {
        this(new Branch(name), listECD, selectedItemECD, selectedIndexECD, enabledECD);
    }

    public JComboBoxBB(
            Branch branch,
            ObjectIlaECD listECD,
            ObjectECD selectedItemECD,
            IntegerECD selectedIndexECD,
            BooleanECD enabledECD) {
        this(branch, listECD, selectedItemECD, selectedIndexECD, enabledECD, new Initiator[0]);
    }

    public JComboBoxBB(
            Branch branch,
            ObjectIlaECD listECD,
            ObjectECD selectedItemECD,
            IntegerECD selectedIndexECD,
            BooleanECD enabledECD,
            Initiator[] initiators) {

        this.branch = branch;

        SelectionInitiator selectionInitiator =
                new SelectionInitiator("JComboBoxBB", selectedItemECD, selectedIndexECD, this);

        addActionListener(selectionInitiator);
        branch.add(selectionInitiator);

        List<Initiator> list = new ArrayList<>(Arrays.asList(initiators));
        list.add(selectionInitiator);
        initiators = list.toArray(new Initiator[list.size()]);
        SelectionAndListCommit selectionAndListCommit = new SelectionAndListCommit(
                "JComboBoxBB",
                listECD,
                selectedItemECD,
                selectedIndexECD,
                initiators,
                new ActionListener[] {selectionInitiator},
                this);

        if (enabledECD != null) {
            branch.add(new EnabledCommit("JComboBoxBB", enabledECD, this, null));
        }
        branch.add(selectionAndListCommit);
    }

    public final Branch getBranch() {
        return (branch);
    }
}
