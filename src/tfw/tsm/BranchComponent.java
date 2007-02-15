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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import tfw.check.Argument;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.value.ValueException;

public abstract class BranchComponent extends TreeComponent
{
    public static final String DEFAULT_EXPORT_TAG = "All";

    /** This component's children */
    private Map children = null;
    private ArrayList deferredAddRemoveSets = null;

	protected Set immediateChildren = null;
	
	public abstract void remove(TreeComponent treeComponent);
	
	BranchComponent(String name, Sink[] sinks, Source[] sources,
		EventChannel[] eventChannels)
	{
    	super(name, sinks, sources, eventChannels);
	}
	
    void addToChildren(TreeComponent child)
    {
        if (child.getParent() != null)
        {
            throw new IllegalArgumentException("Child, '" + child.getName()
                    + "', already has a parent.");
        }

        if (child == this)
        {
            throw new IllegalArgumentException("child == this not allowed");
        }

        if (child.isRooted())
        {
            throw new IllegalArgumentException("Child, '" + child.getName()
                    + "' is rooted. Can't add a rooted tree!");
        }

        if (children == null)
        {
            this.children = new HashMap();
        }
        if (children.containsKey(child.getName()))
        {
            throw new IllegalArgumentException(
                    "Attempt to add child with duplicate name, '"
                            + child.getName() + "'");
        }
        children.put(child.getName(), child);
        child.setParent(this);
    }

    void removeFromChildren(TreeComponent child)
    {
        if (child.getParent() != this)
        {
            throw new IllegalArgumentException("child, " + child.getName()
					+ ", not connected to this component, '"+
					this.getFullyQualifiedName()+"'");
        }
        children.remove(child.getName());
        child.setParent(null);
    }

    /**
     * Returns an copy of this component's map of children.
     * 
     * @return an copy of this component's map of children.
     */
    Map getChildren()
    {
        if (children == null)
        {
            return new HashMap();
        }

        synchronized (children)
        {
            return new HashMap(children);
        }
    }

    /**
     * Recursively terminates child component ports and terminates leaf ports.
     * The initial call to this method to kick off the recursion is made by the
     * transaction manager.
     * 
     * @return Un-terminated ports.
     */
    Set terminateChildAndLocalConnections()
    {
        HashSet unterminatedConnections = new HashSet();
        TreeComponent[] treeComponents = getChildComponents();
        for (int i = 0; i < treeComponents.length; i++)
        {
        	if (treeComponents[i] instanceof BranchComponent)
        	{
        		unterminatedConnections.addAll(
        			((BranchComponent)treeComponents[i])
                    .terminateChildAndLocalConnections());
        	}
        }

        return (terminateLocally(unterminatedConnections));
    }

    private TreeComponent[] getChildComponents()
    {
        if (children == null)
        {
            return new TreeComponent[0];
        }
        synchronized (children)
        {
            return ((TreeComponent[]) children.values().toArray(
                    new TreeComponent[children.size()]));
        }
    }

    /**
     * Calls disconnectPorts() and then recursively calls disconnect on the
     * child components.
     */
    void disconnect()
    {
        disconnectPorts();
        
        TreeComponent[] childComponents = getChildComponents();
        for (int i = 0; i < childComponents.length; i++)
        {
        	if (childComponents[i] instanceof BranchComponent)
        	{
        		((BranchComponent)childComponents[i]).disconnect();
        	}
        }
    }

    /**
     * Returns the this components tree state using the
     * {@link #DEFAULT_EXPORT_TAG}.
     * 
     * @return this components tree state.
     * @throws IllegalStateException
     *             if this component is not rooted.
     * @throws IllegalStateException
     *             if called outside of the of the transaction manager's
     *             transaction queue thread.
     */
    public TreeState getTreeState()
    {
        return getTreeState(DEFAULT_EXPORT_TAG);
    }

