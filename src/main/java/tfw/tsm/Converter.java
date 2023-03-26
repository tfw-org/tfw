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
    public Converter(String name, EventChannelDescription[] sinkDescriptions,
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
    public Converter(String name, EventChannelDescription[] triggeringSinks,
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

    private static EventChannelDescription[] checkTriggeringSinks(
            EventChannelDescription[] triggeringSinks)
    {
        Argument.assertNotNull(triggeringSinks, "triggeringSinks");
        Argument.assertElementNotNull(triggeringSinks, "triggeringSinks");

        return triggeringSinks;
    }

    /**
     * Called when one or more of the <code>sinkEventChannels</code>
     * specified at construction has it's state changed and all of the
     * <code>sinkEventChannels</code> have non-null state. The call to this
     * method will occur in the processing phase immediately following the
     * the state change phase in which one or more of the
     * <code>sinkEventChannels</code> has it's state changed.
     */
    protected abstract void convert();

    /**
     * Called when one or more of the <code>sinkEventChannels</code>
     * specified at construction has it's state changed and all of the
     * <code>sinkEventChannels</code> have non-null state. The call to this
     * method will occur in the processing phase immediately following the
     * the state change phase in which one or more of the
     * <code>sinkEventChannels</code> has it's state changed.
     */
    protected void debugConvert()
    {
    }
}
