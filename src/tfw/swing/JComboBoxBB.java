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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;

import tfw.awt.component.EnabledCommit;
import tfw.swing.combobox.SelectionAndListCommit;
import tfw.swing.combobox.SelectionInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class JComboBoxBB extends JComboBox implements BranchBox {
	private final Branch branch;

	public JComboBoxBB(String name, ObjectIlaECD listECD,
			ObjectECD selectedItemECD, IntegerECD selectedIndexECD,
			BooleanECD enabledECD) {
		this(new Branch(name), listECD, selectedItemECD, selectedIndexECD,
				enabledECD);
	}

	public JComboBoxBB(Branch branch, ObjectIlaECD listECD,
			ObjectECD selectedItemECD, IntegerECD selectedIndexECD,
			BooleanECD enabledECD) {
		this(branch, listECD, selectedItemECD, selectedIndexECD, enabledECD,
				new Initiator[0]);
	}

	public JComboBoxBB(Branch branch, ObjectIlaECD listECD,
			ObjectECD selectedItemECD, IntegerECD selectedIndexECD,
			BooleanECD enabledECD, Initiator[] initiators) {

		this.branch = branch;

		SelectionInitiator selectionInitiator = new SelectionInitiator(
				"JComboBoxBB", selectedItemECD, selectedIndexECD, this);

		addActionListener(selectionInitiator);
		branch.add(selectionInitiator);

		List list = new ArrayList(Arrays.asList(initiators));
		list.add(selectionInitiator);
		initiators = (Initiator[])list.toArray(new Initiator[list.size()]);
		SelectionAndListCommit selectionAndListCommit = new SelectionAndListCommit(
				"JComboBoxBB", listECD, selectedItemECD, selectedIndexECD,
				initiators, this);

		if (enabledECD != null) {
			branch
					.add(new EnabledCommit("JComboBoxBB", enabledECD, this,
							null));
		}
		branch.add(selectionAndListCommit);
	}

	public final Branch getBranch() {
		return (branch);
	}
}
