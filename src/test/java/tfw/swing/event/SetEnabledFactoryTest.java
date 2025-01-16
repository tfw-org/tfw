package tfw.swing.event;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.swing.JButton;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import tfw.tsm.ecd.BooleanECD;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
final class SetEnabledFactoryTest {
    private static final String TEST_NAME = "TestName";
    private static final BooleanECD ENABLED_ECD = new BooleanECD("TestEnabled");

    @BeforeAll
    static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Test
    void argumentsTest() {
        final JButton jButton = GuiActionRunner.execute(() -> new JButton());

        assertThatThrownBy(() -> SetEnabledFactory.create(null, ENABLED_ECD, jButton, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> SetEnabledFactory.create(TEST_NAME, null, jButton, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("enabledECD == null not allowed!");
        assertThatThrownBy(() -> SetEnabledFactory.create(TEST_NAME, ENABLED_ECD, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("component == null not allowed!");
    }
}
