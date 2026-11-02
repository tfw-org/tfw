package tfw.tsm.ecd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class IntegerRangePredicateTest {
    @Test
    void constructionTest() {
        assertThatThrownBy(() -> new IntegerRangePredicate(10, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("min (=10) > max (=0) not allowed!");

        assertThatThrownBy(() -> new IntegerRangePredicate(1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("min (=1) > max (=0) not allowed!");
    }

    @Test
    void testTest() {
        IntegerRangePredicate predicate = new IntegerRangePredicate(10, 20);

        assertThat(predicate.test(9)).isFalse();
        assertThat(predicate.test(10)).isTrue();
        assertThat(predicate.test(15)).isTrue();
        assertThat(predicate.test(20)).isTrue();
        assertThat(predicate.test(21)).isFalse();

        assertThat(predicate.test(null)).isFalse();
        assertThat(predicate.test("15")).isFalse();
    }

    @Test
    void equalsTest() {
        IntegerRangePredicate predicate1 = new IntegerRangePredicate(10, 20);
        IntegerRangePredicate predicate2 = new IntegerRangePredicate(10, 20);

        assertThat(predicate1).isEqualTo(predicate2);
        assertThat(predicate2).isEqualTo(predicate1);
        assertThat(predicate1).hasSameHashCodeAs(predicate2);

        predicate2 = new IntegerRangePredicate(10, 21);

        assertThat(predicate1).isNotEqualTo(predicate2);

        predicate2 = new IntegerRangePredicate(11, 20);

        assertThat(predicate1).isNotEqualTo(predicate2);
    }

    @Test
    void equalsDifferentTypeTest() {
        IntegerRangePredicate predicate = new IntegerRangePredicate(10, 20);

        assertThat(predicate)
                .isNotEqualTo(null)
                .isNotEqualTo(new Object())
                .isNotEqualTo(new IsAssignableFromPredicate(Integer.class));
    }
}
