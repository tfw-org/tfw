/*
 * The Framework Project Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package tfw.swing;

import java.awt.event.ItemListener;
import javax.swing.JCheckBoxMenuItem;
import tfw.awt.component.EnabledCommit;
import tfw.swing.button.ButtonSelectedCommit;
import tfw.swing.button.ButtonSelectedInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;

public class JCheckBoxMenuItemBB extends JCheckBoxMenuItem implements BranchBox
{
	private final Branch branch;

	public JCheckBoxMenuItemBB(String name, BooleanECD selectedECD,
			BooleanECD enabledECD)
	{
		this(new Branch(name), selectedECD, enabledECD);
	}

	public JCheckBoxMenuItemBB(Branch branch, BooleanECD selectedECD,
			BooleanECD enabledECD)
	{
		this.branch = branch;

		ButtonSelectedInitiator buttonSelectedInitiator =
			new ButtonSelectedInitiator("JCheckBoxBB", selectedECD, this);

		addItemListener(buttonSelectedInitiator);
		branch.add(buttonSelectedInitiator);

		if (enabledECD != null){
            branch.add(new EnabledCommit("JCheckBoxBB", enabledECD, this, null));
        }
		branch.add(new ButtonSelectedCommit("JCheckBoxBB", selectedECD,
				new Initiator[] { buttonSelectedInitiator },
				new ItemListener[] { buttonSelectedInitiator }, this));
	}

	public final Branch getBranch()
	{
		return (branch);
	}
}