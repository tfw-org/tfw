package tfw.tsm;

import tfw.tsm.ecd.EventChannelDescription;

public interface MultiplexerConfig<T extends EventChannelDescription, U extends EventChannelDescription> {
    T getEventChannelDescription();

    U getMultiEventChannelDescription();

    MultiplexerStrategy getMultiplexerStrategy();

    Object getInitialMultiState();

    StateChangeRule getStateChangeRule();

    String[] getExportTags();
}
