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

import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.value.ValueConstraint;
import tfw.value.ValueException;


/**
 * Translates between a set of child and parent event channels. This class
 * acts as an event channel for the child sources and sinks. It appears to be
 * a single source and sink for the parent event channel.
 */
class Translator extends Terminator
{
    /**
     * The sink for capturing parent event channel events to be relayed
     * down to child event channel sinks.
     */
    private final Sink parentRelaySink;

    /**
     * The source for relaying child event channel events up to the parent
     * event channel.
     */
    private final Source parentRelaySource;

    /** A source for pushing parent event channel state down to child sinks. */
    private final Source childRelaySource;

    /**
     * Creates a translator with the specified attributes.
     * @param childPort a description of the child ports to be converted into
     * the parent event channel.
     * @param parentPort a description of the parent ports to be converted
     * into the child event channel.
     *
     * @throws IllegalArgumentException if the child and parent port
     * description value constraints are not two way compatible.
     */
    public Translator(EventChannelDescription childPort, EventChannelDescription parentPort)
        throws ValueException
    {
        super(childPort, null, AlwaysChangeRule.RULE);
        Argument.assertNotNull(childPort, "childPort");
        Argument.assertNotNull(parentPort, "parentPort");

        ValueConstraint pvc = parentPort.getConstraint();
        ValueConstraint cvc = childPort.getConstraint();

        if (!cvc.isCompatible(pvc))
        {
            throw new IllegalArgumentException(
                "The parent value constraint is not compatable with the child value constraint");
        }

        if (!pvc.isCompatible(cvc))
        {
            throw new IllegalArgumentException(
                "The child value constraint is not compatable with the parent value constraint");
        }

        this.parentRelaySink = new ParentSink(parentPort, this);
        this.parentRelaySource = new ProcessorSource("ParentSource",
                parentPort);
        this.childRelaySource = new ProcessorSource("ChildRelaySource",
                childPort);
        childRelaySource.setEventChannel(this);
    }

    /**
     * Returns the translator's parent sink.
     * @return the translator's parent sink.
     */
    Sink getParentSink()
    {
        return this.parentRelaySink;
    }

    /**
     * Returns the translator's parent source.
     * @return the translator's parent source.
     */
    Source getParentSource()
    {
        return this.parentRelaySource;
    }

    /**
     * Over-rides Terminator method to handl propagation up to
     * the parent event channel.
     *
     * @see co2.ui.fw.EventChannel#setState(Source, Object)
     */
    public void setState(Source source, Object state, EventChannel forwardingEventChannel)
    {
        // TODO figure out how to handle the forwarding event channel...
        // Propagate the state change down...
        super.setState(source, state, forwardingEventChannel);

        // if this translator is not responsible for the state change...
        if ((source != parentRelaySource) && (source != childRelaySource))
        {
            // Propagate the state change up...
			try{
				this.parentRelaySource.setState(state);
			} catch (ValueException ve){
				throw new IllegalArgumentException(ve.getMessage());
			}
        }
    }

    public void setTreeComponent(TreeComponent component){
    	this.childRelaySource.setTreeComponent(component);
    	super.setTreeComponent(component);
    }
    /**
     * A class to use as the parent event channel sink relay for moving state
     * changes from the parent event channel, down to the child event channel
     *  sinks.
     */
    private static class ParentSink extends Sink
    {
        private final Translator translator;

        ParentSink(EventChannelDescription pd, Translator translator)
        {
            super("ParentSink", pd, true);
            this.translator = translator;
        }

        void stateChange()
        {
            if (getEventChannel().getCurrentStateSource() != translator.parentRelaySource)
            {
				try{
					translator.childRelaySource.setState(this.getEventChannel()
																	 .getState());
				} catch (ValueException ve){
					throw new IllegalArgumentException(ve.getMessage());
				}
            }
        }
    }
}
