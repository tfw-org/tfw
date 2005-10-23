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

import java.util.HashMap;

/**
 * The base class for branch component factories.
 */
class BaseBranchFactory
{
    private final HashMap terminators = new HashMap();

    /**
     * Creates a factory.
     */
    public BaseBranchFactory()
    {
    }

    /**
     * Returns the current set of Event Channel terminators.
     * 
     * @return the current set of Event Channel terminators.
     */
    EventChannel[] getTerminators()
    {
        return (EventChannel[]) terminators.values().toArray(
                new EventChannel[terminators.size()]);
    }

    //
    // private void checkOverLap(HashMap terms, HashMap trans, HashMap multi)
    // {
    // HashSet set = new HashSet();
    // set.addAll(terms.keySet());
    // set.addAll(trans.keySet());
    // set.addAll(multi.keySet());
    //
    // if (set.size() != (terms.size() + trans.size() + multi.size()))
    // {
    // throw new IllegalArgumentException(
    // "Terminators, Translators and multiplexers overlap!");
    // }
    // }

    /**
     * Adds an event channel terminator based on the specified description
     * 
     * @param eventChannelDescription
     *            a description of the event channel.
     */
    public void addEventChannel(EventChannelDescription eventChannelDescription)
    {
        addEventChannel(eventChannelDescription, null, AlwaysChangeRule.RULE);
    }

    /**
     * Adds an event channel terminator based on the specified description and
     * initial state. The {@link DotEqualsRule} is used.
     * 
     * @param eventChannelDescription
     *            a description of the event channel.
     * @param initialState
     *            the initial state for the event channel.
     * @throws ValueException
     *             if the <code>initialState</code> value is incompatible with
     *             the event channel.
     */
    public void addEventChannel(
            EventChannelDescription eventChannelDescription, Object initialState)
            throws ValueException
    {
        addEventChannel(eventChannelDescription, initialState,
                DotEqualsRule.RULE);
    }

    /**
     * Adds an event channel terminator based on the specified description
     * 
     * @param eventChannelDescription
     *            a description of the event channel.
     * @param initialState
     *            the initial state for the event channel.
     * @throws ValueException
     *             if the <code>initialState</code> value is incompatible with
     *             the event channel.
     */
    public void addEventChannel(
            EventChannelDescription eventChannelDescription,
            Object initialState, StateChangeRule rule) throws ValueException
    {
        Argument.assertNotNull(eventChannelDescription,
                "eventChannelDescription");
        Argument.assertNotNull(rule, "rule");

        if (isTerminated(eventChannelDescription))
        {
            throw new IllegalArgumentException("The event channel '"
                    + eventChannelDescription.getEventChannelName()
                    + "' is already exists");
        }

        terminators.put(eventChannelDescription.getEventChannelName(),
                new Terminator(eventChannelDescription, initialState, rule));
    }

    boolean isTerminated(EventChannelDescription ecd)
    {
        return terminators.containsKey(ecd.getEventChannelName());
    }

    /**
     * Clears all previously set terminators.
     */
    public void clear()
    {
        terminators.clear();
    }

    /**
     * Adds the specified export tag to the specified event channel. The event
     * channel must be added to the branch before this method can be called.
     * 
     * @param ecd
     *            The description of the event channel to be tagged.
     * @param tag
     *            The tag to be added to the event channel
     */
    public void addExportTag(EventChannelDescription ecd, String tag)
    {
        Argument.assertNotNull(ecd, "ecd");
        Argument.assertNotNull(tag, "tag");
        Argument.assertNotEmpty(tag, "tag");
        if (!isTerminated(ecd))
        {
            throw new IllegalArgumentException("The event channel '"
                    + ecd.getEventChannelName()
                    + "' has not been added to this factory and "
                    + "therefore can not be tagged.");
        }

        Terminator t = (Terminator) this.terminators.get(ecd
                .getEventChannelName());
        t.addExportStateTag(tag);
    }
}
