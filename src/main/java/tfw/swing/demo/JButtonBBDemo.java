package tfw.swing.demo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tfw.swing.JButtonBB;
import tfw.tsm.AWTTransactionQueue;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class JButtonBBDemo {
    private static final BooleanECD ENABLE_ECD = new BooleanECD("enable");
    private static final StatelessTriggerECD TRIGGER_ECD = new StatelessTriggerECD("trigger");
    private static final String PREFIX = "Button pressed: ";

    private static long buttonPresses = 0;

    private JButtonBBDemo() {}

    public static final void main(String[] args) {
        final JLabel l = new JLabel();
        l.setText(PREFIX + buttonPresses);

        final TriggeredCommit commit = new TriggeredCommit("JBttonBBTest", TRIGGER_ECD, null, null) {
            @Override
            protected void commit() {
                l.setText(PREFIX + ++buttonPresses);
            }
        };

        final Initiator initiator = new Initiator("JButtonBBTest", new ObjectECD[] {ENABLE_ECD});

        final JCheckBox cb = new JCheckBox();
        cb.setText("Enable Button");
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initiator.set(ENABLE_ECD, cb.isSelected() ? Boolean.TRUE : Boolean.FALSE);
            }
        });

        JButtonBB b = new JButtonBB("JButtonBBTest", ENABLE_ECD, TRIGGER_ECD);
        b.setText("Press Me!");

        final JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(cb, BorderLayout.NORTH);
        p.add(l, BorderLayout.SOUTH);

        RootFactory rf = new RootFactory();
        rf.addEventChannel(ENABLE_ECD, Boolean.FALSE);
        rf.addEventChannel(TRIGGER_ECD);
        Root r = rf.create("JButtonBBTest", new AWTTransactionQueue());
        r.add(commit);
        r.add(initiator);
        r.add(b);

        final JFrame f = new JFrame();
        f.getContentPane().add(b, BorderLayout.NORTH);
        f.getContentPane().add(p, BorderLayout.SOUTH);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.pack();
        f.setVisible(true);
    }
}
