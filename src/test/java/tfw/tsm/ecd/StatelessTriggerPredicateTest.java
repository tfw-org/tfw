package tfw.tsm.ecd;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class StatelessTriggerPredicateTest {
    @Test
    void testTest() {
        StatelessTriggerPredicate predicate = new StatelessTriggerPredicate();

        assertThat(predicate.test(null)).isTrue();
        assertThat(predicate.test(Boolean.TRUE)).isFalse();
        assertThat(predicate.test(Boolean.FALSE)).isFalse();
        assertThat(predicate.test("trigger")).isFalse();
        assertThat(predicate.test(Integer.valueOf(1))).isFalse();
    }

    @Test
    void equalsTest() {
        StatelessTriggerPredicate predicate1 = new StatelessTriggerPredicate();
        StatelessTriggerPredicate predicate2 = new StatelessTriggerPredicate();

        assertThat(predicate1).isEqualTo(predicate2);
        assertThat(predicate2).isEqualTo(predicate1);
        assertThat(predicate1.hashCode()).isEqualTo(predicate2.hashCode());
    }

    @Test
    void equalsDifferentTypeTest() {
        StatelessTriggerPredicate predicate = new StatelessTriggerPredicate();

        assertThat(predicate).isNotEqualTo(null);
        assertThat(predicate).isNotEqualTo(new Object());
        assertThat(predicate).isNotEqualTo(new IsAssignableFromPredicate(Object.class));
    }
}
