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
import java.util.List;
import java.util.Set;

import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.value.ValueException;

/**
 * This class provides an interface to generate a transaction and to set the
 * state of event channels in that transaction. The initiator will queue state
 * changes if it is not rooted. The queueing strategy can be set by providing
 * the appropriate {@link StateQueueFactory} on the constructor
 * {@link #Initiator(String, EventChannelDescription[], StateQueueFactory)}. The default
 * queuing strategy employs an unbounded queue which will store up state changes
 * until the initiator becomes rooted and can begin to fire its state changes.
 * Note that a component is said to be rooted when it is attached to a root
 * component or its parent is rooted.
 * <P>
 * State Change Rules:
 * <ol>
 * <li>If one of the 'set' or 'trigger' methods is called while the
 * <tt>Initiator</tt> is rooted, the state changes will be immediately
 * scheduled with the transaction manager.</li>
 * <li>If one of the 'set' or 'trigger' methods is called while the
 * <tt>Initiator</tt> is not rooted the state changes will be queued,
 * according to the queuing strategy supplied at construction, and deferred
 * until such time as the <tt>Initiator</tt> becomes rooted.</li>
 * <li>An <tt>Initiator</tt> will schedule all of its deferred state changes
 * with the transaction manager immediately upon becoming rooted. </li>
 * <li>If the <tt>Initiator</tt> becomes un-rooted after state changes have
 * been scheduled with the transaction manager and before the state changes have
 * occurred, the state changes will occur if and only if the associated event
 * channel is rooted when the state change transaction is executed. </li>
 * </ol>
 */
public class Initiator extends Leaf
{
    /**
     * The list of state changes which occur while the component is not
     * connected.
     */
    private List deferredStateChanges = null;

    /**
     * Constructs an <code>Initiator</code> with the specified name and source
     * event channel.
     * 
     * @param name
     *            the non-null name for the <code>Initiator</code>
     * @param source
     *            the source event channel for this initiator.
     */
    public Initiator(String name, EventChannelDescription source)
    {
        this(name, new EventChannelDescription[] { source });
    }

    /**
     * Constructs an <code>Initiator</code> with the specified name and set of
     * source event channels.
     * 
     * @param name
     *            the non-null name for the <code>Initiator</code>
     * @param sourceEventChannels
     *            A non-null, non-empty array of source event channels for the
     *            <code>Initiator</code>.
     */
    public Initiator(String name, EventChannelDescription[] sources)
    {
        this(name, sources, new DefaultStateQueueFactory());
    }

    /**
     * Constructs an <code>Initiator</code> with the specified name, set of
     * source event channels, and state queue.
     * 
     * @param name
     *            the non-null name for the <code>Initiator</code>
     * @param sourceEventChannels
     *            A non-null, non-empty array of source event channels for the
     *            <code>Initiator</code>.
     * @param queueFactory
     *            a factory for creating state queue for each source event
     *            channel.
     */
    public Initiator(String name, EventChannelDescription[] sources,
            StateQueueFactory queueFactory)
    {
        super(name, null, createSources(name, sources, queueFactory));

        Argument.assertNotNull(name, "name");

        if (sources.length == 0)
        {
            throw new IllegalArgumentException(
                    "sources.length == 0 not allowed");
        }
    }

    synchronized void fireDeferredState()
    {
        synchronized (this)
        {
            if (this.deferredStateChanges != null)
            {
                for (int i = 0; i < deferredStateChanges.size(); i++)
                {
                    SourceNState[] sns = (SourceNState[]) deferredStateChanges
                            .get(i);
                    InitiatorSource[] iSource = setState(sns);
                    this.getTransactionManager().addStateChange(iSource);
                }

                this.deferredStateChanges = null;
            }
        }
    }

    private InitiatorSource[] setState(SourceNState[] sns)
    {
        InitiatorSource[] is = new InitiatorSource[sns.length];
        for (int i = 0; i < sns.length; i++)
        {
            sns[i].source.setState(sns[i].state);
            is[i] = sns[i].source;
        }
        return is;
    }

