package tfw.swing.event;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.Callable;
import javax.swing.JButton;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import tfw.awt.ecd.FontECD;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
final class SetFontFactoryTest {
    private static final String TEST_NAME = "TestName";
    private static final FontECD FONT_ECD = new FontECD("TestFont");

    @BeforeAll
    static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Test
    void argumentsTest() {
        final JButton jButton = GuiActionRunner.execute((Callable<JButton>) JButton::new);

        assertThatThrownBy(() -> SetFontFactory.create(null, FONT_ECD, jButton, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> SetFontFactory.create(TEST_NAME, null, jButton, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("fontECD == null not allowed!");
        assertThatThrownBy(() -> SetFontFactory.create(TEST_NAME, FONT_ECD, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("jComponent == null not allowed!");
    }
}
