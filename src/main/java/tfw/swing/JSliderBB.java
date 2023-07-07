package tfw.swing;

import javax.swing.JSlider;
import tfw.awt.component.EnabledCommit;
import tfw.swing.slider.SetModelCommit;
import tfw.swing.slider.SliderChangeInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.IntegerECD;

public class JSliderBB extends JSlider implements BranchBox {
    private static final long serialVersionUID = 1L;
    private final Branch branch;

    public JSliderBB(
            String name,
            IntegerECD valueECD,
            IntegerECD valueAdjECD,
            IntegerECD extentECD,
            IntegerECD minimumECD,
            IntegerECD maximumECD,
            BooleanECD enabledECD) {
        this(
                new Branch("JSliderBB[" + name + "]"),
                valueECD,
                valueAdjECD,
                extentECD,
                minimumECD,
                maximumECD,
                enabledECD);
    }

    public JSliderBB(
            Branch branch,
            IntegerECD valueECD,
            IntegerECD valueAdjECD,
            IntegerECD extentECD,
            IntegerECD minimumECD,
            IntegerECD maximumECD,
            BooleanECD enabledECD) {
        this.branch = branch;

        SliderChangeInitiator sliderChangeInitiator =
                new SliderChangeInitiator(branch.getName(), valueECD, valueAdjECD, this);
        branch.add(sliderChangeInitiator);
        addChangeListener(sliderChangeInitiator);

        SetModelCommit setModelCommit = new SetModelCommit(
                branch.getName(),
                valueECD,
                extentECD,
                minimumECD,
                maximumECD,
                new Initiator[] {sliderChangeInitiator},
                this);
        branch.add(setModelCommit);

        EnabledCommit setEnabledCommit = new EnabledCommit(branch.getName(), enabledECD, this, null);
        branch.add(setEnabledCommit);

        setEnabled(false);
    }

    public final Branch getBranch() {
        return (branch);
    }
}
