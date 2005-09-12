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

import javax.swing.JToggleButton;
import tfw.awt.component.EnabledCommit;
import tfw.swing.button.ButtonSelectedCommit;
import tfw.swing.button.ButtonSelectedInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;

public class JToggleButtonBB extends JToggleButton implements BranchBox
{
	private final Branch branch;
	
	public JToggleButtonBB(String name, BooleanECD enabledECD,
		BooleanECD selectedECD)
	{
		this(new Branch("JButtonBB["+name+"]"), enabledECD, selectedECD);
	}
	
	public JToggleButtonBB(Branch branch, BooleanECD enabledECD,
		BooleanECD selectedECD)
	{
		this.branch = branch;
		
		branch.add(new EnabledCommit("JButtonBB", enabledECD, this, null));
		
		ButtonSelectedInitiator buttonSelectedInitiator =
			new ButtonSelectedInitiator("JToggleButtonBB", selectedECD, this);
		
		addChangeListener(buttonSelectedInitiator);
		branch.add(buttonSelectedInitiator);
		
		ButtonSelectedCommit buttonSelectedCommit =
			new ButtonSelectedCommit("JToggleButtonBB", selectedECD, 
				new Initiator[] {buttonSelectedInitiator}, this);
		
		branch.add(buttonSelectedCommit);
	}
	
	public final Branch getBranch()
	{
		return(branch);
	}
}