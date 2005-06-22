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
 * witout even the implied warranty of
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class SelectionAndListCommit extends Commit
{
	private final ObjectIlaECD listECD;
	private final ObjectECD selectedItemECD;
	private final IntegerECD selectedIndexECD;
	private final JComboBox comboBox;
	
	public SelectionAndListCommit(String name, ObjectIlaECD listECD,
		ObjectECD selectedItemECD, IntegerECD selectedIndexECD,
		Initiator[] initiators, JComboBox comboBox)
	{
		super("SelectionAndListCommit["+name+"]",
			new EventChannelDescription[] {listECD, selectedItemECD,
				selectedIndexECD},
			null,
			initiators);
		
		this.listECD = listECD;
		this.selectedItemECD = selectedItemECD;
		this.selectedIndexECD = selectedIndexECD;
		this.comboBox = comboBox;
	}

	protected void commit()
	{
		if (isStateChanged(listECD))
		{
			Object[] list = ((ObjectIla)get(listECD)).toArray();
			
			comboBox.setModel(new DefaultComboBoxModel(list));
		}
		if (selectedItemECD != null)
		{
			Object selectedItem = (Object)get(selectedItemECD);
			
			comboBox.setSelectedItem(selectedItem);
		}
		if (selectedIndexECD != null)
		{
			int selectedIndex = ((Integer)get(selectedIndexECD)).intValue();
			
			comboBox.setSelectedIndex(selectedIndex);
		}
	}
}