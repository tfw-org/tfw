package tfw.tsm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public abstract class AbstractComponentFactoryEnum<T extends TreeComponent> implements ComponentFactoryEnum<T> {
    private static final Map<Class<?>, List<Object>> REGISTRY = new ConcurrentHashMap<>();

    protected AbstractComponentFactoryEnum() {
        REGISTRY.computeIfAbsent(
                        findSubClass(this.getClass(), AbstractComponentFactoryEnum.class),
                        k -> new CopyOnWriteArrayList<>())
                .add(this);
    }

    public static <T extends AbstractComponentFactoryEnum<? extends TreeComponent>> List<T> valuesFromClass(
            Class<T> enumClass) {
        List<Object> list = REGISTRY.get(enumClass);

        if (list == null) {
            return Collections.emptyList();
        }

        return list.stream().map(enumClass::cast).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T extends TreeComponent> List<T> instancesFromClass(Class<?> enumClass) {
        List<Object> list = REGISTRY.get(enumClass);

        if (list == null) {
            return Collections.emptyList();
        }

        List<T> instanceList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            final ComponentFactoryConfig<T> f = (ComponentFactoryConfig<T>) list.get(i);

            instanceList.add(f.createComponent());
        }

        return instanceList;
    }

    private static Class<?> findSubClass(final Class<?> startClass, final Class<?> targetParentClass) {
        for (Class<?> c = startClass; c != null && c != Object.class; c = c.getSuperclass()) {
            if (c.getSuperclass() == targetParentClass) {
                return c;
            }
        }

        return null;
    }
}