    /**
     * Returns the this components tree state using the
     * {@link #DEFAULT_EXPORT_TAG}.
     * 
     * @param exportTag
     *            The event channel export tag. Only event channels with this
     *            export tag will be included in the tree state.
     * @return this components tree state.
     * @throws IllegalStateException
     *             if this component is not rooted.
     * @throws IllegalStateException
     *             if called outside of the of the transaction manager's
     *             transaction queue thread.
     */
    public TreeState getTreeState(String exportTag)
    {
        Argument.assertNotNull(exportTag, "exportTag");
        if (!isRooted())
        {
            throw new IllegalStateException(
                    "This component is not rooted and therefore it's state is undefined.");
        }

        if (!getTransactionManager().isDispatchThread())
        {
            throw new IllegalStateException(
                    "This method can not be called from outside the transaction queue thread");
        }

        TreeStateBuffer buff = new TreeStateBuffer();
        buff.setName(this.getName());

        Iterator itr = this.eventChannels.values().iterator();

        while (itr.hasNext())
        {
            EventChannel ec = (EventChannel) itr.next();

            if (isExport(ec, exportTag))
            {
                try
                {
                    EventChannelState ecs = new EventChannelState(
                            (ObjectECD) ec.getECD(), ec.getState());
                    buff.addState(ecs);
                }
                catch (ValueException unexpected)
                {
                    // This should never happen.
                    throw new IllegalStateException(
                            "Event channel has invalid state: "
                                    + unexpected.getMessage());
                }
            }
        }

        TreeComponent[] childComponents = getChildComponents();
        for (int i = 0; i < childComponents.length; i++)
        {
        	if (childComponents[i] instanceof BranchComponent)
        	{
        		buff.addChild(((BranchComponent)childComponents[i]).getTreeState(exportTag));
        	}
        }

        return buff.toTreeState();
    }

    private static boolean isExport(EventChannel ec, String exportTag)
    {
        if ((ec.getECD() instanceof StatelessTriggerECD)
                || !ec.getECD().isFireOnConnect() || (ec.getState() == null))
        {
            return false;
        }

        if (!(ec instanceof Terminator))
        {
            return false;
        }
        return ((Terminator) ec).isExportTag(exportTag);
    }

    /**
     * Sets the state of this tree.
     * 
     * @param state
     *            The state.
     * @param skipMissingEventChannels
     *            A flag indicating whether to allow missing event channels. If
     *            <code>false</code> an IllegalArgumentException will be
     *            thrown if the <code>state</code> contains state for an event
     *            channel which is not present in the tree structure.
     * @param skipMissingChildBranches
     *            A flag indicating whether to allow missing child branches. if
     *            <code>false</code> an IllegalArgumentException will be
     *            thrown if the <code>state</code> contains child tree states
     *            for a branch which is not present in the tree structure.
     * @throws IllegalStateException
     *             if this component is not rooted.
     * @throws IllegalStateException
     *             if called outside of the of the transaction manager's
     *             transaction queue thread.
     */
    public void setTreeState(TreeState state, boolean skipMissingEventChannels,
            boolean skipMissingChildBranches)
    {
        Argument.assertNotNull(state, "state");

        if (!isRooted())
        {
            throw new IllegalStateException(
                    "This component is not rooted and therefore it's state is undefined.");
        }

        if (!getTransactionManager().isDispatchThread())
        {
            throw new IllegalStateException(
                    "This method can not be called from outside the transaction queue thread");
        }

        if (!this.getName().equals(state.getName()))
        {
            throw new IllegalArgumentException(
                    "TreeState name does not match, expected <" + this.getName()
                            + "> found <" + state.getName() + ">.");
        }

        EventChannelState[] ecs = state.getState();

        for (int i = 0; i < ecs.length; i++)
        {
            Terminator ec = (Terminator) this.eventChannels.get(ecs[i]
                    .getEventChannelName());

            if (ec == null)
            {
                if (skipMissingEventChannels)
                {
                    continue;
                }
                throw new IllegalArgumentException(
                        "TreeState contains state for an unknown event channel '"
								+ ecs[i].getEventChannelName() + "'");
            }

            ec.importState(ecs[i].getState());
        }

        TreeState[] childTreeState = state.getChildren();

        if (children == null)
        {
            if ((childTreeState.length == 0) || skipMissingChildBranches)
            {
                return;
            }
            throw new IllegalArgumentException(
                    "'state' contains child tree state and this component has no children");
        }
        for (int i = 0; i < childTreeState.length; i++)
        {
            TreeComponent child = (TreeComponent) this.children
                    .get(childTreeState[i].getName());

            if (child == null)
            {
                if (skipMissingChildBranches)
                {
                    continue;
                }
                throw new IllegalArgumentException(
                        "TreeState contains unknown child tree state with name '"
                                + childTreeState[i].getName());
            }

            if (child instanceof BranchComponent)
            {
            	((BranchComponent)child).setTreeState(childTreeState[i],
            		skipMissingEventChannels, skipMissingChildBranches);
            }
        }
    }
    
