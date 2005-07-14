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
import tfw.tsm.ecd.ObjectIlaECD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A factory for creating a {@link MultiplexedBranch}.
 */
public class MultiplexedBranchFactory
{
    /** A map of multi value event channel discriptions. */
    HashMap multiValueECDMap = new HashMap();

    /** A map of value event channel discriptions. */
    HashMap valueECDMap = new HashMap();

    /** A list of multi value event channel descriptions. */
    ArrayList multiValueECDList = new ArrayList();

    /** A list of value event channel descriptions. */
    ArrayList valueECDList = new ArrayList();

    /**
     * Addes a multiplexer for the specified event channels.
     *
     * @param valueECD the event channel description of the child values.
     * @param multiValueECD the event channel description of the parent
     * multiplexed values.
     */
    public void addMultiplexer(EventChannelDescription valueECD,
        ObjectIlaECD multiValueECD)
    {
        Argument.assertNotNull(valueECD, "valueECD");
        Argument.assertNotNull(multiValueECD, "multiValueECD");

        if (multiValueECDMap.put(multiValueECD.getEventChannelName(),
                multiValueECD) != null)
        {
            throw new IllegalArgumentException(
                "Attempt to add multiple multiplexers for multi event channel '" +
                multiValueECD.getEventChannelName() + "'");
        }

        if (valueECDMap.put(valueECD.getEventChannelName(), multiValueECD) != null)
        {
            throw new IllegalArgumentException(
                "Attempt to add multiple multiplexers for value event channel '" +
                valueECD.getEventChannelName() + "'");
        }

        valueECDList.add(valueECD);
        multiValueECDList.add(multiValueECD);
    }

    private static Multiplexer[] generateMulitplexers(List valueECDList,
        List multiValueECDList)
    {
        ArrayList list = new ArrayList();

        for (int i = 0; i < valueECDList.size(); i++)
        {
            list.add(new Multiplexer(
                    (EventChannelDescription) valueECDList.get(i),
                    (ObjectIlaECD) multiValueECDList.get(i)));
        }

        return (Multiplexer[]) list.toArray(new Multiplexer[list.size()]);
    }

    /**
     * Creates a multiplexed branch with the set of multiplexed event channels
     * as previously defined by calls to
     * {@link #addMultiplexer(EventChannelDescription, ObjectIlaECD)}.
     * @param name the name of the branch.
     * @return the multiplexed branch.
     */
    public MultiplexedBranch create(String name)
    {
        Argument.assertNotNull(name, "name");

        if (multiValueECDMap.size() == 0)
        {
            throw new IllegalStateException("Attempt to create a " +
                MultiplexedBranch.class.getName() +
                " with no multiplexed channels. At least one multiplexer " +
                "must be added by calling the addMultiplexer() method.");
        }

        return new MultiplexedBranch(name,
            generateMulitplexers(valueECDList, multiValueECDList));
    }

    /**
     * Clears any previously added multiplexers.
     */
    public void clear()
    {
        valueECDMap.clear();
        multiValueECDMap.clear();
        valueECDList.clear();
        multiValueECDList.clear();
    }
}