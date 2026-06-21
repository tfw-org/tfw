package tfw.tsm;

import tfw.check.Arguments;

public class DefaultComponentConfig<T extends TreeComponent> implements ComponentConfig<T> {
    public final T component;

    public DefaultComponentConfig(final T component) {
        Arguments.checkNotNull(component, "component");

        this.component = component;
    }

    @Override
    public T getComponent() {
        return component;
    }
}
