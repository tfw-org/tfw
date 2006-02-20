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

import java.util.Map;
import java.util.Set;

import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.value.ValueException;

/**
 * The base class for all event handling components which participate in the
 * processing phase of transactions.
 */
abstract class Processor extends RollbackHandler
{
    private final EventChannelDescription[] triggeringSinks;

    Processor(String name, EventChannelDescription[] triggeringSinks,
            EventChannelDescription[] nonTriggeringSinks,
            EventChannelDescription[] sources)
    {
        super(name, triggeringSinks, nonTriggeringSinks, sources);
        this.triggeringSinks = (EventChannelDescription[]) triggeringSinks
                .clone();
    }

    void stateChange(EventChannel eventChannel)
    {
        getTransactionManager().addProcessor(this);
    }

    /**
     * Returns true if the specified processor publishes to one or more event
     * channels for which this processor has triggering sinks.
     * 
     * @param processor
     *            The processor to check for dependency.
     * @return true if the specified processor publishes to one or more event
     *         channels for which this processor has triggering sinks.
     */
    boolean isDependent(Processor processor)
    {
        Map sources = processor.getSources();
        for (int i = 0; i < this.triggeringSinks.length; i++)
        {
            if (sources.containsKey(triggeringSinks[i].getEventChannelName()))
            {
                return true;
            }
        }
        return false;
    }

    boolean isDependent(Set sources)
    {
        for (int i = 0; i < triggeringSinks.length; i++)
        {
            if (sources.contains(triggeringSinks[i].getEventChannelName()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the state of the specified event channel prior to the current
     * state change cycle.
     * 
     * @param sinkEventChannel
     *            the sink event channel whose state is to be returned.
     * @return the state of the event channel during the previous state change
     *         cycle.
     */
    protected final Object getPreviousCycleState(
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

        return (sink.getEventChannel().getPreviousCycleState());
    }

    /**
     * Asynchronously changes the state of the specified event channel.
     * 
     * @param sourceEventChannel
     *            the name of the event channel.
     * @param state
     *            the new value for the event channel.
     * @throws IllegalArgumentException
     *             if the specified state violates the value constraints of the
     *             event channel.
     */
    protected final void set(EventChannelDescription sourceEventChannel,
            Object state)
    {
        Source source = getSource(sourceEventChannel.getEventChannelName());

        if (source == null)
        {
            throw new IllegalArgumentException("Source event channel '"
                    + sourceEventChannel + "' not found");
        }

        try
        {
            source.setState(state);
        }
        catch (ValueException ve)
        {
            throw new IllegalArgumentException(ve.getMessage());
        }
    }

    /**
     * Causes a state-less event channel to fire.
     * 
     * @param triggerECD
     *            the event channel to be fired.
     */
    protected final void trigger(StatelessTriggerECD triggerECD)
    {
        set(triggerECD, null);
    }

    /**
     * This method is called in any transaction in which one or more of the
     * sinks specified at construction receives new state.
     */
    abstract void process();
}
