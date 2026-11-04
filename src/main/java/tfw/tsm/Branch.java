package tfw.tsm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tfw.check.Argument;
import tfw.check.Arguments;

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
     * @param name the name of the branch.
     * @param sinks the set of sinks associated with this branch.
     * @param sources the set of sources associated with this branch.
     * @param eventChannels the set of event channels associated with this branch.
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

    public synchronized Branch add(final List<? extends ComponentConfig<?>> componentConfigs) {
        Arguments.checkNotNull(componentConfigs, "componentConfigs");

        // Validate and capture every component before modifying the branch.
        final List<TreeComponent> components = new ArrayList<>(componentConfigs.size());

        for (final ComponentConfig<?> componentConfig : componentConfigs) {
            Arguments.checkNotNull(componentConfig, "componentConfig");

            final TreeComponent component = componentConfig.getComponent();
            Arguments.checkNotNull(component, "component");

            components.add(component);
        }

        // Validate that every component can be added before modifying the branch.
        final Set<TreeComponent> uniqueComponents = new HashSet<>();
        final Set<String> uniqueNames = new HashSet<>();

        // Include names of existing committed children.
        uniqueNames.addAll(getChildren().keySet());

        // Include names of immediate children, including pending additions.
        if (immediateChildren != null) {
            for (final TreeComponent child : immediateChildren) {
                uniqueNames.add(child.getName());
            }
        }

        for (final TreeComponent component : components) {
            if (!uniqueComponents.add(component)) {
                throw new IllegalArgumentException("Duplicate component: " + component.getName());
            }

            if (component == this) {
                throw new IllegalArgumentException("Cannot add a branch to itself.");
            }

            if (component.immediateParent != null || component.immediateRoot != null || component.getParent() != null) {
                throw new IllegalArgumentException(
                        "Component '" + component.getName() + "' already has a parent or root.");
            }

            if (!uniqueNames.add(component.getName())) {
                throw new IllegalArgumentException("Duplicate component name: " + component.getName());
            }
        }

        // Add components, keeping track of those successfully added.
        final List<TreeComponent> added = new ArrayList<>(components.size());

        try {
            for (final TreeComponent component : components) {
                add(component);
                added.add(component);
            }
        } catch (final RuntimeException failure) {
            // Undo successful additions in reverse order.
            for (int i = added.size() - 1; i >= 0; i--) {
                try {
                    remove(added.get(i));
                } catch (final RuntimeException rollbackFailure) {
                    if (rollbackFailure != failure) {
                        failure.addSuppressed(rollbackFailure);
                    }
                }
            }

            throw failure;
        }

        return this;
    }

    public synchronized Branch remove(final List<? extends ComponentConfig<?>> componentConfigs) {
        Arguments.checkNotNull(componentConfigs, "componentConfigs");

        // Validate and capture every component before modifying the branch.
        final List<TreeComponent> components = new ArrayList<>(componentConfigs.size());

        for (final ComponentConfig<?> componentConfig : componentConfigs) {
            Arguments.checkNotNull(componentConfig, "componentConfig");

            final TreeComponent component = componentConfig.getComponent();
            Arguments.checkNotNull(component, "component");

            components.add(component);
        }

        // Remove components, keeping track of those successfully removed.
        final List<TreeComponent> removed = new ArrayList<>(components.size());

        try {
            for (final TreeComponent component : components) {
                remove(component);
                removed.add(component);
            }
        } catch (final RuntimeException failure) {
            // Undo successful removals in reverse order.
            for (int i = removed.size() - 1; i >= 0; i--) {
                try {
                    add(removed.get(i));
                } catch (final RuntimeException rollbackFailure) {
                    if (rollbackFailure != failure) {
                        failure.addSuppressed(rollbackFailure);
                    }
                }
            }

            throw failure;
        }

        return this;
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
