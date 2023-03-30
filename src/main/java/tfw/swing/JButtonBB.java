package tfw.swing;

import	javax.swing.JButton;

import tfw.awt.component.EnabledCommit;
import tfw.awt.event.ActionInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class JButtonBB extends JButton implements BranchBox
{
	private final Branch branch;
	
	public JButtonBB(String name, BooleanECD enabledECD,
		StatelessTriggerECD triggerECD)
	{
		this(new Branch("JButtonBB["+name+"]"), enabledECD, triggerECD);
	}
	
	public JButtonBB(Branch branch, BooleanECD enabledECD,
		StatelessTriggerECD triggerECD)
	{
		this.branch = branch;
		
		if (enabledECD != null){
            branch.add(new EnabledCommit("JButtonBB", enabledECD, this, null));
        }
		
		ActionInitiator actionInitiator =
			new ActionInitiator("JButtonBB", triggerECD);
		
		addActionListener(actionInitiator);
		branch.add(actionInitiator);
	}
	
	public final Branch getBranch()
	{
		return(branch);
	}
}
