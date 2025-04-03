package tfw.tsm;

public final class ProxyUtil {
    private ProxyUtil() {}

    public static void writeGraph(final Proxy proxy, final StringBuilder stringBuilder, final String indent) {
        final String newIndent = indent + "    ";

        if (proxy instanceof RootProxy) {
            final RootProxy rootProxy = (RootProxy) proxy;

            stringBuilder.append("ROOT: ");
            stringBuilder.append(rootProxy.getName());
            stringBuilder.append('\n');

            for (EventChannelProxy ecp : rootProxy.getEventChannelProxies()) {
                writeGraph(ecp, stringBuilder, newIndent);
            }
            for (Proxy child : rootProxy.getChildProxies()) {
                writeGraph(child, stringBuilder, newIndent);
            }
        } else if (proxy instanceof BranchProxy) {
            final BranchProxy branchProxy = (BranchProxy) proxy;

            stringBuilder.append(indent);
            stringBuilder.append("BRANCH: ");
            stringBuilder.append(branchProxy.getName());
            stringBuilder.append('\n');

            for (EventChannelProxy ecp : branchProxy.getEventChannelProxies()) {
                writeGraph(ecp, stringBuilder, newIndent);
            }
            for (Proxy child : branchProxy.getChildProxies()) {
                writeGraph(child, stringBuilder, newIndent);
            }
        } else if (proxy instanceof InitiatorProxy) {
            final InitiatorProxy initiatorProxy = (InitiatorProxy) proxy;

            stringBuilder.append(indent);
            stringBuilder.append("INITIATOR: ");
            stringBuilder.append(initiatorProxy.getName());
            stringBuilder.append('\n');
        } else if (proxy instanceof CommitProxy) {
            final CommitProxy commitProxy = (CommitProxy) proxy;

            stringBuilder.append(indent);
            stringBuilder.append("COMMIT: ");
            stringBuilder.append(commitProxy.getName());
            stringBuilder.append('\n');
        } else if (proxy instanceof TriggeredCommitProxy) {
            final TriggeredCommitProxy triggeredCommitProxy = (TriggeredCommitProxy) proxy;

            stringBuilder.append(indent);
            stringBuilder.append("TRIGGERED_COMMIT: ");
            stringBuilder.append(triggeredCommitProxy.getName());
            stringBuilder.append('\n');
        } else if (proxy instanceof EventChannelProxy) {
            final EventChannelProxy eventChannelProxy = (EventChannelProxy) proxy;

            stringBuilder.append(indent);
            stringBuilder.append("EVENT_CHANNEL: ");
            stringBuilder.append(eventChannelProxy.getName());
            stringBuilder.append('\n');
        } else {
            stringBuilder.append(indent);
            stringBuilder.append("UNKNOWN: ");
            stringBuilder.append(proxy);
            stringBuilder.append('\n');
        }
    }
}
