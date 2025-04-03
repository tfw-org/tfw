package tfw.tsm;

import tfw.check.Argument;

public final class SinkProxy implements Proxy {
    private final Sink sink;

    SinkProxy(Sink sink) {
        Argument.assertNotNull(sink, "sink");

        this.sink = sink;
    }

    public EventChannelProxy getEventChannelProxy() {
        return new EventChannelProxy((Terminator) sink.eventChannel);
    }

    @Override
    public String getName() {
        return sink.ecd.getEventChannelName();
    }

    public boolean isTriggering() {
        return sink.isTriggering();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SinkProxy) {
            SinkProxy sp = (SinkProxy) obj;

            return sink.equals(sp.sink);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return sink.hashCode();
    }
}
