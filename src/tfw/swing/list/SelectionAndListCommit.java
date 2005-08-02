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

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ila.IntIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class SelectionAndListCommit extends Commit
{
	private final ObjectIlaECD listECD;
	private final ObjectIlaECD selectedItemsECD;
	private final IntIlaECD selectedIndexesECD;
	private final JList list;
	
	public SelectionAndListCommit(String name, ObjectIlaECD listECD,
		ObjectIlaECD selectedItemsECD, IntIlaECD selectedIndexesECD,
		Initiator[] initiators, JList list)
	{
		super("SelectionAndListCommit["+name+"]",
			new EventChannelDescription[] {listECD, selectedItemsECD,
				selectedIndexesECD},
			null,
			initiators);
		
		this.listECD = listECD;
		this.selectedItemsECD = selectedItemsECD;
		this.selectedIndexesECD = selectedIndexesECD;
		this.list = list;
	}

	protected void commit()
	{
		if (isStateChanged(listECD))
		{
			try
			{
				final Object[] elements = ((ObjectIla)get(listECD)).toArray();
			
				EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						DefaultListModel defaultListModel =
							new DefaultListModel();
						
						defaultListModel.copyInto(elements);
						list.setModel(defaultListModel);						
					}
				});
			}
			catch(DataInvalidException die)
			{
				return;
			}
		}
		if (selectedItemsECD != null)
		{
			try
			{
				final Object[] selectedItems =
					((ObjectIla)get(selectedItemsECD)).toArray();
				
				EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						list.clearSelection();
						
						for (int i=0 ; i < selectedItems.length ; i++)
						{
							list.setSelectedValue(selectedItems[i], false);
						}
					}
				});
			}
			catch (DataInvalidException e)
			{
				return;
			}
		}
		if (selectedIndexesECD != null)
		{
			try
			{
				final int[] selectedIndex =
					((IntIla)get(selectedIndexesECD)).toArray();

				EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						list.setSelectedIndices(selectedIndex);
					}
				});
			}
			catch (DataInvalidException e)
			{
				return;
			}
		}
	}
}