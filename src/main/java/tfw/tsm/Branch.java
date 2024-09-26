package tfw.tsm;

import tfw.check.Argument;

/**
 * The framework uses a tree metaphor to describe its hierarchical
 * communications structure. The structure consists of a single root, branches
 * and leaves.
 *
 */
public class Branch extends BranchComponent {
    /**
     * Constructs a trivial branch with no event channels or ports. To create a
     * non-trivial branch see {@link BranchFactory}.
     *
     * @param name
     *            The name of the branch.
     */
    public Branch(String name) {
        this(name, null, null, null);
    }

    /**
     * Constructs a branch with the specified event channels and ports.
     *
     * @param name
     *            the name of the branch.
     * @param eventChannels
     *            the set of event channels positioned at this branch.
     * @param isMultiplexed
     *            boolean indicating whether the event channels are to be
     *            multiplexed.
     * @param ports
     *            the set of ports for this branch. These are the multiplexer
     *            and translator ports which are local to this branch.
     */
    Branch(String name, Sink[] sinks, Source[] sources, EventChannel[] eventChannels) {
        super(name, sinks, sources, eventChannels);
    }

    public final void add(TreeComponent child) {
        Argument.assertNotNull(child, "child");

        addChild(child);
    }

    /**
     * Adds branch contained in the branch box to this branch as a child.
     *
     * @param branchBox
     *            The branch box containing the child branch to be added.
     */
    public final void add(BranchBox branchBox) {
        Argument.assertNotNull(branchBox, "branchBox");

        addChild(branchBox.getBranch());
    }

    @Override
    public final void remove(TreeComponent child) {
        Argument.assertNotNull(child, "child");

        removeChild(child);
    }

    /**
     * Removes the specified branch box from this branch.
     *
     * @param branchBox
     *            the branch box to be removed.
     */
    public final void remove(BranchBox branchBox) {
        Argument.assertNotNull(branchBox, "branchBox");

        removeChild(branchBox.getBranch());
    }

    public final synchronized void removeAll() {
        if (immediateChildren != null) {
            for (TreeComponent child : immediateChildren) {
                removeChild(child);
            }
        }
    }

    public final void addAndRemove(AddRemoveOperation[] operations) {
        Argument.assertElementNotNull(operations, "operations");

        performAddRemoveOperations(operations);
    }
}
