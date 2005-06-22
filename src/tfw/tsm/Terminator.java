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
import tfw.tsm.ecd.EventChannelDescription;

import tfw.value.ValueException;

import java.util.HashSet;
import java.util.Iterator;

/**
 * A terminating event channel.
 */
class Terminator implements EventChannel, CommitRollbackListener {
	private final EventChannelDescription ecd;

	/** The subscribing sinks for this event channel. */
	private final HashSet sinks = new HashSet();

	/** The current state value for the event channel. */
	private Object state = null;

	/** The event channel state prior to the current state change cycle. */
	private Object previousState = null;

	/** The Source which set the current state value. */
	private Source stateSource = null;

	/** The state prior to the current transaction. */
	private Object rollbackState = null;

	/** The Source which set the final state in the previous transaction. */
	private Source rollbackSource = null;

	/** Any sinks which haven't been initialized. */
	private HashSet uninitializedSinks = null;

	/** Flag indicating whether the terminator state is locked. */
	private boolean terminatorLocked = false;

	/** The branch associated with this terminator. */
	protected TreeComponent component = null;

	private final StateChangeRule stateChangeRule;

	/**
	 * Create an event channel termainator.
	 * 
	 * @param ecd
	 *            the description of the event channel.
	 * @param initialState
	 *            the initial state of the event channel
	 * @param stateChangeRule
	 *            the rule for determining whether to notify sinks of a state
	 *            change.
	 * @throws ValueException
	 *             if the specified <code>initialState</code> does not comply
	 *             with the specified <code>ecd.getConstraint()</code>.
	 */
	Terminator(EventChannelDescription ecd, Object initialState,
			StateChangeRule stateChangeRule) throws ValueException {
		this.ecd = ecd;
		this.state = initialState;
		this.previousState = initialState;
		// TODO Make sure that rollback's on initialization cause an Error.
		//this.rollbackState = initialState;

		if (initialState != null) {
			ecd.getConstraint().checkValue(initialState);
		}

		this.stateChangeRule = stateChangeRule;
	}

	// TODO: this method was added to investigate indirect dependencies.
	// It should be removed if the investigation does not pan out.
	Sink[] getSinks() {
		return (Sink[]) sinks.toArray(new Sink[sinks.size()]);
	}

	private void updateSinks(HashSet set) {
		Iterator itr = set.iterator();

		while (itr.hasNext()) {
			Sink sink = (Sink) itr.next();

			if (sink.isTriggering()) {
				sink.stateChange();
			}
		}
	}

	public void commit() {
	}

	public void rollback() {
		previousState = rollbackState;
		state = rollbackState;
		stateSource = rollbackSource;
	}

	/**
	 * Returns the description of this event channel.
	 * 
	 * @return the description of this event channel.
	 */
	public EventChannelDescription getECD() {
		return ecd;
	}

	/**
	 * Sets this terminators branch.
	 * 
	 * @param branch
	 *            the branch for this terminator
	 */
	public void setTreeComponent(TreeComponent branch) {
		if (this.component != null) {
			throw new IllegalStateException(
					"Terminator is already initialized!");
		}

		this.component = branch;
	}

	/**
	 * Returns the current branch associated with this terminator.
	 * 
	 * @return the current branch associated with this terminator.
	 */
	public TreeComponent getTreeComponent() {
		if (component == null) {
			throw new IllegalStateException("Branch has not been initialized.");
		}

		return component;
	}

	public synchronized void add(Port port) {
		Argument.assertNotNull(port, "port");

		if (port instanceof Sink) {
			addSink((Sink) port);
		} else {
			addSource((Source) port);
		}
	}

	public synchronized void remove(Port port) {
		if (port instanceof Sink) {
			removeSink((Sink) port);
		} else {
			removeSource((Source) port);
		}
	}

	void addSource(Source source) {
		if (!ecd.getConstraint().isCompatable(source.getConstraint())) {
			throw new TerminatorException("The source '"
					+ source.getFullyQualifiedName() + ", with '"
					+ source.getConstraint()
					+ "' is not compatible with the event channel '"
					+ ecd.getEventChannelName() + "' with '"
					+ ecd.getConstraint() + "'");
		}

		source.setEventChannel(this);
	}

	void removeSource(Source source) {
		source.setEventChannel(null);
	}

