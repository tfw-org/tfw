package tfw.swing.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;

public class ActionListenerFactory {
    private ActionListenerFactory() {}

    public static ActionListener create(final String name, final StatelessTriggerECD statelessTriggerECD) {
        return new ActionInitiator(name, statelessTriggerECD);
    }

    private static class ActionInitiator extends Initiator implements ActionListener {
        private final StatelessTriggerECD statelessTriggerECD;

        public ActionInitiator(final String name, final StatelessTriggerECD statelessTriggerECD) {
            super(name, new EventChannelDescription[] {statelessTriggerECD}, null);

            this.statelessTriggerECD = statelessTriggerECD;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            trigger(statelessTriggerECD);
        }
    }
}
