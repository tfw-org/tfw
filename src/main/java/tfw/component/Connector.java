package tfw.component;

import tfw.tsm.Branch;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class Connector
{
	private final Branch commitBranch;
	private final Branch initiatorBranch;
	private final Initiator initiator;
	private final Commit commit;
	private final TriggeredCommit triggeredCommit;
	
	public Connector(String name, Branch commitBranch, Branch initiatorBranch,
		final EventChannelDescription eventChannelDescription)
	{
		this.commitBranch = commitBranch;
		this.initiatorBranch = initiatorBranch;
		
		initiator = new Initiator("Connector["+name+"]",
			new EventChannelDescription[] {eventChannelDescription});
		
		if (eventChannelDescription instanceof StatelessTriggerECD)
		{
			commit = null;
			triggeredCommit = new TriggeredCommit("Connector["+name+"]",
				(StatelessTriggerECD)eventChannelDescription,
				null,
				null)
			{
				public void commit()
				{
					initiator.trigger((StatelessTriggerECD)eventChannelDescription);

				}
			};
			
			commitBranch.add(triggeredCommit);
		}
		else
		{
			commit = new Commit("Connector["+name+"]",
				new ObjectECD[] {(ObjectECD)eventChannelDescription},
				null,
				null)
			{
				public void commit()
				{
					initiator.set((ObjectECD)eventChannelDescription,
						get((ObjectECD)eventChannelDescription));
				}
			};
			triggeredCommit = null;
			
			commitBranch.add(commit);
		}
		
		initiatorBranch.add(initiator);
	}
	
	public final void remove()
	{
		initiatorBranch.remove(initiator);
		if (commit != null)
		{
			commitBranch.remove(commit);
		}
		else
		{
			commitBranch.remove(triggeredCommit);
		}
	}
}