package tfw.swing;

import javax.swing.JMenuItem;
import tfw.awt.component.EnabledCommit;
import tfw.awt.event.ActionInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class JMenuItemBB extends JMenuItem implements BranchBox
{
	private final Branch branch;
	
	public JMenuItemBB(String name, StatelessTriggerECD triggerECD,
	    BooleanECD enabledECD)
	{
		this(new Branch("JMenuItemBB["+name+"]"), triggerECD, enabledECD);
	}
	
	public JMenuItemBB(Branch branch, StatelessTriggerECD triggerECD,
	    BooleanECD enabledECD)
	{
		this.branch = branch;
		
		ActionInitiator actionInitiator = new ActionInitiator(
		    "JMenuItemBB", triggerECD);
		addActionListener(actionInitiator);
		branch.add(actionInitiator);
		
		if (enabledECD != null) 
		{
			EnabledCommit enabledCommit = new EnabledCommit("JMenuItemBB",
					enabledECD, this, null);
			branch.add(enabledCommit);
		}
	}
	
	public final Branch getBranch()
	{
		return(branch);
	}
}