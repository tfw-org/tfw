package tfw.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.StatelessTriggerECD;

public class TestActionListenerTreeComponent extends Initiator implements ActionListener {
    public TestActionListenerTreeComponent(final StatelessTriggerECD statelessTriggerECD) {
        super("TestActionListenerTreeComponent", statelessTriggerECD);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
