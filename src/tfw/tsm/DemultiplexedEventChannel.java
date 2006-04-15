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
import tfw.tsm.ecd.EventChannelDescription;
import tfw.value.ValueException;

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

    /** The sub-channel identifier of this event channel. */
    final Object demultiplexSlotId;

    /** The number of ports connected to this event channel. */
    private int connectionCount = 0;

    /**
     * Creates an event channel for a specific sub-channel.
     * 
     * @param parent
     *            The parent multiplexer.
     * @param demultiplexSlotId
     *            The sub-channel indentifier of this event channel.
     * @param stateChangeRule
     *            The state change rule for this event channel.
     */
    DemultiplexedEventChannel(Multiplexer parent, Object demultiplexSlotId,
			Object initialState, StateChangeRule stateChangeRule)
    {
		super(parent.valueECD, initialState, stateChangeRule);
        Argument.assertNotNull(parent, "parent");
        this.parent = parent;
        this.demultiplexSlotId = demultiplexSlotId;
		this.deMultiSource = new DemultiSource(parent.processorMultiSource
                .getPortName()
                + "[" + demultiplexSlotId + "]", parent.valueECD);
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
		if (this.component != source.getTreeComponent())
		{
            parent.processorMultiSource.setState(this);
        }
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
                            + this.demultiplexSlotId
                            + "' of multiplexed channel '"
                            + parent.multiSink.getEventChannelName() + "'");
        }
        this.deMultiSource.setState(state);
    }

	private class DemultiSource extends ProcessorSource
	{
		private int fireCount = 0;

		DemultiSource(String name, EventChannelDescription ecd)
		{
			super(name, ecd);
}
		Object getState()
		{
			Object state = super.getState();
			return state;
		}

		void setState(Object state) throws ValueException
		{
			Argument.assertNotNull(state, "state");
			if (this.getState() != null)
			{
				// (new IllegalStateException("Attempt to overwrite state "
				// +this)).printStackTrace();
			}
			this.fireCount = 0;
			super.setState(state);
		}

		Object fire()
		{
			this.fireCount++;
			if (getState() == null)
			{
				// (new IllegalStateException(
				// "Attempt to fire null state(fireCount == "
				// + fireCount + ") on event channel: "
				// + this.getEventChannelName()+" - "
				// + this)).printStackTrace();
				return null;
			}
			return super.fire();
		}
	}
}
