package tfw.swing.slider;

import java.awt.EventQueue;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;

public class SetModelCommit extends Commit {
    private final IntegerECD valueECD;
    private final IntegerECD extentECD;
    private final IntegerECD minimumECD;
    private final IntegerECD maximumECD;
    private final Initiator[] initiators;
    private final JSlider slider;

    public SetModelCommit(
            String name,
            IntegerECD valueECD,
            IntegerECD extentECD,
            IntegerECD minimumECD,
            IntegerECD maximumECD,
            Initiator[] initiators,
            JSlider slider) {
        super(
                "SetModelCommit[" + name + "]",
                new ObjectECD[] {valueECD, extentECD, minimumECD, maximumECD},
                null,
                initiators);

        this.valueECD = valueECD;
        this.extentECD = extentECD;
        this.minimumECD = minimumECD;
        this.maximumECD = maximumECD;
        this.initiators = initiators.clone();
        this.slider = slider;
    }

    protected void commit() {
        final int value = ((Integer) get(valueECD)).intValue();
        final int extent = ((Integer) get(extentECD)).intValue();
        final int minimum = ((Integer) get(minimumECD)).intValue();
        final int maximum = ((Integer) get(maximumECD)).intValue();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                for (int i = 0; i < initiators.length; i++) {
                    slider.removeChangeListener((ChangeListener) initiators[i]);
                }

                slider.setModel(new DefaultBoundedRangeModel(value, extent, minimum, maximum));

                for (int i = 0; i < initiators.length; i++) {
                    slider.addChangeListener((ChangeListener) initiators[i]);
                }
            }
        });
    }
}
