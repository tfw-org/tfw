package tfw.swing.event;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JButton;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import tfw.swing.ecd.IconECD;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class SetIconFactoryTest {
    private static final String TEST_NAME = "TestName";
    private static final IconECD ICON_ECD = new IconECD("TestIcon");

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
        assertThrows(IllegalArgumentException.class, () -> SetIconFactory.create(null, ICON_ECD, jButton, null));
        assertThrows(IllegalArgumentException.class, () -> SetIconFactory.create(TEST_NAME, null, jButton, null));
        assertThrows(IllegalArgumentException.class, () -> SetIconFactory.create(TEST_NAME, ICON_ECD, null, null));
    }
}
