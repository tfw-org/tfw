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
import java.util.Map;
import java.util.Set;

import tfw.check.Argument;

/**
 * A branch which will multiplex child event channels into a single parent event
 * channel and demultiplex the single parent event channel into multiple child
 * event channels. To create one you must use {@link MultiplexedBranchFactory}.
 */
public class MultiplexedBranch extends BranchComponent
{
    private final Map slotIdFromChild = new HashMap();
    private final Map subBranchFromSlotId = new HashMap();
    private final Multiplexer[] multiplexers;

    /**
     * Creates a multiplexed branch.
     * 
     * @param name
     *            the name of the branch.
     * @param multiplexers
     * @param ports
     *            the multiplexing ports.
     */
    MultiplexedBranch(String name, Multiplexer[] multiplexers)
    {
        super(name, null, null, multiplexers);
        
        this.multiplexers = (Multiplexer[])multiplexers.clone();
    }

    /**
     * Over-ride the super method so we can add the special multiplexer ports.
     */
    Set terminateLocally(Set connections)
    {
        for (int i = 0; i < this.multiplexers.length; i++)
        {
            connections.add(multiplexers[i].processorMultiSource);
            connections.add(multiplexers[i].multiSink);
        }

        return super.terminateLocally(connections);
    }

    /**
     * Adds a component in the specified multiplex index slot.
     * 
     * @param child
     *            the component to add.
     * @param multipexIndex
     *            the multiplexer slot into which to add the component
     */
    public synchronized final void add(TreeComponent child, int multipexIndex)
    {
        Argument.assertNotNull(child, "child");
        Argument.assertGreaterThanOrEqualTo(multipexIndex, 0, "multipexIndex");
        
        add(child, new Integer(multipexIndex));
    }

    public synchronized final void add(TreeComponent child, Object slotId)
    {
        Argument.assertNotNull(child, "child");
        Argument.assertNotNull(slotId, "slotId");
        
        Branch subBranch = (Branch)subBranchFromSlotId.get(slotId);
        if (subBranch == null)
        {
        	subBranch = new Branch(getName() + "[" + slotId + "]");
        	
        	addChild(subBranch);
        	subBranchFromSlotId.put(slotId, subBranch);
        }
        
    	slotIdFromChild.put(child, slotId);        
    	subBranch.add(child);
    }
    
    public synchronized final void add(BranchBox branchBox, Object slotId)
    {
    	Argument.assertNotNull(branchBox, "branchBox");
    	
    	add(branchBox.getBranch(), slotId);
    }

    /**
     * Removes the specified child component.
     * 
     * @link child The child component to remove.
     */
    public synchronized final void remove(TreeComponent child)
    {
        Argument.assertNotNull(child, "child");
        
        Object slotId = slotIdFromChild.get(child);
        if (slotId == null)
        {
            throw new IllegalStateException("Attempt to remove '"
                    + child.getName() + "(" + child
                    + ")' for which no slot identifier exists");
        }

        Branch subBranch = (Branch)subBranchFromSlotId.get(slotId);
        if (subBranch == null)
        {
            throw new IllegalStateException("Attempt to remove '"
                    + child.getName() + "(" + child
                    + ")' for which no sub-branch exists");
        }
        
        slotIdFromChild.remove(child);
        subBranch.remove(child);
        
        if (subBranch.immediateChildren.size() == 0)
        {
        	removeChild(subBranch);
        	subBranchFromSlotId.remove(slotId);
        }
    }
    
    public synchronized final void remove(BranchBox branchBox)
    {
    	Argument.assertNotNull(branchBox, "branchBox");
    	
    	remove(branchBox.getBranch());
    }

    public synchronized final void removeAll()
    {
        Object[] slotIds = this.subBranchFromSlotId.keySet().toArray();
        for (int i = 0; i < slotIds.length; i++)
        {
            removeAll( slotIds[i]);
        }
    }

    public synchronized final void removeAll(Object slotId)
    {
        Branch subBranch = (Branch) this.subBranchFromSlotId.get(slotId);
        if (subBranch != null)
        {
            Object[] children = subBranch.immediateChildren.toArray();
            for (int i = 0; i < children.length; i++)
            {
                remove((TreeComponent)children[i]);
            }
        }
    }

    /**
     * Returns the multiplexer slot identifier of the specified child.
     * 
     * @param child
     *            the child component whose slot identifier is to be returned.
     * @return the multiplexer slot identifier of the specified child if it is
     *         found, otherwise null.
     */
    final Object getSlotId(TreeComponent child)
    {
        return slotIdFromChild.get(child);
    }
}
