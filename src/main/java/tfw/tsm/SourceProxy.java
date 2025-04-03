package tfw.tsm;

import tfw.check.Argument;

public final class SourceProxy implements Proxy {
    private final Source source;

    SourceProxy(Source source) {
        Argument.assertNotNull(source, "source");

        this.source = source;
    }

    public EventChannelProxy getEventChannelProxy() {
        return new EventChannelProxy((Terminator) source.eventChannel);
    }

    @Override
    public String getName() {
        return source.ecd.getEventChannelName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SourceProxy) {
            SourceProxy sp = (SourceProxy) obj;

            return source.equals(sp.source);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return source.hashCode();
    }
}
