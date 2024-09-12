package tfw.swing.slider;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import tfw.tsm.EventChannelStateBuffer;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.EventChannelDescriptionUtil;
import tfw.tsm.ecd.IntegerECD;

public class SliderChangeInitiator extends Initiator implements ChangeListener {
    private final IntegerECD valueECD;
    private final IntegerECD valueAdjECD;
    private final JSlider slider;

    public SliderChangeInitiator(String name, IntegerECD valueECD, IntegerECD valueAdjECD, JSlider slider) {
        super(
                "SliderChangeInitiator[" + name + "]",
                EventChannelDescriptionUtil.create(new EventChannelDescription[] {valueECD, valueAdjECD}));

        this.valueECD = valueECD;
        this.valueAdjECD = valueAdjECD;
        this.slider = slider;
    }

    public void stateChanged(ChangeEvent changeEvent) {
        EventChannelStateBuffer ecsb = new EventChannelStateBuffer();

        if (slider.getValueIsAdjusting()) {
            if (valueAdjECD != null) {
                ecsb.put(valueAdjECD, slider.getValue());
            }
        } else {
            if (valueECD != null) {
                ecsb.put(valueECD, slider.getValue());
            }
        }

        set(ecsb.toArray());
    }
}
