package tfw.tsm;

public interface ComponentFactoryConfig<T extends TreeComponent> {
    T createComponent();
}
