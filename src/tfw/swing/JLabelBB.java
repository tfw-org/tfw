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
package tfw.swing;

import javax.swing.JLabel;

import tfw.swing.label.SetTextCommit;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.ecd.StringECD;

public class JLabelBB extends JLabel implements BranchBox
{
	private final Branch branch;
	
	public JLabelBB(String name, StringECD textECD)
	{
		this(new Branch("JLabelBB["+name+"]"), textECD);
	}
	
	public JLabelBB(Branch branch, StringECD textECD)
	{
		this.branch = branch;
		
		branch.add(new SetTextCommit("JLabelBB", textECD, this, null));
	}
	
	public final Branch getBranch()
	{
		return(branch);
	}
}
