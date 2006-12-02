/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * witout even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.tsm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.value.NullConstraint;

/**
 * The base class for all components.
 */
public class TreeComponent
{
    private static final Map EMPTY = Collections
            .unmodifiableMap(new HashMap(0));

    /** The name of the component. */
    private final String name;

    /** This component's parent. */
    private TreeComponent parent = null;

    /** This components sinks. */
    final PortMap sinks;

    /** This components sources. */
    final Map sources;

    /** This components event channels. */
    final Map eventChannels;

    /** The transaction manager for the parent tree. */
    private TransactionMgr transactionMgr = null;

	protected Root immediateRoot = null;
	protected BranchComponent immediateParent = null;
    /**
     * Creates a tree component with the specified attributes.
     * 
     * @param name
     *            the name of the component.
     * @param sinks
     *            the sinks for this component.
     * @param sources
     *            the sources for this component.
     * @param eventChannels
     *            the event channels for this component.
     */
    TreeComponent(String name, Sink[] sinks, Source[] sources,
            EventChannel[] eventChannels)
    {
        Argument.assertNotNull(name, "name");
        this.name = name;
        this.sinks = initializeSinks(sinks);
        this.sources = initializeSources(sources);
        this.eventChannels = initializeEventChannels(eventChannels);
    }

    /**
	 * Disconnects this component from its parent if it is connected to a
	 * parent.
	 */
	public void disconnectFromParent()
	{
		if (parent != null && parent instanceof BranchComponent)
		{
			((BranchComponent)parent).remove(this);
		}
	}

	/**
     * Creates an unmodifiable map of the specified sinks.
     * 
     * @param sinks
     *            the sinks.
     * @return an unmodifiable map of the sinks, mapped by event channel name.
     */
    private PortMap initializeSinks(Sink[] sinks)
    {
        PortMap map;

        if ((sinks != null) && (sinks.length > 0))
        {
            map = new PortMap("sinks", sinks);

            for (int i = 0; i < sinks.length; i++)
            {
                sinks[i].setTreeComponent(this);
            }
        }
        else
        {
            map = PortMap.EMPTY;
        }

        return map;
    }

    /**
     * Creates an unmodifiable map of the specified sources.
     * 
     * @param sources
     *            the sources.
     * @return an unmodifiable map of the sources, mapped by event channel name.
     */
    private Map initializeSources(Source[] sources)
    {
        Map map;

        if ((sources != null) && (sources.length > 0))
        {
            map = new HashMap(sources.length);

            for (int i = 0; i < sources.length; i++)
            {
                sources[i].setTreeComponent(this);

                if (map.put(sources[i].getEventChannelName(), sources[i]) != null)
                {
                    throw new IllegalArgumentException(
                            "Multiple sources detected for event channel '"
                                    + sources[i].getEventChannelName() + "'");
                }
            }

            map = Collections.unmodifiableMap(map);
        }
        else
        {
            map = EMPTY;
        }

        return map;
    }

    /**
     * Creates an unmodifiable map of the specified event channels.
     * 
     * @param eventChannels
     *            the event channels.
     * @return an unmodifiable map of the event channels, mapped by event
     *         channel name.
     */
    private Map initializeEventChannels(EventChannel[] eventChannels)
    {
        Map map;

        if ((eventChannels != null) && (eventChannels.length > 0))
        {
            Argument.assertElementNotNull(eventChannels, "eventChannels");
            map = new HashMap(eventChannels.length);

            for (int i = 0; i < eventChannels.length; i++)
            {
                if (map.put(eventChannels[i].getECD().getEventChannelName(),
                        eventChannels[i]) != null)
                {
                    throw new IllegalArgumentException(
                            "Multiple event channels detected with name '"
                                    + eventChannels[i].getECD() + "'");
                }
            }

            map = Collections.unmodifiableMap(map);
        }
        else
        {
            map = EMPTY;
        }

        if (eventChannels != null)
        {
            for (int i = 0; i < eventChannels.length; i++)
            {
                eventChannels[i].setTreeComponent(this);
            }
        }

        return map;
    }

    /**
     * Returns the name of the component.
     * 
     * @return the name of the component.
     */
    public final String getName()
    {
        return name;
    }

    public final String getFullyQualifiedName()
    {
        StringBuffer sb = new StringBuffer();

        if (parent != null)
        {
            parent.getName(sb);
            sb.append('.');
        }

        sb.append(name);

        return sb.toString();
    }

    private void getName(StringBuffer sb)
    {
        if (parent != null)
        {
            parent.getName(sb);
            sb.append('.');
        }

        sb.append(name);
    }

    /**
     * Returns the component's parent.
     * 
     * @return the component's parent.
     */
    final TreeComponent getParent()
    {
        return parent;
    }

    /**
     * Sets this components parent.
     * 
     * @param parent
     *            the parent component.
     */
    void setParent(TreeComponent parent)
    {
        this.parent = parent;
    }

    /**
     * Returns an unmodifiable map of the sinks, mapped by event channel name.
     * 
     * @return an unmodifiable map of the sinks, mapped by event channel name.
     */
    final PortMap getSinks()
    {
        return sinks;
    }

    /**
     * Returns an unmodifiable map of the sources, mapped by event channel name.
     * 
     * @return an unmodifiable map of the sources, mapped by event channel name.
     */
    final Map getSources()
    {
        return sources;
    }

    /**
     * Returns the source for the specified event channel.
     * 
     * @param eventChannelName
     *            the name of the event channel whose source is to be returned.
     * @return the source for the specified event channel.
     */
    final Source getSource(String eventChannelName)
    {
        // checkSource(eventChannelName);
        return ((Source) sources.get(eventChannelName));
    }

