package tfw.immutable.ilaf.bitilaf;

import tfw.immutable.ila.bitila.BitIla;

/**
 * This interface defines a factory that creates BooleanIla objects.
 */
public interface BitIlaFactory {
    /**
     * Create a BooleanIla object.
     *
     * @return the BooleanIla object.
     */
    BitIla create();
}
