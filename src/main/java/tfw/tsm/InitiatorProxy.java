package tfw.tsm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import tfw.check.Argument;

public final class InitiatorProxy implements Proxy {
    private final Initiator initiator;

    public InitiatorProxy(Initiator initiator) {
        Argument.assertNotNull(initiator, "initiator");

        this.initiator = initiator;
    }

    @Override
    public String getName() {
        return initiator.getName();
    }

    public SourceProxy[] getSourceProxies() {
        Collection<Source> collection = new ArrayList<Source>(initiator.sources);
        Iterator<Source> iterator = collection.iterator();
        SourceProxy[] sp = new SourceProxy[collection.size()];

        for (int i = 0; iterator.hasNext(); i++) {
            Object o = iterator.next();
            sp[i] = new SourceProxy((Source) o);
        }
        return sp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InitiatorProxy) {
            InitiatorProxy ip = (InitiatorProxy) obj;

            return initiator.equals(ip.initiator);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return initiator.hashCode();
    }
}
