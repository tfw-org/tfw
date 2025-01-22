package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class DifferentObjectRuleTest {
    @Test
    void isChangeTest() {
        StateChangeRule rule = DifferentObjectRule.RULE;
        Object currentState = new Object();
        Object newState = new Object();

        assertThat(rule.isChange(currentState, newState)).isTrue();
        assertThat(rule.isChange(currentState, currentState)).isFalse();
        assertThat(rule.isChange(null, newState)).isTrue();

        assertThatThrownBy(() -> rule.isChange(currentState, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("newState == null not allowed!");
    }
}
