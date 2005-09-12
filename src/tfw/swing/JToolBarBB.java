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

import java.awt.Component;

import javax.swing.JToolBar;

import tfw.tsm.Branch;
import tfw.tsm.BranchBox;

public class JToolBarBB extends JToolBar implements BranchBox
{
	private final Branch branch;
	
	public JToolBarBB(String name)
	{
		this(new Branch(name));
	}
	
	public JToolBarBB(Branch branch)
	{
		this.branch = branch;
	}
	
	public final Component addToBoth(Component comp)
	{
		branch.add((BranchBox)comp);
		return(add(comp));
	}
	
	public final Component addToBoth(Component comp, int index)
	{
		branch.add((BranchBox)comp);
		return(add(comp, index));
	}
	
	public final void addToBoth(Component comp, Object constraints)
	{
		branch.add((BranchBox)comp);
		add(comp, constraints);
	}
	
	public final void addToBoth(Component comp, Object constraints, int index)
	{
		branch.add((BranchBox)comp);
		add(comp, constraints, index);
	}
	
	public final Component addToBoth(String name, Component comp)
	{
		branch.add((BranchBox)comp);
		return(add(name, comp));
	}
	
	public final void removeFromBoth(Component comp)
	{
		branch.remove((BranchBox)comp);
		remove(comp);
	}
	
	public final void removeFromBoth(int index)
	{
		branch.remove((BranchBox)getComponent(index));
		remove(index);
	}
	
	public final void removeAllFromBoth()
	{
//		branch.removeAll();
		removeAll();
	}
	
	public Branch getBranch()
	{
		return(branch);
	}
}