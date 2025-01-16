package tfw.swing.event;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.swing.JButton;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import tfw.swing.ecd.IconECD;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
final class SetIconFactoryTest {
    private static final String TEST_NAME = "TestName";
    private static final IconECD ICON_ECD = new IconECD("TestIcon");

    @BeforeAll
    static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Test
    void argumentsTest() {
        final JButton jButton = GuiActionRunner.execute(() -> new JButton());

        assertThatThrownBy(() -> SetIconFactory.create(null, ICON_ECD, jButton, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> SetIconFactory.create(TEST_NAME, null, jButton, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("iconECD == null not allowed!");
        assertThatThrownBy(() -> SetIconFactory.create(TEST_NAME, ICON_ECD, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("abstractButton == null not allowed!");
    }
}
