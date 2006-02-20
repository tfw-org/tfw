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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tfw.check.Argument;

/**
 * A branch which will multiplex child event channels into a single parent event
 * channel and demultiplex the single parent event channel into multiple child
 * event channels. To create one you must use {@link MultiplexedBranchFactory}.
 */
public class MultiplexedBranch extends TreeComponent
{
    private final Map children = new HashMap();

    private final Map subBranches = new HashMap();

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
        this.multiplexers = new Multiplexer[multiplexers.length];
        System.arraycopy(multiplexers, 0, this.multiplexers, 0,
                multiplexers.length);
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
        if (isRooted())
        {
            getTransactionManager().addComponent(this, child, slotId);
        }
        else
        {
            addToSubBranch(child, slotId);
        }
    }

    private void addAll(TreeComponent child, Object slotId)
    {
        children.put(child, slotId);

        if (child instanceof Branch)
        {
            for (Iterator i = ((Branch) child).getChildren().values()
                    .iterator(); i.hasNext();)
            {
                addAll((TreeComponent) i.next(), slotId);
            }
        }
    }

    void addToSubBranch(TreeComponent child, Object slotId)
    {
        addAll(child, slotId);
        Branch branch = (Branch) this.subBranches.get(slotId);
        if (branch == null)
        {
            branch = createSubBranch(slotId);
            // Add the child to the new branch...
            branch.addToChildren(child);
            // Add the new branch to the super class...
            super.addToChildren(branch);
            this.subBranches.put(slotId, branch);
            this.children.put(branch, slotId);
        }
        else
        {
            branch.addToChildren(child);
        }
    }

    private Branch createSubBranch(Object slotId)
    {
        BranchFactory bf = new BranchFactory();
        return bf.create(this.getName() + "[" + slotId + "]");
    }

    /**
     * Removes the specified child component.
     * 
     * @link child The child component to remove.
     */
    public synchronized final void remove(TreeComponent child)
    {
        Argument.assertNotNull(child, "child");
        if (isRooted())
        {
            getTransactionManager().removeComponent(this, child);
        }
        else
        {
            removeFromChildren(child);
        }
    }

    void removeFromChildren(TreeComponent child)
    {
        Object slotId = children.get(child);
        if (slotId == null)
        {
            throw new IllegalStateException("Attempt to remove '"
                    + child.getName() + "(" + child
                    + ")' for which no slot identifier exists");
        }

        Branch subBranch = (Branch) this.subBranches.get(slotId);
        if (subBranch == null)
        {
            throw new IllegalStateException("Attempt to remove '"
                    + child.getName() + "(" + child
                    + ")' for which no sub-branch exists");
        }

        // If the child is the subBranch...
        if (subBranch == child)
        {
            // Remove it from the sub-branch map...
            this.subBranches.remove(slotId);
            // Remove it from the super class childern...
            super.removeFromChildren(child);
        }
        else
        {
            subBranch.removeFromChildren(child);
            if (subBranch.getChildren().isEmpty())
            {
                super.remove(subBranch);
            }
        }
        removeAll(child);
    }

    private synchronized void removeAll(TreeComponent child)
    {
        children.remove(child);

        if (child instanceof Branch)
        {
            for (Iterator i = ((Branch) child).getChildren().values()
                    .iterator(); i.hasNext();)
            {
                removeAll((TreeComponent) i.next());
            }
        }
    }

    public synchronized void removeAll()
    {
        Object[] slotIds = this.subBranches.keySet().toArray();
        for (int i = 0; i < slotIds.length; i++)
        {
            removeAll( slotIds[i]);
        }
    }

    public synchronized void removeAll(Object slotId)
    {
        Branch subBranch = (Branch) this.subBranches.get(slotId);
        if (subBranch != null)
        {
            Object[] tc = subBranch.getChildren().values().toArray();
            for (int i = 0; i < tc.length; i++)
            {
                remove((TreeComponent) tc[i]);
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
    Object getSlotId(TreeComponent child)
    {
        return children.get(child);
    }
}
