package tfw.tsm.ecd;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Utilities for manipulating event channel descriptions.
 */
public final class ECDUtility {
    private ECDUtility() {}
    /**
     * Creates a new array with the specified description added.
     *
     * @param eventChannelDescription the eventChannelDescription to be added.
     * @param eventChannelDescriptions the eventChannelDescriptions to add too.
     * @return the appended array.
     */
    public static EventChannelDescription[] append(
            EventChannelDescription eventChannelDescription, EventChannelDescription[] eventChannelDescriptions) {
        if (eventChannelDescription == null) {
            return eventChannelDescriptions;
        }

        if (eventChannelDescriptions == null) {
            return new EventChannelDescription[] {eventChannelDescription};
        }

        ArrayList<EventChannelDescription> list = new ArrayList<>(Arrays.asList(eventChannelDescriptions));
        list.add(eventChannelDescription);

        return list.toArray(new EventChannelDescription[list.size()]);
    }

    /**
     * Creates a new array with the specified descriptions added.
     *
     * @param eventChannelDescriptions1 the eventChannelDescriptions1 to be added.
     * @param eventChannelDescriptions2 the eventChannelDescriptions2 to add too.
     * @return the concatenated array.
     */
    public static EventChannelDescription[] concat(
            EventChannelDescription[] eventChannelDescriptions1, EventChannelDescription[] eventChannelDescriptions2) {
        if (eventChannelDescriptions1 == null) {
            return eventChannelDescriptions2;
        }

        if (eventChannelDescriptions2 == null) {
            return eventChannelDescriptions1;
        }

        ArrayList<EventChannelDescription> list = new ArrayList<>(Arrays.asList(eventChannelDescriptions1));
        list.addAll(Arrays.asList(eventChannelDescriptions2));

        return list.toArray(new EventChannelDescription[list.size()]);
    }

    public static ObjectECD[] concat(ObjectECD[] objectECDs1, ObjectECD[] objectECDs2) {
        EventChannelDescription[] ecds =
                concat((EventChannelDescription[]) objectECDs1, (EventChannelDescription[]) objectECDs2);
        ObjectECD[] oEcds = new ObjectECD[ecds.length];
        System.arraycopy(ecds, 0, oEcds, 0, ecds.length);
        return oEcds;
    }

    /**
     * Converts an array of event channel descriptions into an array of event
     * channel names
     *
     * @param eventChannelDescriptions
     *            The set of event channel descriptions to convert.
     * @return an array of the event channel names derived from the specified
     *         set of descriptions.
     */
    public static String[] toEventChannelNames(EventChannelDescription[] eventChannelDescriptions) {
        if (eventChannelDescriptions == null) {
            return new String[0];
        }

        String[] names = new String[eventChannelDescriptions.length];

        for (int i = 0; i < eventChannelDescriptions.length; i++) {
            names[i] = eventChannelDescriptions[i].getEventChannelName();
        }

        return names;
    }
}
