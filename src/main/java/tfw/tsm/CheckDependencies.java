package tfw.tsm;

import java.util.List;
import java.util.logging.Logger;

public interface CheckDependencies {
    public void checkDependencies(List<Processor> processors, List<Processor> delayedProcessors, Logger logger);

    public void clearCache();
}
