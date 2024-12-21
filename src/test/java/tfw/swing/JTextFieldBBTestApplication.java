package tfw.swing;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import tfw.tsm.Branch;
import tfw.tsm.OneDeepStateQueueFactory;

public class JTextFieldBBTestApplication extends JFrame {
    public JTextFieldBBTestApplication(final Branch branch) {
        final JTextFieldBB jTextFieldBB = JTextFieldBB.builder()
                .setName(JTextFieldBBTest.TEXTFIELD_NAME)
                .setEnabledInputECD(JTextFieldBBTest.TEXTFIELD_ENABLED_ECD)
                .setTextInputOutputECD(JTextFieldBBTest.TEXTFIELD_TEXT_ECD)
                .setTextOutputStateQueueFactory(new OneDeepStateQueueFactory())
                .build();

        jTextFieldBB.setName(JTextFieldBBTest.TEXTFIELD_NAME);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jTextFieldBB, BorderLayout.CENTER);

        branch.add(jTextFieldBB);

        setSize(100, 50);
    }
}
