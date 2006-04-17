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
import java.util.Iterator;
import java.util.List;

import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.RollbackECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.value.ValueException;

/**
 * The base class for event handling leaf components.
 */
abstract class EventHandler extends Leaf
{
    // private final List initiators;

    /**
     * Creates an event handler with the specified attributes.
     * 
     * @param name
     *            the name of the event handler.
     * @param sinkDescriptions
     *            the set of sink event channels.
     * @param sourceEventChannels
     *            the set of source event channels.
     * @throws IllegalArgumentException
     *             if there are no sources or sinks specified.
     */
    EventHandler(String name, EventChannelDescription[] triggeringSinks,
            EventChannelDescription[] nonTriggeringSinks,
            EventChannelDescription[] sources)
    {
        super(name, createSinks(name, triggeringSinks, nonTriggeringSinks),
                createSources(name, sources));

        int portCount = (sources == null) ? 0 : sources.length;

        portCount += ((nonTriggeringSinks == null) ? 0
                : nonTriggeringSinks.length);
        portCount += ((triggeringSinks == null) ? 0 : triggeringSinks.length);

        if (portCount == 0)
        {
            throw new IllegalArgumentException(
                    "(nonTriggeringSinks.length + nonTriggeringSinks.length"
                            + " + triggeringSinks.length)"
                            + " == 0 not allowed");
        }

        Iterator sinks = getSinks().values().iterator();

        while (sinks.hasNext())
        {
            ((EventHandlerSink) sinks.next()).setHandler(this);
        }
    }

    private static Sink[] createSinks(String name,
            EventChannelDescription[] triggeringSinks,
            EventChannelDescription[] nonTriggeringSinks)
    {
        ArrayList list = new ArrayList();

        if (nonTriggeringSinks != null)
        {
            Argument.assertElementNotNull(nonTriggeringSinks,
                    "nonTriggeringSinks");

            for (int i = 0; i < nonTriggeringSinks.length; i++)
            {
                list.add(new EventHandlerSink(name, nonTriggeringSinks[i],
                        false));
            }
        }

        if (triggeringSinks != null)
        {
            Argument.assertElementNotNull(triggeringSinks, "triggeringSinks");

            for (int i = 0; i < triggeringSinks.length; i++)
            {
                list.add(new EventHandlerSink(name, triggeringSinks[i], true));
            }
        }

        if (list.size() > 0)
        {
            return (Sink[]) list.toArray(new Sink[list.size()]);
        }
        else
        {
            return null;
        }
    }

    private static Source[] createSources(String name,
            EventChannelDescription[] sources)
    {
        List list = new ArrayList();

        if (sources != null)
        {
            Argument.assertElementNotNull(sources, "sources");

            StateQueueFactory factory = new DefaultStateQueueFactory();

            for (int i = 0; i < sources.length; i++)
            {
                if (sources[i] instanceof RollbackECD)
                {
                    list.add(new InitiatorSource(name, sources[i], factory
                            .create()));
                }
                else
                {
                    list.add(new ProcessorSource(name, sources[i]));
                }
            }
        }

        return (Source[]) list.toArray(new Source[list.size()]);
    }

    static void assertNotStateless(EventChannelDescription ecd)
    {
        if (ecd instanceof StatelessTriggerECD)
        {
            throw new IllegalArgumentException("The event channel '"
                    + ecd.getEventChannelName()
                    + "' is stateless, no state available.");
        }
    }

    /**
     * Returns the state of the specified event channel at the conclusion of
     * transaction prior to the current transaction.
     * 
     * @param sinkEventChannel
     *            the sink event channel whose state is to be returned.
     * @return The stat of the event channel prior to the current transaction.
     */
    protected final Object getPreviousTransactionState(
            EventChannelDescription sinkEventChannel)
    {
        Argument.assertNotNull(sinkEventChannel, "sinkEventChannel");
        assertNotStateless(sinkEventChannel);
        Sink sink = getSink(sinkEventChannel);

        if (sink == null)
        {
            throw new IllegalArgumentException(sinkEventChannel
                    .getEventChannelName()
                    + " not found");
        }

        if (sink.getEventChannel() == null)
        {
            throw new IllegalStateException(sinkEventChannel
                    + " is not connected to an event channel");
        }

        return (sink.getEventChannel().getPreviousTransactionState());
    }

