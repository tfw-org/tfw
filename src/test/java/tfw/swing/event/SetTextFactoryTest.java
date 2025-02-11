package tfw.swing.event;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.Callable;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import tfw.tsm.ecd.StringECD;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
final class SetTextFactoryTest {
    private static final String TEST_NAME = "TestName";
    private static final StringECD TEXT_ECD = new StringECD("TestText");

    @BeforeAll
    static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Test
    void argumentsTest() {
        final JButton jButton = GuiActionRunner.execute((Callable<JButton>) JButton::new);
        final JTextField jTextField = GuiActionRunner.execute((Callable<JTextField>) JTextField::new);

        assertThatThrownBy(() -> SetTextFactory.create(null, TEXT_ECD, jButton, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> SetTextFactory.create(TEST_NAME, null, jButton, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("textECD == null not allowed!");
        assertThatThrownBy(() -> SetTextFactory.create(TEST_NAME, TEXT_ECD, (AbstractButton) null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("abstractButton == null not allowed!");
        assertThatThrownBy(() -> SetTextFactory.create(null, TEXT_ECD, jTextField, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> SetTextFactory.create(TEST_NAME, null, jTextField, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("textECD == null not allowed!");
        assertThatThrownBy(() -> SetTextFactory.create(TEST_NAME, TEXT_ECD, (JTextComponent) null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("jTextComponent == null not allowed!");
    }
}
