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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectIlaECD;


/**
 *
 */
class Multiplexer implements EventChannel
{
    /** The branch associated with this terminator. */
    private MultiplexedBranch component = null;

    /** The description of the single value event channel. */
    private final EventChannelDescription valueECD;

    /** The description of the multi value event channel. */
    private final ObjectIlaECD multiValueECD;

    /** The multiplexed value sink. */
    final Sink multiSink;

    /** The list of child sinks. */
    private final Set childSinks = new HashSet();

    /** The multiplexed value initiator source. */
    final ProcessorSource processorMultiSource;

    /** The set of demultiplexing event channels. */
    private final Map demultiplexedEventChannels = new HashMap();

    public Multiplexer(EventChannelDescription valueECD,
        ObjectIlaECD multiValueECD)
    {
        this.valueECD = valueECD;
        this.multiValueECD = multiValueECD;

        this.multiSink = new MultiSink(multiValueECD);

        DefaultStateQueueFactory factory = new DefaultStateQueueFactory();

        this.processorMultiSource = new ProcessorSource("Multiplexer Source",
                multiValueECD);
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#getName()
     */
    public EventChannelDescription getECD()
    {
        return valueECD;
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#setTreeComponent(co2.ui.fw.TreeComponent)
     */
    public void setTreeComponent(TreeComponent component)
    {
        this.component = (MultiplexedBranch) component;
    }

    private EventChannel getDemultiplexedEventChannel(int index)
    {
        if (index < 0)
        {
            throw new IllegalArgumentException("index < 0 not allowed");
        }

        Integer integerIndex = new Integer(index);

        if (!demultiplexedEventChannels.containsKey(integerIndex))
        {
            demultiplexedEventChannels.put(integerIndex,
                new DemultiplexedEventChannel(integerIndex));
        }

        return ((EventChannel) demultiplexedEventChannels.get(integerIndex));
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#add(co2.ui.fw.Port)
     */
    public void add(Port port)
    {
        int index = component.getIndex(port.getTreeComponent());

        if (index < 0)
        {
            throw new IllegalArgumentException(
                "'port' is not from a multiplexed component.");
        }

        if (port instanceof Sink)
        {
            childSinks.add(port);
        }

        getDemultiplexedEventChannel(index).add(port);
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#remove(co2.ui.fw.Port)
     */
    public void remove(Port port)
    {
        if (port instanceof Sink)
        {
            childSinks.remove(port);
        }

        port.getEventChannel().remove(port);
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#getState()
     */
    public Object getState()
    {
        return multiSink.getEventChannel().getState();
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#getPreviousCycleState()
     */
    public Object getPreviousCycleState()
    {
        throw new UnsupportedOperationException(
            "Multiplexer does not participate directly in transactions.");
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#getPreviousTransactionState()
     */
    public Object getPreviousTransactionState()
    {
        throw new UnsupportedOperationException(
            "Multiplexer does not participate directly in transactions.");
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#setState(co2.ui.fw.Source, java.lang.Object)
     */
    public void setState(Source source, Object state)
    {
        throw new UnsupportedOperationException(
            "Multiplexer does not participate directly in transactions.");
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#getCurrentStateSource()
     */
    public Source getCurrentStateSource()
    {
        throw new UnsupportedOperationException(
            "Multiplexer does not participate directly in transactions.");
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#synchronizeCycleState()
     */
    public void synchronizeCycleState()
    {
        throw new UnsupportedOperationException(
            "Multiplexer does not participate directly in transactions.");
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#synchronizeTransactionState()
     */
    public void synchronizeTransactionState()
    {
        throw new UnsupportedOperationException(
            "Multiplexer does not participate directly in transactions.");
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#fire()
     */
    public Object fire()
    {
        throw new UnsupportedOperationException(
            "Multiplexer does not participate directly in transactions.");
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#isFireOnConnect()
     */
    public boolean isFireOnConnect()
    {
        throw new UnsupportedOperationException(
            "Multiplexer does not participate directly in transactions.");
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#isRollbackParticipant()
     */
    public boolean isRollbackParticipant()
    {
        throw new UnsupportedOperationException(
            "Multiplexer does not participate directly in transactions.");
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#addDeferredStateChange(co2.ui.fw.ProcessorSource)
     */
    public void addDeferredStateChange(ProcessorSource source)
    {
        throw new UnsupportedOperationException(
            "Multiplexer does not participate directly in transactions.");
    }

    /* (non-Javadoc)
     * @see co2.ui.fw.EventChannel#addDeferredStateChange(co2.ui.fw.InitiatorSource)
     */
    public void addDeferredStateChange(InitiatorSource[] source)
    {
        throw new UnsupportedOperationException(
            "Multiplexer does not participate directly in transactions.");
    }

    /**
     * Creates a new {@link Objects} from an existing one, substituting
     * the specified new value and a the specified index.
     * @param index the index at which to place the new value.
     * @param state the new value.
     * @param objs the objects to be modified.
     * @return returns the new objects.
     */
    private static ObjectIla newObjects(int index, Object state, ObjectIla objs)
    {
        Object[] array = null;

        try
		{
			if (objs == null)
			{
			    array = new Object[index + 1];
			}
			else if (index >= objs.length())
			{
			    array = new Object[index + 1];
			    objs.toArray(array, 0, 0L, (int) objs.length());
			}
			else
			{
			    array = objs.toArray();
			}
		}
		catch (DataInvalidException e)
		{
			throw new RuntimeException(
				"Multiplexer received DataInvalidException: " +
				e.getMessage(), e);
		}

        array[index] = state;

        return ObjectIlaFromArray.create(array);
    }

    private class MultiSink extends Sink
    {
        public MultiSink(EventChannelDescription ecd)
        {
            super("MultiplexSink[" + ecd.getEventChannelName() + "]", ecd, true);
        }

        /* (non-Javadoc)
        * @see co2.ui.fw.Sink#stateChange()
        */
        void stateChange()
        {
            Iterator itr = childSinks.iterator();

            while (itr.hasNext())
            {
                Sink sink = (Sink) itr.next();
                sink.stateChange();
            }
        }
    }

    /**
     * This class represents an {@link EventChannel} for a specific index
     * position within the multiplexed {@link EventChannel}.
     *
     */
    private class DemultiplexedEventChannel implements EventChannel
    {
        /** The index within the multiplexed channel. */
        private final Integer demultiplexIndex;

        /** The number of ports connected to this event channel. */
        private int connectionCount = 0;

        DemultiplexedEventChannel(Integer demultiplexIndex)
        {
            this.demultiplexIndex = demultiplexIndex;
        }

        public EventChannelDescription getECD()
        {
            return Multiplexer.this.getECD();
        }

        /**
         * Sets this terminators component.
         * @param component the component for this terminator
         */
        public void setTreeComponent(TreeComponent component)
        {
            throw new UnsupportedOperationException(
                "Demultiplexer event channels do not support this method");
        }

        /**
         * Returns the current component associated with this terminator.
         * @return the current component associated with this terminator.
         */
        public TreeComponent getTreeComponent()
        {
            if (component == null)
            {
                throw new IllegalStateException(
                    "Component has not been initialized.");
            }

            return component;
        }

        public void add(Port port)
        {
            port.setEventChannel(this);
            connectionCount++;
        }

        public void remove(Port port)
        {
            connectionCount--;

            // if no ports are connected...
            if (connectionCount == 0)
            {
                // remove this event channel so it can be garbage collected.
                demultiplexedEventChannels.remove(demultiplexIndex);
            }

            port.setEventChannel(null);
        }

        private Object getElement(ObjectIla objects, long index)
        {
            if (objects == null)
            {
                return null;
            }

            if (index >= objects.length())
            {
                return null;
            }

            try
			{
				return (objects.toArray((long) index, 1)[0]);
			}
			catch (DataInvalidException e)
			{
				throw new RuntimeException(
						"Multiplexer received DataInvalidException: " +
						e.getMessage(), e);
			}
        }

        public Object getState()
        {
            ObjectIla objects = (ObjectIla) multiSink.getEventChannel()
                                                     .getState();

            return (getElement(objects, demultiplexIndex.intValue()));
        }

        public Object getPreviousCycleState()
        {
            ObjectIla objects = (ObjectIla) multiSink.getEventChannel()
                                                     .getPreviousCycleState();

            return (getElement(objects, demultiplexIndex.intValue()));
        }

        public Object getPreviousTransactionState()
        {
            ObjectIla objects = (ObjectIla) multiSink.getEventChannel()
                                                     .getPreviousTransactionState();

            return (getElement(objects, demultiplexIndex.intValue()));
        }

        public void setState(Source source, Object state)
        {
            ObjectIla objs = (ObjectIla) multiSink.getEventChannel().getState();
            objs = newObjects(demultiplexIndex.intValue(), state, objs);
            processorMultiSource.getEventChannel().setState(source, objs);

            //            if (source instanceof InitiatorSource)
            //            {
            //                initiatorMultiSource.getEventChannel().setState(source, objs);
            //            }
            //            else
            //            {
            //                processorMultiSource.getEventChannel().setState(source, objs);
            //            }
        }

        public Source getCurrentStateSource()
        {
            return (multiSink.getEventChannel().getCurrentStateSource());
        }

        public void synchronizeCycleState()
        {
            multiSink.getEventChannel().synchronizeCycleState();
        }

        public void synchronizeTransactionState()
        {
            multiSink.getEventChannel().synchronizeTransactionState();
        }

        public Object fire()
        {
            throw new UnsupportedOperationException(
                "Can't fire on Demultiplexed Event Channel");
        }

        public boolean isFireOnConnect()
        {
            return (multiSink.getEventChannel().isFireOnConnect());
        }

        public boolean isRollbackParticipant()
        {
            return (multiSink.getEventChannel().isRollbackParticipant());
        }

        /* (non-Javadoc)
         * @see co2.ui.fw.EventChannel#addDeferredStateChange(co2.ui.fw.InitiatorSource)
         */
        public void addDeferredStateChange(InitiatorSource[] source)
        {
            component.getTransactionManager().addStateChange(source);
        }

        /* (non-Javadoc)
         * @see co2.ui.fw.EventChannel#addDeferredStateChange(co2.ui.fw.ProcessorSource)
         */
        public void addDeferredStateChange(ProcessorSource source)
        {
            source.fire();
        }
    }
}
