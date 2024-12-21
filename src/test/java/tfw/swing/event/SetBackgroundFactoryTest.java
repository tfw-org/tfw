package tfw.swing.event;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JButton;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import tfw.awt.ecd.ColorECD;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class SetBackgroundFactoryTest {
    private static final String TEST_NAME = "TestName";
    private static final ColorECD COLOR_ECD = new ColorECD("TestColor");

    private JButton jButton;

    @BeforeAll
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setUp() {
        jButton = GuiActionRunner.execute(() -> new JButton());
    }

    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> SetBackgroundFactory.create(null, COLOR_ECD, jButton, null));
        assertThrows(IllegalArgumentException.class, () -> SetBackgroundFactory.create(TEST_NAME, null, jButton, null));
        assertThrows(
                IllegalArgumentException.class, () -> SetBackgroundFactory.create(TEST_NAME, COLOR_ECD, null, null));
    }
}
