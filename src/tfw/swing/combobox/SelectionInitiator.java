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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;

public class SelectionInitiator extends Initiator implements ActionListener
{
	private final ObjectECD selectedItemECD;
	private final IntegerECD selectedIndexECD;
	private final JComboBox comboBox;
	
	public SelectionInitiator(String name, ObjectECD selectedItemECD,
		IntegerECD selectedIndexECD, JComboBox comboBox)
	{
		super("SelectionInitiator["+name+"]",
			new EventChannelDescription[] {selectedItemECD, selectedIndexECD});
		
		this.selectedItemECD = selectedItemECD;
		this.selectedIndexECD = selectedIndexECD;
		this.comboBox = comboBox;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (selectedItemECD != null)
		{
			set(selectedItemECD, comboBox.getSelectedItem());
		}
		if (selectedIndexECD != null)
		{
			set(selectedIndexECD, new Integer(comboBox.getSelectedIndex()));
		}
	}
}
