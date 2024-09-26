package tfw.tsm;

import tfw.tsm.ecd.EventChannelDescription;
import tfw.value.ValueException;

public abstract class Source extends Port {
    Source(String name, EventChannelDescription ecd) {
        super(name, ecd);
    }

    abstract void setState(Object state) throws ValueException;

    abstract Object fire();

    boolean isStateSource() {
        return eventChannel.getCurrentStateSource() == this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[name = ").append(getFullyQualifiedName());
        sb.append(", eventChannelName = ").append(ecd.getEventChannelName());
        //        sb.append(", stateQueue = ").append(stateQueue).append("]");

        return sb.toString();
    }
}
