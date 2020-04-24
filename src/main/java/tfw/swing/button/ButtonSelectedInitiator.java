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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.AbstractButton;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;

public class ButtonSelectedInitiator extends Initiator implements ItemListener
{
	private final BooleanECD selectedECD;
	private final AbstractButton abstractButton;
	
	public ButtonSelectedInitiator(String name, BooleanECD selectedECD,
		AbstractButton abstractButton)
	{
		super("ButtonSelectedInitiator["+name+"]",
			new ObjectECD[] {selectedECD});
		
		this.selectedECD = selectedECD;
		this.abstractButton = abstractButton;
	}
	
	public void itemStateChanged(ItemEvent e)
	{
		set(selectedECD, new Boolean(abstractButton.isSelected()));
	}
}