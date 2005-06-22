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
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Leaf;

public class JPanelBB extends JPanel implements BranchBox
{
	private final Branch branch;
	
	public JPanelBB(String name)
	{
		this(new Branch("JPanelBB["+name+"]"));
	}
	
	public JPanelBB(Branch branch)
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
	
	public final void addMouseListenerToBoth(MouseListener listener)
	{
		addMouseListener(listener);
		branch.add((Leaf)listener);
	}
	
	public final void removeMouseListenerFromBoth(MouseListener listener)
	{
		removeMouseListener(listener);
		branch.remove((Leaf)listener);
	}
	
	public final void addMouseMotionListenerToBoth(MouseMotionListener listener)
	{
		addMouseMotionListener(listener);
		branch.add((Leaf)listener);
	}
	
	public final void removeMouseMotionListenerFromBoth(MouseMotionListener listener)
	{
		removeMouseMotionListener(listener);
		branch.remove((Leaf)listener);
	}
	
	public final Branch getBranch()
	{
		return(branch);
	}
}