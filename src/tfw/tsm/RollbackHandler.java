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
import tfw.tsm.ecd.RollbackECD;

/**
 * The base class for all event handling components which participate in
 * transaction rollbacks.
 */
abstract class RollbackHandler extends EventHandler
{
    /**
     * Creates a rollback event handler with the specified attributes.
     * 
     * @param name
     *            the name of the component.
     * @param sinkDescriptions
     *            the descriptions of the sinks.
     * @param sourceDescriptions
     *            the descriptions of the sources.
     */
    RollbackHandler(String name, EventChannelDescription[] triggeringSinks,
            EventChannelDescription[] nonTriggeringSinks,
            EventChannelDescription[] sourceDescriptions)
    {
        super(name, triggeringSinks, nonTriggeringSinks, sourceDescriptions);
    }

    /**
     * Causes a rollback of the current transaction. All event channels which
     * participate in rollbacks will be set back to their state prior to the
     * initiation of the current transaction.
     */
    protected final void rollback()
    {
        throw new RollbackException();
    }

    /**
     * Causes a rollback of the current transaction and sets the state of the
     * specified event channel in a follow-on transaction.
     * 
     * @param ecd
     *            The event channel on which the state is to be set.
     * @param state
     *            The state for the event channel.
     */
    protected final void rollback(RollbackECD ecd, Object state)
    {
        Argument.assertNotNull(ecd, "ecd");
        Argument.assertNotNull(state, "state");
        initiatorSet(ecd, state);
        rollback();
    }

    /**
     * Causes a rollback of the current transaction and sets the state of the
     * specified event channels in a follow-on transaction.
     * 
     * @param eventChannelState
     *            The event channels and state to be set in a follow-on
     *            transaction.
     */
    protected final void rollback(EventChannelState[] eventChannelState)
    {
        Argument.assertNotNull(eventChannelState, "eventChannelState");
        Argument.assertElementNotNull(eventChannelState, "eventChannelState");
        initiatorSet(eventChannelState);
        rollback();
    }
}