    /**
     * Adds a child component to this branch.
     * 
     * @param child
     *            the child component.
     */
    final synchronized void addChild(final TreeComponent child)
    {
    	Argument.assertNotNull(child, "child");
    	
    	// Create the PARENT -> CHILD relationship.
		if (immediateChildren == null)
		{
			immediateChildren = new HashSet();
		}
		if (!immediateChildren.add(child))
		{
			throw new IllegalStateException(
				child.getFullyQualifiedName() +
				" is already a child of this Branch");
		}
		
		// Create the CHILD -> PARENT relationship.
		if (child.immediateParent == null)
		{
			child.immediateParent = this;
		}
		else
		{
			throw new IllegalStateException(
				child.getFullyQualifiedName() +
				" already has a parent!");
		}
		
		TransactionMgr.AddComponentRunnable addComponentRunnable =
			new TransactionMgr.AddComponentRunnable(this, child);
		
		// Perform deferred add on child & descendents if this node has a Root.
    	if (immediateRoot != null)
    	{
    		synchronized(immediateRoot)
    		{
                immediateRoot.getTransactionManager().addComponent(
                	addComponentRunnable);
                ArrayList allDeferredAddRemoveSets = new ArrayList();
        		
    			addChildAndDescendents(this, child, allDeferredAddRemoveSets);
    			
    			TransactionMgrUtil.postAddRemoveSetsToQueue(
    				(TransactionMgrUtil.AddRemoveSetContainer[])
    				allDeferredAddRemoveSets.toArray(
    				new TransactionMgrUtil.AddRemoveSetContainer[allDeferredAddRemoveSets.size()]),
    				immediateRoot.getTransactionManager());
    		}
    	}
    	else
    	{
    		if (deferredAddRemoveSets == null)
    		{
    			deferredAddRemoveSets = new ArrayList();
    		}
    		deferredAddRemoveSets.add(
    			TransactionMgrUtil.createAddRemoveSetContainer(
    			addComponentRunnable));
    		
    		if (child instanceof Initiator)
            {
    			Initiator.SourceNState[] sourceNState = 
    				((Initiator)child).getDeferredStateChangesAndClear();
    			
    			if (sourceNState != null)
    			{
	    			for (int i=0 ; i < sourceNState.length ; i++)
	    			{
	    				addStateChange(sourceNState[i]);
	    			}
    			}
            }
    	}
    }
    
