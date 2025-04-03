package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

final class OneDeepStateQueueFactoryTest {
    @Test
    void factoryTest() {
        StateQueueFactory factory = new OneDeepStateQueueFactory();
        StateQueue queue = factory.create();
        assertThat(queue).isNotNull();
        assertThat(queue.isEmpty()).isTrue();

        assertThatThrownBy(queue::pop)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Queue is empty");

        Object state = new Object();
        queue.push(state);

        assertThat(queue.isEmpty()).isFalse();
        assertThat(state).isEqualTo(queue.pop());
        assertThat(queue.isEmpty()).isTrue();
    }
}
