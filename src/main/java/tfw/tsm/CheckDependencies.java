package tfw.tsm;

import java.util.List;

public interface CheckDependencies {
    void checkDependencies(List<Processor> processors, List<Processor> delayedProcessors, boolean logging);

    void clearCache();
}
