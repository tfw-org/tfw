/*
 * Created on Dec 17, 2005
 *
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
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;

/**
 * The event channel for sub-channels under a multiplexer.
 */
class DemultiplexedEventChannel extends Terminator
{
    /** The parent multiplexer. */
    private final Multiplexer parent;

    /**
     * The source for pushing events from above the multiplexer down into the
     * sub-channel.
     */
    private final ProcessorSource deMultiSource;

    /** The sub-channel index of this event channel. */
    final Integer demultiplexIndex;

    /** The number of ports connected to this event channel. */
    private int connectionCount = 0;

    /**
     * Creates an event channel for a specific sub-channel.
     * 
     * @param parent
     *            The parent multiplexer.
     * @param demultiplexIndex
     *            The sub-channel index of this event channel.
     * @param stateChangeRule
     *            The state change rule for this event channel.
     */
    DemultiplexedEventChannel(Multiplexer parent, Integer demultiplexIndex,
            StateChangeRule stateChangeRule)
    {
        super(parent.valueECD, null, stateChangeRule);
        Argument.assertNotNull(parent, "parent");
        this.parent = parent;
        this.demultiplexIndex = demultiplexIndex;
        this.deMultiSource = new ProcessorSource(parent.processorMultiSource
                .getPortName()
                + "[" + demultiplexIndex + "]", parent.valueECD);
        this.deMultiSource.setTreeComponent(parent.getTreeComponent());
        super.add(this.deMultiSource);
    }

    /**
     * Overrides the Terminator method in order to propogate the state up
     * through the multiplexer.
     */
    public void setState(Source source, Object state,
            EventChannel forwardingEventChannel)
    {
        super.setState(source, state, forwardingEventChannel);
        parent.processorMultiSource.setState(this);
    }

    /**
     * Overrides the Terminator method inorder to count connections.
     */
    public void add(Port port)
    {
        super.add(port);
        connectionCount++;
    }

    /**
     * Overrides the Terminator method inorder to count connections and remove
     * this demultiplexer event channel when connections reach zero.
     */
    public void remove(Port port)
    {
        super.remove(port);
        connectionCount--;

        // if no ports are connected...
        if (connectionCount == 0)
        {
            // remove this event channel so it can be garbage collected.
            parent.remove(this);
        }
    }

    /**
     * Called by MultiSink to push state into the demultiplexed sub-channel.
     * 
     * @param state
     *            The state value for this event channel.
     */
    void setDeMultiState(Object state)
    {
        if (state == null)
        {
            throw new IllegalStateException(
                    "Demultiplexing error, attempt to set state to null in sub-channel '"
                            + this.demultiplexIndex
                            + "' of multiplexed channel '"
                            + parent.multiSink.getEventChannelName() + "'");
        }
        this.deMultiSource.setState(state);
    }
}
