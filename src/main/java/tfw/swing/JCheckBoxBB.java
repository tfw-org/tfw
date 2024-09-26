package tfw.swing;

import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import tfw.awt.component.EnabledCommit;
import tfw.swing.button.ButtonSelectedCommit;
import tfw.swing.button.ButtonSelectedInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;

public class JCheckBoxBB extends JCheckBox implements BranchBox {
    private static final long serialVersionUID = 1L;
    private final Branch branch;

    public JCheckBoxBB(String name, BooleanECD selectedECD, BooleanECD enabledECD) {
        this(new Branch(name), selectedECD, enabledECD);
    }

    public JCheckBoxBB(Branch branch, BooleanECD selectedECD, BooleanECD enabledECD) {
        this.branch = branch;

        ButtonSelectedInitiator buttonSelectedInitiator = new ButtonSelectedInitiator("JCheckBoxBB", selectedECD, this);

        addItemListener(buttonSelectedInitiator);
        branch.add(buttonSelectedInitiator);

        if (enabledECD != null) {
            branch.add(new EnabledCommit("JCheckBoxBB", enabledECD, this, null));
        }
        branch.add(new ButtonSelectedCommit(
                "JCheckBoxBB",
                selectedECD,
                new Initiator[] {buttonSelectedInitiator},
                new ItemListener[] {buttonSelectedInitiator},
                this));
    }

    @Override
    public final Branch getBranch() {
        return branch;
    }
}
