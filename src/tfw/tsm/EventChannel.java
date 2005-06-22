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

import tfw.tsm.ecd.EventChannelDescription;

/**
 * An interface which defines the services an event channel
 * must provide.
 */
interface EventChannel
{
	/**
	 * Returns the non_name of this event channel
	 * @return the non-null name for this event channel.
	 */
	public EventChannelDescription getECD();

	/**
	 * Sets the component associated with this event channel.
	 * @param component the component associated with this event channel.
	 */
	public void setTreeComponent(TreeComponent component);
	
	/**
	 * Connects a {@link Sink} to the event channel.
	 * @param sink the sink to be connected.
	 */
	public void add(Port port);

	/**
	 * Disconnects a {@link Sink} from this event channel.
	 * @param sink the sink to be disconnected.
	 */
	public void remove(Port port);
	
	/**
	 * Returns the current state of the event channel.
	 */
	public Object getState();

	/**
	 * Returns the state of this event channel prior
	 * to the current state change cycle.
	 */
	public Object getPreviousCycleState();

	/**
	 * Returns the state of this event channel prior
	 * to the current state change cycle.
	 */
	public Object getPreviousTransactionState();

	/**
	 * Sets the state of this event channel.
	 * @param state the new value.
	 */
	public void setState(Source source, Object state);

	/**
	 * Returns the Source which set the current state.
	 */
	public Source getCurrentStateSource();

	/**
	 * Sets the previous cycle state to the current state.
	 */
	public void synchronizeCycleState();

	/**
	 * Sets the previous transaction state to the current state.
	 */
	public void synchronizeTransactionState();

	/**
	 * Nofies any uninitialized sinks of the current state.
	 */
	public Object fire();

	/**
	 * Returns true if the <code>EventChannel</code> fires on connect.
	 */
	public boolean isFireOnConnect();

	/**
	 * Returns true if the <code>EventChannel</code> participates in rollbacks.
	 * An <code>EventChannel</code> that participates in rollbacks is one that
	 * guarentees that its state will be set back to it's value prior to the
	 * beginning of the transaction in which a rollback occurs.
	 */
	public boolean isRollbackParticipant();
	
	/**
	 * Adds an asynchronous state change. This method is generally called by
	 * an {@link Source} to notify the event channel that it has a state change
	 * 
	 * @param source the source with the state change.
	 */
	public void addDeferredStateChange(ProcessorSource source);
	
	public void addDeferredStateChange(InitiatorSource[] source);
}
