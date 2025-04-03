package tfw.tsm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import tfw.check.Argument;

// TODO: What should happen if an exception occurs while processing a
// transaction? Currently the transaction state is left as is.
// pending transaction will attempt to execute.
// TODO: Review exception handling for exceptions that are not
// currently caught.
// TODO: test rollback state changes
// TODO: test rollback component changes

/**
 * The transaction manager for the framework.
 *
 * Three kinds of transaction initiation:
 * <UL>
 * <LI>State Change - any call to (@link #addStateChange(InitiatorSource[], Object[]))
 * causes a new transaction to be create.</LI>
 * <LI>Component Change - consecutive calls to
 * (@link #addComponent(AddComponentRunnable, Throwable)) and
 * (@link #removeComponent(RemoveComponentRunnable, Throwable)) may or may not be
 * coalesced into one or more transactions. A call to
 * (@link #addStateChange(InitiatorSource[], Object[])) will cause the coalescing of
 * component changes in order to insure the correct ordering of transactions.
 * </LI>
 * <LI>Event channelfiring (Need to verify the validity of this type).</LI>
 * </UL>
 */
public final class TransactionMgr {
    final TransactionQueue queue;
    final CheckDependencies checkDependencies;

    private long transactionCount = 0;

    private long transactionId;
    private long currentlyExecutingTransactionId;
    private final Object lock = new Object();

    private boolean logging;

    private static final Object STATIC_LOCK = new Object();
    private static boolean traceLogging = false;

    private final ArrayList<Source> stateChanges = new ArrayList<Source>();

    private HashSet<Validator> validators = null;

    private ArrayList<Processor> processors = new ArrayList<Processor>();
    private ArrayList<Processor> delayedProcessors = new ArrayList<Processor>();

    private ArrayList<CommitRollbackListener> crListeners = new ArrayList<CommitRollbackListener>();

    private ArrayList<EventChannel> cycleStateChanges = new ArrayList<EventChannel>();

    private ArrayList<EventChannel> transStateChanges = new ArrayList<EventChannel>();

    private ArrayList<EventChannel> eventChannelFires = new ArrayList<EventChannel>();

    private Runnable componentChange = null;

    private boolean inTransaction = false;

    private boolean executingStateChanges = false;

    private TransactionExceptionHandler exceptionHandler = new TransactionExceptionHandler() {
        @Override
        public void handle(Exception exception) {
            logger.log(Level.INFO, "Unexpected Exception!", exception);

            throw new RuntimeException(
                    "An unhandled exception occured while processing a transaction: " + exception.getMessage(),
                    exception);
        }
    };

    private LocationFormatter locationFormatter = null;

    private static final Logger logger = Logger.getLogger(TransactionMgr.class.getName());

    /**
     * Constructs a transaction manager
     *
     * @param queue
     *            the transaction queue
     * @param logging
     *            a flag to turn logging on or off.
     */
    TransactionMgr(TransactionQueue queue, CheckDependencies checkDependencies, boolean logging) {
        Argument.assertNotNull(queue, "queue");
        this.queue = queue;
        this.checkDependencies = checkDependencies;
        this.logging = logging;
    }

