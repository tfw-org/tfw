package tfw.tsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import tfw.check.Argument;
import tfw.tsm.AddRemoveOperation.Operation;
import tfw.tsm.TransactionMgr.AddComponentRunnable;
import tfw.tsm.TransactionMgr.RemoveComponentRunnable;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.value.ValueException;

public abstract class BranchComponent extends TreeComponent {
    public static final String DEFAULT_EXPORT_TAG = "All";

    /** This component's children */
    private Map<String, TreeComponent> children = null;

    private final ArrayList<Object> deferredAddRemoveSets = new ArrayList<Object>();
    protected Set<TreeComponent> immediateChildren = null;

    public abstract void remove(TreeComponent treeComponent);

    BranchComponent(String name, Sink[] sinks, Source[] sources, EventChannel[] eventChannels) {
        super(name, sinks, sources, eventChannels);
    }

    void addToChildren(TreeComponent child) {
        if (child.getParent() != null) {
            throw new IllegalArgumentException("Child, '" + child.getName() + "', already has a parent.");
        }

        if (child == this) {
            throw new IllegalArgumentException("child == this not allowed");
        }

        if (child.isRooted()) {
            throw new IllegalArgumentException("Child, '" + child.getName() + "' is rooted. Can't add a rooted tree!");
        }

        if (children == null) {
            this.children = new HashMap<String, TreeComponent>();
        }
        if (children.containsKey(child.getName())) {
            throw new IllegalArgumentException("Attempt to add child with duplicate name, '" + child.getName() + "'");
        }
        children.put(child.getName(), child);
        child.setParent(this);
    }

    void removeFromChildren(TreeComponent child) {
        if (child.getParent() != this) {
            throw new IllegalArgumentException("child, " + child.getName()
                    + ", not connected to this component, '" + this.getFullyQualifiedName()
                    + "'");
        }
        children.remove(child.getName());
        child.setParent(null);
    }

    /**
     * Returns an copy of this component's map of children.
     *
     * @return an copy of this component's map of children.
     */
    Map<String, TreeComponent> getChildren() {
        if (children == null) {
            return new HashMap<String, TreeComponent>();
        }

        synchronized (children) {
            return new HashMap<String, TreeComponent>(children);
        }
    }

    /**
     * Recursively terminates child component ports and terminates leaf ports.
     * The initial call to this method to kick off the recursion is made by the
     * transaction manager.
     *
     * @return Unterminated ports.
     */
    Set<Port> terminateChildAndLocalConnections() {
        HashSet<Port> unterminatedConnections = new HashSet<Port>();
        TreeComponent[] treeComponents = getChildComponents();

        for (int i = 0; i < treeComponents.length; i++) {
            if (treeComponents[i] instanceof BranchComponent) {
                unterminatedConnections.addAll(
                        ((BranchComponent) treeComponents[i]).terminateChildAndLocalConnections());
            } else {
                unterminatedConnections.addAll(treeComponents[i].terminateLocally(unterminatedConnections));
            }
        }

        return terminateLocally(unterminatedConnections);
    }

    private TreeComponent[] getChildComponents() {
        if (children == null) {
            return new TreeComponent[0];
        }
        synchronized (children) {
            return children.values().toArray(new TreeComponent[children.size()]);
        }
    }

    /**
     * Calls disconnectPorts() and then recursively calls disconnect on the
     * child components.
     */
    void disconnect() {
        disconnectPorts();

        TreeComponent[] childComponents = getChildComponents();
        for (int i = 0; i < childComponents.length; i++) {
            if (childComponents[i] instanceof BranchComponent) {
                ((BranchComponent) childComponents[i]).disconnect();
            } else {
                childComponents[i].disconnectPorts();
            }
        }
    }

    /**
     * Returns the this components tree state using the
     * {@link #DEFAULT_EXPORT_TAG}.
     *
     * @return this components tree state.
     * @throws IllegalStateException
     *             if this component is not rooted.
     * @throws IllegalStateException
     *             if called outside of the of the transaction manager's
     *             transaction queue thread.
     */
    public TreeState getTreeState() {
        return getTreeState(DEFAULT_EXPORT_TAG);
    }

