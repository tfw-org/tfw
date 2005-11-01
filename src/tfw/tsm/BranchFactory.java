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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A factory for creating an {@link Branch}.
 */
public class BranchFactory extends BaseBranchFactory
{
    /** The set of translators. */
    private final HashMap translators = new HashMap();

    EventChannel[] getTerminators()
    {
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(super.getTerminators()));
        list.addAll(translators.values());

        return (EventChannel[]) list.toArray(new EventChannel[list.size()]);
    }

    private Sink[] getSinks()
    {
        HashSet ports = new HashSet();

        // add all translator ports to the ports hash set.
        Iterator itr = translators.values().iterator();

        while (itr.hasNext())
        {
            Translator translator = (Translator) itr.next();
            ports.add(translator.getParentSink());
        }

        return (Sink[]) ports.toArray(new Sink[ports.size()]);
    }

    private Source[] getSources()
    {
        HashSet ports = new HashSet();

        // add all translator ports to the ports hash set.
        Iterator itr = translators.values().iterator();

        while (itr.hasNext())
        {
            Translator translator = (Translator) itr.next();
            ports.add(translator.getParentSource());
        }

        return (Source[]) ports.toArray(new Source[ports.size()]);
    }

    /**
     * Adds translator between the specified parent and child event channels.
     * 
     * @param childEventChannel
     *            the child event channel.
     * @param parentEventChannel
     *            the parent event channel.
     */
    public void addTranslation(EventChannelDescription childEventChannel,
            EventChannelDescription parentEventChannel)
    {
        Argument.assertNotNull(childEventChannel, "childEventChannel");
        Argument.assertNotNull(parentEventChannel, "parentEventChannel");

        if (isTerminated(childEventChannel))
        {
            throw new IllegalStateException(
                    "Attempt to translate the event channel '"
                            + childEventChannel.getEventChannelName()
                            + "' which is already terminated.");
        }

        if (isTranslated(childEventChannel))
        {
            throw new IllegalStateException(
                    "Attempt to translate the event channel '"
                            + childEventChannel.getEventChannelName()
                            + "' which is already translated.");
        }

        if (!childEventChannel.getConstraint().isCompatable(
                parentEventChannel.getConstraint()))
        {
            throw new IllegalArgumentException(
                    "Incompatible event channels, values from the parent event channel '"
                            + parentEventChannel.getEventChannelName()
                            + "' are not assignable to the child event channel '"
                            + childEventChannel.getEventChannelName() + "'");
        }
        if (!parentEventChannel.getConstraint().isCompatable(
                childEventChannel.getConstraint()))
        {
            throw new IllegalArgumentException(
                    "Incompatible event channels values from the child event channel '"
                            + childEventChannel.getEventChannelName()
                            + "' are not assignable to the parent event channel '"
                            + parentEventChannel.getEventChannelName() + "'");
        }

        try
        {
            translators.put(childEventChannel.getEventChannelName(),
                    new Translator(childEventChannel, parentEventChannel));
        }
        catch (ValueException unexpected)
        {
            throw new RuntimeException(
                    "Unexpected error creating a translator: "
                            + unexpected.getMessage(), unexpected);
        }
    }

    /**
     * Adds a terminator for the specified event channel. A terminator is the
     * point within the component tree structure where an event channel stops.
     * Events from sources or publishers move up through the component hierarchy
     * until they reach the terminator. If the event represents a state change
     * as defined by the {@link StateChangeRule} the terminator reflects the
     * event back down through the component tree structure to the sinks or
     * subscribers.
     * 
     * @param eventChannelDescription
     *            The event channel to be terminated.
     * @param initialState
     *            The initial value to be assigned to the event channel.
     * @param rule
     *            The state change rule for the event channel.
     */
    public void addEventChannel(
            EventChannelDescription eventChannelDescription,
            Object initialState, StateChangeRule rule) throws ValueException
    {
        if ((eventChannelDescription != null)
                && isTranslated(eventChannelDescription))
        {
            throw new IllegalStateException(
                    "Attemp to terminate an event channel, '"
                            + eventChannelDescription.getEventChannelName()
                            + "', which is already transalated.");
        }

        super.addEventChannel(eventChannelDescription, initialState, rule);
    }

    private boolean isTranslated(EventChannelDescription ecd)
    {
        return translators.containsKey(ecd.getEventChannelName());
    }

    /**
     * Removes all previously specified translators and terminators.
     */
    public void clear()
    {
        this.translators.clear();
        super.clear();
    }

    /**
     * Creates a {@link Branch} with the previously specified translators and
     * terminators. Note that this method calls {@link #clear()}.
     * 
     * @param name
     *            The name of the branch.
     * @return A branch with the previously specified translators and
     *         terminators.
     */
    public Branch create(String name)
    {
        Branch branch = new Branch(name, getSinks(), getSources(),
                getTerminators());
        this.clear();
        return branch;
    }
}
