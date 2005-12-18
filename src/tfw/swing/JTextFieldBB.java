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

import java.awt.event.ActionListener;
import javax.swing.JTextField;
import tfw.awt.component.EnabledCommit;
import tfw.check.Argument;
import tfw.swing.event.DocumentInitiator;
import tfw.swing.textcomponent.SetTextCommit;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Initiator;
import tfw.tsm.TreeComponent;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.StringECD;

public class JTextFieldBB extends JTextField implements BranchBox
{
	private final Branch branch;
	
	public JTextFieldBB(String name, StringECD textECD, BooleanECD enabledECD)
	{
		this(new Branch("JTextFieldBB["+name+"]"), textECD, enabledECD);
	}
	
	public JTextFieldBB(Branch branch, StringECD textECD, BooleanECD enabledECD)
	{
        Argument.assertNotNull(branch, "branch");
        Argument.assertNotNull(textECD, "textECD");
		this.branch = branch;
		
		DocumentInitiator initiator = new DocumentInitiator(
			"JTextFieldBB", textECD);
		
		branch.add(initiator);
		if (enabledECD != null){
            branch.add(new EnabledCommit("JTextFieldBB", enabledECD, this, null));
        }
		branch.add(new SetTextCommit("JTextFieldBB", textECD, this,
			initiator, new Initiator[] {initiator}));
	}
		
	public final void addActionListenerToBoth(ActionListener listener)
	{
	    addActionListener(listener);
	    
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