    /**
     * Returns the this components tree state using the
     * {@link #DEFAULT_EXPORT_TAG}.
     *
     * @param exportTag
     *            The event channel export tag. Only event channels with this
     *            export tag will be included in the tree state.
     * @return this components tree state.
     * @throws IllegalStateException
     *             if this component is not rooted.
     * @throws IllegalStateException
     *             if called outside of the of the transaction manager's
     *             transaction queue thread.
     */
    public TreeState getTreeState(String exportTag) {
        Argument.assertNotNull(exportTag, "exportTag");
        if (!isRooted()) {
            throw new IllegalStateException("This component is not rooted and therefore it's state is undefined.");
        }

        if (!getTransactionManager().isDispatchThread()) {
            throw new IllegalStateException("This method can not be called from outside the transaction queue thread");
        }

        TreeStateBuffer buff = new TreeStateBuffer();
        buff.setName(this.getName());

        for (EventChannel ec : this.eventChannels.values()) {
            if (isExport(ec, exportTag)) {
                try {
                    EventChannelState ecs = new EventChannelState(ec.getECD(), ec.getState());
                    buff.addState(ecs);
                } catch (ValueException unexpected) {
                    // This should never happen.
                    throw new IllegalStateException("Event channel has invalid state: " + unexpected.getMessage());
                }
            }
        }

        TreeComponent[] childComponents = getChildComponents();
        for (int i = 0; i < childComponents.length; i++) {
            if (childComponents[i] instanceof BranchComponent) {
                buff.addChild(((BranchComponent) childComponents[i]).getTreeState(exportTag));
            }
        }

        return buff.toTreeState();
    }

    private static boolean isExport(EventChannel ec, String exportTag) {
        if (ec.getECD() instanceof StatelessTriggerECD || !ec.getECD().isFireOnConnect() || ec.getState() == null) {
            return false;
        }

        if (!(ec instanceof Terminator)) {
            return false;
        }
        return ((Terminator) ec).isExportTag(exportTag);
    }

    /**
     * Sets the state of this tree.
     *
     * @param state
     *            The state.
     * @param skipMissingEventChannels
     *            A flag indicating whether to allow missing event channels. If
     *            <code>false</code> an IllegalArgumentException will be
     *            thrown if the <code>state</code> contains state for an event
     *            channel which is not present in the tree structure.
     * @param skipMissingChildBranches
     *            A flag indicating whether to allow missing child branches. if
     *            <code>false</code> an IllegalArgumentException will be
     *            thrown if the <code>state</code> contains child tree states
     *            for a branch which is not present in the tree structure.
     * @throws IllegalStateException
     *             if this component is not rooted.
     * @throws IllegalStateException
     *             if called outside of the of the transaction manager's
     *             transaction queue thread.
     */
    public void setTreeState(TreeState state, boolean skipMissingEventChannels, boolean skipMissingChildBranches) {
        Argument.assertNotNull(state, "state");

        if (!isRooted()) {
            throw new IllegalStateException(
                    "This component (" + getName() + ") is not rooted and therefore it's state is undefined.");
        }

        if (!getTransactionManager().isDispatchThread()) {
            throw new IllegalStateException("This method can not be called from outside the transaction queue thread");
        }

        if (!this.getName().equals(state.getName())) {
            throw new IllegalArgumentException("TreeState name does not match, expected <" + this.getName()
                    + "> found <" + state.getName() + ">.");
        }

        EventChannelState[] ecs = state.getState();

        for (int i = 0; i < ecs.length; i++) {
            Terminator ec = (Terminator) this.eventChannels.get(ecs[i].getEventChannelName());

            if (ec == null) {
                if (skipMissingEventChannels) {
                    continue;
                }
                throw new IllegalArgumentException(
                        "TreeState contains state for an unknown event channel '" + ecs[i].getEventChannelName() + "'");
            }

            ec.importState(ecs[i].getState());
        }

        TreeState[] childTreeState = state.getChildren();

        if (children == null) {
            if (childTreeState.length == 0 || skipMissingChildBranches) {
                return;
            }
            throw new IllegalArgumentException("'state' contains child tree state and this component has no children");
        }
        for (int i = 0; i < childTreeState.length; i++) {
            TreeComponent child = children.get(childTreeState[i].getName());

            if (child == null) {
                if (skipMissingChildBranches) {
                    continue;
                }
                throw new IllegalArgumentException(
                        "TreeState contains unknown child tree state with name '" + childTreeState[i].getName());
            }

            if (child instanceof BranchComponent) {
                ((BranchComponent) child)
                        .setTreeState(childTreeState[i], skipMissingEventChannels, skipMissingChildBranches);
            }
        }
    }

