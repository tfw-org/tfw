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
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.value.ValueException;


/**
 * This class provides an interface to generate a transaction and to set
 * the state of event channels in that transaction. The initiator will queue
 * state changes if it is not rooted. The queueing strategy can be set
 * by providing the appropriate {@link StateQueueFactory} on the constructor
 * {@link #Initiator(String, EventChannelDescription[], StateQueueFactory)}.
 */
public class Initiator extends Leaf
{
    /** The list of state changes which occur while the component is not connected. */
    private List deferredStateChanges = null;

    /**
     * Constructs an <code>Initiator</code> with the specified name and
     * source event channel.
     * @param name the non-null name for the <code>Initiator</code>
     * @param source the source event channel for this initiator.
     */
    public Initiator(String name, EventChannelDescription source)
    {
        this(name, new EventChannelDescription[]{ source });
    }

    /**
     * Constructs an <code>Initiator</code> with the specified name and
     * set of source event channels.
     * @param name the non-null name for the <code>Initiator</code>
     * @param sourceEventChannels A non-null, non-empty array of source
     * event channels for the <code>Initiator</code>.
     */
    public Initiator(String name, EventChannelDescription[] sources)
    {
        this(name, sources, new DefaultStateQueueFactory());
    }

    /**
     * Constructs an <code>Initiator</code> with the specified name,
     * set of source event channels, and state queue.
     * @param name the non-null name for the <code>Initiator</code>
     * @param sourceEventChannels A non-null, non-empty array of source
     * event channels for the <code>Initiator</code>.
     * @param queueFactory a factory for creating state queue for each
     * source event channel.
     */
    public Initiator(String name, EventChannelDescription[] sources,
        StateQueueFactory queueFactory)
    {
        super("Initiator[" + name + "]", null,
            createSources(name, sources, queueFactory));
        Argument.assertNotNull(name, "name");

        if (sources.length == 0)
        {
            throw new IllegalArgumentException(
                "sources.length == 0 not allowed");
        }
    }

    void terminateParentAndLocalConnections(Set connections)
    {
        super.terminateParentAndLocalConnections(connections);
        fireDeferredState();
    }

    void fireDeferredState()
    {
        synchronized (this)
        {
            if (this.deferredStateChanges != null)
            {
                for (int i = 0; i < deferredStateChanges.size(); i++)
                {
                    this.getTransactionManager().addStateChange((InitiatorSource[]) deferredStateChanges.get(
                            i));
                }

                this.deferredStateChanges = null;
            }
        }
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
            srcs[i] = new InitiatorSource(name,
                    sources[i], factory.create());
        }

        return srcs;
    }

    //    /**
    //     * Sets the event channel to the specified state value in a new 
    //     * transaction.
    //     * @param channel a port discription of source on which to set the state.
    //     * @param state the state for the event channel.
    //     */
    //    public final void set(EventChannelDescription channel, final Object state)
    //    {
    //        CheckArgument.checkNull(channel, "channel");
    //        set(channel.getEventChannelName(), state);
    //    }
    private void newTransaction(InitiatorSource[] sources)
    {
        if (isRooted())
        {
            getTransactionManager().addStateChange(sources);

            return;
        }

        synchronized (this)
        {
            if (this.deferredStateChanges == null)
            {
                this.deferredStateChanges = new ArrayList();
            }

            this.deferredStateChanges.add(sources);
        }
    }

    /**
     * Sets, asynchronously, the state of the specified event channel to
     * the specified state. Note that the <code>sourceEventChannel</code>
     * will be updated in transaction manager event queue thread regardless
     * of what thread calls this method.
     *
     * @param sourceEventChannel The event channel to be updated. It must
     * be one of the event channels specified at construction.
     *
     * @param state the new state for the event channel.
     */
    public final void set(EventChannelDescription sourceEventChannel,
        final Object state)
    {
        Argument.assertNotNull(sourceEventChannel, "sourceEventChannel");
        // Trigger have null values...
        //CheckArgument.checkNull(state, "state");

        final InitiatorSource source = (InitiatorSource) getSource(sourceEventChannel.getEventChannelName());

        if (source == null)
        {
            throw new IllegalArgumentException(sourceEventChannel +
                " not found");
        }

        try{
        	source.setState(state);
        } catch (ValueException ve){
        	throw new IllegalArgumentException(ve.getMessage());
        }
        newTransaction(new InitiatorSource[]{ source });
    }

    /**
     * Sets the state of the eventChannels with values specfied in the map.
     * The event channel name is the key in the map and the map value is
     * the event channel state.
     * @param state the map of the state.
     */
    public final void set(EventChannelState[] state)
    {
        Argument.assertNotEmpty(state, "state");

        final InitiatorSource[] sources = new InitiatorSource[state.length];
        int index = 0;

        for (int i = 0; i < state.length; i++)
        {
            String eventChannel = state[i].getECD().getEventChannelName();
            InitiatorSource source = (InitiatorSource) getSource(eventChannel);

            if (source == null)
            {
                throw new IllegalArgumentException(eventChannel + " not found");
            }
            
			try{
				source.setState(state[i].getState());
			} catch (ValueException ve){
				throw new IllegalArgumentException(ve.getMessage());
			}

            sources[index++] = source;
        }

        newTransaction(sources);
    }

    /**
     * Sets, asynchronously, the state of the specified event channel to
     * <code>new Object()</code>. This is intended to be used to activate
     * {@link TriggeredConverter}s where the state changes is not relevant.
     * @param sourceEventChannel The event channel to receive the trigger
     * event.
     */
    public final void trigger(StatelessTriggerECD triggerEventChannel)
    {
        set(triggerEventChannel, null);
    }
}
