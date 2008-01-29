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
 * without even the implied warranty of
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

import java.util.Comparator;

class TransactionMgrUtil
{
	private TransactionMgrUtil() {}
	
	private static long counter = 0;
	
	public static class AddRemoveSetContainer
	{
		private final long count;
		private final Object object;
		
		private AddRemoveSetContainer(Object object)
		{
			this.count = counter++;
			this.object = object;
		}
	}
	
	public static synchronized AddRemoveSetContainer createAddRemoveSetContainer(
		TransactionMgr.AddComponentRunnable addComponentRunnable)
	{
		return(new AddRemoveSetContainer(addComponentRunnable));
	}
	
	public static synchronized AddRemoveSetContainer createAddRemoveSetContainer(
		TransactionMgr.RemoveComponentRunnable removeComponentRunnable)
	{
		return(new AddRemoveSetContainer(removeComponentRunnable));
	}
	
	public static synchronized AddRemoveSetContainer createAddRemoveSetContainer(
		Initiator.SourceNState sourceNState)
	{
		return(new AddRemoveSetContainer(sourceNState));
	}
	
	public static void postAddRemoveSetsToQueue(AddRemoveSetContainer[] containers,
		TransactionMgr transactionMgr)
	{
		for (int i=0 ; i < containers.length ; i++)
		{
			if (containers[i].object instanceof TransactionMgr.AddComponentRunnable)
			{
				transactionMgr.addComponent(
					(TransactionMgr.AddComponentRunnable)containers[i].object);
			}
			else if (containers[i].object instanceof TransactionMgr.RemoveComponentRunnable)
			{
				transactionMgr.removeComponent(
					(TransactionMgr.RemoveComponentRunnable)containers[i].object);
			}
			else if (containers[i].object instanceof Initiator.SourceNState)
			{
				Initiator.SourceNState sourceNState =
					(Initiator.SourceNState)containers[i].object;
				
				transactionMgr.addStateChange(
					sourceNState.sources, sourceNState.state);
			}
		}
	}
	
	private static Comparator comparator = new Comparator()
	{
		public int compare(Object object1, Object object2)
		{
			AddRemoveSetContainer aRSC1 = (AddRemoveSetContainer)object1;
			AddRemoveSetContainer aRSC2 = (AddRemoveSetContainer)object2;
			
			if (aRSC1.count < aRSC2.count)
				return(-1);
			else if (aRSC1.count > aRSC2.count)
				return(1);
			else
				return(0);
		}
		
	};
}