    /**
     * Returns the current state for the specified event channel.
     * 
     * @param sinkEventChannel
     *            the event channel whose state is to be returned.
     * @return the current state for the specified event channel.
     */
    protected final Object get(EventChannelDescription sinkEventChannel)
    {
        Argument.assertNotNull(sinkEventChannel, "sinkEventChannel");
        assertNotStateless(sinkEventChannel);

        Sink sink = getSink(sinkEventChannel);

        if (sink == null)
        {
            throw new IllegalArgumentException(
                    "The component does not subscribe to the requested event channel, '"
                            + sinkEventChannel.getEventChannelName() + ".");
        }

        if (sink.getEventChannel() == null)
        {
            throw new IllegalStateException("The requested sink, '"
                    + sinkEventChannel.getEventChannelName()
                    + "', is not connected to an event channel");
        }

        return (sink.getEventChannel().getState());
    }

    /**
     * Returns the state of all of the sink event channels for this component.
     * 
     * @return the state of all of the sink event channels for this component.
     */
    protected final StateMap get()
    {
        PortMap map = getSinks();
        StateMap stateMap = new StateMap(map.size());

        for (Iterator itr = map.keySet().iterator(); itr.hasNext();)
        {
            EventChannelDescription sinkEventChannel = (EventChannelDescription) itr
                    .next();

            if (sinkEventChannel instanceof ObjectECD)
            {
                stateMap.put((ObjectECD) sinkEventChannel,
                        get(sinkEventChannel));
            }
        }

        return stateMap;
    }

    /**
     * Set the state of the specified event channel in a new transaction.
     * 
     * @param initiateECD
     *            the event channel.
     * @param state
     *            the new state for the event channel.
     */
    final void initiatorSet(RollbackECD initiateECD, Object state)
    {
        Argument.assertNotNull(initiateECD, "initiateECD");
        Argument.assertNotNull(state, "state");

        InitiatorSource source = (InitiatorSource) getSource(initiateECD
                .getEventChannelName());

        if (source == null)
        {
            throw new IllegalArgumentException(
                    "Attempt to set state on unknown event channel '"
                            + initiateECD.getEventChannelName() + "'.");
        }
        try
        {
            source.setState(state);
        }
        catch (ValueException ve)
        {
            throw new IllegalArgumentException(ve.getMessage());
        }

        newTransaction(new InitiatorSource[] { source });
    }

    final void initiatorSet(EventChannelState[] eventChannelState)
    {
        Argument.assertNotNull(eventChannelState, "eventChannelState");
        Argument.assertElementNotNull(eventChannelState, "eventChannelState");

        InitiatorSource[] sources = new InitiatorSource[eventChannelState.length];

        for (int i = 0; i < eventChannelState.length; i++)
        {
            String ecName = eventChannelState[i].getEventChannelName();
            sources[i] = (InitiatorSource) getSource(ecName);

            if (sources[i] == null)
            {
                throw new IllegalArgumentException("eventChannelState[" + i
                        + "].getECD().getEventChannelName() == " + ecName
                        + " is not a known event channel");
            }

            try
            {
                sources[i].setState(eventChannelState[i].getState());
            }
            catch (ValueException ve)
            {
                throw new IllegalArgumentException(ve.getMessage());
            }
        }

        newTransaction(sources);
    }

    private void newTransaction(InitiatorSource[] sources)
    {
        if (!isRooted())
        {
            throw new IllegalStateException(
                    "This component is not connected to a root");
        }

        getTransactionManager().addStateChange(sources);
    }

    /**
     * Called when a sink event channels state changes. Must be implemented by
     * sub-classes to receive state change notification.
     * 
     * @param eventChannel
     *            the event channel who's sate has changed.
     */
    abstract void stateChange(EventChannel eventChannel);

    private static class EventHandlerSink extends Sink
    {
        private EventHandler handler = null;

        EventHandlerSink(String name, EventChannelDescription description,
                boolean isTriggering)
        {
            super(name, description, isTriggering);
        }

        void stateChange()
        {
            Source source = handler.getSource(getEventChannelName());

            if ((source != null) && source.isStateSource())
            {
                return;
            }

            handler.stateChange(EventHandlerSink.this.getEventChannel());
        }

        void setHandler(EventHandler handler)
        {
            this.handler = handler;
        }
    }
}
