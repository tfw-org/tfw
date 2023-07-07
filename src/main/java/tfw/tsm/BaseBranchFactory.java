package tfw.tsm;

import java.util.HashMap;
import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.RollbackECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.value.ValueException;

/**
 * The base class for branch component factories. It is made public solely to
 * make the base class type of all branch factories available. It is not
 * intended to be sub-classed outside of its package.
 */
public class BaseBranchFactory {
    private final HashMap<String, Terminator> terminators = new HashMap<String, Terminator>();

    /**
     * Creates a factory. The constructor is package private to prevent
     * sub-classing outside the package.
     */
    BaseBranchFactory() {}

    /**
     * Returns the current set of Event Channel terminators.
     *
     * @return the current set of Event Channel terminators.
     */
    EventChannel[] getTerminators() {
        return terminators.values().toArray(new EventChannel[terminators.size()]);
    }

    /**
     * Adds an event channel based on the specified description with a null
     * initial state and an {@link DotEqualsRule}
     *
     * @param eventChannelDescription
     *            a description of the event channel.
     */
    public void addEventChannel(EventChannelDescription eventChannelDescription) {
        addEventChannel(eventChannelDescription, null);
    }

    /**
     * Adds an event channel based on the specified description and initial
     * state. The {@link DotEqualsRule} is used as long as the event channel is
     * not a {@link StatelessTriggerECD}. The {@link AlwaysChangeRule} is used
     * for {@link StatelessTriggerECD}.
     *
     * @param eventChannelDescription
     *            a description of the event channel.
     * @param initialState
     *            the initial state for the event channel.
     * @throws ValueException
     *             if the <code>initialState</code> value is incompatible with
     *             the event channel.
     */
    public void addEventChannel(EventChannelDescription eventChannelDescription, Object initialState)
            throws ValueException {
        if ((eventChannelDescription instanceof StatelessTriggerECD)
                || (eventChannelDescription instanceof RollbackECD)) {
            addEventChannel(eventChannelDescription, initialState, AlwaysChangeRule.RULE, null);
        } else {
            addEventChannel(eventChannelDescription, initialState, DotEqualsRule.RULE, null);
        }
    }

    /**
     * Adds an event channel terminator based on the specified description
     *
     * @param eventChannelDescription
     *            a description of the event channel.
     * @param initialState
     *            the initial state for the event channel.
     * @param rule
     *            the rule to be used to determine what represents a state
     *            change for the event channel.
     * @param exportTags
     *            The list of export tags for this event channel.
     * @throws ValueException
     *             if the <code>initialState</code> value is incompatible with
     *             the event channel.
     */
    public void addEventChannel(
            EventChannelDescription eventChannelDescription,
            Object initialState,
            StateChangeRule rule,
            String[] exportTags)
            throws ValueException {
        Argument.assertNotNull(eventChannelDescription, "eventChannelDescription");
        Argument.assertNotNull(rule, "rule");
        if (exportTags != null) {
            Argument.assertElementNotNull(exportTags, "exportTags");
        }

        if (isTerminated(eventChannelDescription)) {
            throw new IllegalArgumentException(
                    "The event channel '" + eventChannelDescription.getEventChannelName() + "' is already exists");
        }

        // if the event channel is memory-less and non-null initial state...
        if (!eventChannelDescription.isFireOnConnect() && (initialState != null)) {
            throw new IllegalArgumentException("Non-null 'initialState' is not permitted given"
                    + " eventChannelDescription.isFireOnConnect() == false");
        }

        if ((eventChannelDescription instanceof StatelessTriggerECD)
                || (eventChannelDescription instanceof RollbackECD)) {
            if (!(rule instanceof AlwaysChangeRule)) {
                throw new IllegalArgumentException("(eventChannelDescription instanceof "
                        + ((eventChannelDescription instanceof StatelessTriggerECD)
                                ? "StatelessTriggerECD"
                                : "RollbackECD")
                        + ") && !(rule instanceof AlwaysChangeRule) not allowed");
            }
        }
        terminators.put(
                eventChannelDescription.getEventChannelName(),
                new Terminator(eventChannelDescription, initialState, rule));

        if (exportTags != null) {
            for (int i = 0; i < exportTags.length; i++) {
                this.addExportTag(eventChannelDescription, exportTags[i]);
            }
        }
    }

    boolean isTerminated(EventChannelDescription ecd) {
        return terminators.containsKey(ecd.getEventChannelName());
    }

    /**
     * Clears all previously set terminators.
     */
    public void clear() {
        terminators.clear();
    }

    /**
     * Adds the specified export tag to the specified event channel. The event
     * channel must be added to the branch before this method can be called.
     *
     * @param ecd
     *            The description of the event channel to be tagged.
     * @param tag
     *            The tag to be added to the event channel
     */
    public void addExportTag(EventChannelDescription ecd, String tag) {
        Argument.assertNotNull(ecd, "ecd");
        Argument.assertNotNull(tag, "tag");
        Argument.assertNotEmpty(tag, "tag");
        if (!isTerminated(ecd)) {
            throw new IllegalArgumentException("The event channel '"
                    + ecd.getEventChannelName()
                    + "' has not been added to this factory and "
                    + "therefore can not be tagged.");
        }

        Terminator t = this.terminators.get(ecd.getEventChannelName());
        t.addExportStateTag(tag);
    }
}
