package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/**
 *
 */
class OneDeepStateQueueFactoryTest {
    @Test
    void testFactory() {
        StateQueueFactory factory = new OneDeepStateQueueFactory();
        StateQueue queue = factory.create();
        assertNotNull(queue, "factory return null");
        assertTrue(queue.isEmpty(), "isEmpty() == false when empty");
        try {
            queue.pop();
            fail("pop() on an empty queue didn't throw exception!");
        } catch (NoSuchElementException expected) {
            // System.out.println(expected);
        }

        Object state = new Object();
        queue.push(state);
        assertFalse(queue.isEmpty(), "isEmpty() == true when empty");
        assertEquals(state, queue.pop(), "push/pop returned the wrong value!");
        assertTrue(queue.isEmpty(), "isEmpty() == false after pop()");
    }
}