    /**
     * Returns the sink for the specified event channel.
     * 
     * @param eventChannelName
     *            the name of the event channel whose sink is to be returned.
     * @return the sink for the specified event channel.
     */
    final Sink getSink(EventChannelDescription eventChannelName)
    {
        // checkSink(eventChannelName);
        return ((Sink) sinks.get(eventChannelName));
    }

    final void checkSource(String eventChannelName)
    {
        if (!sources.containsKey(eventChannelName))
        {
            throw new IllegalArgumentException(eventChannelName
                    + " is invalid source in Leaf[" + getName() + "]");
        }
    }

    final void checkSink(EventChannelDescription eventChannelName)
    {
        if (!sinks.containsKey(eventChannelName))
        {
            throw new IllegalArgumentException(eventChannelName
                    + " is invalid sink in Leaf[" + getName() + "]");
        }
    }

    /**
     * Returns the names of the sources for the leaves.
     * 
     * @return the names of the sources for the leaves.
     */
    public String[] getSourceNames()
    {
        Set kset = sources.keySet();

        return (String[]) kset.toArray(new String[kset.size()]);
    }

    /**
     * Returns the names of the sinks for the leaves.
     * 
     * @return the names of the sinks for the leaves.
     */
    public EventChannelDescription[] getSinkNames()
    {
        return sinks.getKeys();
    }

    /**
     * Returns the names of the sinks for the leaves.
     * 
     * @return the names of the sinks for the leaves.
     */
    public String[] getEventChannelNames()
    {
        Set kset = eventChannels.keySet();

        return (String[]) kset.toArray(new String[kset.size()]);
    }

    /**
     * Returns <code>true</code> if all of this components sinks event
     * channels are non-null, otherwise returns <code>false</code>.
     * 
     * @return <code>true</code> if all of this components sinks event
     *         channels are non-null, otherwise returns <code>false</code>.
     */
    final boolean isStateNonNull()
    {
        return (isStateNonNull(sinks.keySet()));
    }

    /**
     * Returns <code>true</code> if the specified set of event channels are
     * non-null, otherwise returns <code>false</code>.
     * 
     * @param eventChannels
     *            the set of event channels
     * @return <code>true</code> if the specified set of event channels are
     *         non-null, otherwise returns <code>false</code>.
     */
    final boolean isStateNonNull(Set eventChannels)
    {
        Iterator itr = eventChannels.iterator();

        while (itr.hasNext())
        {
            EventChannelDescription ecd = (EventChannelDescription) itr.next();
            checkSink(ecd);

            Sink sink = (Sink) sinks.get(ecd);

            if (sink.getEventChannel() == null)
            {
                throw new IllegalStateException(ecd
                        + " is not connected to an event channel");
            }

            if (!(sink.getConstraint() instanceof NullConstraint))
            {
                if (sink.getEventChannel().getState() == null)
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns true if the component is a root, or is connected to a root
     * component, otherwise returns false.
     * 
     * @return true if the component is a root, or is connected to a root
     *         component, otherwise returns false.
     */
    public boolean isRooted()
    {
        if (parent == null)
        {
            return false;
        }

        return parent.isRooted();
    }

    /**
     * Returns the transaction manager for this component tree.
     * 
     * @return the transaction manager for this component tree.
     */
    TransactionMgr getTransactionManager()
    {
        if (transactionMgr != null)
        {
            return transactionMgr;
        }

        if (!isRooted())
        {
            throw new IllegalStateException(
                    "'"
                            + getName()
                            + "' is not rooted. "
                            + "The transaction manager is only available when the component is rooted.");
        }

        this.transactionMgr = getParent().getTransactionManager();

        return this.transactionMgr;
    }

    /**
     * Terminates the specified ports locally and then recursively passes the
     * unterminated ports up to the parent component.
     * 
     * @param connections
     *            The set of ports to be terminated.
     */
    void terminateParentAndLocalConnections(Set connections)
    {
        Argument.assertNotNull(connections, "connections");

        if (connections.size() == 0)
        {
            return;
        }

        connections = terminateLocally(connections);

        if (connections.size() == 0)
        {
            return;
        }

        if (parent == null)
        {
            throw new IllegalStateException(
                    "Event channels left unterminated:\n" + connections);
        }

        parent.terminateParentAndLocalConnections(connections);
    }

    /**
     * For each port for which this component has a terminator, terminate the
     * port.
     * 
     * @param connections
     *            the set of unterminated ports.
     * @return the set of unterminated ports.
     */
    Set terminateLocally(Set connections)
    {
        Port[] ports = (Port[]) connections
                .toArray(new Port[connections.size()]);

        for (int i = 0; i < ports.length; i++)
        {
            if (eventChannels.containsKey(ports[i].getEventChannelName()))
            {
                EventChannel t = (EventChannel) eventChannels.get(ports[i]
                        .getEventChannelName());
                t.add(ports[i]);
                connections.remove(ports[i]);
            }
        }

        connections.addAll(sinks.values());
        connections.addAll(sources.values());

        return connections;
    }

    /**
     * Disconnects this component's sinks and sources from the event channels.
     */
    final void disconnectPorts()
    {
        Iterator itr = sinks.values().iterator();

        while (itr.hasNext())
        {
            Sink sink = (Sink) itr.next();
            sink.getEventChannel().remove(sink);
        }

        itr = sources.values().iterator();

        while (itr.hasNext())
        {
            Source source = (Source) itr.next();
            source.getEventChannel().remove(source);
        }
    }

}
