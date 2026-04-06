package tfw.tsm;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import tfw.tsm.ecd.EventChannelDescription;

public abstract class AbstractEventChannelEnum<T extends EventChannelDescription> extends DefaultEventChannelConfig<T>
        implements EventChannelEnum<T> {
    private static final Map<Class<?>, List<Object>> REGISTRY = new ConcurrentHashMap<>();

    protected AbstractEventChannelEnum(T ecd, StateChangeRule rule, Object state, String[] tags) {
        super(ecd, rule, state, tags);

        REGISTRY.computeIfAbsent(this.getClass(), k -> new CopyOnWriteArrayList<>())
                .add(this);
    }

    public static <T extends AbstractEventChannelEnum<? extends EventChannelDescription>> List<T> valuesFromClass(
            Class<T> enumClass) {
        List<Object> list = REGISTRY.get(enumClass);

        return list == null
                ? Collections.emptyList()
                : list.stream().map(enumClass::cast).collect(Collectors.toList());
    }
}
