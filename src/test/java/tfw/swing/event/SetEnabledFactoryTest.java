package tfw.swing.event;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JButton;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import tfw.tsm.ecd.BooleanECD;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class SetEnabledFactoryTest {
    private static final String TEST_NAME = "TestName";
    private static final BooleanECD ENABLED_ECD = new BooleanECD("TestEnabled");

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
        assertThrows(IllegalArgumentException.class, () -> SetEnabledFactory.create(null, ENABLED_ECD, jButton, null));
        assertThrows(IllegalArgumentException.class, () -> SetEnabledFactory.create(TEST_NAME, null, jButton, null));
        assertThrows(
                IllegalArgumentException.class, () -> SetEnabledFactory.create(TEST_NAME, ENABLED_ECD, null, null));
    }
}
