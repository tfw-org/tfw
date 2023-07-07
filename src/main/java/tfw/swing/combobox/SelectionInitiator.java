package tfw.swing.combobox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;

public class SelectionInitiator extends Initiator implements ActionListener {
    private final ObjectECD selectedItemECD;

    private final IntegerECD selectedIndexECD;

    private final JComboBox<Object> comboBox;

    public SelectionInitiator(
            String name, ObjectECD selectedItemECD, IntegerECD selectedIndexECD, JComboBox<Object> comboBox) {
        super("SelectionInitiator[" + name + "]", checkECDs(selectedItemECD, selectedIndexECD));

        this.selectedItemECD = selectedItemECD;
        this.selectedIndexECD = selectedIndexECD;
        this.comboBox = comboBox;
    }

    private static ObjectECD[] checkECDs(ObjectECD selectedItemECD, IntegerECD selectedIndexECD) {
        ArrayList<ObjectECD> list = new ArrayList<>();
        if (selectedItemECD != null) {
            list.add(selectedItemECD);
        }
        if (selectedIndexECD != null) {
            list.add(selectedIndexECD);
        }
        if (list.isEmpty()) {
            throw new IllegalArgumentException("(selectedItemECD == null)&&(selectedIndexECD == null) not allowed");
        }
        return list.toArray(new ObjectECD[list.size()]);
    }

    public void actionPerformed(ActionEvent e) {
        if ((selectedItemECD != null) && (comboBox.getSelectedItem() != null)) {
            set(selectedItemECD, comboBox.getSelectedItem());
        }
        if (selectedIndexECD != null) {
            set(selectedIndexECD, new Integer(comboBox.getSelectedIndex()));
        }
    }
}
