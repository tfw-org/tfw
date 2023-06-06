package tfw.tsm;

import java.util.HashSet;
import java.util.Set;
import tfw.check.Argument;
import tfw.value.ValueException;

/**
 * A buffer for constructing {@link TreeState} structures.
 * TODO: Need to check for loops in the tree structure.
 */
public class TreeStateBuffer {
    private String name = null;
    private Set<EventChannelState> stateMap = new HashSet<EventChannelState>();
    private Set<TreeState> children = new HashSet<TreeState>();

    /**
     * Set the name of the tree component.
     * @param name the tree component name.
     */
    public void setName(String name) {
        Argument.assertNotNull(name, "name");
        this.name = name;
    }

    /**
     * Adds the state for the specified event channel.
     * @param state the event channel state.
     * @throws ValueException if the specfiec state violates the event channel
     * value constraint.
     */
    public void addState(EventChannelState state) throws ValueException {
        Argument.assertNotNull(state, "state");
        stateMap.add(state);
    }

    /**
     * adds the specified child.
     * @param child the child to add.
     */
    public void addChild(TreeState child) {
        Argument.assertNotNull(child, "child");
        children.add(child);
    }

    /**
     * Returns a state tree as described in previous method calls on
     * this buffer.
     * @return a state tree as described in previous method calls on
     * this buffer.
     */
    public TreeState toTreeState() {
        if (name == null) {
            throw new IllegalStateException("The 'setName(String)' method must be called before this method.");
        }

        return new TreeState(
                name, (EventChannelState[]) stateMap.toArray(new EventChannelState[stateMap.size()]), (TreeState[])
                        children.toArray(new TreeState[children.size()]));
    }
}
