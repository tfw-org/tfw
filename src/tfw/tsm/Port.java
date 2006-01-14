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

import tfw.value.ValueConstraint;

/**
 * The base class for sinks and sources.
 */
abstract class Port
{
    private final EventChannelDescription ecd;

    private final String name;

    private EventChannel eventChannel = null;

    private TreeComponent component = null;

    /**
     * Construct a port with the specified attributes.
     * 
     * @param name
     *            the name of the port.
     * @param eventChannelName
     *            the name of the event channel.
     * @param constraint
     *            the value constraint for the port.
     */
    Port(String name, EventChannelDescription ecd)
    {
        this.name = name;
        this.ecd = ecd;
    }

    EventChannelDescription getECD()
    {
        return ecd;
    }

    /**
     * Returns this ports name.
     * 
     * @return this ports name.
     */
    String getFullyQualifiedName()
    {
        StringBuffer sb = new StringBuffer();

        if (this.component != null)
        {
            sb.append(this.component.getFullyQualifiedName());
            sb.append("[").append(name).append("]");
        }
        else
        {
            sb.append(name);
        }

        return sb.toString();
    }

    /**
     * Connects this <code>Port</code> to the specified event channel.
     * 
     * @param eventChannel
     *            the event channel for this <code>Port</code>.
     */
    void setEventChannel(EventChannel eventChannel)
    {
        this.eventChannel = eventChannel;
    }

    /**
     * Returns the event channel for this port. The value may be null.
     * 
     * @return the event channel for this port.
     */
    EventChannel getEventChannel()
    {
        if (eventChannel == null)
        {
            throw new IllegalStateException(name
                    + " is not connected to event channel '"
                    + ecd.getEventChannelName() + "'");
        }

        return eventChannel;
    }

    /**
     * Returns the name of the event channel for this port.
     * 
     * @return the name of the event channel for this port.
     */
    String getEventChannelName()
    {
        return ecd.getEventChannelName();
    }

    /**
     * Returns the value constraint for this port.
     * 
     * @return the value constraint for this port.
     */
    ValueConstraint getConstraint()
    {
        return ecd.getConstraint();
    }

    /**
     * Sets this ports associated tree component.
     * 
     * @param component
     *            the component to which this port is bound.
     */
    void setTreeComponent(TreeComponent component)
    {
        if (this.component != null)
        {
            throw new IllegalStateException(
                    "Illegal attempt to re-assign component");
        }

        this.component = component;
    }

    /**
     * Returns the component to which this port is bound.
     * 
     * @return the component to which this port is bound.
     */
    TreeComponent getTreeComponent()
    {
        if (this.component == null)
        {
            throw new IllegalStateException("Port '" + name
                    + "' for event channel '" + ecd.getEventChannelName()
                    + "' has not been connected to a component");
        }

        return this.component;
    }
    
    /**
     * Get the name of this port.
     * @return the port name.
     */
    String getPortName(){
        return this.name;
    }
}
