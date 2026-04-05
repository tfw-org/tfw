package tfw.tsm;

import java.util.List;
import tfw.tsm.ecd.EventChannelDescription;

public interface MultiplexerEnum<T extends EventChannelDescription, U extends EventChannelDescription>
        extends MultiplexerConfig<T, U> {
    List<MultiplexerEnum<T, U>> getValues();
}
