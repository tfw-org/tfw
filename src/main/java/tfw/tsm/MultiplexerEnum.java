package tfw.tsm;

import tfw.tsm.ecd.EventChannelDescription;

public interface MultiplexerEnum<T extends EventChannelDescription, U extends EventChannelDescription>
        extends MultiplexerConfig<T, U> {}
