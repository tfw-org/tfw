package tfw.tsm;

import java.util.NoSuchElementException;

/**
 * Defines a queue which implements the state queuing strategy for an
 * {@link Initiator}. See {@link StateQueueFactory}.
 */
public interface StateQueue {
    /**
     * Puts the specified state at the end of the queue.
     * @param state the state to be added to the queue.
     */
    void push(Object state);

    /**
     * Removes the state currently at the top of the queue.
     * @return the state currently at the top of the queue.
     * @throws NoSuchElementException if the queue is empty.
     */
    Object pop() throws NoSuchElementException;

    /**
     * Returns true if the queue is empty.
     * @return true if the queue is empty.
     */
    boolean isEmpty();
}
