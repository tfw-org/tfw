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
 * without even the implied warranty of
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
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;


/**
 * The base class for components which participate in transactions base on
 * the firing of a stateless event channel.
 */
public abstract class TriggeredConverter extends Processor
{
    private final EventChannelDescription triggerDescription;

    /**
     * Creates a TriggeredConverter with the specified attributes.
     * @param name the name of the component.
     * @param triggerDescription the description of the trigger event channel.
     * @param sinkDescriptions the set of event channel sink descriptions.
     * @param sourceDescriptions the set of event channel source descriptions.
     */
    public TriggeredConverter(String name, StatelessTriggerECD triggerDescription,
        ObjectECD[] sinkDescriptions,
        EventChannelDescription[] sourceDescriptions)
    {
        super(name, new EventChannelDescription[]{ triggerDescription },
            sinkDescriptions, sourceDescriptions);

        if (triggerDescription == null)
        {
            throw new IllegalArgumentException(
                "triggerEventChannel == null not allowed");
        }

        this.triggerDescription = triggerDescription;
    }

    // Override Processor's stateChanged() method to filter on the
    // triggerEventChannel.
    void stateChange(EventChannel eventChannel)
    {
        if (eventChannel.getECD().getEventChannelName().equals(triggerDescription.getEventChannelName()))
        {
            getTransactionManager().addProcessor(this);
        }
    }

    void process()
    {
        if (isStateNonNull())
        {
        	convert();
        }
        else
        {
        	debugConvert();
        }
    }

    /**
     * This method is called in any transaction in which the trigger
     * event channel is fired and all of the sinks which were specified
     * at construction have non-null state.
     */
    protected abstract void convert();

    /**
     * This method is called in any transaction in which the trigger
     * event channel is fired and one or more of the sinks which were specified
     * at construction have null state.
     */
    protected void debugConvert()
    {
    }
}