    public void addChangedEventChannel(EventChannel ec) {
        cycleStateChanges.add(ec);
        transStateChanges.add(ec);
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    public boolean isLogging() {
        return logging;
    }

    public void setLocationFormatter(LocationFormatter locationFormatter) {
        synchronized (lock) {
            this.locationFormatter = locationFormatter;
        }
    }

    public static void setTraceLogging(boolean traceLogging) {
        synchronized (STATIC_LOCK) {
            TransactionMgr.traceLogging = traceLogging;
        }
    }

    public static boolean isTraceLogging() {
        synchronized (STATIC_LOCK) {
            return traceLogging;
        }
    }

    public TransactionQueue getTransactionQueue() {
        return queue;
    }

    /**
     * Gets the ID of the currently-executing transaction (or last executed
     * transaction if there is currently no transaction)
     *
     * @return the ID of the currently-executing transaction
     */
    long getCurrentlyExecutingTransactionId() {
        return currentlyExecutingTransactionId;
    }

    Logger getLogger() {
        if (logging) {
            return Logger.getLogger(TransactionMgr.class.getName());
        } else {
            return null;
        }
    }

    /**
     * Set the exception handler for this transaction manager.
     *
     * @param exceptionHandler
     *            the new exception handler
     */
    void setExceptionHandler(TransactionExceptionHandler exceptionHandler) {
        Argument.assertNotNull(exceptionHandler, "exceptionHandler");
        this.exceptionHandler = exceptionHandler;
    }

    private void executeTransaction(TransactionState transactionState, Throwable location) {
        Throwable thrown = null;

        try {
            thrown = executeTransactionHelper(transactionState, location);
        } finally {
            if (transactionState != null) {
                transactionState.getResultFuture().setResultAndRelease(thrown);
            }
        }
    }

    private void executeTransactionCycles() {
        executeComponentChange();

        long cycleNumber = 0;
        do {
            Logger logger = getLogger();

            if (logger != null) {
                logger.log(Level.INFO, "Cycle " + cycleNumber);
                logger.log(Level.INFO, "  State Changes:");
            }

            if (logger != null) {
                logger.log(Level.FINE, "executeEventChannelFires()");
            }
            executeEventChannelFires();

            if (logger != null) {
                logger.log(Level.FINE, "executeStateChanges()");
            }
            executeStateChanges();

            if (logger != null) {
                logger.log(Level.INFO, "  Validators:");
            }
            executeValidators();

            if (logger != null) {
                logger.log(Level.INFO, "  Processors:");
            }
            executeProcessors();

            if (logger != null) {
                logger.log(Level.FINE, "synchronizeCycleState()");
            }
            synchronizeCycleState();

            if (logger != null) {
                logger.log(Level.FINE, "executeEndOfCycleRunnables");
            }
            executeEndOfCycleRunnables();

            cycleNumber++;
        } while (stateChanges.size() != 0 || eventChannelFires.size() != 0 || processors.size() != 0);

        componentChange = null;
    }

    private Throwable executeTransactionHelper(TransactionState transactionState, Throwable location) {
        inTransaction = true;

        if (transactionState != null) {
            while (true) {
                try {
                    currentlyExecutingTransactionId =
                            transactionState.getTransactionIdFuture().get().longValue();

                    break;
                } catch (Exception e) {
                }
            }
        }

        Logger logger = getLogger();

        if (logger != null) {
            logger.log(Level.INFO, "******** Begin transaction: " + ++transactionCount + " *********");

            synchronized (lock) {
                if (locationFormatter != null) {
                    locationFormatter.formatLocation(logger, location);
                }
            }
        }

        Throwable thrown = null;

        try {
            executeTransactionCycles();
        } catch (RollbackException re) {
            thrown = re;
            executeRollback();
        } catch (Exception exception) {
            // TODO figure out if this is the right thing to do.
            thrown = exception;
            executeRollback();
            exceptionHandler.handle(exception);
        }

        try {
            commitTransaction();
            synchronizeTransState();
        } catch (Exception exception) {
            exceptionHandler.handle(exception);
        } finally {
            inTransaction = false;
        }

        if (logger != null) {
            logger.log(Level.INFO, "End transaction: " + transactionCount + "\n");
        }

        return thrown;
    }

    private int sourcesArraySize = 0;
    private Source[] sourcesArray = new Source[sourcesArraySize];

    private void executeStateChanges() {
        if (stateChanges.size() == 0) {
            return;
        }

        sourcesArraySize = stateChanges.size();
        if (sourcesArray.length < sourcesArraySize) {
            sourcesArray = new Source[sourcesArraySize];
        }
        stateChanges.toArray(sourcesArray);
        stateChanges.clear();
        executingStateChanges = true;

        Logger logger = getLogger();
        for (int i = 0; i < sourcesArraySize; i++) {
            Object state = sourcesArray[i].fire();
            cycleStateChanges.add(sourcesArray[i].eventChannel);
            transStateChanges.add(sourcesArray[i].eventChannel);
            if (logger != null) {
                logger.log(
                        Level.INFO,
                        "    S" + i + " : " + sourcesArray[i].getTreeComponent().getName()
                                + " : " + sourcesArray[i].ecd.getEventChannelName()
                                + " = " + state);
            }
        }

        executingStateChanges = false;

        // System.out.println();
    }

    private void executeValidators() {
        // if no validators have been added...
        if (validators == null) {
            return;
        }

        // System.out.print("executeValidators()");
        Validator[] v = validators.toArray(new Validator[validators.size()]);
        validators = null;

        Logger logger = getLogger();
        for (int i = 0; i < v.length; i++) {
            if (logger != null) {
                logger.log(Level.INFO, "validators[" + i + "].validateState(): " + v[i].getName());
            }

            v[i].validate();
        }

        // System.out.println();
    }

    private int executeProcessorsArraySize = 0;
    private Processor[] executeProcessorsArray = new Processor[executeProcessorsArraySize];

    private void executeProcessors() {
        // if there are no processors...
        if (processors.size() == 0) {
            return;
        }

        if (processors.size() > 1) {
            checkDependencies.checkDependencies(processors, delayedProcessors, logger);
        }

        // process the independent processors...
        executeProcessorsArraySize = processors.size();
        if (executeProcessorsArray.length < executeProcessorsArraySize) {
            executeProcessorsArray = new Processor[executeProcessorsArraySize];
        }
        processors.toArray(executeProcessorsArray);
        processors.clear();

        // if there are dependent processor to be delayed...
        if (delayedProcessors.size() > 0) {
            // remember the delayed processors for the next state change
            // cycle...
            ArrayList<Processor> t = processors;
            processors = delayedProcessors;
            delayedProcessors = t;
        }

        Logger logger = getLogger();
        for (int i = 0; i < executeProcessorsArraySize; i++) {
            if (logger != null) {
                logger.log(Level.INFO, "    P" + i + " : " + executeProcessorsArray[i].getName());
            }
            try {
                executeProcessorsArray[i].process();
            } catch (Exception e) {
                Logger.getLogger(TransactionMgr.class.getName())
                        .log(Level.WARNING, "Exception in " + executeProcessorsArray[i].getName(), e);
            }
        }
    }

    /**
     * Searches the set of processors for dependencies. If a dependency is
     * found, the dependent processor is removed from the processor set and
     * added to the delayedProcessors set.
     *
     * @param processors
     *            The set of processors to be check for dependencies.
     * @param delayedProcessors
     *            The set of dependent processors which are to be delayed.
     */
    private static void checkDependencies(Set<TreeComponent> processors, Set<TreeComponent> delayedProcessors) {
        Set<TreeComponent> processorCrumbs = new HashSet<TreeComponent>();
        Set<Terminator> terminatorCrumbs = new HashSet<Terminator>();
        Processor[] procs = processors.toArray(new Processor[processors.size()]);

        for (int i = 0; i < procs.length; i++) {
            // if the ith processor has not already been delayed...
            if (processors.contains(procs[i])) {
                checkDependencies(procs[i], processors, delayedProcessors, processorCrumbs, terminatorCrumbs);
            }
        }
    }

    /**
     * Checks to see if any processors are dependent on the specified processor.
     *
     * @param processor The processor to checked
     * @param processors The list of processors to run.
     * @param delayedProcessors The list of delayed processors to run later.
     * @param processorCrumbs Processors that have already been visit in the recursive descent algorithm.
     * @param terminatorCrumbs Terminators that have already been visited in the recursive descent algorithm.
     */
    private static void checkDependencies(
            TreeComponent processor,
            Set<TreeComponent> processors,
            Set<TreeComponent> delayedProcessors,
            Set<TreeComponent> processorCrumbs,
            Set<Terminator> terminatorCrumbs) {
        processorCrumbs.add(processor);

        // For each of the processor's sources
        List<Source> sources = processor.getSources();
        for (int i = 0; i < sources.size(); i++) {
            Source src = sources.get(i);

            // If the source is connected to a terminator...
            // TODO: Climb up through multiplexors/demultiplexors to
            // to find the terminators (This isn't going to be fun).
            if (src.eventChannel instanceof Terminator) {
                Terminator t = (Terminator) src.eventChannel;
                if (terminatorCrumbs.add(t) == false) {
                    continue;
                }
                Sink[] sinks = t.getSinks();
                // For each sink that the processors might publish too...
                for (int j = 0; j < sinks.length; j++) {
                    // get the sink's component...
                    TreeComponent tc = sinks[j].getTreeComponent();

                    // if we have already visited this component...
                    if (processorCrumbs.contains(tc)) {
                        // Break infinite loop...
                        continue;
                    }

                    // if the dependent component is in the set of processors
                    // remove it and add it to the set of delayed processors...
                    if (processors.remove(tc)) {
                        delayedProcessors.add(tc);
                    }

                    // continue searching the dependency chain...
                    checkDependencies(tc, processors, delayedProcessors, processorCrumbs, terminatorCrumbs);
                }
            }
        }

        processorCrumbs.remove(processor);
    }

    private int cycleStateChangesArraySize = 0;
    private EventChannel[] cycleStateChangesArray = new EventChannel[cycleStateChangesArraySize];

    private void synchronizeCycleState() {
        if (cycleStateChanges.size() == 0) {
            return;
        }

        cycleStateChangesArraySize = cycleStateChanges.size();
        if (cycleStateChangesArray.length < cycleStateChangesArraySize) {
            cycleStateChangesArray = new EventChannel[cycleStateChangesArraySize];
        }
        cycleStateChanges.toArray(cycleStateChangesArray);
        cycleStateChanges.clear();

        Logger logger = getLogger();
        for (int i = 0; i < cycleStateChangesArraySize; i++) {
            if (logger != null) {
                logger.log(
                        Level.FINE,
                        "eventChannels[" + i
                                + "].synchronizeCycleState(): "
                                + cycleStateChangesArray[i].getECD().getEventChannelName());
            }
            cycleStateChangesArray[i].synchronizeCycleState();
        }
    }

    private int eventChannelsSize = 0;
    private EventChannel[] eventChannels = new EventChannel[eventChannelsSize];

    private void synchronizeTransState() {
        if (transStateChanges.size() == 0) {
            return;
        }

        eventChannelsSize = transStateChanges.size();
        if (eventChannels.length < eventChannelsSize) {
            eventChannels = new EventChannel[eventChannelsSize];
        }
        transStateChanges.toArray(eventChannels);
        transStateChanges.clear();

        Logger logger = getLogger();
        for (int i = 0; i < eventChannelsSize; i++) {
            if (logger != null) {
                logger.log(
                        Level.FINE,
                        "eventChannels[" + i
                                + "].synchronizeTransactionState(): "
                                + eventChannels[i].getECD().getEventChannelName());
            }
            eventChannels[i].synchronizeTransactionState();
        }
    }

    private void executeEventChannelFires() {
        if (eventChannelFires.size() == 0) {
            return;
        }

        // System.out.print("executeEventChannelFires()");
        EventChannel[] ec = eventChannelFires.toArray(new EventChannel[eventChannelFires.size()]);
        eventChannelFires.clear();

        executingStateChanges = true;

        Logger logger = getLogger();
        for (int i = 0; i < ec.length; i++) {
            ec[i].fire();
            if (logger != null) {
                logger.log(
                        Level.INFO,
                        "    S" + i + " : " + ec[i].getECD().getEventChannelName() + " : " + ec[i].getState());
            }
            this.transStateChanges.add(ec[i]);
        }

        executingStateChanges = false;
    }

    private void executeComponentChange() {
        if (componentChange == null) {
            return;
        }

        Logger logger = getLogger();
        if (logger != null) {
            logger.log(Level.INFO, "Add/Remove Component");
            logger.log(Level.INFO, "  " + componentChange);
        }

        componentChange.run();
    }

    private void executeRollback() {
        // System.out.print("executeRollback()");
        stateChanges.clear();
        validators = null;
        processors.clear();
        delayedProcessors.clear();
        cycleStateChanges.clear();
        transStateChanges.clear();
        eventChannelFires.clear();
        componentChange = null;

        CommitRollbackListener[] crls = crListeners.toArray(new CommitRollbackListener[crListeners.size()]);
        crListeners.clear();

        for (int i = 0; i < crls.length; i++) {
            // System.out.print("*");
            crls[i].rollback();
        }

        // System.out.println();
        executeTransactionCycles();
    }

    private int commitRollbackListenersSize = 0;
    private CommitRollbackListener[] commitRollbackListeners = new CommitRollbackListener[commitRollbackListenersSize];

    private void commitTransaction() {
        Logger logger = getLogger();
        // System.out.print("commitTransaction()");
        if (logger != null) {
            logger.log(Level.INFO, "Commit:");
        }

        synchronized (this) {
            commitRollbackListenersSize = crListeners.size();
            if (commitRollbackListeners.length < commitRollbackListenersSize) {
                commitRollbackListeners = new CommitRollbackListener[commitRollbackListenersSize];
            }
            crListeners.toArray(commitRollbackListeners);
            crListeners.clear();
        }

        for (int i = 0, c = 0; i < commitRollbackListenersSize; i++) {
            if (logger != null) {
                if (!(commitRollbackListeners[i] instanceof Terminator)) {
                    logger.log(Level.INFO, "  C" + (c++) + " : " + commitRollbackListeners[i].getName());
                }
            }

            // System.out.print("*");
            try {
                commitRollbackListeners[i].commit();
            } catch (Exception e) {
                Logger.getLogger(TransactionMgr.class.getName())
                        .log(Level.WARNING, "Exception in " + commitRollbackListeners[i].getName(), e);
            }
        }

        // System.out.println();
    }

    void addCommitRollbackListener(CommitRollbackListener listener) {
        synchronized (this) {
            if (!crListeners.contains(listener)) {
                crListeners.add(listener);
            }
        }
    }

    /**
     * Adds a source for a state change. This method is called by {@link Source}.
     * It can be called at any time, by any thread.
     *
     * @param source
     *            The source to be added.
     */
    void addStateChange(ProcessorSource source) {
        if (!queue.isDispatchThread()) {
            StringBuilder sb = new StringBuilder();
            sb.append("A Processor attempted to change state of event channel '");
            sb.append(source.ecd.getEventChannelName());
            sb.append("' outside of the transaction thread.");
            sb.append(" The source for the state change is '");
            sb.append(source.getFullyQualifiedName()).append("'");
            throw new IllegalStateException(sb.toString());
        }

        if (!inTransaction) {
            StringBuilder sb = new StringBuilder();
            sb.append("A Processor attempted to change state of event channel '");
            sb.append(source.ecd.getEventChannelName());
            sb.append("' outside of an active transaction.");
            sb.append(" The source for the state change is '");
            sb.append(source.getFullyQualifiedName()).append("'");
            throw new IllegalStateException(sb.toString());
        }

        if (!stateChanges.add(source)) {
            throw new IllegalStateException(source.getTreeComponent().getName()
                    + " attempted to change the state of '"
                    + source.ecd.getEventChannelName()
                    + "' twice in the same state change cycle.");
        }
    }

    void addStateChange(InitiatorSource[] sources, Object[] state) {
        addStateChange(sources, state, queue.createTransactionState(), null);
    }

    void addStateChange(
            InitiatorSource[] sources, Object[] state, TransactionState transactionState, Throwable setLocation) {
        long id = invokeLater(new StateChangeTransaction(sources, state, transactionState, setLocation));
        transactionState.getTransactionIdFuture().setResultAndRelease(Long.valueOf(id));
    }

    /**
     * This method is called by {@link Processor}and {@link TriggeredConverter}.
     * It should only be called in the state change phase.
     *
     * @param processor
     *            the processor to be added.
     */
    void addProcessor(Processor processor) {
        if (!executingStateChanges) {
            throw new IllegalStateException("Processors can only be active during state change cycles.");
        }

        if (!processors.contains(processor)) {
            processors.add(processor);
        }
    }

    /**
     * This method is called by {@link Validator}. It should only be called in
     * the state change phase.
     *
     * @param validator
     *            the validator to be added.
     */
    void addValidator(Validator validator) {
        if (!executingStateChanges) {
            throw new IllegalStateException("Validators can only be active during state change cycles.");
        }

        if (validators == null) {
            validators = new HashSet<Validator>();
        }

        validators.add(validator);
    }

    /**
     * Creates an adds component change. This is called by {@link TreeComponent}.
     * It can be called at any time from any thread.
     *
     * @param addComponentRunnable the runnable that will add the component.
     * @param addLocation the location where the add occurred or null.
     */
    void addComponent(AddComponentRunnable addComponentRunnable, Throwable addLocation) {
        invokeLater(new ComponentChangeTransaction(addComponentRunnable, addLocation));
    }

    /**
     * Creates remove component change. This is called by {@link TreeComponent}.
     * It can be called at any time from any thread.
     *
     * @param removeComponentRunnable the runnable that will remove the component.
     * @param removeLocation the location where the remove occurred or null.
     */
    void removeComponent(RemoveComponentRunnable removeComponentRunnable, Throwable removeLocation) {
        invokeLater(new ComponentChangeTransaction(removeComponentRunnable, removeLocation));
    }

    void addEventChannelFire(EventChannel eventChannel) {
        if (!inTransaction) {
            invokeLater(new EventChannelFireTransaction(
                    eventChannel, isTraceLogging() ? new Throwable("EventChannelFire") : null));
        } else if (!queue.isDispatchThread()) {
            // This means we are in a transaction but the method call
            // occurred outside of the transaction.
            throw new IllegalStateException(
                    "can't call outside transaction queue thread while a transaction is " + "in progress");
        } else {
            eventChannelFires.add(eventChannel);
        }
    }

    boolean isDispatchThread() {
        return queue.isDispatchThread();
    }

    boolean isComponentChangeTransactionExecuting() {
        return componentChange != null;
    }

    private final HashSet<Runnable> endOfCycleRunnables = new HashSet<Runnable>();

    public void addEndOfCycleRunnable(Runnable runnable) {
        Argument.assertNotNull(runnable, "runnable");

        synchronized (this) {
            endOfCycleRunnables.add(runnable);
        }
    }

    private void executeEndOfCycleRunnables() {
        Runnable[] runnables = null;

        synchronized (this) {
            if (endOfCycleRunnables.size() == 0) {
                return;
            }

            runnables = endOfCycleRunnables.toArray(new Runnable[endOfCycleRunnables.size()]);
            endOfCycleRunnables.clear();
        }

        for (int i = 0; i < runnables.length; i++) {
            runnables[i].run();
        }
    }

    void lockTransactionQueue() {
        queue.lock();
    }

    void unlockTransactionQueue() {
        queue.unlock();
    }

    private long invokeLater(Runnable runnable) {
        try {
            queue.lock();
            queue.invokeLater(runnable);

            return transactionId++;
        } finally {
            queue.unlock();
        }
    }

    private class ComponentChangeTransaction implements Runnable {
        private final Runnable change;
        private final Throwable addRemoveLocation;

        public ComponentChangeTransaction(Runnable change, Throwable addRemoveLocation) {
            this.change = change;
            this.addRemoveLocation = addRemoveLocation;
        }

        @Override
        public void run() {
            componentChange = change;

            try {
                executeTransaction(null, addRemoveLocation);
            } catch (Exception exp) {
                exceptionHandler.handle(exp);
            }
        }
    }

    private class StateChangeTransaction implements Runnable {
        private final InitiatorSource[] sources;
        private final Object[] state;
        private final TransactionState transactionState;
        private final Throwable setLocation;

        public StateChangeTransaction(
                InitiatorSource[] sources, Object[] state, TransactionState transactionState, Throwable setLocation) {
            this.sources = sources;
            this.state = state;
            this.transactionState = transactionState;
            this.setLocation = setLocation;
        }

        @Override
        public void run() {
            for (int i = 0; i < sources.length; i++) {
                if (sources[i].isConnected()) {
                    sources[i].setState(state[i]);
                    stateChanges.add(sources[i]);
                } else {
                    IllegalStateException ise = new IllegalStateException("Attempting to change state on source "
                            + sources[i].getFullyQualifiedName() + "; however, it is not connected!");

                    if (transactionState != null) {
                        transactionState.getResultFuture().setResultAndRelease(ise);
                    } else {
                        throw ise;
                    }
                }
            }

            executeTransaction(transactionState, setLocation);
        }
    }

    private class EventChannelFireTransaction implements Runnable {
        private final EventChannel eventChannel;
        private final Throwable eventChannelLocation;

        public EventChannelFireTransaction(EventChannel eventChannel, Throwable eventChannelLocation) {
            this.eventChannel = eventChannel;
            this.eventChannelLocation = eventChannelLocation;
        }

        @Override
        public void run() {
            eventChannelFires.add(eventChannel);
            executeTransaction(null, eventChannelLocation);
        }
    }

    static class AddComponentRunnable implements Runnable {
        final BranchComponent parent;
        final TreeComponent child;
        private TransactionMgr transactionMgr;

        AddComponentRunnable(final BranchComponent parent, final TreeComponent child) {
            this.parent = parent;
            this.child = child;
        }

        @Override
        public void run() {
            parent.addToChildren(child);
            if (child instanceof BranchComponent) {
                parent.terminateParentAndLocalConnections(
                        ((BranchComponent) child).terminateChildAndLocalConnections());
            } else {
                parent.terminateParentAndLocalConnections(child.terminateLocally(new HashSet<Port>()));
            }
            if (!(child instanceof Initiator) && !(child instanceof BaseCommit)) {
                transactionMgr.checkDependencies.clearCache();
            }
        }

        public TransactionMgr getTransactionMgr() {
            return transactionMgr;
        }

        public void setTransactionMgr(TransactionMgr transactionMgr) {
            this.transactionMgr = transactionMgr;
        }

        @Override
        public String toString() {
            return "add Component " + child.getName() + " to " + parent.getName();
        }
    }

    static class RemoveComponentRunnable implements Runnable {
        final BranchComponent parent;
        final TreeComponent child;
        final TreeComponent[] children;
        private TransactionMgr transactionMgr;

        RemoveComponentRunnable(final BranchComponent parent, final TreeComponent child) {
            this.parent = parent;
            this.child = child;
            this.children = null;
        }

        RemoveComponentRunnable(final BranchComponent parent, final TreeComponent[] children) {
            this.parent = parent;
            this.child = null;
            this.children = children;
        }

        @Override
        public void run() {
            Logger logger = transactionMgr.getLogger();
            if (logger != null) {
                logger.info(this.toString());
            }

            boolean clearCache = false;

            if (child != null) {
                clearCache = removeChild(child);
            }

            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    clearCache = removeChild(children[i]) ? true : clearCache;
                }
            }

            if (clearCache) {
                transactionMgr.checkDependencies.clearCache();
            }
        }

        private boolean removeChild(final TreeComponent child) {

            if (child.isRooted()) {
                parent.removeFromChildren(child);
                if (child instanceof BranchComponent) {
                    ((BranchComponent) child).disconnect();
                } else {
                    child.disconnectPorts();
                }
            }

            return !(child instanceof Initiator) && !(child instanceof BaseCommit);
        }

        public TransactionMgr getTransactionMgr() {
            return transactionMgr;
        }

        public void setTransactionMgr(TransactionMgr transactionMgr) {
            this.transactionMgr = transactionMgr;
        }

        @Override
        public String toString() {
            return "remove Component " + child.getName() + " from " + parent.getName();
        }
    }

    public interface LocationFormatter {
        void formatLocation(Logger logger, Throwable throwable);
    }
}
