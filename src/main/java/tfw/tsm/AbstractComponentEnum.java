package tfw.tsm;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public abstract class AbstractComponentEnum<T extends TreeComponent> extends DefaultComponentConfig<T>
        implements ComponentEnum<T> {
    private static final Map<Class<?>, List<Object>> REGISTRY = new ConcurrentHashMap<>();

    protected AbstractComponentEnum(T component) {
        super(component);

        REGISTRY.computeIfAbsent(this.getClass(), k -> new CopyOnWriteArrayList<>())
                .add(this);
    }

    public static <T extends AbstractComponentEnum<? extends TreeComponent>> List<T> valuesFromClass(
            Class<T> enumClass) {
        List<Object> list = REGISTRY.get(enumClass);

        if (list == null) {
            return Collections.emptyList();
        }

        return list.stream().map(enumClass::cast).collect(Collectors.toList());
    }
}
