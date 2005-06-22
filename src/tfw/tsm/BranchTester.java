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
package tfw.tsm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import tfw.tsm.ecd.EventChannelDescription;

//import co2.ui.fw.test.SynchronizeThread;

final class BranchTester
{
	private final Branch testBranch;
//	private final Initiator initiator;
	private final HashMap sourceState = new HashMap();

	public BranchTester(Leaf leaf)
	{
		PortMap sinkMap = leaf.getSinks();
		EventChannelDescription[] sinks = sinkMap.getKeys();
		Map sourceMap = leaf.getSources();
		String[] sources = (String[])sourceMap.keySet().toArray(
				new String[sourceMap.size()]);

//		initiator = new Initiator("BranchTester", sinks);
//		Commit commit = new Commit("BranchTester",sources)
//		{
//			protected void commit()
//			{
//				synchronized(sourceState)
//				{
//					sourceState.clear();
//					sourceState.putAll(this.get());
//				}
//			}
//		};

		HashSet eventChannels = new HashSet();
		eventChannels.addAll(sinkMap.keySet());
		eventChannels.addAll(sourceMap.keySet());

		RootFactory nf = new RootFactory();
		for(Iterator itr = eventChannels.iterator(); itr.hasNext(); )
		{
//			nf.addTerminator((String)itr.next());
		}
		testBranch = nf.create("TestRootBranch", new BasicTransactionQueue());
//		testBranch.add(initiator);
//		testBranch.add(commit);
		testBranch.add(leaf);
//		SynchronizeThread.sync();
	}

	public void set(HashMap stateMap)
	{
//		initiator.set(stateMap);
//		SynchronizeThread.sync();
	}

	public Map get()
	{
		synchronized(sourceState)
		{
			return ( (Map)sourceState.clone() );
		}
	}
}
