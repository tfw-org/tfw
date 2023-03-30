package tfw.awt.event;

import	java.awt.event.ActionEvent;
import	java.awt.event.ActionListener;
import	tfw.tsm.Initiator;
import	tfw.tsm.ecd.EventChannelDescription;
import	tfw.tsm.ecd.StatelessTriggerECD;

public class ActionInitiator extends Initiator implements ActionListener
{
	private final StatelessTriggerECD triggerECD;
	
	public ActionInitiator(String name, StatelessTriggerECD triggerECD)
	{
		super("ActionInitiator["+name+"]",
			new EventChannelDescription[] {triggerECD});
		
		this.triggerECD = triggerECD;
	}
	
	public final void actionPerformed(ActionEvent e)
	{
		trigger(triggerECD);
	}
}