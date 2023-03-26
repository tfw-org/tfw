package tfw.swing.list;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;
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
			new ObjectECD[] {listECD, selectedItemsECD,
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