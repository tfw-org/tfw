package tfw.swing.button;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.AbstractButton;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;

public class ButtonSelectedInitiator extends Initiator implements ItemListener {
    private final BooleanECD selectedECD;
    private final AbstractButton abstractButton;

    public ButtonSelectedInitiator(String name, BooleanECD selectedECD, AbstractButton abstractButton) {
        super("ButtonSelectedInitiator[" + name + "]", new ObjectECD[] {selectedECD});

        this.selectedECD = selectedECD;
        this.abstractButton = abstractButton;
    }

    public void itemStateChanged(ItemEvent e) {
        set(selectedECD, abstractButton.isSelected());
    }
}
