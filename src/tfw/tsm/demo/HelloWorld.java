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
package tfw.tsm.demo;

import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;

public class HelloWorld
{
	public static final void main(String[] args)
	{
		StatelessTriggerECD triggerECD = new StatelessTriggerECD("trigger");
		
		RootFactory rf = new RootFactory();
		rf.addEventChannel(triggerECD);
		Root r = rf.create("Hello World Root", new BasicTransactionQueue());
		
		Initiator i = new Initiator("Hello World Initiator",
			new EventChannelDescription[] {triggerECD});
		
		TriggeredCommit c = new TriggeredCommit("Hello World Commit",
			triggerECD,
			null,
			null)
		{
			protected void commit()
			{
				System.out.println("HelloWorld");
			}
		};
		
		r.add(i);
		r.add(c);
		
		i.trigger(triggerECD);
	}
}