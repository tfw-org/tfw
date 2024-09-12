package tfw.tsm.ecd;

import java.util.HashSet;

public class EventChannelDescriptionUtil {
    private EventChannelDescriptionUtil() {}

    public static EventChannelDescription[] create(EventChannelDescription[] ecd) {
        if (ecd == null) throw new IllegalArgumentException("ecd == null not allowed!");

        HashSet<EventChannelDescription> set = new HashSet<>();
        for (int i = 0; i < ecd.length; i++) {
            if (ecd[i] != null) {
                set.add(ecd[i]);
            }
        }

        return set.toArray(new EventChannelDescription[set.size()]);
    }
}
