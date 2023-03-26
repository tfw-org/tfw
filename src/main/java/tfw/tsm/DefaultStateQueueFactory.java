package tfw.tsm;

import java.util.ArrayList;
import java.util.NoSuchElementException;


/**
 * A factory for creating basic queues.
 */
class DefaultStateQueueFactory implements StateQueueFactory
{
    /**
     * @see co2.ui.fw.StateQueueFactory#create()
     */
    public StateQueue create()
    {
        return new BasicStateQueue();
    }

	/**
	 * The basic state queue. Queues multiple state values to an arbitrary depth.
	 */
    private static class BasicStateQueue implements StateQueue
    {
        ArrayList<Object> queue = new ArrayList<Object>();

        /**
        * @see co2.ui.fw.StateQueue#isEmpty()
        */
        public boolean isEmpty()
        {
            return queue.size() == 0;
        }

        /**
         * @see co2.ui.fw.StateQueue#pop()
         */
        public Object pop() throws NoSuchElementException
        {
            if (isEmpty())
            {
                throw new NoSuchElementException("Queue is empty.");
            }

            return queue.remove(0);
        }

        /**
         * @see co2.ui.fw.StateQueue#push(java.lang.Object)
         */
        public void push(Object state)
        {
            queue.add(state);
        }
    }
}
