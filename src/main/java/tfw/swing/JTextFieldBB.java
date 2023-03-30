package tfw.swing;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
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
		this.getDocument().addDocumentListener(initiator);
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
	
	public final void addKeyListenerToBoth(KeyListener listener)
	{
	    addKeyListener(listener);
	    
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