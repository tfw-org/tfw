package tfw.tsm;

import tfw.check.Argument;

public final class TriggeredCommitProxy implements Proxy {
    private final TriggeredCommit triggeredCommit;

    public TriggeredCommitProxy(TriggeredCommit triggeredCommit) {
        Argument.assertNotNull(triggeredCommit, "triggeredCommit");

        this.triggeredCommit = triggeredCommit;
    }

    public String getName() {
        return (triggeredCommit.getName());
    }

    public SinkProxy[] getSinkProxies() {
        Object[] sinks = triggeredCommit.sinks.values().toArray();
        SinkProxy[] sp = new SinkProxy[sinks.length];

        for (int i = 0; i < sinks.length; i++) {
            sp[i] = new SinkProxy((Sink) sinks[i]);
        }
        return (sp);
    }

    public boolean equals(Object obj) {
        if (obj instanceof TriggeredCommitProxy) {
            TriggeredCommitProxy ip = (TriggeredCommitProxy) obj;

            return (triggeredCommit.equals(ip.triggeredCommit));
        }

        return (false);
    }

    public int hashCode() {
        return (triggeredCommit.hashCode());
    }
}
