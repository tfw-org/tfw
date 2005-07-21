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
package tfw.component;

import tfw.tsm.Branch;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.ecd.EventChannelDescription;
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
					initiator.set(eventChannelDescription,
						get(eventChannelDescription));

				}
			};
			
			commitBranch.add(triggeredCommit);
		}
		else
		{
			commit = new Commit("Connector["+name+"]",
				new EventChannelDescription[] {eventChannelDescription},
				null,
				null)
			{
				public void commit()
				{
					initiator.set(eventChannelDescription,
						get(eventChannelDescription));
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