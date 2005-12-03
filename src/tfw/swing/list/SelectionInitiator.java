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

public class SelectionInitiator extends Initiator
	implements ListSelectionListener
{
	private final ObjectIlaECD selectedItemsECD;
	private final IntIlaECD selectedIndexesECD;
	private final JList list;
	
	public SelectionInitiator(String name, ObjectIlaECD selectedItemsECD,
		IntIlaECD selectedIndexesECD, JList list)
	{
		super("SelectionInitiator["+name+"]",
			new ObjectECD[] {selectedItemsECD, selectedIndexesECD});
		
		this.selectedItemsECD = selectedItemsECD;
		this.selectedIndexesECD = selectedIndexesECD;
		this.list = list;
	}
	
	public void valueChanged(ListSelectionEvent e)
	{
		if (selectedItemsECD != null)
		{
			set(selectedItemsECD,
				ObjectIlaFromArray.create(list.getSelectedValues()));
		}
		if (selectedIndexesECD != null)
		{
			set(selectedIndexesECD,
				IntIlaFromArray.create(list.getSelectedIndices()));
		}
	}
}