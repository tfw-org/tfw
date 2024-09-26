package tfw.tsm;

import java.util.NoSuchElementException;

/**
 * A factory for creating one deep queue's.
 */
public class OneDeepStateQueueFactory implements StateQueueFactory {
    /**
     * Returns a new instance of a queue
     */
    @Override
    public StateQueue create() {
        return new OneDeepStateQueue();
    }

    /**
     * A state queue that has a depth of one.
     */
    private static class OneDeepStateQueue implements StateQueue {
        private Object state = null;

        private boolean empty = true;

        /**
         * Returns the current state
         *
         * @return the current state.
         * @throws NoSuchElementException
         *             if the queue is empty.
         */
        @Override
        public Object pop() throws NoSuchElementException {
            if (empty) {
                throw new NoSuchElementException("Queue is empty");
            }
            empty = true;
            return state;
        }

        /**
         * Puts the specified value on the queue, over writing any previously
         * pushed state that hasn't been removed.
         */
        @Override
        public void push(Object state) {
            this.state = state;
            this.empty = false;
        }

        @Override
        public boolean isEmpty() {
            return empty;
        }
    }
}
