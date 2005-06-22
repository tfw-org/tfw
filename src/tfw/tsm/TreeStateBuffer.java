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


import tfw.check.Argument;
import tfw.value.ValueException;

import java.util.HashSet;
import java.util.Set;


/**
 * A buffer for constructing {@link TreeState} structures.
 * TODO: Need to check for loops in the tree structure.
 */
public class TreeStateBuffer
{
    private String name = null;
    private Set stateMap = new HashSet();
    private Set children = new HashSet();

    /**
     * Set the name of the tree component.
     * @param name the tree component name.
     */
    public void setName(String name)
    {
        Argument.assertNotNull(name, "name");
        this.name = name;
    }

    /**
     * Adds the state for the specified event channel.
     * @param ecd the event channel.
     * @param state the event channel state.
     * @throws ValueException if the specfiec state violates the event channel
     * value constraint.
     */
    public void addState(EventChannelState state) throws ValueException
    {
        Argument.assertNotNull(state, "state");
        stateMap.add(state);
    }

    /**
     * adds the specified child.
     * @param child the child to add.
     */
    public void addChild(TreeState child)
    {
        Argument.assertNotNull(child, "child");
        children.add(child);
    }

    /**
     * Returns a state tree as described in previous method calls on
     * this buffer.
     * @return a state tree as described in previous method calls on
     * this buffer.
     */
    public TreeState toTreeState()
    {
        if (name == null)
        {
            throw new IllegalStateException(
                "The 'setName(String)' method must be called before this method.");
        }

        return new TreeState(name,
            (EventChannelState[]) stateMap.toArray(
                new EventChannelState[stateMap.size()]),
            (TreeState[]) children.toArray(new TreeState[children.size()]));
    }
}
