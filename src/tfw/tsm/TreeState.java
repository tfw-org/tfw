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

import tfw.array.ArrayUtil;
import tfw.check.Argument;



/**
 * A representation of the state of a tree component. Note that a state of
 * a tree component includes its name, the state of its event channels and 
 * the state of its child sub-trees.
 */
public class TreeState
{
	/** The name of the tree component. */
    private final String name;
    
    /** The child sub-trees. */
    private final TreeState[] children;
    
    /** The event channel state for the tree component. */
    private final EventChannelState[] state;

    /**
     * Creates a tree state with the specified values.
     * @param name The name of the component.
     * @param state The set of event channel state for the node in the tree.
     * @param children The set of sub-trees
     */
    public TreeState(String name, EventChannelState[] state,
        TreeState[] children)
    {
        Argument.assertNotNull(name, "name");
        this.name = name;

        if (children == null)
        {
            this.children = new TreeState[0];
        }
        else
        {
            Argument.assertElementNotNull(children, "children");
            this.children = children;
        }

        if (state == null)
        {
            this.state = new EventChannelState[0];
        }
        else
        {
            Argument.assertElementNotNull(state, "state");
            this.state = (EventChannelState[]) state.clone();
        }
    }

    /**
     * Returns the name the tree for which this is the state.
     * @return the name the tree for which this is the state.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the child tree state for this tree state.
     * @return the child tree state for this tree state.
     */
    public TreeState[] getChildren()
    {
        return (TreeState[]) children.clone();
    }

    /**
     * Returns the state for the specified event channel or null if the
     * event channel is not found in this tree state.
     *
     * @param ecd the event channel whose state is to be returned.
     *
     * @return the state for the specified event channel or null if the
     * event channel is not found in this tree state.
     */
    public EventChannelState[] getState()
    {
        return (EventChannelState[]) state.clone();
    }

	/**
	 * Returns true if the specified object is a TreeState and is 
	 * equivalent to this one.
	 * @return  true if the specified object is a TreeState and is 
	 * equivalent to this one.
	 */
    public boolean equals(Object object)
    {
        if (!(object instanceof TreeState))
        {
            return false;
        }

        TreeState ts = (TreeState) object;

        return ts.name.equals(this.name) &&
        	ArrayUtil.unorderedEquals(ts.state, this.state) 
        	&& ArrayUtil.unorderedEquals(ts.children, this.children);
    }
    
    /**
     * Returns a hash code for this TreeState.
     * @return a hash code for this TreeState.
     */
    public int hashCode(){
    	return name.hashCode();
    }
    
    /**
     * Creates a string representation of this treestate.
     */
    public String toString(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("TreeState[");
    	sb.append("name = ").append(this.name);
    	sb.append(", state = {");
    	for(int i = 0; i < this.state.length; i++){
    		if (i != 0){
    			sb.append(", ");
    		}
    		sb.append(state[i]);
    	}
    	sb.append("}, children = {");
    	
    	for(int i = 0; i < this.children.length; i++){
    		if (i != 0){
    			sb.append(", ");
    		}
    		sb.append(children[i]);
    	}
    	sb.append("}]");
    	return sb.toString();
    }
}
