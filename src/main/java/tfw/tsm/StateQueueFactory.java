package tfw.tsm;

/**
 * A factory used by {@link Initiator} to create a {@link StateQueue} for each
 * source event channel.
 */
public interface StateQueueFactory {
    /**
     * Returns a new state queue.
     *
     * @return a new state queue.
     */
    public StateQueue create();
}
