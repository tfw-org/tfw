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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tfw.check.Argument;
import tfw.tsm.MultiplexerStrategy.MultiStateAccessor;
import tfw.tsm.MultiplexerStrategy.MultiStateFactory;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;

class Multiplexer implements EventChannel
{
    private final MultiplexerStrategy multiStrategy;

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
            ObjectECD multiValueECD, StateChangeRule stateChangeRule,
            MultiplexerStrategy multiStrategy)
    {
        Argument.assertNotNull(valueECD, "valueECD");
        Argument.assertNotNull(multiValueECD, "multiValueECD");
        Argument.assertNotNull(multiStrategy, "multiStrategy");

        this.valueECD = valueECD;
        this.stateChangeRule = stateChangeRule;
        this.multiSink = new MultiSink(multiValueECD);
        this.processorMultiSource = new MultiSource("Multiplexer Source["
                + multiplexerBranchName + "]", multiValueECD);
        this.multiStrategy = multiStrategy;
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

            MultiStateAccessor stateAccessor = multiStrategy
                    .toMultiStateAccessor(getState());
            Iterator itr = Multiplexer.this.demultiplexedEventChannels.values()
                    .iterator();
            while (itr.hasNext())
            {
                DemultiplexedEventChannel dm = (DemultiplexedEventChannel) itr
                        .next();

                Object state = stateAccessor.getState(dm.demultiplexSlotId);
                if (state != null && 
                	stateChangeRule.isChange(dm.getPreviousCycleState(), state))
                {
                    dm.setDeMultiState(state);
                }
            }
        }
    }

    class MultiSource extends ProcessorSource
    {
        Set pendingStateChanges = new HashSet();

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
            MultiStateFactory stateFactory = Multiplexer.this.multiStrategy
                    .toMultiStateFactory(this.getEventChannel().getState());
            DemultiplexedEventChannel[] dms = (DemultiplexedEventChannel[]) pendingStateChanges
                    .toArray(new DemultiplexedEventChannel[pendingStateChanges
                            .size()]);
            pendingStateChanges.clear();

            for (int i = 0; i < dms.length; i++)
            {
                stateFactory.setState(dms[i].demultiplexSlotId, dms[i]
                        .getState());
            }
            Object multiState = stateFactory.toMultiState();
            getEventChannel().setState(this, multiState, null);
            return multiState;
        }
    }

    private EventChannel getDemultiplexedEventChannel(Object slotId)
    {
        if (slotId == null)
        {
            throw new IllegalArgumentException("slotId == null not allowed");
        }

        if (!demultiplexedEventChannels.containsKey(slotId))
        {
            Object currentSlotState = null;
            /*
             * TODO: If the multiSink is not connected, initial state is not
             * handled properly.
             */
            if (this.multiSink.isConnected())
            {
                Object multiState = getState();
                MultiStateAccessor msa = this.multiStrategy
                        .toMultiStateAccessor(multiState);
                currentSlotState = msa.getState(slotId);
            }
            final Object defaultSlotState = this.multiStrategy
                    .getDefaultSlotState();
            Object initialSlotState = currentSlotState;
            if (initialSlotState == null)
            {
                initialSlotState = defaultSlotState;
            }

            DemultiplexedEventChannel dm = new DemultiplexedEventChannel(this,
                    slotId, initialSlotState, stateChangeRule);
            /*
             * if the initial slot state is non-null, make sure the state gets
             * added to the multiplexed state.
             */
            if (this.multiSink.isConnected() && (currentSlotState == null)
                    && (defaultSlotState != null))
            {
                this.processorMultiSource.setState(dm);
            }

            dm.setTreeComponent(this.component);
            demultiplexedEventChannels.put(slotId, dm);
        }

        return ((EventChannel) demultiplexedEventChannels.get(slotId));
    }

    TreeComponent getTreeComponent()
    {
        return this.component;
    }

    void remove(DemultiplexedEventChannel deMultiplexer)
    {
        if (demultiplexedEventChannels.remove(deMultiplexer.demultiplexSlotId) == null)
        {
            throw new IllegalStateException(
                    "Demultiplexer not found attempting to remove demultiplexer for "
                            + valueECD.getEventChannelName() + "["
                            + deMultiplexer.demultiplexSlotId + "]");
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
        Object slotId = component.getSlotId(port.getTreeComponent());
        TreeComponent tc = port.getTreeComponent().getParent();
        while ((slotId == null) && (tc != null))
        {
            slotId = component.getSlotId(tc);
            tc = tc.getParent();
        }

        // if we didn't find a multiplexed component...
        if (slotId == null)
        {
            throw new IllegalArgumentException("The specified port, '"
                    + port.getEventChannelName()
                    + "', is not from a multiplexed component.");
        }

        getDemultiplexedEventChannel(slotId).add(port);
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
     * @see tfw.tsm.EventChannel#getParent()
     */
    public TreeComponent getParent()
    {
        return this.component;
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
        throw new UnsupportedOperationException(
                "Multiplexer does not participate directly in transactions.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#getPreviousTransactionState()
     */
    public Object getPreviousTransactionState()
    {
        throw new UnsupportedOperationException(
                "Multiplexer does not participate directly in transactions.");
    }

    public boolean isStateChanged()
    {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see tfw.tsm.EventChannel#getState()
     */
    public Object getState()
    {
        return this.multiSink.getEventChannel().getState();
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
