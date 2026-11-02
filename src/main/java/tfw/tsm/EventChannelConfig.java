package tfw.tsm;

import tfw.tsm.ecd.EventChannelDescription;

public interface EventChannelConfig<T extends EventChannelDescription> {
    T getEventChannelDescription();

    Object getInitialState();

    StateChangeRule getStateChangeRule();

    String[] getExportTags();
}
