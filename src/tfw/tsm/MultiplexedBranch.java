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


/**
 * A branch which will multiplex child event channels into a single parent 
 * event channel and demultiplex the single parent event channel into multiple
 * child event channels. To create one you must use 
 * {@link MultiplexBranchFactory}.
 */
public class MultiplexedBranch extends TreeComponent
{
    private final Map children = new HashMap();
    private final Multiplexer[] multiplexers;

    /**
     * Creates a multiplexed branch.
     *
     * @param name the name of the branch.
     * @param multiplexers
     * @param ports the multiplexing ports.
     */
    MultiplexedBranch(String name, Multiplexer[] multiplexers)
    {
        super(name, null, null, multiplexers);
        this.multiplexers = new Multiplexer[multiplexers.length];
        System.arraycopy(multiplexers, 0, this.multiplexers, 0,
            multiplexers.length);
    }

	/**
	 * Over-ride the super method so we can add the special multiplexer
	 * ports.
	 */
    Set terminateLocally(Set connections)
    {
        for (int i = 0; i < this.multiplexers.length; i++)
        {
			//connections.add(multiplexers[i].initiatorMultiSource);
			connections.add(multiplexers[i].processorMultiSource);
			connections.add(multiplexers[i].multiSink);
        }

        return super.terminateLocally(connections);
    }

    /**
     * Adds a component in the specified multiplex index slot.
     * @param child the component to add.
     * @param multipexIndex the multiplexer slot into which to add the
     * component
     */
    public final void add(TreeComponent child, int multipexIndex)
    {
		children.put(child, new Integer(multipexIndex));
        super.add(child);
    }

	/**
	 * Removes the specified child component.
	 * @link child The child component to remove.
	 */
    public final void remove(TreeComponent child)
    {
        super.remove(child);
        children.remove(child);
    }

    /**
     * Returns the multiplexer index of the specified child.
     * @param child the child component whose index is to be returned.
     * @return the multiplexer index of the specified child if it  is
     * found, otherwise -1.
     */
    int getIndex(TreeComponent child)
    {
        Integer index = (Integer) children.get(child);

        if (index != null)
        {
            return index.intValue();
        }

        return -1;
    }
}
