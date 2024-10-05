package tfw.swing;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import tfw.tsm.Branch;

public class JButtonBBTestApplication extends JFrame {
    public JButtonBBTestApplication(final Branch branch) {
        final JButtonBB emptyJButtonBB = JButtonBB.builder()
                .setName(JButtonBBTest.EMPTY_BUTTON_NAME)
                .setActionOutput(JButtonBBTest.BUTTON_ACTION_ECD)
                .create();

        final JButtonBB jButtonBB = JButtonBB.builder()
                .setName(JButtonBBTest.BUTTON_NAME)
                .setActionOutput(JButtonBBTest.BUTTON_ACTION_ECD)
                .setEnabledInput(JButtonBBTest.BUTTON_ENABLED_ECD)
                .setBackgroundColorInput(JButtonBBTest.BUTTON_BACKGROUND_ECD)
                .setForegroundColorInput(JButtonBBTest.BUTTON_FOREGROUND_ECD)
                .setFontInput(JButtonBBTest.BUTTON_FONT_ECD)
                .setTextInput(JButtonBBTest.BUTTON_TEXT_ECD)
                .setIconInput(JButtonBBTest.BUTTON_ICON_ECD)
                .create();

        jButtonBB.setName(JButtonBBTest.BUTTON_NAME);
        emptyJButtonBB.setName(JButtonBBTest.EMPTY_BUTTON_NAME);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jButtonBB, BorderLayout.CENTER);
        getContentPane().add(emptyJButtonBB, BorderLayout.SOUTH);

        branch.add(jButtonBB);
        branch.add(emptyJButtonBB);

        setSize(100, 50);
    }
}
