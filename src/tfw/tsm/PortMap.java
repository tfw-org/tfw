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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import tfw.tsm.ecd.EventChannelDescription;


/**
 *
 */
class PortMap
{
    public static final PortMap EMPTY = new PortMap("empty", new Port[0]);
    private final Map map;

    public PortMap(String name, Port[] ports)
    {
        Map temp = new HashMap(ports.length);

        for (int i = 0; i < ports.length; i++)
        {
            if (temp.put(ports[i].getECD(), ports[i]) != null)
            {
                throw new IllegalArgumentException("Multiple " + name +
                    " detected for event channel '" +
                    ports[i].getEventChannelName() + "'");
            }
        }

        this.map = Collections.unmodifiableMap(temp);
    }

    public Port get(EventChannelDescription ecd)
    {
        return (Port) map.get(ecd);
    }

    public boolean containsKey(EventChannelDescription ecd)
    {
        return map.containsKey(ecd);
    }

    public EventChannelDescription[] getKeys()
    {
        return (EventChannelDescription[]) map.keySet().toArray(new EventChannelDescription[map.size()]);
    }

    public Set keySet()
    {
        return map.keySet();
    }
    
    public Collection values(){
    	return map.values();
    }
    
    public int size(){
    	return map.size();
    }
}
