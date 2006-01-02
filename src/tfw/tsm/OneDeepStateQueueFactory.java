/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * witout even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.tsm;

import java.util.NoSuchElementException;

/**
 * A factory for creating one deep queue's.
 */
public class OneDeepStateQueueFactory implements StateQueueFactory
{
    /**
     * Returns a new instance of a queue
     */
    public StateQueue create()
    {
        return new OneDeepStateQueue();
    }

    /**
     * A state queue that has a depth of one.
     */
    private static class OneDeepStateQueue implements StateQueue
    {
        private Object state = null;

        private boolean empty = true;

        /**
         * Returns the current state
         * 
         * @return the current state.
         * @throws NoSuchElementException
         *             if the queue is empty.
         */
        public Object pop() throws NoSuchElementException
        {
            if (empty)
            {
                throw new NoSuchElementException("Queue is empty");
            }
            empty = true;
            return state;
        }

        /**
         * Puts the specified value on the queue, over writing any previously
         * pushed state that hasn't been removed.
         */
        public void push(Object state)
        {
            this.state = state;
            this.empty = false;
        }

        public boolean isEmpty()
        {
            return empty;
        }
    }
}
