package tfw.swing;

import java.awt.event.ItemListener;
import javax.swing.JToggleButton;
import tfw.awt.component.EnabledCommit;
import tfw.swing.button.ButtonSelectedCommit;
import tfw.swing.button.ButtonSelectedInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;

public class JToggleButtonBB extends JToggleButton implements BranchBox {
    private static final long serialVersionUID = 1L;
    private final Branch branch;

    public JToggleButtonBB(String name, BooleanECD enabledECD, BooleanECD selectedECD) {
        this(new Branch("JButtonBB[" + name + "]"), enabledECD, selectedECD);
    }

    public JToggleButtonBB(Branch branch, BooleanECD enabledECD, BooleanECD selectedECD) {
        this.branch = branch;

        branch.add(new EnabledCommit("JButtonBB", enabledECD, this, null));

        ButtonSelectedInitiator buttonSelectedInitiator =
                new ButtonSelectedInitiator("JToggleButtonBB", selectedECD, this);

        addItemListener(buttonSelectedInitiator);
        branch.add(buttonSelectedInitiator);

        ButtonSelectedCommit buttonSelectedCommit = new ButtonSelectedCommit(
                "JToggleButtonBB",
                selectedECD,
                new Initiator[] {buttonSelectedInitiator},
                new ItemListener[] {buttonSelectedInitiator},
                this);

        branch.add(buttonSelectedCommit);
    }

    public final Branch getBranch() {
        return (branch);
    }
}
