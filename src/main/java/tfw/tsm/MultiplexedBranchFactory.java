package tfw.tsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tfw.check.Argument;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

/**
 * A factory for creating a {@link MultiplexedBranch}.
 */
public class MultiplexedBranchFactory
{
    /** A map of multi value event channel descriptions. */
    HashMap<String, ObjectECD> multiValueECDMap = new HashMap<String, ObjectECD>();

    /** A map of value event channel descriptions. */
    HashMap<String, ObjectECD> valueECDMap = new HashMap<String, ObjectECD>();

    /** A list of multi value event channel descriptions. */
    ArrayList<ObjectECD> multiValueECDList = new ArrayList<ObjectECD>();

    /** A list of value event channel descriptions. */
    ArrayList<ObjectECD> valueECDList = new ArrayList<ObjectECD>();

    /** A list of State change rules. */
    ArrayList<StateChangeRule> stateChangeRules = new ArrayList<StateChangeRule>();

    /** A list of multiplexer strategies. */
    ArrayList<MultiplexerStrategy> strategyList = new ArrayList<MultiplexerStrategy>();

    /**
     * Addes a multiplexer for the specified event channels.
     * 
     * @param valueECD
     *            The event channel description of the child values.
     * @param multiValueECD
     *            The event channel description of the parent multiplexed
     *            values.
     */
    public void addMultiplexer(ObjectECD valueECD, ObjectIlaECD multiValueECD)
    {
        this.addMultiplexer(valueECD, multiValueECD, DotEqualsRule
                .getInstance(), new ObjectIlaMultiplexerStrategy());
    }

    /**
     * Addes a multiplexer for the specified event channels.
     * 
     * @param valueECD
     *            The event channel description of the child values.
     * @param multiValueECD
     *            The event channel description of the parent multiplexed
     *            values.
     * @param valueStateChangeRule
     *            The state change rule for the child or demultiplexed event
     *            channels.
     * @param multiplexerStrategy
     *            The strategy for multiplexing and demultiplexing state.
     */
    public void addMultiplexer(ObjectECD valueECD, ObjectECD multiValueECD,
            StateChangeRule valueStateChangeRule,
            MultiplexerStrategy multiplexerStrategy)
    {
        Argument.assertNotNull(valueECD, "valueECD");
        Argument.assertNotNull(multiValueECD, "multiValueECD");
        Argument.assertNotNull(valueStateChangeRule, "valueStateChangeRule");
        Argument.assertNotNull(multiplexerStrategy, "multiplexerStrategy");

        if (valueECD.getEventChannelName().equals(
                multiValueECD.getEventChannelName()))
        {
            throw new IllegalArgumentException(
                    "valueECD.getEventChannelName().equals(multiValueECD.getEventChannelName()) not allowed");
        }
        if (multiValueECDMap.put(multiValueECD.getEventChannelName(),
                multiValueECD) != null)
        {
            throw new IllegalArgumentException(
                    "Attempt to add multiple multiplexers for multi event channel '"
                            + multiValueECD.getEventChannelName() + "'");
        }

        if (valueECDMap.put(valueECD.getEventChannelName(), multiValueECD) != null)
        {
            throw new IllegalArgumentException(
                    "Attempt to add multiple multiplexers for value event channel '"
                            + valueECD.getEventChannelName() + "'");
        }

        valueECDList.add(valueECD);
        multiValueECDList.add(multiValueECD);
        stateChangeRules.add(valueStateChangeRule);
        strategyList.add(multiplexerStrategy);
    }

    private static Multiplexer[] generateMulitplexers(String name,
            List<ObjectECD> valueECDList, List<ObjectECD> multiValueECDList,
            List<StateChangeRule> stateChangeRules,
            List<MultiplexerStrategy> strategyList)
    {
        ArrayList<Multiplexer> list = new ArrayList<Multiplexer>();

        for (int i = 0; i < valueECDList.size(); i++)
        {
            list.add(new Multiplexer(name, valueECDList.get(i),
                    multiValueECDList.get(i), stateChangeRules.get(i),
                    strategyList.get(i)));
        }

        return (Multiplexer[]) list.toArray(new Multiplexer[list.size()]);
    }

    /**
     * Creates a multiplexed branch with the set of multiplexed event channels
     * as previously defined by calls to
     * {@link #addMultiplexer(ObjectECD, ObjectIlaECD)}.
     * 
     * @param name
     *            the name of the branch.
     * @return the multiplexed branch.
     */
    public MultiplexedBranch create(String name)
    {
        Argument.assertNotNull(name, "name");

        return new MultiplexedBranch(name,
                generateMulitplexers(name, valueECDList, multiValueECDList,
                        stateChangeRules, strategyList));
    }

    /**
     * Clears any previously added multiplexers.
     */
    public void clear()
    {
        valueECDMap.clear();
        multiValueECDMap.clear();
        valueECDList.clear();
        multiValueECDList.clear();
        stateChangeRules.clear();
    }
}