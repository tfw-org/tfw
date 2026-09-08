package tfw.tsm.ecd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class IsAssignableFromPredicateTest {
    @Test
    void constructionTest() {
        assertThatThrownBy(() -> new IsAssignableFromPredicate(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("clazz (=null) == null not allowed!");
    }

    @Test
    void testTest() {
        IsAssignableFromPredicate predicate = new IsAssignableFromPredicate(String.class);

        assertThat(predicate.test("string")).isTrue();
        assertThat(predicate.test(new StringBuilder())).isFalse();
        assertThat(predicate.test(null)).isFalse();

        predicate = new IsAssignableFromPredicate(CharSequence.class);

        assertThat(predicate.test("string")).isTrue();
        assertThat(predicate.test(new StringBuilder())).isTrue();
        assertThat(predicate.test(Integer.valueOf(1))).isFalse();
        assertThat(predicate.test(null)).isFalse();
    }

    @Test
    void equalsTest() {
        IsAssignableFromPredicate predicate1 = new IsAssignableFromPredicate(String.class);
        IsAssignableFromPredicate predicate2 = new IsAssignableFromPredicate(String.class);

        assertThat(predicate1).isEqualTo(predicate2);
        assertThat(predicate2).isEqualTo(predicate1);
        assertThat(predicate1.hashCode()).isEqualTo(predicate2.hashCode());

        predicate2 = new IsAssignableFromPredicate(Integer.class);

        assertThat(predicate1).isNotEqualTo(predicate2);
    }

    @Test
    void equalsDifferentTypeTest() {
        IsAssignableFromPredicate predicate = new IsAssignableFromPredicate(String.class);

        assertThat(predicate).isNotEqualTo(null);
        assertThat(predicate).isNotEqualTo(new Object());
    }
}
