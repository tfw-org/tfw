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
package tfw.swing;

import javax.swing.JMenuItem;
import tfw.awt.component.EnabledCommit;
import tfw.awt.event.ActionInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class JMenuItemBB extends JMenuItem implements BranchBox
{
	private final Branch branch;
	
	public JMenuItemBB(String name, StatelessTriggerECD triggerECD,
	    BooleanECD enabledECD)
	{
		this(new Branch("JMenuItemBB["+name+"]"), triggerECD, enabledECD);
	}
	
	public JMenuItemBB(Branch branch, StatelessTriggerECD triggerECD,
	    BooleanECD enabledECD)
	{
		this.branch = branch;
		
		ActionInitiator actionInitiator = new ActionInitiator(
		    "JMenuItemBB", triggerECD);
		addActionListener(actionInitiator);
		branch.add(actionInitiator);
		
		EnabledCommit enabledCommit = new EnabledCommit(
		    "JMenuItemBB", enabledECD, this, null);
		branch.add(enabledCommit);
	}
	
	public final Branch getBranch()
	{
		return(branch);
	}
}