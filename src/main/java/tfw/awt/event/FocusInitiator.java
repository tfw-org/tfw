package tfw.awt.event;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.EventChannelDescription;

public class FocusInitiator extends Initiator implements FocusListener {
    private final BooleanECD hasFocusECD;

    public FocusInitiator(String name, BooleanECD hasFocusECD) {
        super("FocusInitiator[" + name + "]", new EventChannelDescription[] {hasFocusECD});

        this.hasFocusECD = hasFocusECD;
    }

    @Override
    public void focusGained(FocusEvent e) {
        set(hasFocusECD, Boolean.TRUE);
    }

    @Override
    public void focusLost(FocusEvent e) {
        set(hasFocusECD, Boolean.FALSE);
    }
}
