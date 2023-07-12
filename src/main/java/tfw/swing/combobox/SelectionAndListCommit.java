package tfw.swing.combobox;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class SelectionAndListCommit extends Commit {
    private final ObjectIlaECD listECD;
    private final ObjectECD selectedItemECD;
    private final IntegerECD selectedIndexECD;
    private final ActionListener[] actionListeners;
    private final JComboBox<Object> comboBox;

    public SelectionAndListCommit(
            String name,
            ObjectIlaECD listECD,
            ObjectECD selectedItemECD,
            IntegerECD selectedIndexECD,
            Initiator[] initiators,
            ActionListener[] actionListeners,
            JComboBox<Object> comboBox) {
        super(
                "SelectionAndListCommit[" + name + "]",
                toArray(listECD, selectedItemECD, selectedIndexECD),
                null,
                initiators);

        this.listECD = listECD;
        this.selectedItemECD = selectedItemECD;
        this.selectedIndexECD = selectedIndexECD;
        if (actionListeners == null) {
            this.actionListeners = null;
        } else {
            this.actionListeners = new ActionListener[actionListeners.length];
            System.arraycopy(actionListeners, 0, this.actionListeners, 0, actionListeners.length);
        }
        this.comboBox = comboBox;
    }

    private static ObjectECD[] toArray(ObjectIlaECD listECD, ObjectECD selectedItemECD, IntegerECD selectedIndexECD) {
        Argument.assertNotNull(listECD, "listECD");
        if (selectedItemECD == null && selectedIndexECD == null) {
            throw new IllegalStateException("(selectedItemECD == null) && (selectedIndexECD == null) not allowed");
        }
        ArrayList<ObjectECD> list = new ArrayList<ObjectECD>();
        list.add(listECD);
        if (selectedItemECD != null) {
            list.add(selectedItemECD);
        }
        if (selectedIndexECD != null) {
            list.add(selectedIndexECD);
        }
        return list.toArray(new ObjectECD[list.size()]);
    }

    protected void commit() {
        try {
            final ObjectIla listIla = (ObjectIla) get(listECD);
            final Object[] list = new Object[(int) listIla.length()];

            listIla.toArray(list, 0, 0, list.length);

            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    ComboBoxModel<Object> cbm = comboBox.getModel();
                    if (cbm.getSize() == list.length) {
                        boolean equal = true;
                        for (int i = 0; i < list.length; i++) {
                            if (list[i] != cbm.getElementAt(i)) {
                                equal = false;
                                break;
                            }
                        }
                        if (equal) {
                            return;
                        }
                    }

                    if (actionListeners != null) {
                        for (int i = 0; i < actionListeners.length; i++) {
                            comboBox.removeActionListener(actionListeners[i]);
                        }
                    }

                    DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>(list);
                    if (model.getIndexOf(comboBox.getSelectedItem()) > 0) {
                        model.setSelectedItem(comboBox.getSelectedItem());
                    }
                    comboBox.setModel(model);

                    if (actionListeners != null) {
                        for (int i = 0; i < actionListeners.length; i++) {
                            comboBox.addActionListener(actionListeners[i]);
                        }
                    }
                }
            });
        } catch (DataInvalidException die) {
        }

        if (selectedItemECD != null) {
            final Object selectedItem = get(selectedItemECD);

            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    if (selectedItem != comboBox.getSelectedItem()) {
                        if (actionListeners != null) {
                            for (int i = 0; i < actionListeners.length; i++) {
                                comboBox.removeActionListener(actionListeners[i]);
                            }
                        }

                        comboBox.setSelectedItem(selectedItem);

                        if (actionListeners != null) {
                            for (int i = 0; i < actionListeners.length; i++) {
                                comboBox.addActionListener(actionListeners[i]);
                            }
                        }
                    }
                }
            });
        }
        if (selectedIndexECD != null) {
            final int selectedIndex = ((Integer) get(selectedIndexECD)).intValue();

            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    if (selectedIndex != comboBox.getSelectedIndex()) {

                        if (actionListeners != null) {
                            for (int i = 0; i < actionListeners.length; i++) {
                                comboBox.removeActionListener(actionListeners[i]);
                            }
                        }

                        comboBox.setSelectedIndex(selectedIndex);

                        if (actionListeners != null) {
                            for (int i = 0; i < actionListeners.length; i++) {
                                comboBox.addActionListener(actionListeners[i]);
                            }
                        }
                    }
                }
            });
        }
    }
}
