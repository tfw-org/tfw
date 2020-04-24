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

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import tfw.tsm.Branch;
import tfw.tsm.BranchBox;

public class JPopupMenuBB extends JPopupMenu implements BranchBox
{
	private final Branch branch;
	
	public JPopupMenuBB(String name)
	{
		this(new Branch("JPopupMenuBB["+name+"]"));
	}
	
	public JPopupMenuBB(Branch branch)
	{
		this.branch = branch;
	}
	
	public JMenuItem addToBoth(JMenuItem menuItem)
	{
		branch.add((BranchBox)menuItem);
		
		return(add(menuItem));
	}
	
	public final Branch getBranch()
	{
		return(branch);
	}
}