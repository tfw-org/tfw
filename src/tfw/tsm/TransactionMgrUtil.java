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

import tfw.tsm.TransactionMgr.AddComponentRunnable;
import tfw.tsm.TransactionMgr.RemoveComponentRunnable;

class TransactionMgrUtil
{
	private TransactionMgrUtil() {}
	
	public static void postAddRemoveSetsToQueue(Object[] objects,
		TransactionMgr transactionMgr)
	{
		transactionMgr.lockTransactionQueue();
		
		try
		{
			for (int i=0 ; i < objects.length ; i++)
			{
				if (objects[i] instanceof TransactionMgr.AddComponentRunnable)
				{
					AddComponentRunnable acr = (AddComponentRunnable)objects[i];
					acr.setTransactionMgr(transactionMgr);
					transactionMgr.addComponent(acr, new Throwable("Add"));
				}
				else if (objects[i] instanceof TransactionMgr.RemoveComponentRunnable)
				{
					RemoveComponentRunnable rcr = (RemoveComponentRunnable)objects[i];
					rcr.setTransactionMgr(transactionMgr);
					transactionMgr.removeComponent(rcr, new Throwable("REMOVE"));
				}
				else if (objects[i] instanceof Initiator.TransactionContainer)
				{
					Initiator.TransactionContainer transactionContainer =
						(Initiator.TransactionContainer)objects[i];
					
					transactionMgr.addStateChange(
						transactionContainer.state.sources,
						transactionContainer.state.state,
						transactionContainer.transactionState,
						transactionContainer.setLocation);
				}
			}
		}
		finally
		{
			transactionMgr.unlockTransactionQueue();
		}
	}
}