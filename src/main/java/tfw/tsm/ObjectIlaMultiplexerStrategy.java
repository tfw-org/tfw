package tfw.tsm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;

/**
 * A strategy for multiplexing/de-multiplexing an {@link ObjectIla} event
 * channel.
 */
public class ObjectIlaMultiplexerStrategy implements MultiplexerStrategy {
    /**
     * @see tfw.tsm.MultiplexerStrategy#toMultiStateAccessor(java.lang.Object)
     */
    public MultiStateAccessor toMultiStateAccessor(Object multiState) {
        return new MyMultiStateAccessor(
                (multiState == null) ? ObjectIlaFromArray.create(new Object[0]) : (ObjectIla) multiState);
    }

    public Object getDefaultSlotState() {
        return null;
    }

    private class MyMultiStateAccessor implements MultiStateAccessor {

        private final Object[] objs;

        MyMultiStateAccessor(ObjectIla ila) {
            try {
                this.objs = new Object[(int) ila.length()];

                ila.toArray(objs, 0, 0, objs.length);
            } catch (DataInvalidException e) {
                throw new RuntimeException("Exception occurred accessing multiplexed state:" + e.getMessage(), e);
            }
        }

        /**
         * @see tfw.tsm.MultiplexerStrategy.MultiStateAccessor#getState(java.lang.Object)
         */
        public Object getState(Object key) {
            int index = ((Integer) key).intValue();
            if ((index >= 0) && (index < this.objs.length)) {
                return objs[index];
            }
            return null;
        }
    }

    @Override
    public Object addToMultiState(
            Object originalMultiState, Object[] keys, Object[] values, int numberOfKeyValuePairs) {
        Object[] array = null;

        if (originalMultiState == null) {
            array = new Object[0];
        } else {
            try {
                ObjectIla objectIla = (ObjectIla) originalMultiState;
                array = new Object[(int) objectIla.length()];

                objectIla.toArray(array, 0, 0, array.length);
            } catch (DataInvalidException e) {
                throw new RuntimeException("Exception occurred accessing multiplexed state:" + e.getMessage(), e);
            }
        }

        for (int i = 0; i < numberOfKeyValuePairs; i++) {
            int index = ((Integer) keys[i]).intValue();

            if (array.length - 1 < index) {
                Object[] newArray = new Object[index + 1];
                System.arraycopy(array, 0, newArray, 0, array.length);
                array = newArray;
            }

            array[index] = values[i];
        }

        return ObjectIlaFromArray.create(array, false);
    }
}