    private static Source[] createSources(String name,
            EventChannelDescription[] sources, StateQueueFactory factory)
    {
        Argument.assertNotNull(sources, "sources");
        Argument.assertElementNotNull(sources, "sources");
        Argument.assertNotNull(factory, "factory");

        Source[] srcs = new Source[sources.length];

        for (int i = 0; i < sources.length; i++)
        {
            srcs[i] = new InitiatorSource(name, sources[i], factory.create());
        }

        return srcs;
    }

    // /**
    // * Sets the event channel to the specified state value in a new
    // * transaction.
    // * @param channel a port discription of source on which to set the state.
    // * @param state the state for the event channel.
    // */
    // public final void set(EventChannelDescription channel, final Object
    // state)
    // {
    // CheckArgument.checkNull(channel, "channel");
    // set(channel.getEventChannelName(), state);
    // }
    private void newTransaction(InitiatorSource[] sources, Object[] state)
    {
        if (isRooted())
        {
            for (int i = 0; i < sources.length; i++)
            {
                sources[i].setState(state[i]);
            }
            getTransactionManager().addStateChange(sources);

            return;
        }
        SourceNState[] sns = new SourceNState[sources.length];
        for (int i = 0; i < sources.length; i++)
        {
            sns[i] = new SourceNState(sources[i], state[i]);
        }

        if (this.deferredStateChanges == null)
        {
            this.deferredStateChanges = new ArrayList();
        }

        this.deferredStateChanges.add(sns);
    }

    /**
     * Sets, asynchronously, the state of the specified event channel to the
     * specified state. Note that the <code>sourceEventChannel</code> will be
     * updated in transaction manager event queue thread regardless of what
     * thread calls this method.
     * 
     * @param sourceEventChannel
     *            The event channel to be updated. It must be one of the event
     *            channels specified at construction.
     * 
     * @param state
     *            the new state for the event channel.
     */
    public synchronized final void set(
            EventChannelDescription sourceEventChannel, final Object state)
    {

        Argument.assertNotNull(sourceEventChannel, "sourceEventChannel");
        // Trigger have null values...
        // CheckArgument.checkNull(state, "state");

        final InitiatorSource source = (InitiatorSource) getSource(sourceEventChannel
                .getEventChannelName());

        if (source == null)
        {
            throw new IllegalArgumentException(sourceEventChannel
                    + " not found");
        }

        try
        {
            source.getConstraint().checkValue(state);
        }
        catch (ValueException ve)
        {
            throw new IllegalArgumentException(ve.getMessage());
        }
        newTransaction(new InitiatorSource[] { source }, new Object[] { state });
    }

    /**
     * Sets the state of the eventChannels with values specfied.
     * 
     * @param state
     *            the event channel state to set.
     */
    public synchronized final void set(EventChannelState[] state)
    {
        Argument.assertNotEmpty(state, "state");

        InitiatorSource[] sources = new InitiatorSource[state.length];
        Object[] stateObjects = new Object[state.length];

        for (int i = 0; i < state.length; i++)
        {
            String eventChannel = state[i].getECD().getEventChannelName();
            InitiatorSource source = (InitiatorSource) getSource(eventChannel);

            if (source == null)
            {
                throw new IllegalArgumentException(eventChannel + " not found");
            }

            sources[i] = source;
            stateObjects[i] = state[i].getState();
        }

        newTransaction(sources, stateObjects);
    }

    /**
     * Sets, asynchronously, the state of the specified event channel to
     * <code>new Object()</code>. This is intended to be used to activate
     * {@link TriggeredConverter}s where the state changes is not relevant.
     * 
     * @param sourceEventChannel
     *            The event channel to receive the trigger event.
     */
    public synchronized final void trigger(
            StatelessTriggerECD triggerEventChannel)
    {
        set(triggerEventChannel, null);
    }

    private class SourceNState
    {
        private final InitiatorSource source;

        private final Object state;

        public SourceNState(InitiatorSource source, Object state)
        {
            this.source = source;
            this.state = state;
        }
    }
}
