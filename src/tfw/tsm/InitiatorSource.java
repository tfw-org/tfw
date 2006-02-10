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
import tfw.value.ValueException;

/**
 * 
 */
class InitiatorSource extends Source
{
    private final StateQueue stateQueue;

    InitiatorSource(String name, EventChannelDescription ecd,
            StateQueue stateQueue)
    {
        super(name, ecd);
        this.stateQueue = stateQueue;
    }

    /**
     * Connects this <code>Source</code> to the specified event channel. If
     * the deferred state is set, the <code>eventChannel</code> will be set
     * the deferred state asynchronously. This method should only be called
     * during the branch construction phase of a transaction.
     * 
     * @param eventChannel
     *            the event channel for this <code>Source</code>.
     */
    public synchronized void setEventChannel(EventChannel eventChannel)
    {
        /*
         * TODO: This needs to be re-thought. It was put here to make sure the
         * deferred state of an initiator gets fired after it has been removed.
         */
        if (eventChannel == null)
        {
            return;
        }
        super.setEventChannel(eventChannel);

        if (this.getTreeComponent() instanceof Initiator)
        {
            ((Initiator) this.getTreeComponent()).fireDeferredState();
        }
    }

    /**
     * This method asynchronously causes the event channel state to be changed
     * to the specified state value. If this <code>Source</code> is not
     * connected to an event channel it will cause a state change to the event
     * channel when it is connected.
     * 
     * @param state
     *            the new event channel value.
     */
    synchronized void setState(Object state) throws ValueException
    {
        getConstraint().checkValue(state);
        this.stateQueue.push(state);
    }

    /**
     * Sets the state of the event channel. This method should only be called by
     * {@link TransactionMgr}.
     */
    synchronized Object fire()
    {
        Object state = null;
        if ((getEventChannel() != null) && (!stateQueue.isEmpty()))
        {
            state = stateQueue.pop();
            getEventChannel().setState(this, state, null);
        }
        return state;
    }
}
