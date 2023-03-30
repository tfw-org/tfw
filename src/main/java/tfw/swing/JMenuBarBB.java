package tfw.swing;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;

public class JMenuBarBB extends JMenuBar implements BranchBox
{
	private final Branch branch;
	
	public JMenuBarBB(String name)
	{
		this(new Branch("JMenuBarBB["+name+"]"));
	}
	
	public JMenuBarBB(Branch branch)
	{
		this.branch = branch;
	}
	
	public JMenu addToBoth(JMenu menu)
	{
		branch.add((BranchBox)menu);
		
		return(add(menu));
	}
	
	public final Branch getBranch()
	{
		return(branch);
	}
}
