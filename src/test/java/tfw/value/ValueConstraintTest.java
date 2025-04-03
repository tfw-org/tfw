package tfw.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ValueConstraintTest {
    private static final class TestConstraint extends ClassValueConstraint<Object> {
        public TestConstraint(Class<Object> valueType) {
            super(valueType);
        }
    }

    @Test
    void nullValueTypeTest() {
        assertThatThrownBy(() -> new TestConstraint(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("valueType == null not allowed!");
    }

    @Test
    void getValueComplianceTest() {
        ClassValueConstraint<Integer> vc = ClassValueConstraint.getInstance(Integer.class);
        String result = vc.getValueCompliance(0);
        assertThat(result).isEqualTo(ClassValueConstraint.VALID);
        result = vc.getValueCompliance(null);
        assertThat(result).isNotEqualTo(ClassValueConstraint.VALID);
        result = vc.getValueCompliance(new Object());
        assertThat(result).isNotEqualTo(ClassValueConstraint.VALID);
    }

    @Test
    void isValidTest() {
        ClassValueConstraint<Integer> vc = ClassValueConstraint.getInstance(Integer.class);
        assertThat(vc.isValid(1)).isTrue();
    }

    @Test
    void checkValueTest() {
        ClassValueConstraint<Integer> vc = ClassValueConstraint.getInstance(Integer.class);

        final Object v = new Object();

        assertThatThrownBy(() -> vc.checkValue(v))
                .isInstanceOf(ValueException.class)
                .hasMessage("The value, of type 'java.lang.Object', is not assignable to type 'java.lang.Integer'.");

        vc.checkValue(0);
    }
}
