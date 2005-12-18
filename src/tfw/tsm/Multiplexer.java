/*
 * Created on Dec 17, 2005
 *
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

class Multiplexer implements EventChannel
{
    /** The branch associated with this terminator. */
    private MultiplexedBranch component = null;

    /** The description of the single value event channel. */
    final ObjectECD valueECD;
    
    /** The state change rule for the sub-channels. */
    private final StateChangeRule stateChangeRule;

    /** The multiplexed value sink. */
    final Sink multiSink;

    /** The multiplexed value initiator source. */
    final MultiSource processorMultiSource;

    /** The set of demultiplexing event channels. */
    private final Map demultiplexedEventChannels = new HashMap();

    /**
     * Creates a multiplexer with the specified value and multi-value event
     * channels.
     * 
     * @param multiplexerBranchName
     * @param valueECD
     * @param multiValueECD
     */
    Multiplexer(String multiplexerBranchName, ObjectECD valueECD,
            ObjectIlaECD multiValueECD, StateChangeRule stateChangeRule)
    {
        Argument.assertNotNull(valueECD, "valueECD");
        Argument.assertNotNull(multiValueECD, "multiValueECD");

        this.valueECD = valueECD;
        this.stateChangeRule = stateChangeRule;
        this.multiSink = new MultiSink(multiValueECD);
        this.processorMultiSource = new MultiSource("Multiplexer Source["
                + multiplexerBranchName + "]", multiValueECD);
    }

    private class MultiSink extends Sink
    {
        public MultiSink(ObjectECD ecd)
        {
            super("MultiplexSink[" + ecd.getEventChannelName() + "]", ecd, true);
        }

        /*
         * (non-Javadoc)
         * 
         * @see co2.ui.fw.Sink#stateChange()
         */
        void stateChange()
        {
            if (processorMultiSource.isStateSource())
            {
                return;
            }

            Object[] state;
            try
            {
                state = ((ObjectIla) getState()).toArray();
            }
            catch (DataInvalidException e)
            {
                throw new RuntimeException(
                        "Multiplexed data invalid in event channel '"
                                + multiSink.getEventChannelName() + "': "
                                + e.getMessage(), e);
            }
            Iterator itr = Multiplexer.this.demultiplexedEventChannels.values()
                    .iterator();
            while (itr.hasNext())
            {
                DemultiplexedEventChannel dm = (DemultiplexedEventChannel) itr
                        .next();
                int index = dm.demultiplexIndex.intValue();
                if (index > state.length)
                {
                    throw new IllegalStateException(
                            "Multiplexed state does not have a value for demultiplexed channel '"
                                    + index + "'.");
                }
                dm.setDeMultiState(state[index]);
            }
        }
    }

    class MultiSource extends ProcessorSource
    {
        ArrayList pendingStateChanges = new ArrayList();

        MultiSource(String name, EventChannelDescription ecd)
        {
            super(name, ecd);
        }

        void setState(DemultiplexedEventChannel deMultiplexer)
        {
            if (pendingStateChanges.size() == 0)
            {
                getEventChannel().addDeferredStateChange(this);
            }
            pendingStateChanges.add(deMultiplexer);
        }

        Object fire()
        {
            if (pendingStateChanges.size() == 0)
            {
                throw new IllegalStateException(
                        "No pending state changes to fire.");
            }
            ObjectIla objs = (ObjectIla) this.getEventChannel().getState();
            DemultiplexedEventChannel[] dms = (DemultiplexedEventChannel[]) pendingStateChanges
                    .toArray(new DemultiplexedEventChannel[pendingStateChanges
                            .size()]);
            pendingStateChanges.clear();
            int maxIndex = dms[0].demultiplexIndex.intValue();
            for (int i = 1; i < dms.length; i++)
            {
                if (dms[i].demultiplexIndex.intValue() > maxIndex)
                {
                    maxIndex = dms[i].demultiplexIndex.intValue();
                }
            }

            Object[] array = null;

            try
            {
                if (objs == null)
                {
                    array = new Object[maxIndex + 1];
                }
                else if (maxIndex >= objs.length())
                {
                    array = new Object[maxIndex + 1];
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
                        "Multiplexer received DataInvalidException: "
                                + e.getMessage(), e);
            }

            for (int i = 0; i < dms.length; i++)
            {
                array[dms[i].demultiplexIndex.intValue()] = dms[i].getState();
            }
            objs = ObjectIlaFromArray.create(array);
            getEventChannel().setState(this, objs, null);
            return objs;
        }
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
            DemultiplexedEventChannel dm = new DemultiplexedEventChannel(this,
                    integerIndex, stateChangeRule);
            dm.setTreeComponent(this.component);
            demultiplexedEventChannels.put(integerIndex, dm);
        }

        return ((EventChannel) demultiplexedEventChannels.get(integerIndex));
    }

    TreeComponent getTreeComponent()
    {
        return this.component;
    }

    void remove(DemultiplexedEventChannel deMultiplexer)
    {
        if (demultiplexedEventChannels.remove(deMultiplexer.demultiplexIndex) == null)
        {
            throw new IllegalStateException(
                    "Demultiplexer not found attempting to remove demultiplexer for "
                            + valueECD.getEventChannelName() + "["
                            + deMultiplexer.demultiplexIndex + "]");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#add(tfw.tsm.Port)
     */
    public void add(Port port)
    {
        // Search for the multiplexed component...
        int index = component.getIndex(port.getTreeComponent());
        TreeComponent tc = port.getTreeComponent().getParent();
        while ((index < 0) && (tc != null))
        {
            index = component.getIndex(tc);
            tc = tc.getParent();
        }

        // if we didn't find a multiplexed component...
        if (index < 0)
        {
            throw new IllegalArgumentException(
                    "'port' is not from a multiplexed component.");
        }

        getDemultiplexedEventChannel(index).add(port);
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#addDeferredStateChange(tfw.tsm.InitiatorSource[])
     */
    public void addDeferredStateChange(InitiatorSource[] source)
    {
        throw new UnsupportedOperationException(
                "Multiplexer does not participate directly in transactions.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#addDeferredStateChange(tfw.tsm.ProcessorSource)
     */
    public void addDeferredStateChange(ProcessorSource source)
    {
        throw new UnsupportedOperationException(
                "Multiplexer does not participate directly in transactions.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#fire()
     */
    public Object fire()
    {
        throw new UnsupportedOperationException(
                "Multiplexer does not participate directly in transactions.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#getCurrentStateSource()
     */
    public Source getCurrentStateSource()
    {
        throw new UnsupportedOperationException(
                "Multiplexer does not participate directly in transactions.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#getECD()
     */
    public EventChannelDescription getECD()
    {
        return valueECD;
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#getPreviousCycleState()
     */
    public Object getPreviousCycleState()
    {
        return valueECD;
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#getPreviousTransactionState()
     */
    public Object getPreviousTransactionState()
    {
        return valueECD;
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#getState()
     */
    public Object getState()
    {
        return multiSink.getEventChannel().getState();
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#isFireOnConnect()
     */
    public boolean isFireOnConnect()
    {
        throw new UnsupportedOperationException(
                "Multiplexer does not participate directly in transactions.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#isRollbackParticipant()
     */
    public boolean isRollbackParticipant()
    {
        throw new UnsupportedOperationException(
                "Multiplexer does not participate directly in transactions.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#remove(tfw.tsm.Port)
     */
    public void remove(Port port)
    {
        port.getEventChannel().remove(port);
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#setState(tfw.tsm.Source, java.lang.Object,
     *      tfw.tsm.EventChannel)
     */
    public void setState(Source source, Object state,
            EventChannel forwardingEventChannel)
    {
        throw new UnsupportedOperationException(
                "Multiplexer does not participate directly in transactions.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#setTreeComponent(tfw.tsm.TreeComponent)
     */
    public void setTreeComponent(TreeComponent component)
    {
        this.component = (MultiplexedBranch) component;
        this.multiSink.setTreeComponent(component);
        this.processorMultiSource.setTreeComponent(component);
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#synchronizeCycleState()
     */
    public void synchronizeCycleState()
    {
        throw new UnsupportedOperationException(
                "Multiplexer does not participate directly in transactions.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#synchronizeTransactionState()
     */
    public void synchronizeTransactionState()
    {
        throw new UnsupportedOperationException(
                "Multiplexer does not participate directly in transactions.");
    }
}
