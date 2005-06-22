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
import tfw.tsm.ecd.StatelessTriggerECD;


/**
 * The base class for all components which participate in the commit phase
 * of a transaction based on the firing of a state-less event channel firing.
 */
public abstract class TriggeredCommit extends BaseCommit
{
    /**
     * Creates a commit component with the specified values.
     * @param name the name of the commit component.
     * @param trigger The state-less event channel which triggers this 
     * component to participate in the commit phase of the transaction.
     */
    public TriggeredCommit(String name, StatelessTriggerECD trigger)
    {
        this(name, trigger, null, null);
    }

    /**
     * Creates a commit component with the specified values.
     *
     * @param name the name of the component.
     *
     * @param trigger The state-less event channel which triggers this 
     * component to participate in the commit phase of the transaction.
     *
     * @param nonTriggerSinks the set of event channel sinks whose state the
     * commit component needs, but whose state changes do not cause the
     * component to call it's {@link #commit()} method.
     *
     * @param initiators the set of {@link Initiator}s whose state changes
     * this commit component will ignore. For example if an initiator sources
     * events on event channel 'A' and 'A' is one of the event channels
     * specified in the <code>triggerSinks</code> list, the {@link #commit()}
     * method will not be called as a result of the initiator changing 'A's
     * state.
     *
     * @throws IllegalArgumentException if <code>triggerSinks == null</code>.
     * @throws IllegalArgumentException if <code>triggerSinks.length == 0</code>.
     * @throws IllegalArgumentException if <code>triggerSinks[i] == null</code>.
     */
    public TriggeredCommit(String name, StatelessTriggerECD trigger,
        EventChannelDescription[] nonTriggerSinks, Initiator[] initiators)
    {
        super(name, checkTrigger(trigger), nonTriggerSinks, initiators);
    }

    private static EventChannelDescription[] checkTrigger(StatelessTriggerECD trigger)
    {
        Argument.assertNotNull(trigger, "trigger");

        return new EventChannelDescription[]{ trigger };
    }
}
