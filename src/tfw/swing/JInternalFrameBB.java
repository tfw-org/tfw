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

import java.awt.Container;

import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.event.InternalFrameListener;

import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.TreeComponent;

public class JInternalFrameBB extends JInternalFrame implements BranchBox
{
	private final Branch branch;
	
	public JInternalFrameBB(String name)
	{
		this(new Branch("JPanelBB["+name+"]"));
	}
	
	public JInternalFrameBB(Branch branch)
	{
		this.branch = branch;
	}
	
	public final void setContentPaneForBoth(Container contentPane)
	{
		if (getContentPane() instanceof BranchBox)
		{
			branch.remove((BranchBox)getContentPane());
		}
		setContentPane(contentPane);
		branch.add((BranchBox)contentPane);
	}
	
	public final void setJMenuBarForBoth(JMenuBar menuBar)
	{
	    setJMenuBar(menuBar);
	    branch.add((BranchBox)menuBar);
	}
	
	public final void addInternalFrameListenerToBoth(
		InternalFrameListener listener)
	{
	    addInternalFrameListener(listener);
	    
	    if (listener instanceof BranchBox)
	    {
	        branch.add((BranchBox)listener);
	    }
	    else if (listener instanceof TreeComponent)
	    {
	        branch.add((TreeComponent)listener);
	    }
	    else
	    {
	        throw new IllegalArgumentException(
	            "listener != BranchBox || TreeComponent not allowed!");
	    }
	}
	
	public final Branch getBranch()
	{
		return(branch);
	}
}
