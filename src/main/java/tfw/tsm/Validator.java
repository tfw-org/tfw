package tfw.tsm;

import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.RollbackECD;
import tfw.tsm.ecd.StatelessTriggerECD;

/**
 * The base class for all components which perform validation in the validation
 * phase of a transaction. Subclasses must implement the
 * {@link #validateState()} method.
 */
public abstract class Validator extends RollbackHandler
{
    /**
     * Creates a validate with the specified attributes.
     * 
     * @param name
     *            the name of this validator component.
     * @param triggeringSinks
     *            the set of sinks which trigger this validator.
     * 
     */
    public Validator(String name, ObjectECD[] triggeringSinks,
            RollbackECD[] initiators)
    {
        this(name, triggeringSinks, null, initiators);
    }

    /**
     * Creates a validator with the specified attributes.
     * 
     * @param name
     *            the name of this validator component.
     * @param triggeringSinks
     *            the set of sinks which trigger this validator.
     * @param nonTriggeringSinks
     *            the set of sinks which do not trigger this validator, but are
     *            used in validation. Note that the event channels associated
     *            with these sinks must be non-null before the
     *            {@link #validateState()} method will be called.
     * @param initiators
     *            the set of sources used to initiate new transactions.
     */
    public Validator(String name, ObjectECD[] triggeringSinks,
            ObjectECD[] nonTriggeringSinks, RollbackECD[] initiators)
    {
        super(name, checkSinks(triggeringSinks), nonTriggeringSinks, initiators);
    }

    /**
     * Creates a triggered validator. The validator will only run when the
     * trigger fires.
     * 
     * @param name
     *            The name of this validator.
     * @param trigger
     *            The trigger which causes the validator to execute.
     * @param nonTriggeringSinks
     *            the set of sinks which do not trigger this validator, but are
     *            used in validation. Note that the event channels associated
     *            with these sinks must be non-null before the
     *            {@link #validateState()} method will be called.
     * @param initiators
     *            the set of sources used to initiate new transactions.
     */
    public Validator(String name, StatelessTriggerECD trigger,
            ObjectECD[] nonTriggeringSinks, RollbackECD[] initiators)
    {
        super(name, new EventChannelDescription[] { trigger },
                nonTriggeringSinks, initiators);
    }

    private static ObjectECD[] checkSinks(ObjectECD[] triggeringSinks)
    {
        Argument.assertNotNull(triggeringSinks, "triggeringSinks");
        return triggeringSinks;
    }

    /**
     * Returns the state of the specified event channel prior to the current
     * state change cycle.
     * 
     * @param sinkEventChannel
     *            the sink event channel whose state is to be returned.
     * @return the state of the event channel during the previous state change
     *         cycle.
     */
    protected final Object getPreviousCycleState(ObjectECD sinkEventChannel)
    {
        Argument.assertNotNull(sinkEventChannel, "sinkEventChannel");
        assertNotStateless(sinkEventChannel);
        Sink sink = getSink(sinkEventChannel);

        if (sink == null)
        {
            throw new IllegalArgumentException(sinkEventChannel
                    .getEventChannelName()
                    + " not found");
        }

        if (sink.eventChannel == null)
        {
            throw new IllegalStateException(sinkEventChannel
                    + " is not connected to an event channel");
        }

        return (sink.eventChannel.getPreviousCycleState());
    }

    final void stateChange(EventChannel eventChannel)
    {
        getTransactionManager().addValidator(this);
    }

    final void validate()
    {
        if (isStateNonNull())
        {
            validateState();
        }
        else
        {
            debugValidateState();
        }
    }

    /**
     * Called in the validation phase of a transaction where one or more of the
     * triggering event channels specified at construction has its state
     * changed.
     */
    protected abstract void validateState();

    /**
     * This method is called in the validation phase of a transaction where one
     * or more of the triggering event channels specified at construction has
     * its state changed and one or more of the event channels has
     * <code>null</code>.
     */
    protected void debugValidateState()
    {
        // Do nothing by default.
    }
}