    private final synchronized void addChildAndDescendents(
    	final TreeComponent parent, final TreeComponent child,
    	ArrayList allDeferredAddRemoveSets)
    {
    	synchronized(child)
    	{
    		if (child.immediateRoot != null)
    		{
    			throw new IllegalStateException(
    				child.getFullyQualifiedName() +
    				" already has a Root!");
    		}
    		child.immediateRoot = immediateRoot;
            
    		if (child instanceof BranchComponent)
    		{
    			BranchComponent ptc = (BranchComponent)child;
    			
    			if (ptc.deferredAddRemoveSets != null)
    			{
    				allDeferredAddRemoveSets.addAll(ptc.deferredAddRemoveSets);
    				ptc.deferredAddRemoveSets = null;
    			}
    			
    			if (ptc.immediateChildren != null)
    			{
	    			for(Iterator i=ptc.immediateChildren.iterator() ; i.hasNext() ; )
	    			{
	    				addChildAndDescendents(child, (TreeComponent)i.next(),
	    					allDeferredAddRemoveSets);
	    			}
    			}
    		}
    		
    		if (child instanceof Initiator)
            {
    			Initiator.SourceNState[] sourceNState = 
    				((Initiator)child).getDeferredStateChangesAndClear();
    			
    			if (sourceNState != null)
    			{
	    			for (int i=0 ; i < sourceNState.length ; i++)
	    			{
						immediateRoot.getTransactionManager().addStateChange(
    							sourceNState[i].sources, sourceNState[i].state);
	    			}
    			}
            }
    		
    	}
    }
    
    synchronized void addStateChange(Initiator.SourceNState sourceNState)
    {
    	Argument.assertNotNull(sourceNState, "sourceNState");
    	
    	if (deferredAddRemoveSets == null)
    	{
    		deferredAddRemoveSets = new ArrayList();
    	}
    	
    	deferredAddRemoveSets.add(
    		TransactionMgrUtil.createAddRemoveSetContainer(sourceNState));
    }
    
    /**
     * Removes a child component.
     * 
     * @param child
     *            the component to be removed.
     */
    final synchronized void removeChild(final TreeComponent child)
    {
    	Argument.assertNotNull(child, "child");
    	
    	// Remove the PARENT -> CHILD relationship.
		if (immediateChildren == null || !immediateChildren.remove(child))
		{
			throw new IllegalStateException(
				child.getFullyQualifiedName() +
				" is not a child of this Branch!");
		}
		if (immediateChildren.size() == 0)
		{
			immediateChildren = null;
		}
		
		// Remove the CHILD -> PARENT relationship.
		if (child.immediateParent == this)
		{
			child.immediateParent = null;
		}
		else
		{
			throw new IllegalStateException(
				child.getFullyQualifiedName() +
				" has " + child.immediateParent + " Parent!");
		}
		
		TransactionMgr.RemoveComponentRunnable removeComponentRunnable =
			new TransactionMgr.RemoveComponentRunnable(this, child);
		
		// Perform deferred remove on child & descendents if this node has a Root.
    	if (immediateRoot != null)
    	{
    		synchronized(immediateRoot)
    		{
                immediateRoot.getTransactionManager().removeComponent(
                	removeComponentRunnable);
        		
    			removeChildAndDescendents(this, child);
    		}
    	}
    	else
    	{
    		if (deferredAddRemoveSets == null)
    		{
    			deferredAddRemoveSets = new ArrayList();
    		}
    		deferredAddRemoveSets.add(
    			TransactionMgrUtil.createAddRemoveSetContainer(
    			removeComponentRunnable));
    	}
    }
    
    private final synchronized void removeChildAndDescendents(
    	final TreeComponent parent, final TreeComponent child)
    {
    	synchronized(child)
    	{
    		if (child instanceof BranchComponent)
    		{
    			BranchComponent ptc = (BranchComponent)child;
    			
	    		if (ptc.immediateChildren != null)
	    		{
	    			for(Iterator i=ptc.immediateChildren.iterator() ; i.hasNext() ; )
	    			{
	    				removeChildAndDescendents(child, (TreeComponent)i.next());
	    			}
	    		}
    		}
    		
    		if (child.immediateRoot != immediateRoot)
    		{
    			throw new IllegalStateException(
    				child.getFullyQualifiedName() +
    				" has " + child.immediateRoot + " Root!");
    		}
    		child.immediateRoot = null;
    	}
    }
}