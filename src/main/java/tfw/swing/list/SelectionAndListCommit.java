package tfw.swing.list;

import java.awt.EventQueue;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaUtil;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.IntIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class SelectionAndListCommit extends Commit {
    private final ObjectIlaECD listECD;
    private final ObjectIlaECD selectedItemsECD;
    private final IntIlaECD selectedIndexesECD;
    private final JList<Object> list;

    public SelectionAndListCommit(
            String name,
            ObjectIlaECD listECD,
            ObjectIlaECD selectedItemsECD,
            IntIlaECD selectedIndexesECD,
            Initiator[] initiators,
            JList<Object> list) {
        super(
                "SelectionAndListCommit[" + name + "]",
                new ObjectECD[] {listECD, selectedItemsECD, selectedIndexesECD},
                null,
                initiators);

        this.listECD = listECD;
        this.selectedItemsECD = selectedItemsECD;
        this.selectedIndexesECD = selectedIndexesECD;
        this.list = list;
    }

    @SuppressWarnings("unchecked")
    protected void commit() {
        if (isStateChanged(listECD)) {
            try {
                final ObjectIla<Object> elementsIla = (ObjectIla<Object>) get(listECD);
                final Object[] elements = new Object[(int) elementsIla.length()];

                elementsIla.get(elements, 0, 0, elements.length);

                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        DefaultListModel<Object> defaultListModel = new DefaultListModel<>();

                        defaultListModel.copyInto(elements);
                        list.setModel(defaultListModel);
                    }
                });
            } catch (IOException e) {
                return;
            }
        }
        if (selectedItemsECD != null) {
            try {
                final ObjectIla<Object> selectedItemsIla = (ObjectIla<Object>) get(selectedItemsECD);
                final Object[] selectedItems = new Object[(int) selectedItemsIla.length()];

                selectedItemsIla.get(selectedItems, 0, 0, selectedItems.length);

                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        list.clearSelection();

                        for (int i = 0; i < selectedItems.length; i++) {
                            list.setSelectedValue(selectedItems[i], false);
                        }
                    }
                });
            } catch (IOException e) {
                return;
            }
        }
        if (selectedIndexesECD != null) {
            try {
                final int[] selectedIndex = IntIlaUtil.toArray((IntIla) get(selectedIndexesECD));

                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        list.setSelectedIndices(selectedIndex);
                    }
                });
            } catch (IOException e) {
                return;
            }
        }
    }
}
