package tfw.swing.list;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.IntIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class SelectionInitiator extends Initiator implements ListSelectionListener {
    private final ObjectIlaECD selectedItemsECD;
    private final IntIlaECD selectedIndexesECD;
    private final JList<Object> list;

    public SelectionInitiator(
            String name, ObjectIlaECD selectedItemsECD, IntIlaECD selectedIndexesECD, JList<Object> list) {
        super("SelectionInitiator[" + name + "]", new ObjectECD[] {selectedItemsECD, selectedIndexesECD});

        this.selectedItemsECD = selectedItemsECD;
        this.selectedIndexesECD = selectedIndexesECD;
        this.list = list;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (selectedItemsECD != null) {
            set(
                    selectedItemsECD,
                    ObjectIlaFromArray.create(list.getSelectedValuesList().toArray()));
        }
        if (selectedIndexesECD != null) {
            set(selectedIndexesECD, IntIlaFromArray.create(list.getSelectedIndices()));
        }
    }
}
