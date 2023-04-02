package tfw.tsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import tfw.check.Argument;
import tfw.tsm.ecd.ECDUtility;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;

/**
 * A base class for components which convert one set of events, 'setA', into
 * another, 'setB' and visa versa. This component is active in the processing
 * phase of a state change cycle within a transaction. Note that it is only
 * legal to convert in one direction within a single transaction. Therefore,
 * converting from 'setA' to 'setB' and then from 'setB' to 'setA' within a
 * transaction is not allowed and will result in a
 * <code>java.lang.IllegalStateException</code> being thrown.
 */
public abstract class Synchronizer extends Processor
{
    private final List<ObjectECD> aEventList;
    private final List<ObjectECD> bEventList;
    private final List<ObjectECD> additionalList;
    private HashSet<EventChannel> aToBConvert = new HashSet<EventChannel>();
    private HashSet<EventChannel> bToAConvert = new HashSet<EventChannel>();
    private CommitRollbackListener crListener = new CommitRollbackListener()
    {
    	public void rollback()
    	{
    		aToBConvert.clear();
    		bToAConvert.clear();
    	}
    	
    	public void commit() {}
    	
    	public String getName()
    	{
    		return Synchronizer.this.getName();
    	}
    };
    
    /**
     * Creates a synchronizer.
     * 
     * @param name
     *            The name of the component.
     * @param aPortInputDescriptions
     *            The 'A' set of event channels.
     * @param bPortInputDescriptions
     *            The 'B' set of event channels.
     * @param additionalInputDescriptions
     *            The "additional" set of event channels.
     * @param outputDescriptions
     *            The array of EventChannelDescriptions for output.
     */
    public Synchronizer(String name,
            ObjectECD[] aPortInputDescriptions,
            ObjectECD[] bPortInputDescriptions,
            ObjectECD[] additionalInputDescriptions,
            EventChannelDescription[] outputDescriptions)
    {
        super(name,
        	checkTriggeringSinks(aPortInputDescriptions,
        		bPortInputDescriptions),
            checkAdditionalSinks(additionalInputDescriptions),
        	ECDUtility.concat(aPortInputDescriptions,
        			ECDUtility.concat(bPortInputDescriptions, outputDescriptions)));

        this.aEventList = Collections.unmodifiableList(
        	Arrays.asList(aPortInputDescriptions));
        this.bEventList = Collections.unmodifiableList(
        	Arrays.asList(bPortInputDescriptions));
        this.additionalList = (additionalInputDescriptions == null) ?
        	Collections.unmodifiableList(new ArrayList<ObjectECD>()) :
        	Collections.unmodifiableList(
        		Arrays.asList(additionalInputDescriptions));
    }

    /**
     * This method is called during the processing phase of a transaction when
     * one or more of the event channels in set 'A' has it's state changed and
     * all of the dependent event channels have non-null state.
     */
    protected abstract void convertAToB();

    /**
     * This method is called during the processing phase of a transaction when
     * one or more of the event channels in set 'B' has it's state changed and
     * all of the dependent event channels have non-null state.
     */
    protected abstract void convertBToA();

    /**
     * This method is called during the processing phase of a transaction when
     * one or more of the event channels in set 'A' has it's state changed and
     * one or more it's dependent event channels have null state. This method
     * can be overridden for debugging purposes to determine what event channels
     * are null.
     */
    protected void debugConvertAToB()
    {
    }

    /**
     * This method is called during the processing phase of a transaction when
     * one or more of the event channels in set 'B' has it's state changed and
     * one or more its dependent event channels have null state. This method
     * can be overridden for debugging purposes to determine what event channels
     * are null.
     */
    protected void debugConvertBToA()
    {
    }

    void process()
    {
        if (!aToBConvert.isEmpty())
        {
//        	if (bToAConvert.isEmpty() ||
//        		getTransactionManager().isComponentChangeTransactionExecuting())
//        	{
            	if (isStateNonNull(aEventList) && isStateNonNull(additionalList))
                {
                    convertAToB();
                }
                else
                {
                    debugConvertAToB();
                }
//        	}
//        	else
//        	{
//        		throwBothSetsChangedException();
//        	}
        }
        else if (!bToAConvert.isEmpty())
        {
//        	if (aToBConvert.isEmpty())
//        	{
            	if (isStateNonNull(bEventList) && isStateNonNull(additionalList))
                {
                    convertBToA();
                }
                else
                {
                    debugConvertBToA();
                }
//        	}
//        	else
//        	{
//        		throwBothSetsChangedException();
//        	}
        }
        
        aToBConvert.clear();
        bToAConvert.clear();
    }

    void stateChange(EventChannel eventChannel)
    {
    	getTransactionManager().addCommitRollbackListener(crListener);
        // call super to get added to the transaction processors...
        super.stateChange(eventChannel);
        
        if (aEventList.contains(eventChannel.getECD()))
        {
            aToBConvert.add(eventChannel);
        }
        else if (bEventList.contains(eventChannel.getECD()))
        {
            bToAConvert.add(eventChannel);
        }
    }

    private static ObjectECD[] checkAdditionalSinks(
            ObjectECD[] sinkEventChannels)
    {
        if (sinkEventChannels != null)
        {
            Argument.assertElementNotNull(sinkEventChannels,
                    "sinkEventChannels");
        }
        return sinkEventChannels;
    }
    
    /**
     * Validates the arguments and returns a concatinated list of sinks.
     * 
     * @param sinks
     *            non-triggering sinks.
     * @param aPortDescriptions
     *            the 'setA' list of event channels.
     * @param bPortDescriptions
     *            the 'setB' list of event channels.
     * @return an aggregation of <code>sinks</code>,
     *         <code>aPortDescriptions</code> and
     *         <code>bPortDescriptions</code>
     */
    private static ObjectECD[] checkTriggeringSinks(
            ObjectECD[] aPortDescriptions,
            ObjectECD[] bPortDescriptions)
    {
        Argument.assertNotNull(aPortDescriptions, "aPortDescriptions");
        Argument.assertNotNull(bPortDescriptions, "bPortDescriptions");
        Argument.assertElementNotNull(aPortDescriptions, "aPortDescription");
        Argument.assertElementNotNull(bPortDescriptions, "bPortDescription");

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
    
    private void throwBothSetsChangedException()
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append(getFullyQualifiedName());
    	sb.append(" - Cannot convert AToB and BToA in the same transaction!\n");
    	sb.append("A changes:\n");
    	
    	for (EventChannel ec : aToBConvert)
    	{
    		sb.append(ec.getECD().getEventChannelName());
    		sb.append(" by ");
    		sb.append(ec.getCurrentStateSource());
    		sb.append("\n");
    	}
    	
    	sb.append("B Changes:\n");
    	
    	for (EventChannel ec : bToAConvert)
    	{
    		sb.append(ec.getECD().getEventChannelName());
    		sb.append(" by ");
    		sb.append(ec.getCurrentStateSource());
    		sb.append("\n");
    	}
    	
        throw new IllegalStateException(sb.toString());
    }
}