package tfw.tsm;

import java.util.List;
import tfw.tsm.ecd.EventChannelDescription;

public interface EventChannelEnum<T extends EventChannelDescription> extends EventChannelConfig<T> {
    List<EventChannelEnum<T>> values();
}
