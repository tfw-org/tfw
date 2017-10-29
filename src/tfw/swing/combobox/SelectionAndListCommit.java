/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
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

public class SelectionAndListCommit extends Commit
{
    private final ObjectIlaECD listECD;
    private final ObjectECD selectedItemECD;
    private final IntegerECD selectedIndexECD;
    private final ActionListener[] actionListeners;
    private final JComboBox comboBox;

    public SelectionAndListCommit(String name, ObjectIlaECD listECD,
            ObjectECD selectedItemECD, IntegerECD selectedIndexECD,
            Initiator[] initiators, ActionListener[] actionListeners,
            JComboBox comboBox)
    {
        super("SelectionAndListCommit[" + name + "]", toArray(listECD,
                selectedItemECD, selectedIndexECD), null, initiators);

        this.listECD = listECD;
        this.selectedItemECD = selectedItemECD;
        this.selectedIndexECD = selectedIndexECD;
        if (actionListeners == null)
        {
        	this.actionListeners = null;
        }
        else
        {
        	this.actionListeners = new ActionListener[actionListeners.length];
        	System.arraycopy(actionListeners, 0, this.actionListeners, 0, actionListeners.length);
        }
        this.comboBox = comboBox;
    }

    private static ObjectECD[] toArray(ObjectIlaECD listECD,
            ObjectECD selectedItemECD, IntegerECD selectedIndexECD)
    {
        Argument.assertNotNull(listECD, "listECD");
        if ((selectedItemECD == null) && (selectedIndexECD == null))
        {
            throw new IllegalStateException(
                    "(selectedItemECD == null) && (selectedIndexECD == null) not allowed");
        }
        ArrayList<ObjectECD> list = new ArrayList<ObjectECD>();
        list.add(listECD);
        if (selectedItemECD != null)
        {
            list.add(selectedItemECD);
        }
        if (selectedIndexECD != null)
        {
            list.add(selectedIndexECD);
        }
        return list.toArray(new ObjectECD[list.size()]);
    }

    protected void commit()
    {
        try
        {
            final Object[] list = ((ObjectIla) get(listECD)).toArray();

            EventQueue.invokeLater(new Runnable()
            {
                public void run()
                {
                    ComboBoxModel cbm = comboBox.getModel();
                    if (cbm.getSize() == list.length)
                    {
                        boolean equal = true;
                        for (int i = 0; i < list.length; i++)
                        {
                            if (list[i] != cbm.getElementAt(i))
                            {
                                equal = false;
                                break;
                            }
                        }
                        if (equal)
                        {
                            return;
                        }
                    }
                    
                    if (actionListeners != null)
                    {
                    	for (int i=0 ; i < actionListeners.length ; i++)
                		{
                    		comboBox.removeActionListener(actionListeners[i]);
                		}
                    }
                    
                    DefaultComboBoxModel model = new DefaultComboBoxModel(list);
                    if (model.getIndexOf(comboBox.getSelectedItem()) > 0)
                    {
                        model.setSelectedItem(comboBox.getSelectedItem());
                    }
                    comboBox.setModel(model);
                    
                    
                    if (actionListeners != null)
                    {
                    	for (int i=0 ; i < actionListeners.length ; i++)
                		{
                    		comboBox.addActionListener(actionListeners[i]);
                		}
                    }
                }
            });
        }
        catch (DataInvalidException die)
        {
        }

		if (selectedItemECD != null) 
		{
            final Object selectedItem = get(selectedItemECD);

			EventQueue.invokeLater(new Runnable() 
			{
				public void run() 
				{
					if (selectedItem != comboBox.getSelectedItem())
					{
	                    if (actionListeners != null)
	                    {
	                    	for (int i=0 ; i < actionListeners.length ; i++)
	                		{
	                    		comboBox.removeActionListener(actionListeners[i]);
	                		}
	                    }

	                    comboBox.setSelectedItem(selectedItem);

	                    if (actionListeners != null)
	                    {
	                    	for (int i=0 ; i < actionListeners.length ; i++)
	                		{
	                    		comboBox.addActionListener(actionListeners[i]);
	                		}
	                    }
					}
				}
            });
        }
		if (selectedIndexECD != null) 
		{
            final int selectedIndex = ((Integer) get(selectedIndexECD))
                    .intValue();

			EventQueue.invokeLater(new Runnable() 
			{
				public void run() 
				{
					if (selectedIndex != comboBox.getSelectedIndex())
					{
	                    
	                    if (actionListeners != null)
	                    {
	                    	for (int i=0 ; i < actionListeners.length ; i++)
	                		{
	                    		comboBox.removeActionListener(actionListeners[i]);
	                		}
	                    }

	                    comboBox.setSelectedIndex(selectedIndex);
	                    
	                    if (actionListeners != null)
	                    {
	                    	for (int i=0 ; i < actionListeners.length ; i++)
	                		{
	                    		comboBox.addActionListener(actionListeners[i]);
	                		}
	                    }
                	}
				}
            });
        }
    }
}