package tfw.swing.event;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JButton;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import tfw.awt.ecd.FontECD;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class SetFontFactoryTest {
    private static final String TEST_NAME = "TestName";
    private static final FontECD FONT_ECD = new FontECD("TestFont");

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
        assertThrows(IllegalArgumentException.class, () -> SetFontFactory.create(null, FONT_ECD, jButton, null));
        assertThrows(IllegalArgumentException.class, () -> SetFontFactory.create(TEST_NAME, null, jButton, null));
        assertThrows(IllegalArgumentException.class, () -> SetFontFactory.create(TEST_NAME, FONT_ECD, null, null));
    }
}
