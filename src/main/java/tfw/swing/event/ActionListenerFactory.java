package tfw.swing.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tfw.tsm.Initiator;
import tfw.tsm.StateQueueFactory;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;

public class ActionListenerFactory {
    private ActionListenerFactory() {}

    public static ActionListener create(
            final String name,
            final StatelessTriggerECD statelessTriggerECD,
            final StateQueueFactory stateQueueFactory) {
        return stateQueueFactory == null
                ? new TwoArgActionInitiator(name, statelessTriggerECD)
                : new ThreeArgActionInitiator(name, statelessTriggerECD, stateQueueFactory);
    }

    private static class TwoArgActionInitiator extends Initiator implements ActionListener {
        private final StatelessTriggerECD statelessTriggerECD;

        public TwoArgActionInitiator(final String name, final StatelessTriggerECD statelessTriggerECD) {
            super(name, new EventChannelDescription[] {statelessTriggerECD});

            this.statelessTriggerECD = statelessTriggerECD;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            trigger(statelessTriggerECD);
        }
    }

    private static class ThreeArgActionInitiator extends Initiator implements ActionListener {
        private final StatelessTriggerECD statelessTriggerECD;

        public ThreeArgActionInitiator(
                final String name,
                final StatelessTriggerECD statelessTriggerECD,
                final StateQueueFactory stateQueueFactory) {
            super(name, new EventChannelDescription[] {statelessTriggerECD}, stateQueueFactory);

            this.statelessTriggerECD = statelessTriggerECD;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            trigger(statelessTriggerECD);
        }
    }
}
