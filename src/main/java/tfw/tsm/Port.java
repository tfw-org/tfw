package tfw.tsm;

import tfw.tsm.ecd.EventChannelDescription;

/**
 * The base class for sinks and sources.
 */
abstract class Port {
    final EventChannelDescription ecd;
    final String name;

    public EventChannel eventChannel = null;

    private TreeComponent component = null;

    /**
     * Construct a port with the specified attributes.
     *
     * @param name
     *            the name of the port.
     * @param eventChannelName
     *            the name of the event channel.
     * @param constraint
     *            the value constraint for the port.
     */
    Port(String name, EventChannelDescription ecd) {
        this.name = name;
        this.ecd = ecd;
    }

    /**
     * Returns this ports name.
     *
     * @return this ports name.
     */
    String getFullyQualifiedName() {
        StringBuffer sb = new StringBuffer();

        if (this.component != null) {
            sb.append(this.component.getFullyQualifiedName());
            sb.append("[").append(name).append("]");
        } else {
            sb.append(name);
        }

        return sb.toString();
    }

    /**
     * Connects this <code>Port</code> to the specified event channel.
     *
     * @param eventChannel
     *            the event channel for this <code>Port</code>.
     */
    void setEventChannel(EventChannel eventChannel) {
        this.eventChannel = eventChannel;
    }

    /**
     * Check whether this port is connected.
     *
     * @return <tt>true</tt> if the this port is connected to an event
     *         channel, otherwise returns <tt>false</tt>.
     */
    boolean isConnected() {
        return eventChannel != null;
    }

    /**
     * Sets this ports associated tree component.
     *
     * @param component
     *            the component to which this port is bound.
     */
    void setTreeComponent(TreeComponent component) {
        if (this.component != null) {
            throw new IllegalStateException("Illegal attempt to re-assign component");
        }

        this.component = component;
    }

    /**
     * Returns the component to which this port is bound.
     *
     * @return the component to which this port is bound.
     */
    public TreeComponent getTreeComponent() {
        if (this.component == null) {
            throw new IllegalStateException("Port '" + name
                    + "' for event channel '" + ecd.getEventChannelName()
                    + "' has not been connected to a component");
        }

        return this.component;
    }
}
