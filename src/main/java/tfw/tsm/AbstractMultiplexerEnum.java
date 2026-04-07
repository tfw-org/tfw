package tfw.tsm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import tfw.tsm.ecd.EventChannelDescription;

public abstract class AbstractMultiplexerEnum<T extends EventChannelDescription, U extends EventChannelDescription>
        extends DefaultMultiplexerConfig<T, U> implements MultiplexerEnum<T, U> {
    private static final Map<Class<?>, List<Object>> REGISTRY = new ConcurrentHashMap<>();

    protected AbstractMultiplexerEnum(
            T ecd, U mecd, MultiplexerStrategy strategy, StateChangeRule rule, Object state, String[] tags) {
        super(ecd, mecd, strategy, rule, state, tags);

        REGISTRY.computeIfAbsent(this.getClass(), k -> new CopyOnWriteArrayList<>())
                .add(this);
    }

    public static <
                    T extends
                            AbstractMultiplexerEnum<
                                            ? extends EventChannelDescription, ? extends EventChannelDescription>>
            List<T> valuesFromClass(Class<T> enumClass) {
        List<Object> list = REGISTRY.get(enumClass);

        return list.stream().map(enumClass::cast).collect(Collectors.toList());
    }
}
