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
import tfw.tsm.ecd.ObjectECD;


/**
 * The base class for event handlers that take in one set of events and
 * produces another. Sub-classes must override the <code>convert</code>
 * method. <code>Converter</code> is an event processor and participates
 * in the processing phase of transactions.
 */
public abstract class Converter extends Processor
{
    /**
     * Constructs a <code>Converter</code> with the specified
     * inputs and outputs.
     *
     * @param name the name of this converter.
     * @param sinkEventChannels the input event channels.
     * @param sourceEventChannels the output event channels.
     */
    public Converter(String name, ObjectECD[] sinkDescriptions,
        EventChannelDescription[] sourceDescriptions)
    {
        this(name, sinkDescriptions, null, sourceDescriptions);
    }

    /**
     * Constructs a <code>Converter</code> with the specified
     * inputs and outputs.
     *
     * @param name the name of this converter.
     * @param nonTriggeringSinks sinks which do not cause the
     * {@link #convert()} or the {@link #debugConvert()} method
     *  to be called.
     * @param triggeringSinks sinks which cause the {@link #convert()}
     * or the {@link #debugConvert()} method to be called.
     * @param sources output event channels for this converter.
     */
    public Converter(String name, ObjectECD[] triggeringSinks,
        ObjectECD[] nonTriggeringSinks,
        EventChannelDescription[] sources)
    {
        super(name, checkTriggeringSinks(triggeringSinks), nonTriggeringSinks,
            sources);
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

    private static ObjectECD[] checkTriggeringSinks(
        ObjectECD[] triggeringSinks)
    {
        Argument.assertNotNull(triggeringSinks, "triggeringSinks");
        Argument.assertElementNotNull(triggeringSinks, "triggeringSinks");

        return triggeringSinks;
    }

    /**
     * Called when one or more of the <code>sinkEventChannels</code>
     * specified at contruction has it's state changed and all of the
     * <code>sinkEventChannels</code> have non-null state. The call to this
     * method will occur in the processing phase immediately following the
     * the state change phase in which one or more of the
     * <code>sinkEventChannels</code> has it's state changed.
     */
    protected abstract void convert();

    /**
     * Called when one or more of the <code>sinkEventChannels</code>
     * specified at contruction has it's state changed and all of the
     * <code>sinkEventChannels</code> have non-null state. The call to this
     * method will occur in the processing phase immediately following the
     * the state change phase in which one or more of the
     * <code>sinkEventChannels</code> has it's state changed.
     */
    protected void debugConvert()
    {
    }
}
