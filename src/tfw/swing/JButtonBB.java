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

import	javax.swing.JButton;

import tfw.awt.component.EnabledCommit;
import tfw.awt.event.ActionInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class JButtonBB extends JButton implements BranchBox
{
	private final Branch branch;
	
	public JButtonBB(String name, BooleanECD enabledECD,
		StatelessTriggerECD triggerECD)
	{
		this(new Branch("JButtonBB["+name+"]"), enabledECD, triggerECD);
	}
	
	public JButtonBB(Branch branch, BooleanECD enabledECD,
		StatelessTriggerECD triggerECD)
	{
		this.branch = branch;
		
		if (enabledECD != null){
            branch.add(new EnabledCommit("JButtonBB", enabledECD, this, null));
        }
		
		ActionInitiator actionInitiator =
			new ActionInitiator("JButtonBB", triggerECD);
		
		addActionListener(actionInitiator);
		branch.add(actionInitiator);
	}
	
	public final Branch getBranch()
	{
		return(branch);
	}
}
