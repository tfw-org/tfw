package tfw.swing.event;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.Callable;
import javax.swing.JButton;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import tfw.awt.ecd.ColorECD;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
final class SetForegroundFactoryTest {
    private static final String TEST_NAME = "TestName";
    private static final ColorECD COLOR_ECD = new ColorECD("TestColor");

    @BeforeAll
    static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Test
    void argumentsTest() {
        final JButton jButton = GuiActionRunner.execute((Callable<JButton>) JButton::new);

        assertThatThrownBy(() -> SetForegroundFactory.create(null, COLOR_ECD, jButton, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> SetForegroundFactory.create(TEST_NAME, null, jButton, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("colorECD == null not allowed!");
        assertThatThrownBy(() -> SetForegroundFactory.create(TEST_NAME, COLOR_ECD, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("jComponent == null not allowed!");
    }
}
