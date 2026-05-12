package tfw.immutable.ilmf.objectilmf;

import tfw.immutable.ilm.objectilm.ObjectIlm;

/**
 * This interface defines a factory that creates ObjectIla objects.
 */
public interface ObjectIlmFactory<T> {
    /**
     * Create a ObjectIlm object.
     *
     * @return the ObjectIlm object.
     */
    ObjectIlm<T> create();
}
// AUTO GENERATED FROM TEMPLATE
