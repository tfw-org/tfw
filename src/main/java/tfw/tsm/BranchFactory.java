package tfw.tsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.value.ValueException;

/**
 * A factory for creating an {@link Branch}.
 */
public class BranchFactory extends BaseBranchFactory {
    /** The set of translators. */
    private final HashMap<String, Translator> translators = new HashMap<String, Translator>();

    @Override
    EventChannel[] getTerminators() {
        ArrayList<EventChannel> list = new ArrayList<EventChannel>();
        list.addAll(Arrays.asList(super.getTerminators()));
        list.addAll(translators.values());

        return list.toArray(new EventChannel[list.size()]);
    }

    private Sink[] getSinks() {
        HashSet<Sink> ports = new HashSet<Sink>();

        // add all translator ports to the ports hash set.
        for (Translator translator : translators.values()) {
            ports.add(translator.getParentSink());
        }

        return ports.toArray(new Sink[ports.size()]);
    }

    private Source[] getSources() {
        HashSet<Source> ports = new HashSet<Source>();

        // add all translator ports to the ports hash set.
        for (Translator translator : translators.values()) {
            ports.add(translator.getParentSource());
        }

        return ports.toArray(new Source[ports.size()]);
    }

    /**
     * Adds translator between the specified parent and child event channels.
     *
     * @param childEventChannel
     *            the child event channel.
     * @param parentEventChannel
     *            the parent event channel.
     */
    public void addTranslation(EventChannelDescription childEventChannel, EventChannelDescription parentEventChannel) {
        Argument.assertNotNull(childEventChannel, "childEventChannel");
        Argument.assertNotNull(parentEventChannel, "parentEventChannel");

        if (isTerminated(childEventChannel)) {
            throw new IllegalStateException("Attempt to translate the event channel '"
                    + childEventChannel.getEventChannelName()
                    + "' which is already terminated.");
        }

        if (isTranslated(childEventChannel)) {
            throw new IllegalStateException("Attempt to translate the event channel '"
                    + childEventChannel.getEventChannelName()
                    + "' which is already translated.");
        }

        if (!childEventChannel.getConstraint().isCompatible(parentEventChannel.getConstraint())) {
            throw new IllegalArgumentException("Incompatible event channels, values from the parent event channel '"
                    + parentEventChannel.getEventChannelName()
                    + "' are not assignable to the child event channel '"
                    + childEventChannel.getEventChannelName() + "'");
        }
        if (!parentEventChannel.getConstraint().isCompatible(childEventChannel.getConstraint())) {
            throw new IllegalArgumentException("Incompatible event channels values from the child event channel '"
                    + childEventChannel.getEventChannelName()
                    + "' are not assignable to the parent event channel '"
                    + parentEventChannel.getEventChannelName() + "'");
        }

        try {
            translators.put(
                    childEventChannel.getEventChannelName(), new Translator(childEventChannel, parentEventChannel));
        } catch (ValueException unexpected) {
            throw new RuntimeException(
                    "Unexpected error creating a translator: " + unexpected.getMessage(), unexpected);
        }
    }

    /**
     * Adds a terminator for the specified event channel. A terminator is the
     * point within the component tree structure where an event channel stops.
     * Events from sources or publishers move up through the component hierarchy
     * until they reach the terminator. If the event represents a state change
     * as defined by the {@link StateChangeRule} the terminator reflects the
     * event back down through the component tree structure to the sinks or
     * subscribers.
     *
     * @param eventChannelDescription
     *            The event channel to be terminated.
     * @param initialState
     *            The initial value to be assigned to the event channel.
     * @param rule
     *            The state change rule for the event channel.
     * @param exportTags
     *            The list of export tags for this event channel.
     */
    @Override
    public void addEventChannel(
            EventChannelDescription eventChannelDescription,
            Object initialState,
            StateChangeRule rule,
            String[] exportTags)
            throws ValueException {
        if (eventChannelDescription != null && isTranslated(eventChannelDescription)) {
            throw new IllegalStateException("Attemp to terminate an event channel, '"
                    + eventChannelDescription.getEventChannelName()
                    + "', which is already transalated.");
        }

        super.addEventChannel(eventChannelDescription, initialState, rule, exportTags);
    }

    private boolean isTranslated(EventChannelDescription ecd) {
        return translators.containsKey(ecd.getEventChannelName());
    }

    /**
     * Removes all previously specified translators and terminators.
     */
    @Override
    public void clear() {
        this.translators.clear();
        super.clear();
    }

    /**
     * Creates a {@link Branch} with the previously specified translators and
     * terminators. Note that this method calls {@link #clear()}.
     *
     * @param name
     *            The name of the branch.
     * @return A branch with the previously specified translators and
     *         terminators.
     */
    public Branch create(String name) {
        Branch branch = new Branch(name, getSinks(), getSources(), getTerminators());

        return branch;
    }
}
