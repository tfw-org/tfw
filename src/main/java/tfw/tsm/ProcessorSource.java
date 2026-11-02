package tfw.tsm;

import tfw.tsm.ecd.EventChannelDescription;

/**
 *
 */
class ProcessorSource extends Source {
    private Object state = null;

    ProcessorSource(String name, EventChannelDescription ecd) {
        super(name, ecd);
    }

    Object getState() {
        return state;
    }

    @Override
    void setState(Object state) {
        setState(state, true);
    }

    void setState(Object state, boolean defer) {
        // This check is potentially invalid if a rollback occurs
        //        if (this.state != null)
        //        {
        //            throw new IllegalStateException(
        //                "Attempt to overwrite state on event channel '" +
        //                this.getEventChannelName() + "'.");
        //        }
        if (eventChannel == null) {
            throw new IllegalStateException("Attempt to set state using disconnected source");
        }
        if (!ecd.getPredicate().test(state)) {
            throw new IllegalArgumentException("FIX THIS MESSAGE");
        }

        this.state = state;
        if (defer) {
            eventChannel.addDeferredStateChange(this);
        } else {
            fire();
            getTreeComponent().getTransactionManager().addChangedEventChannel(eventChannel);
        }
    }

    /**
     * @see co2.ui.fw.Source#fire()
     */
    @Override
    Object fire() {
        Object temp = state;
        state = null;
        eventChannel.setState(this, temp, null);
        return temp;
    }
}
