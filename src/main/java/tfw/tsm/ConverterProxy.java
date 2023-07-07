package tfw.tsm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import tfw.check.Argument;

public final class ConverterProxy implements Proxy {
    private final Converter converter;

    public ConverterProxy(Converter converter) {
        Argument.assertNotNull(converter, "converter");

        this.converter = converter;
    }

    public String getName() {
        return (converter.getName());
    }

    public SourceProxy[] getSourceProxies() {
        Collection<Source> collection = new ArrayList<Source>(converter.sources);
        Iterator<Source> iterator = collection.iterator();
        SourceProxy[] sp = new SourceProxy[collection.size()];

        for (int i = 0; iterator.hasNext(); i++) {
            Object o = iterator.next();
            sp[i] = new SourceProxy((Source) o);
        }
        return (sp);
    }

    public SinkProxy[] getSinkProxies() {
        Object[] sinks = converter.sinks.values().toArray();
        SinkProxy[] sp = new SinkProxy[sinks.length];

        for (int i = 0; i < sinks.length; i++) {
            sp[i] = new SinkProxy((Sink) sinks[i]);
        }
        return (sp);
    }

    public boolean equals(Object obj) {
        if (obj instanceof ConverterProxy) {
            ConverterProxy ip = (ConverterProxy) obj;

            return (converter.equals(ip.converter));
        }

        return (false);
    }

    public int hashCode() {
        return (converter.hashCode());
    }
}
