package tfw.tsm;

import com.google.common.flogger.FluentLogger;
import com.google.common.flogger.context.LogLevelMap;
import com.google.common.flogger.context.ScopedLoggingContexts;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class DefaultCheckDependencies implements CheckDependencies {
    private static final FluentLogger LOGGER = FluentLogger.forEnclosingClass();
    private static final LogLevelMap DEBUG_LEVELS = LogLevelMap.builder()
            .setDefault(Level.FINE) // Force all logs at FINE and above
            .build();

    private final Map<Processor, Map<Processor, Boolean>> processorCache = new HashMap<>();

    private static int origProcessorsArraySize = 0;
    private static Processor[] origProcessorsArray = new Processor[origProcessorsArraySize];
    private static int processorsArraySize = 0;
    private static Processor[] processorsArray = new Processor[processorsArraySize];
    private static int toProcessorsSize = 0;
    private static Processor[] toProcessors = new Processor[toProcessorsSize];
    private static Set<Processor> processorCrumbs = new HashSet<>();
    private static Set<EventChannel> terminatorCrumbs = new HashSet<>();

    @Override
    public void checkDependencies(List<Processor> processors, List<Processor> delayedProcessors, boolean logging) {
        if (logging) {
            ScopedLoggingContexts.newContext()
                    .withLogLevelMap(DEBUG_LEVELS)
                    .run(() -> privateCheckDependencies(processors, delayedProcessors));
        } else {
            privateCheckDependencies(processors, delayedProcessors);
        }
    }

    public void privateCheckDependencies(List<Processor> processors, List<Processor> delayedProcessors) {
        LOGGER.atInfo().log(
                "CDN: p.s=%d dp.s=%d c.s=%d", processors.size(), delayedProcessors.size(), processorCache.size());

        origProcessorsArraySize = processors.size();
        if (origProcessorsArray.length < origProcessorsArraySize) {
            origProcessorsArray = new Processor[origProcessorsArraySize];
        }
        processors.toArray(origProcessorsArray);

        for (int i = 0; i < origProcessorsArraySize; i++) {
            Processor origProcessor = origProcessorsArray[i];

            // if the ith processor has not already been delayed...
            if (processors.contains(origProcessor)) {
                boolean skipDependencyCheck = false;
                Map<Processor, Boolean> map = processorCache.get(origProcessor);
                if (map != null) {
                    skipDependencyCheck = true;
                    toProcessorsSize = processors.size();
                    if (toProcessors.length < toProcessorsSize) {
                        toProcessors = new Processor[toProcessorsSize];
                    }
                    processors.toArray(toProcessors);

                    for (int j = 0; j < toProcessorsSize; j++) {
                        Processor p = toProcessors[j];

                        if (p == origProcessor) {
                            continue;
                        }

                        Boolean b = map.get(p);

                        if (b == null) {
                            skipDependencyCheck = false;
                            break;
                        } else if (b.booleanValue()) {
                            processors.remove(p);
                            delayedProcessors.add(p);
                        }
                    }
                }

                if (!skipDependencyCheck) {
                    processorCrumbs.clear();
                    terminatorCrumbs.clear();

                    checkDependenciesNew(
                            origProcessor,
                            origProcessor,
                            processors,
                            delayedProcessors,
                            processorCrumbs,
                            terminatorCrumbs,
                            processorCache,
                            2,
                            null);

                    map = processorCache.get(origProcessor);

                    processorsArraySize = processors.size();
                    if (processorsArray.length < processorsArraySize) {
                        processorsArray = new Processor[processorsArraySize];
                    }
                    processors.toArray(processorsArray);

                    for (int j = 0; j < processorsArraySize; j++) {
                        Processor p = processorsArray[j];

                        if (p != origProcessor) {
                            if (map == null) {
                                map = new HashMap<>();
                                processorCache.put(origProcessor, map);
                            }
                            map.put(p, Boolean.FALSE);
                        }
                    }
                }
            }
        }
    }

    private static void checkDependenciesNew(
            Processor fromProcessor,
            Processor toProcessor,
            List<Processor> processors,
            List<Processor> delayedProcessors,
            Set<Processor> visitedProcessors,
            Set<EventChannel> visitedEventChannels,
            Map<Processor, Map<Processor, Boolean>> cache,
            int spaces,
            List<Processor> dependencyChain) {
        if (visitedProcessors.contains(toProcessor)) {
            return;
        }

        if (fromProcessor != toProcessor && processors.remove(toProcessor)) {
            delayedProcessors.add(toProcessor);

            Map<Processor, Boolean> map = cache.get(fromProcessor);
            if (map == null) {
                map = new HashMap<>();
                cache.put(fromProcessor, map);
            }

            map.put(toProcessor, Boolean.TRUE);
            if (dependencyChain != null) {
                StringBuilder sb = new StringBuilder();
                for (Processor p : dependencyChain) {
                    sb.append(p.getName());
                    sb.append(", ");
                }
                LOGGER.atFine().log("%s", sb);
            }
        }

        visitedProcessors.add(toProcessor);

        List<Source> sources = toProcessor.getSources();
        for (int i = 0; i < sources.size(); i++) {
            Source source = sources.get(i);
            EventChannel eventChannel = source.eventChannel;

            checkDependenciesNew(
                    fromProcessor,
                    eventChannel,
                    processors,
                    delayedProcessors,
                    visitedProcessors,
                    visitedEventChannels,
                    cache,
                    spaces + 2,
                    dependencyChain);
        }
    }

    private static void checkDependenciesNew(
            Processor fromProcessor,
            EventChannel eventChannel,
            List<Processor> processors,
            List<Processor> delayedProcessors,
            Set<Processor> visitedProcessors,
            Set<EventChannel> visitedEventChannels,
            Map<Processor, Map<Processor, Boolean>> cache,
            int spaces,
            List<Processor> dependencyChain) {
        if (visitedEventChannels.contains(eventChannel)) {
            return;
        }

        visitedEventChannels.add(eventChannel);

        if (eventChannel instanceof DemultiplexedEventChannel) {
            DemultiplexedEventChannel demultiplexedEventChannel = (DemultiplexedEventChannel) eventChannel;
            Source multiSource = demultiplexedEventChannel.getMultiplexer().processorMultiSource;

            checkDependenciesNew(
                    fromProcessor,
                    multiSource.eventChannel,
                    processors,
                    delayedProcessors,
                    visitedProcessors,
                    visitedEventChannels,
                    cache,
                    spaces + 2,
                    dependencyChain);
        }

        Terminator terminator = (Terminator) eventChannel;
        Sink[] sinks = terminator.getSinks();

        for (int i = 0; i < sinks.length; i++) {
            if (sinks[i] instanceof Multiplexer.MultiSink) {
                Multiplexer.MultiSink multiSink = (Multiplexer.MultiSink) sinks[i];

                for (Iterator<DemultiplexedEventChannel> d = multiSink.getDemultiplexedEventChannels(); d.hasNext(); ) {
                    DemultiplexedEventChannel demultiplexedEventChannel = d.next();

                    checkDependenciesNew(
                            fromProcessor,
                            demultiplexedEventChannel,
                            processors,
                            delayedProcessors,
                            visitedProcessors,
                            visitedEventChannels,
                            cache,
                            spaces + 4,
                            dependencyChain);
                }
            } else {
                TreeComponent treeComponent = sinks[i].getTreeComponent();

                if (treeComponent instanceof Processor) {
                    if (dependencyChain != null) {
                        dependencyChain.add((Processor) treeComponent);
                    }

                    checkDependenciesNew(
                            fromProcessor,
                            (Processor) treeComponent,
                            processors,
                            delayedProcessors,
                            visitedProcessors,
                            visitedEventChannels,
                            cache,
                            spaces + 4,
                            dependencyChain);

                    if (dependencyChain != null) {
                        dependencyChain.remove(dependencyChain.size() - 1);
                    }
                }
            }
        }
    }

    @Override
    public void clearCache() {
        processorCache.clear();
    }
}
