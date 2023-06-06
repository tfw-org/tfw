package tfw.tsm;

/**
 * A strategy for multiplexing and demultiplexing state.
 */
public interface MultiplexerStrategy {
    /**
     * Provides an accessor given a multi-state object.
     *
     * @param multiState
     *            The multi-state object to be accessed.
     * @return An accessor initialized with the specified state.
     */
    public MultiStateAccessor toMultiStateAccessor(Object multiState);

    public Object getDefaultSlotState();

    /**
     * An interface for accessing a multiplexed state object.
     */
    public interface MultiStateAccessor {
        /**
         * Gets the state associated with the specified key.
         *
         * @param key
         *            The key to use in find an associated state value.
         * @return The value associated with the key or <code>null</code> if
         *         no state is associated with the specified key.
         */
        public Object getState(Object key);
    }

    public Object addToMultiState(Object originalMultiState, Object[] keys, Object[] values, int numberOfKeyValues);
}