	/**
	 * Connects a {@link Sink}to the event channel.
	 * 
	 * @param sink
	 *            the sink to be connected.
	 */
	private void addSink(Sink sink) {
		Argument.assertNotNull(sink, "sink");

		if (!sink.getConstraint().isCompatable(ecd.getConstraint())) {
			throw new TerminatorException("The sink '"
					+ sink.getFullyQualifiedName() + ", with '"
					+ sink.getConstraint()
					+ "' is not compatible with the event channel '"
					+ ecd.getEventChannelName() + "' with '"
					+ ecd.getConstraint() + "'");
		}

		sinks.add(sink);
		sink.setEventChannel(this);

		if (ecd.isFireOnConnect() && (state != null)) {
			if (uninitializedSinks == null) {
				uninitializedSinks = new HashSet();
			}

			uninitializedSinks.add(sink);
			component.getTransactionManager().addEventChannelFire(this);
		}
	}

	/**
	 * Disconnects a {@link Sink}from this event channel.
	 * 
	 * @param sink
	 *            the sink to be disconnected.
	 */
	private void removeSink(Sink sink) {
		sink.setEventChannel(null);
		sinks.remove(sink);

		if (uninitializedSinks != null) {
			uninitializedSinks.remove(sink);

			if (uninitializedSinks.size() == 0) {
				uninitializedSinks = null;
			}
		}
	}

	/**
	 * Returns the current state of the event channel.
	 */
	public Object getState() {
		return state;
	}

	/**
	 * Returns the state of this event channel prior to the current state change
	 * cycle.
	 */
	public Object getPreviousCycleState() {
		return previousState;
	}

	/**
	 * Returns the state of this event channel prior to the current transaction.
	 */
	public Object getPreviousTransactionState() {
		return rollbackState;
	}

	/**
	 * Sets the state of this event channel.
	 * 
	 * @param state
	 *            the new value.
	 * 
	 * TODO Add error handler for value compliance errors.
	 */
	public void setState(Source source, Object state) {
		if (ecd.isRollBackParticipant()) {
			component.getTransactionManager().addCommitRollbackListener(this);
		}

		if (this.state != previousState) {
			throw new IllegalStateException(
					"Attempt to change the state of event channel '"
							+ getECD().getEventChannelName()
							+ "' twice in the same state change cycle is not allowed."
							+ "The first state change source is "
							+ stateSource.getTreeComponent().getName()
							+ " and the state value is " + previousState
							+ ". The second attempt was made by "
							+ source.getTreeComponent().getName()
							+ " and the new state value is " + state);
		}

		if (stateChangeRule.isChange(this.state, state)) {
			stateSource = source;
			this.state = state;
			updateSinks(sinks);
		}
	}

	/**
	 * Returns the Source which set the current state.
	 * 
	 * @return the {@link Source}that set the current state, <code>
	 * null</code>
	 *         if the state has not been set by a <code>Source
	 * </code>.
	 */
	public Source getCurrentStateSource() {
		return stateSource;
	}

	/**
	 * Sets the previous cycle state to the current state.
	 */
	public void synchronizeCycleState() {
		previousState = state;
	}

	/**
	 * Sets the previous transaction state to the current state.
	 */
	public void synchronizeTransactionState() {
		rollbackState = state;
		rollbackSource = stateSource;
		terminatorLocked = false;
	}

	private HashSet resetUninitializedSinks() {
		HashSet temp = null;

		synchronized (this) {
			if (uninitializedSinks == null) {
				temp = new HashSet();
			} else {
				temp = uninitializedSinks;
				uninitializedSinks = null;
			}
		}

		return temp;
	}

	public Object fire() {
		updateSinks(sinks);
		return state;
	}

	public boolean isFireOnConnect() {
		return ecd.isFireOnConnect();
	}

	public boolean isRollbackParticipant() {
		return ecd.isRollBackParticipant();
	}

	public void addDeferredStateChange(ProcessorSource source) {
		this.component.getTransactionManager().addStateChange(source);
	}

	public void addDeferredStateChange(InitiatorSource[] source) {
		this.component.getTransactionManager().addStateChange(source);
	}

	/**
	 * Fires and then clears uninitialized sinks. This method should only be
	 * called by {@link TransactionMgr}.
	 */
	void initializeSinks() {
		if (uninitializedSinks != null) {
			updateSinks(resetUninitializedSinks());
		}
	}

	void importState(Object state) {
		if (state == null) {
			return;
		}

		terminatorLocked = true;
		previousState = state;
		this.state = state;
		uninitializedSinks = sinks;
		component.getTransactionManager().addEventChannelFire(this);

		if (ecd.isRollBackParticipant()) {
			component.getTransactionManager().addCommitRollbackListener(this);
		}
	}
}