    final synchronized void performAddRemoveOperations(AddRemoveOperation[] operations) {
        Argument.assertElementNotNull(operations, "operations");

        boolean containsAdd = false;
        for (AddRemoveOperation operation : operations) {
            if (operation.getOperation() == Operation.ADD) {
                containsAdd = true;
                break;
            }
        }
        if (containsAdd && immediateChildren == null) {
            immediateChildren = new HashSet<TreeComponent>();
        }
        List<Runnable> runnables = new LinkedList<Runnable>();
        for (AddRemoveOperation operation : operations) {
            TreeComponent child = operation.getTreeComponent();
            if (operation.getOperation() == Operation.ADD) {
                if (!immediateChildren.add(child)) {
                    throw new IllegalStateException(
                            child.getFullyQualifiedName() + " is already a child of this Branch");
                }
                // Create the CHILD -> PARENT relationship.
                if (child.immediateParent == null) {
                    child.immediateParent = this;
                } else {
                    throw new IllegalStateException(child.getFullyQualifiedName() + " already has a parent!");
                }
                runnables.add(new TransactionMgr.AddComponentRunnable(this, child));
            } else if (operation.getOperation() == Operation.REMOVE) {
                if (immediateChildren == null || !immediateChildren.remove(child)) {
                    throw new IllegalStateException(child.getFullyQualifiedName() + " is not a child of this Branch!");
                }
                // Remove the CHILD -> PARENT relationship.
                if (child.immediateParent == this) {
                    child.immediateParent = null;
                } else {
                    throw new IllegalStateException(
                            child.getFullyQualifiedName() + " has " + child.immediateParent + " Parent!");
                }
                runnables.add(new RemoveComponentRunnable(this, child));
            }
        }

        if (immediateChildren != null && immediateChildren.isEmpty()) {
            immediateChildren = null;
        }

        // Perform deferred add on child & descendants if this node has a Root.
        if (immediateRoot != null) {
            synchronized (immediateRoot) {
                TransactionMgr transactionMgr = immediateRoot.getTransactionManager();

                transactionMgr.lockTransactionQueue();
                try {
                    for (Runnable runnable : runnables) {
                        if (runnable instanceof AddComponentRunnable) {
                            AddComponentRunnable acr = (AddComponentRunnable) runnable;

                            acr.setTransactionMgr(transactionMgr);
                            transactionMgr.addComponent(
                                    acr, TransactionMgr.isTraceLogging() ? new Throwable("AddComponent") : null);

                            ArrayList<Object> allDeferredAddRemoveSets = new ArrayList<Object>();

                            addChildAndDescendents(this, acr.child, allDeferredAddRemoveSets);
                            TransactionMgrUtil.postAddRemoveSetsToQueue(
                                    allDeferredAddRemoveSets.toArray(), transactionMgr);
                        } else if (runnable instanceof RemoveComponentRunnable) {
                            RemoveComponentRunnable rcr = (RemoveComponentRunnable) runnable;

                            rcr.setTransactionMgr(transactionMgr);
                            transactionMgr.removeComponent(
                                    rcr, TransactionMgr.isTraceLogging() ? new Throwable("RemoveComponent") : null);
                            removeChildAndDescendents(this, rcr.child);
                        }
                    }
                } finally {
                    transactionMgr.unlockTransactionQueue();
                }
            }
        } else {
            for (Runnable runnable : runnables) {
                synchronized (deferredAddRemoveSets) {
                    deferredAddRemoveSets.add(runnable);
                }
                if (runnable instanceof AddComponentRunnable) {
                    AddComponentRunnable acr = (AddComponentRunnable) runnable;

                    if (acr.child instanceof Initiator) {
                        Initiator initiator = (Initiator) acr.child;

                        Initiator.TransactionContainer[] sourceNState = initiator.getDeferredStateChangesAndClear();
                        if (sourceNState != null) {
                            for (int i = 0; i < sourceNState.length; i++) {
                                addStateChange(sourceNState[i]);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds a child component to this branch.
     *
     * @param child
     *            the child component.
     */
    final synchronized void addChild(final TreeComponent child) {
        Argument.assertNotNull(child, "child");

        // Create the PARENT -> CHILD relationship.
        if (immediateChildren == null) {
            immediateChildren = new HashSet<TreeComponent>();
        }
        if (!immediateChildren.add(child)) {
            throw new IllegalStateException(child.getFullyQualifiedName() + " is already a child of this Branch");
        }

        // Create the CHILD -> PARENT relationship.
        if (child.immediateParent == null) {
            child.immediateParent = this;
        } else {
            throw new IllegalStateException(child.getFullyQualifiedName() + " already has a parent!");
        }

        TransactionMgr.AddComponentRunnable addComponentRunnable = new TransactionMgr.AddComponentRunnable(this, child);

        // Perform deferred add on child & descendants if this node has a Root.
        if (immediateRoot != null) {
            synchronized (immediateRoot) {
                addComponentRunnable.setTransactionMgr(immediateRoot.getTransactionManager());
                immediateRoot
                        .getTransactionManager()
                        .addComponent(
                                addComponentRunnable,
                                TransactionMgr.isTraceLogging() ? new Throwable("AddComponent") : null);
                List<Object> allDeferredAddRemoveSets = new ArrayList<Object>();

                addChildAndDescendents(this, child, allDeferredAddRemoveSets);

                TransactionMgrUtil.postAddRemoveSetsToQueue(
                        allDeferredAddRemoveSets.toArray(), immediateRoot.getTransactionManager());
            }
        } else {
            synchronized (deferredAddRemoveSets) {
                deferredAddRemoveSets.add(addComponentRunnable);
            }

            if (child instanceof Initiator) {
                Initiator.TransactionContainer[] containers = ((Initiator) child).getDeferredStateChangesAndClear();

                if (containers != null) {
                    for (int i = 0; i < containers.length; i++) {
                        addStateChange(containers[i]);
                    }
                }
            }
        }
    }

    private synchronized void addChildAndDescendents(
            final TreeComponent parent, final TreeComponent child, List<Object> allDeferredAddRemoveSets) {
        synchronized (child) {
            if (child.immediateRoot != null) {
                throw new IllegalStateException(child.getFullyQualifiedName() + " already has a Root!");
            }
            child.immediateRoot = immediateRoot;

            if (child instanceof BranchComponent) {
                BranchComponent ptc = (BranchComponent) child;

                synchronized (ptc.deferredAddRemoveSets) {
                    allDeferredAddRemoveSets.addAll(ptc.deferredAddRemoveSets);
                    ptc.deferredAddRemoveSets.clear();
                }

                if (ptc.immediateChildren != null) {
                    for (TreeComponent element : ptc.immediateChildren) {
                        addChildAndDescendents(child, element, allDeferredAddRemoveSets);
                    }
                }
            }

            if (child instanceof Initiator) {
                Initiator.TransactionContainer[] transactionContainers =
                        ((Initiator) child).getDeferredStateChangesAndClear();

                if (transactionContainers != null) {
                    for (int i = 0; i < transactionContainers.length; i++) {
                        Initiator.SourceNState sourceNState = transactionContainers[i].state;

                        immediateRoot
                                .getTransactionManager()
                                .addStateChange(
                                        sourceNState.sources,
                                        sourceNState.state,
                                        transactionContainers[i].transactionState,
                                        transactionContainers[i].setLocation);
                    }
                }
            }
        }
    }

    synchronized void addStateChange(Initiator.TransactionContainer transactionContainer) {
        Argument.assertNotNull(transactionContainer, "transactionContainer");

        synchronized (deferredAddRemoveSets) {
            deferredAddRemoveSets.add(transactionContainer);
        }
    }

    /**
     * Removes a child component.
     *
     * @param child
     *            the component to be removed.
     */
    final synchronized void removeChild(final TreeComponent child) {
        Argument.assertNotNull(child, "child");

        // Remove the PARENT -> CHILD relationship.
        if (immediateChildren == null || !immediateChildren.remove(child)) {
            throw new IllegalStateException(child.getFullyQualifiedName() + " is not a child of this Branch!");
        }
        if (immediateChildren.size() == 0) {
            immediateChildren = null;
        }

        // Remove the CHILD -> PARENT relationship.
        if (child.immediateParent == this) {
            child.immediateParent = null;
        } else {
            throw new IllegalStateException(
                    child.getFullyQualifiedName() + " has " + child.immediateParent + " Parent!");
        }

        TransactionMgr.RemoveComponentRunnable removeComponentRunnable =
                new TransactionMgr.RemoveComponentRunnable(this, child);

        // Perform deferred remove on child & descendents if this node has a Root.
        if (immediateRoot != null) {
            synchronized (immediateRoot) {
                removeComponentRunnable.setTransactionMgr(immediateRoot.getTransactionManager());
                immediateRoot
                        .getTransactionManager()
                        .removeComponent(
                                removeComponentRunnable,
                                TransactionMgr.isTraceLogging() ? new Throwable("RemoveComponent") : null);

                removeChildAndDescendents(this, child);
            }
        } else {
            synchronized (deferredAddRemoveSets) {
                deferredAddRemoveSets.add(removeComponentRunnable);
            }
        }
    }

    private synchronized void removeChildAndDescendents(final TreeComponent parent, final TreeComponent child) {
        synchronized (child) {
            if (child instanceof BranchComponent) {
                BranchComponent ptc = (BranchComponent) child;

                if (ptc.immediateChildren != null) {
                    for (TreeComponent element : ptc.immediateChildren) {
                        removeChildAndDescendents(child, element);
                    }
                }
            }

            if (child.immediateRoot != immediateRoot) {
                throw new IllegalStateException(
                        child.getFullyQualifiedName() + " has " + child.immediateRoot + " Root!");
            }
            child.immediateRoot = null;
        }
    }
}
