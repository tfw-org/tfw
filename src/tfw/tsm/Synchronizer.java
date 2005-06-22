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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import tfw.check.Argument;
import tfw.tsm.ecd.ECDUtility;
import tfw.tsm.ecd.EventChannelDescription;


/**
 * A base class for components which convert one set of events, 'setA', into
 * another, 'setB' and visa versa. This component is active in the processing
 * phase of a state change cycle within a transaction. Note that it is only 
 * legal to convert in one direction within a single transaction. Therefore,
 * converter from 'setA' to 'setB' and then from 'setB' to 'setA' within a
 * transaction is not allowed and will result in a 
 * <code>java.lang.IllegalStateException</code> being thrown.
 */
public abstract class Synchronizer extends Processor
{
    private final Set aEventSet;
    private final Set bEventSet;
    private boolean aToBConvert = false;
    private boolean bToAConvert = false;
	private CommitRollbackListener crListener = new CommitRollbackListener(){
		public void rollback(){
			aToBConvert = false;
			bToAConvert = false;
		}
		public void commit (){
			aToBConvert = false;
			bToAConvert = false;
		}
	};
    /**
     *
     * @param name the name of the component.
     * @param aPortDescriptions The 'A' set of event channels.
     * @param bPortDescriptions The 'B' set of event channels
     * @param sinkEventChannels Additional sinks for the component.
     * @param sourceEventChannels Additional sources for the component.
     */
    public Synchronizer(String name, EventChannelDescription[] aPortDescriptions,
        EventChannelDescription[] bPortDescriptions,
        EventChannelDescription[] sinkEventChannels,
        EventChannelDescription[] sourceEventChannels)
    {
        super(name,
            checkTriggeringSinks(aPortDescriptions, bPortDescriptions), 
            checkAdditionalSinks(sinkEventChannels),
            checkSources(sourceEventChannels, aPortDescriptions,
                bPortDescriptions));

        this.aEventSet = Collections.unmodifiableSet(new HashSet(Arrays.asList(
                        aPortDescriptions)));
        this.bEventSet = Collections.unmodifiableSet(new HashSet(Arrays.asList(
                        bPortDescriptions)));
    }
	
	private static EventChannelDescription[] checkAdditionalSinks(EventChannelDescription[] sinkEventChannels){
		checkForStatelessTrigger(sinkEventChannels, "sinkEventChannels");
		if (sinkEventChannels != null)
		{
			Argument.assertElementNotNull(sinkEventChannels, "sinkEventChannels");
		}
		return sinkEventChannels;
	}
	
	/**
	 * Validates the arguments and returns a concatinated list of sinks.
	 * @param sinks non-triggering sinks.
	 * @param aPortDescriptions the 'setA' list of event channels.
	 * @param bPortDescriptions the 'setB' list of event channels.
	 * @return an aggregation of <code>sinks</code>, 
	 * <code>aPortDescriptions</code> and <code>bPortDescriptions</code>
	 */
    private static EventChannelDescription[] checkTriggeringSinks(
        EventChannelDescription[] aPortDescriptions, EventChannelDescription[] bPortDescriptions)
    {

        Argument.assertNotNull(aPortDescriptions, "aPortDescriptions");
        Argument.assertNotNull(bPortDescriptions, "bPortDescriptions");
        Argument.assertElementNotNull(aPortDescriptions, "aPortDescription");
        Argument.assertElementNotNull(bPortDescriptions, "bPortDescription");

		checkForStatelessTrigger(aPortDescriptions, "aPortDescriptions");
		checkForStatelessTrigger(bPortDescriptions, "bPortDescriptions");
        if (aPortDescriptions.length == 0)
        {
            throw new IllegalArgumentException(
                "aPortDescriptions.length == 0 not allowed");
        }

        if (bPortDescriptions.length == 0)
        {
            throw new IllegalArgumentException(
                "bPortDescriptions.length == 0 not allowed");
        }

        return ECDUtility.concat(aPortDescriptions, bPortDescriptions);
    }

	/**
	 * Validates the arguments and returns a concatinated list of sources.
	 * @param sources non-triggering sources.
	 * @param aPortDescriptions the 'setA' list of event channels.
	 * @param bPortDescriptions the 'setB' list of event channels.
	 * @return an aggregation of <code>sources</code>, 
	 * <code>aPortDescriptions</code> and <code>bPortDescriptions</code>
	 */
    private static EventChannelDescription[] checkSources(EventChannelDescription[] sources,
        EventChannelDescription[] aPortDescriptions, EventChannelDescription[] bPortDescriptions)
    {
		checkForStatelessTrigger(sources, "sources");
        if (sources != null)
        {
            Argument.assertElementNotNull(sources, "sources");
        }

        return ECDUtility.concat(sources,
            ECDUtility.concat(aPortDescriptions, bPortDescriptions));
    }

    void stateChange(EventChannel eventChannel)
    {
    	this.getTransactionManager().addCommitRollbackListener(crListener);
    	
    	// call super to get added to the transaction processors...
        super.stateChange(eventChannel);
//this.aToBConvert = false;
//this.bToAConvert = false;
        if (aEventSet.contains(eventChannel.getECD()))
        {
            if (bToAConvert)
            {
                throw new IllegalStateException(getName() +
                    " - Can not convert A to B and B to A in the same" +
                    " transaction");
            }

            aToBConvert = true;
        }
        else if (bEventSet.contains(eventChannel.getECD()))
        {
            if (aToBConvert)
            {
                throw new IllegalStateException(getName() +
                    " - Can not convert A to B and B to A in the same" +
                    " transaction");
            }

            bToAConvert = true;
        }
    }

    void process()
    {
        if (aToBConvert)
        {
           if (isStateNonNull(aEventSet))
            {
                convertAToB();
            }
            else
            {
                debugConvertAToB();
            }
        }
        else
        {
            if (isStateNonNull(bEventSet))
            {
                convertBToA();
            }
            else
            {
                debugConvertBToA();
            }
        }
    }

    protected abstract void convertAToB();

    protected abstract void convertBToA();

    protected void debugConvertAToB()
    {
    }

    protected void debugConvertBToA()
    {
    }
}
