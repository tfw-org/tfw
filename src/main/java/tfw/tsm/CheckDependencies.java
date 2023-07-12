package tfw.tsm;

import java.util.List;
import java.util.logging.Logger;

public interface CheckDependencies {
    void checkDependencies(List<Processor> processors, List<Processor> delayedProcessors, Logger logger);

    void clearCache();
}
