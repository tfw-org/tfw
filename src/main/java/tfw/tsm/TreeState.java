package tfw.tsm;

import java.io.Serializable;
import tfw.array.ArrayUtil;
import tfw.check.Argument;

/**
 * A representation of the state of a tree component. Note that a state of a
 * tree component includes its name, the state of its event channels and the
 * state of its child sub-trees.
 */
public class TreeState implements Serializable {
    private static final long serialVersionUID = -3717409984084594135L;

    /** The name of the tree component. */
    private final String name;

    /** The child sub-trees. */
    private final TreeState[] children;

    /** The event channel state for the tree component. */
    private final EventChannelState[] state;

    /**
     * Creates a tree state with the specified values.
     *
     * @param name
     *            The name of the component.
     * @param state
     *            The set of event channel state for the node in the tree.
     * @param children
     *            The set of sub-trees
     */
    public TreeState(String name, EventChannelState[] state, TreeState[] children) {
        Argument.assertNotNull(name, "name");
        this.name = name;

        if (children == null) {
            this.children = new TreeState[0];
        } else {
            Argument.assertElementNotNull(children, "children");
            this.children = children;
        }

        if (state == null) {
            this.state = new EventChannelState[0];
        } else {
            Argument.assertElementNotNull(state, "state");
            this.state = state.clone();
        }
    }

    /**
     * Returns the name the tree for which this is the state.
     *
     * @return the name the tree for which this is the state.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the child tree state for this tree state.
     *
     * @return the child tree state for this tree state.
     */
    public TreeState[] getChildren() {
        return children.clone();
    }

    /**
     * Returns the state for the specified event channel or null if the event
     * channel is not found in this tree state.
     *
     * @return the state for the specified event channel or null if the event
     *         channel is not found in this tree state.
     */
    public EventChannelState[] getState() {
        return state.clone();
    }

    /**
     * Returns true if the specified object is a TreeState and is equivalent to
     * this one.
     *
     * @return true if the specified object is a TreeState and is equivalent to
     *         this one.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TreeState)) {
            return false;
        }

        TreeState ts = (TreeState) object;

        return ts.name.equals(this.name)
                && ArrayUtil.unorderedEquals(ts.state, this.state)
                && ArrayUtil.unorderedEquals(ts.children, this.children);
    }

    /**
     * Returns a hash code for this TreeState.
     *
     * @return a hash code for this TreeState.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Creates a string representation of this treestate.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TreeState[");
        sb.append("name = ").append(this.name);
        sb.append(", state = {");
        for (int i = 0; i < this.state.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(state[i]);
        }
        sb.append("}, children = {");

        for (int i = 0; i < this.children.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(children[i]);
        }
        sb.append("}]");
        return sb.toString();
    }
}
