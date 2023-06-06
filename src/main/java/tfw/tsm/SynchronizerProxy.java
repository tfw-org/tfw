package tfw.tsm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import tfw.check.Argument;

public final class SynchronizerProxy implements Proxy {
    private final Synchronizer synchronizer;

    public SynchronizerProxy(Synchronizer synchronizer) {
        Argument.assertNotNull(synchronizer, "synchronizer");

        this.synchronizer = synchronizer;
    }

    public String getName() {
        return (synchronizer.getName());
    }

    public SourceProxy[] getSourceProxies() {
        Collection<Source> collection = new ArrayList<Source>(synchronizer.sources);
        Iterator<Source> iterator = collection.iterator();
        SourceProxy[] sp = new SourceProxy[collection.size()];

        for (int i = 0; iterator.hasNext(); i++) {
            Object o = iterator.next();
            sp[i] = new SourceProxy((Source) o);
        }
        return (sp);
    }

    public SinkProxy[] getSinkProxies() {
        Object[] sinks = (Object[]) synchronizer.sinks.values().toArray();
        SinkProxy[] sp = new SinkProxy[sinks.length];

        for (int i = 0; i < sinks.length; i++) {
            sp[i] = new SinkProxy((Sink) sinks[i]);
        }
        return (sp);
    }

    public boolean equals(Object obj) {
        if (obj instanceof SynchronizerProxy) {
            SynchronizerProxy ip = (SynchronizerProxy) obj;

            return (synchronizer.equals(ip.synchronizer));
        }

        return (false);
    }

    public int hashCode() {
        return (synchronizer.hashCode());
    }
}
