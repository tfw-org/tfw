package tfw.swing;

import java.awt.Container;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.TreeComponent;

public class JFrameBB extends JFrame implements BranchBox
{
	private final Branch branch;
	
	public JFrameBB(String name)
	{
		this(new Branch("JPanelBB["+name+"]"));
	}
	
	public JFrameBB(Branch branch)
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
	
	public final void addWindowListenerToBoth(WindowListener listener)
	{
	    addWindowListener(listener);
	    
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
