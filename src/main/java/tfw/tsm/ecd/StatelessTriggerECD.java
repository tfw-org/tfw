package tfw.tsm.ecd;

import tfw.value.NullConstraint;

/**
 * An event channel discription for a stateless, trigging
 * event channel.
 */
public class StatelessTriggerECD extends EventChannelDescription {

    /**
     * Constructs an event channel description with the specified name.
     * @param eventChannelName the name of the event channel.
     */
    public StatelessTriggerECD(String eventChannelName) {
        super(eventChannelName, NullConstraint.INSTANCE, false, false);
    }
}
