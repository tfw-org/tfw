package tfw.tsm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import tfw.check.Argument;

public final class MultiplexedBranchProxy implements Proxy {
    private final MultiplexedBranch multiplexedBranch;

    public MultiplexedBranchProxy(MultiplexedBranch multiplexedBranch) {
        Argument.assertNotNull(multiplexedBranch, "multiplexedBranch");

        this.multiplexedBranch = multiplexedBranch;
    }

    public String getName() {
        return (multiplexedBranch.getName());
    }

    public EventChannelProxy[] getEventChannelProxies() {
        Multiplexer[] multiplexers = multiplexedBranch.getMultiplexers();
        EventChannelProxy[] ecp = new EventChannelProxy[multiplexers.length];

        for (int i = 0; i < multiplexers.length; i++) {
            ecp[i] = new EventChannelProxy(multiplexers[i]);
        }

        return (ecp);
    }

    public Proxy getParentProxy() {
        TreeComponent branchParent = multiplexedBranch.immediateParent;

        if (branchParent == null) {
            return (null);
        } else if (branchParent instanceof Root) {
            return (new RootProxy((Root) branchParent));
        } else if (branchParent instanceof MultiplexedBranch) {
            return (new MultiplexedBranchProxy((MultiplexedBranch) branchParent));
        } else if (branchParent instanceof Branch) {
            return (new BranchProxy((Branch) branchParent));
        }

        throw new IllegalStateException("Parent is not a branch/multiplexedBranch!");
    }

    public Map<Object, BranchProxy> getSlotIdSubBranchProxyMap() {
        Map<Object, Branch> multiMap = multiplexedBranch.getSlotIdSubBranchMap();
        Map<Object, BranchProxy> map = new HashMap<Object, BranchProxy>();

        for (Iterator<Entry<Object, Branch>> i = multiMap.entrySet().iterator(); i.hasNext(); ) {
            final Entry<Object, Branch> entry = i.next();

            map.put(entry.getKey(), new BranchProxy(entry.getValue()));
        }

        return map;
    }

    public boolean equals(Object obj) {
        if (obj instanceof MultiplexedBranchProxy) {
            MultiplexedBranchProxy ip = (MultiplexedBranchProxy) obj;

            return (multiplexedBranch.equals(ip.multiplexedBranch));
        }

        return (false);
    }

    public int hashCode() {
        return (multiplexedBranch.hashCode());
    }
}
