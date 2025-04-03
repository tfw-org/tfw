package tfw.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class RangeConstraintTest {
    @Test
    void constructionNullMinimumTest() {
        assertThatThrownBy(() -> new TestIntegerRangeConstraint(Integer.class, null, 10, true, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("min == null not allowed!");
    }

    @Test
    void constructionNullMaximumTest() {
        assertThatThrownBy(() -> new TestIntegerRangeConstraint(Integer.class, 0, null, true, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("max == null not allowed!");
    }

    @Test
    void constructorMinGreaterThanMaxTest() {
        assertThatThrownBy(() -> new TestIntegerRangeConstraint(Integer.class, 10, 0, true, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("min > max not allowed!");
    }

    @Test
    void constructorMinEqualMaxNeitherInclusiveTest() {
        assertThatThrownBy(() -> new TestIntegerRangeConstraint(Integer.class, 0, 0, false, false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Empty range, min == max and neither are inclusive");
    }

    @Test
    void isCompatibleTest() {
        RangeConstraint<Integer> rc = new TestIntegerRangeConstraint(Integer.class, 0, 2, false, false);
        assertThat(rc.isCompatible(null)).isFalse();

        RangeConstraint<Integer> rcLow = new TestIntegerRangeConstraint(Integer.class, 0, 0, true, true);
        assertThat(rc.isCompatible(rcLow)).isFalse();

        RangeConstraint<Integer> rcHigh = new TestIntegerRangeConstraint(Integer.class, 2, 2, true, true);
        assertThat(rc.isCompatible(rcHigh)).isFalse();

        RangeConstraint<Integer> rcMiddle = new TestIntegerRangeConstraint(Integer.class, 1, 1, true, true);
        assertThat(rc.isCompatible(rcMiddle)).isTrue();

        assertThat(rc.isCompatible(rc)).isTrue();
    }

    @Test
    void valueComplianceTest() {
        RangeConstraint<Integer> rc = new TestIntegerRangeConstraint(Integer.class, 0, 2, false, false);
        String answer = RangeConstraint.VALID;
        String result = rc.getValueCompliance(1);
        assertThat(answer).isEqualTo(result);

        Integer value = -1;
        answer = "value = '" + value + "' is out of range, must be greater than '0'";
        result = rc.getValueCompliance(value);
        assertThat(answer).isEqualTo(result);

        value = 0;
        answer = "value = '" + value + "' is out of range, must be greater than '0'";
        result = rc.getValueCompliance(value);
        assertThat(answer).isEqualTo(result);

        value = 3;
        answer = "value = '" + value + "' is out of range, must be less than '2'";
        result = rc.getValueCompliance(value);
        assertThat(answer).isEqualTo(result);

        value = -1;
        answer = "value = '" + value + "' is out of range, must be greater than or equal to '0'";
        rc = new TestIntegerRangeConstraint(Integer.class, 0, 2, true, true);
        result = rc.getValueCompliance(value);
        assertThat(answer).isEqualTo(result);

        value = 3;
        answer = "value = '" + value + "' is out of range, must be less than or equal to '2'";
        result = rc.getValueCompliance(value);
        assertThat(answer).isEqualTo(result);
    }

    private static class TestIntegerRangeConstraint extends RangeConstraint<Integer> {
        public TestIntegerRangeConstraint(
                Class<Integer> valueType, Integer min, Integer max, boolean minInclusive, boolean maxInclusive) {
            super(valueType, min, max, minInclusive, maxInclusive);
        }

        @Override
        protected int compareTo(Integer left, Integer right) {
            return left.compareTo(right);
        }
    }
}
