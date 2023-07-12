package tfw.tsm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import tfw.check.Argument;

public final class TriggeredConverterProxy implements Proxy {
    private final TriggeredConverter triggeredConverter;

    public TriggeredConverterProxy(TriggeredConverter triggeredConverter) {
        Argument.assertNotNull(triggeredConverter, "triggeredConverter");

        this.triggeredConverter = triggeredConverter;
    }

    public String getName() {
        return triggeredConverter.getName();
    }

    public SourceProxy[] getSourceProxies() {
        Collection<Source> collection = new ArrayList<Source>(triggeredConverter.sources);
        Iterator<Source> iterator = collection.iterator();
        SourceProxy[] sp = new SourceProxy[collection.size()];

        for (int i = 0; iterator.hasNext(); i++) {
            Object o = iterator.next();
            sp[i] = new SourceProxy((Source) o);
        }
        return sp;
    }

    public SinkProxy[] getSinkProxies() {
        Object[] sinks = triggeredConverter.sinks.values().toArray();
        SinkProxy[] sp = new SinkProxy[sinks.length];

        for (int i = 0; i < sinks.length; i++) {
            sp[i] = new SinkProxy((Sink) sinks[i]);
        }
        return sp;
    }

    public boolean equals(Object obj) {
        if (obj instanceof TriggeredConverterProxy) {
            TriggeredConverterProxy ip = (TriggeredConverterProxy) obj;

            return triggeredConverter.equals(ip.triggeredConverter);
        }

        return false;
    }

    public int hashCode() {
        return triggeredConverter.hashCode();
    }
}
