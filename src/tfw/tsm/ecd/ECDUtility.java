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
package tfw.tsm.ecd;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Utilities for manipulating event channel descriptions.
 */
public class ECDUtility
{
    /**
     * Creates a new array with the specified description added.
     * @param port the port to be added.
     * @param ports the ports to add too.
     * @return the appended array.
     */
    public static EventChannelDescription[] append(
        EventChannelDescription eventChannelDescription,
        EventChannelDescription[] eventChannelDescriptions)
    {
        if (eventChannelDescription == null)
        {
            return eventChannelDescriptions;
        }

        if (eventChannelDescriptions == null)
        {
            return new EventChannelDescription[]{ eventChannelDescription };
        }

        ArrayList list = new ArrayList(Arrays.asList(eventChannelDescriptions));
        list.add(eventChannelDescription);

        return (EventChannelDescription[]) list.toArray(new EventChannelDescription[list.size()]);
    }

    /**
     * Creates a new array with the specified descriptions added.
     * @param ports1 the ports to be added too.
     * @param ports2 the ports to add too.
     * @return the concatenated array.
     */
    public static EventChannelDescription[] concat(
        EventChannelDescription[] eventChannelDescriptions1,
        EventChannelDescription[] eventChannelDescriptions2)
    {
        if (eventChannelDescriptions1 == null)
        {
            return eventChannelDescriptions2;
        }

        if (eventChannelDescriptions2 == null)
        {
            return eventChannelDescriptions1;
        }

        ArrayList list = new ArrayList(
        	Arrays.asList(eventChannelDescriptions1));
        list.addAll(Arrays.asList(eventChannelDescriptions2));

        return (EventChannelDescription[]) list.toArray(
        	new EventChannelDescription[list.size()]);
    }

	/**
	 * Converts an array of event channel descriptions into an array of 
	 * event channel names
	 * @param eventChannelDescriptions The set of event channel descriptions
	 * to convert.
	 * @return an array of the event channel names derived from the specified
	 * set of descriptions. 
	 */
    public static String[] toEventChannelNames(EventChannelDescription[] eventChannelDescriptions)
    {
        if (eventChannelDescriptions == null)
        {
            return new String[0];
        }

        String[] names = new String[eventChannelDescriptions.length];

        for (int i = 0; i < eventChannelDescriptions.length; i++)
        {
            names[i] = eventChannelDescriptions[i].getEventChannelName();
        }

        return names;
    }
}
