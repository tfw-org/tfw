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
package tfw.swing.button;

import java.awt.EventQueue;
import java.awt.event.ItemListener;
import javax.swing.AbstractButton;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;

public final class ButtonSelectedCommit extends Commit
{
	private final BooleanECD selectedECD;
	private final ItemListener[] itemListeners;
	private final AbstractButton abstractButton;
	
	public ButtonSelectedCommit(String name, BooleanECD selectedECD,
		Initiator[] initiators, ItemListener[] itemListeners,
		AbstractButton abstractButton)
	{
		super("ButtonSelectedCommit["+name+"]",
			new ObjectECD[] {selectedECD},
			null,
			initiators);
		
		this.selectedECD = selectedECD;
		if (itemListeners == null)
		{
			this.itemListeners = null;
		}
		else
		{
			this.itemListeners = new ItemListener[itemListeners.length];
			System.arraycopy(itemListeners, 0, this.itemListeners, 0, itemListeners.length);
		}
		this.abstractButton = abstractButton;
	}
	
	protected void commit()
	{
		final boolean selected = ((Boolean)get(selectedECD)).booleanValue();
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				if (itemListeners != null)
				{
					for (int i=0 ; i < itemListeners.length ; i++)
					{
						abstractButton.removeItemListener(itemListeners[i]);
					}
				}
				
				abstractButton.setSelected(selected);
				
				if (itemListeners != null)
				{
					for (int i=0 ; i < itemListeners.length ; i++)
					{
						abstractButton.addItemListener(itemListeners[i]);
					}
				}
			}
		});
	}
}