/*
 * The Framework Project Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; witout even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package tfw.swing;

import javax.swing.JCheckBoxMenuItem;

import tfw.awt.component.EnabledCommit;
import tfw.component.Connector;
import tfw.swing.button.ButtonSelectedCommit;
import tfw.swing.button.ButtonSelectedInitiator;
import tfw.tsm.AWTTransactionQueue;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
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

		addChangeListener(buttonSelectedInitiator);
		branch.add(buttonSelectedInitiator);
		
		RootFactory rootFactory = new RootFactory();
		rootFactory.addEventChannel(enabledECD);
		rootFactory.addEventChannel(selectedECD);
		Root awtRoot = rootFactory.create(branch.getName()+"_AWT_ROOT",
			new AWTTransactionQueue());

		awtRoot.add(new EnabledCommit("JCheckBoxBB", enabledECD, this, null));
		awtRoot.add(new ButtonSelectedCommit("JCheckBoxBB", selectedECD,
				new Initiator[] { buttonSelectedInitiator }, this));
		
		new Connector(enabledECD.getEventChannelName(),
			branch, awtRoot, enabledECD);
		new Connector(selectedECD.getEventChannelName(),
			branch, awtRoot, selectedECD);
	}

	public final Branch getBranch()
	{
		return (branch);
	}
}