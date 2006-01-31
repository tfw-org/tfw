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

import tfw.check.Argument;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * Three kinds of transaction initiations:
 * <UL>
 * <LI>State Change - any call to {@link #addStateChange(InitiatorSource[])}
 * causes a new transaction to be created.</LI>
 * <LI>Component Change - consecutive calls to
 * {@link #addComponent(TreeComponent, TreeComponent)}and
 * {@link #removeComponent(TreeComponent, TreeComponent)}may or may not be
 * coalesced into one or more transactions. A call to
 * {@link #addStateChange(InitiatorSource[])}will cause the coalescing of
 * component changes in order to insure the correct ordering of transactions.
 * </LI>
 * <LI>Event Channel Firing (Need to verify the validity of this type).</LI>
 * </UL>
 * 
 */
public final class TransactionMgr
{
    private final TransactionQueue queue;

    private long transactionCount = 0;

    private final HashSet stateChanges = new HashSet();

    private HashSet validators = null;

    private HashSet processors = null;

    private HashSet crListeners = new HashSet();

    private HashSet cycleStateChanges = new HashSet();

    private HashSet transStateChanges = new HashSet();

    private HashSet eventChannelFires = new HashSet();

    private List componentChanges = null;

    private ComponentChangeTransaction componentChangeTransaction = null;

    private ArrayList rollbackComponentChanges = new ArrayList();

    private boolean inTransaction = false;

    private boolean executingStateChanges = false;

    private TransactionExceptionHandler exceptionHandler = new TransactionExceptionHandler()
    {
        public void handle(Exception exception)
        {
            exception.printStackTrace();
            throw new RuntimeException(
                    "An unhandled exception occured while processing a transaction: "
                            + exception.getMessage(), exception);
        }
    };

    Logger logger = Logger.getLogger(TransactionMgr.class.getName());

    /**
     * Constructs a transaction manager
     * 
     * @param queue
     *            the transaction queue
     * @param logging
     *            a flag to turn logging on or off.
     */
    TransactionMgr(TransactionQueue queue, boolean logging)
    {
        Argument.assertNotNull(queue, "queue");
        this.queue = queue;

        if (logging)
        {
            logger.setLevel(Level.INFO);
        }
        else
        {
            logger.setLevel(Level.OFF);
        }
    }

    /**
     * Set the exception handler for this transaction manager.
     * 
     * @param exceptionHandler
     *            the new exception handler
     */
    void setExceptionHandler(TransactionExceptionHandler exceptionHandler)
    {
        Argument.assertNotNull(exceptionHandler, "exceptionHandler");
        this.exceptionHandler = exceptionHandler;
    }

    private void executeTransaction()
    {
        inTransaction = true;
        logger.log(Level.INFO, "******** Begin transaction: "
                + ++transactionCount + " *********");

        try
        {
            executeTransactionCycles();
        }
        catch (RollbackException re)
        {
            executeRollback();
        }
        catch (Exception exception)
        {
            // TODO figure out if this is the right thing to do.
            executeRollback();
            exceptionHandler.handle(exception);
        }

        commitTransaction();
        synchronizeTransState();
        inTransaction = false;

        logger.log(Level.INFO, "End transaction: " + transactionCount + "\n");
    }

    private void executeTransactionCycles()
    {
        do
        {
            logger.log(Level.INFO, "executeEventChannelFires()");
            executeEventChannelFires();

            logger.log(Level.INFO, "executeStateChanges()");
            executeStateChanges();

            logger.log(Level.INFO, "executeValidators()");
            executeValidators();

            logger.log(Level.INFO, "executeProcessors()");
            executeProcessors();

            logger.log(Level.INFO, "synchronizeCycleState()");
            synchronizeCycleState();

            logger.log(Level.INFO, "executeComponentChanges()");
            executeComponentChanges();
        } while ((stateChanges.size() != 0) || (eventChannelFires.size() != 0)
                || (processors != null));
    }

    private void executeStateChanges()
    {
        if (stateChanges.size() == 0)
        {
            return;
        }

        Source[] sources = (Source[]) stateChanges
                .toArray(new Source[stateChanges.size()]);
        stateChanges.clear();
        executingStateChanges = true;

        for (int i = 0; i < sources.length; i++)
        {
            Object state = sources[i].fire();
            cycleStateChanges.add(sources[i].getEventChannel());
            transStateChanges.add(sources[i].getEventChannel());
            logger.log(Level.INFO, sources[i].getTreeComponent().getName()
                    + ": sources[" + i + "].fire(): "
                    + sources[i].getEventChannelName() + " = " + state);
        }

        executingStateChanges = false;

        // System.out.println();
    }

    private void executeValidators()
    {
        // if no validators have been added...
        if (validators == null)
        {
            return;
        }

        // System.out.print("executeValidators()");
        Validator[] v = (Validator[]) validators
                .toArray(new Validator[validators.size()]);
        validators = null;

        for (int i = 0; i < v.length; i++)
        {
            logger.log(Level.INFO, "validators[" + i + "].validateState(): "
                    + v[i].getName());
            v[i].validate();
        }

        // System.out.println();
    }

    private void executeProcessors()
    {
        // if there are no processors...
        if (processors == null)
        {
            return;
        }

        Processor[] p = null;

        if (processors.size() > 1)
        {
            HashSet delayedProcessors = new HashSet();
            checkDependencies(processors, delayedProcessors);

            // process the independent processors...
            p = (Processor[]) processors.toArray(new Processor[processors
                    .size()]);
            processors = null;

            // if there are dependent processor to be delayed...
            if (delayedProcessors.size() > 0)
            {
                // remember the delayed processors for the next state change
                // cycle...
                processors = delayedProcessors;
            }
        }
        else
        {
            p = (Processor[]) processors.toArray(new Processor[processors
                    .size()]);
            processors = null;
        }

        for (int i = 0; i < p.length; i++)
        {
            logger.log(Level.INFO, "processors[" + i + "].process(): "
                    + p[i].getName());
            p[i].process();
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
    private static void checkDependencies(Set processors, Set delayedProcessors)
    {
        Set crumbs = new HashSet();

        Processor[] procs = (Processor[]) processors
                .toArray(new Processor[processors.size()]);

        for (int i = 0; i < procs.length; i++)
        {
            // if the ith processor has not already been delayed...
            if (processors.contains(procs[i]))
            {
                checkDependencies(procs[i], processors, delayedProcessors,
                        crumbs);
            }
        }
    }

    /**
     * Checks to see if any processors are dependent on the specified processor.
     * 
     * @param processor
     *            The processor to checked
     * @param processors
     * @param delayedProcessors
     * @param crumbs
     *            Processors that have already been visit in the recursive
     *            descent algorithm.
     */
    private static void checkDependencies(TreeComponent processor,
            Set processors, Set delayedProcessors, Set crumbs)
    {
        Map map = processor.getSources();
        Iterator itr = map.values().iterator();
        crumbs.add(processor);

        // For each of the processor's sources
        while (itr.hasNext())
        {
            Source src = (Source) itr.next();
            // If the source is connected to a terminator...
            // TODO: Climb up through multiplexors/demultiplexors to
            // to find the terminators (This isn't going to be fun).
            if (src.getEventChannel() instanceof Terminator)
            {
                Terminator t = (Terminator) src.getEventChannel();
                Sink[] sinks = t.getSinks();
                // For each sink that the processors might publish too...
                for (int j = 0; j < sinks.length; j++)
                {
                    // get the sink's component...
                    TreeComponent tc = sinks[j].getTreeComponent();

                    // if we have already visited this component...
                    if (crumbs.contains(tc))
                    {
                        // Break infinite loop...
                        continue;
                    }

                    // if the dependent component is in the set of processors
                    // remove it and add it to the set of delayed processors...
                    if (processors.remove(tc))
                    {
                        delayedProcessors.add(tc);
                    }

                    // continue searching the dependency chain...
                    checkDependencies(tc, processors, delayedProcessors, crumbs);
                }
            }
        }

        crumbs.remove(processor);
    }

    private void synchronizeCycleState()
    {
        if (cycleStateChanges.size() == 0)
        {
            return;
        }

        EventChannel[] ec = (EventChannel[]) cycleStateChanges
                .toArray(new EventChannel[cycleStateChanges.size()]);
        cycleStateChanges.clear();

        for (int i = 0; i < ec.length; i++)
        {
            logger.log(Level.INFO, "eventChannels[" + i
                    + "].synchronizeCycleState(): "
                    + ec[i].getECD().getEventChannelName());
            ec[i].synchronizeCycleState();
        }
    }

    private void synchronizeTransState()
    {
        if (transStateChanges.size() == 0)
        {
            return;
        }

        EventChannel[] ec = (EventChannel[]) transStateChanges
                .toArray(new EventChannel[transStateChanges.size()]);
        transStateChanges.clear();

        for (int i = 0; i < ec.length; i++)
        {
            logger.log(Level.INFO, "eventChannels[" + i
                    + "].synchronizeTransactionState(): "
                    + ec[i].getECD().getEventChannelName());
            ec[i].synchronizeTransactionState();
        }
    }

    private void executeEventChannelFires()
    {
        if (eventChannelFires.size() == 0)
        {
            return;
        }

        // System.out.print("executeEventChannelFires()");
        EventChannel[] ec = (EventChannel[]) eventChannelFires
                .toArray(new EventChannel[eventChannelFires.size()]);
        eventChannelFires.clear();

        executingStateChanges = true;

        for (int i = 0; i < ec.length; i++)
        {
            ec[i].fire();
            logger.log(Level.INFO, "eventChannels[" + i + "].fire(): "
                    + ec[i].getECD().getEventChannelName() + " - "
                    + ec[i].getState());
        }

        executingStateChanges = false;
    }

    private void executeComponentChanges()
    {
        Runnable[] runnables = null;

        if (componentChanges == null)
        {
            return;
        }

        runnables = (Runnable[]) componentChanges
                .toArray(new Runnable[componentChanges.size()]);
        componentChanges = null;

        logger.log(Level.INFO, "executeComponentChanges() - "
                + runnables.length);

        for (int i = 0; i < runnables.length; i++)
        {
            logger.log(Level.INFO, runnables[i].toString());
            runnables[i].run();
            rollbackComponentChanges.add(runnables[i]);
        }
    }

    private void executeRollback()
    {
        // System.out.print("executeRollback()");
        stateChanges.clear();
        validators = null;
        processors = null;
        cycleStateChanges.clear();
        transStateChanges.clear();
        eventChannelFires.clear();
        componentChanges = null;

        CommitRollbackListener[] crls = (CommitRollbackListener[]) crListeners
                .toArray(new CommitRollbackListener[crListeners.size()]);
        crListeners.clear();

        for (int i = 0; i < crls.length; i++)
        {
            // System.out.print("*");
            crls[i].rollback();
        }

        while (rollbackComponentChanges.size() > 0)
        {
            ((CommitRollbackListener) rollbackComponentChanges.remove(0))
                    .rollback();
        }

        // System.out.println();
        executeTransactionCycles();
    }

    private void commitTransaction()
    {
        // System.out.print("commitTransaction()");
        CommitRollbackListener[] crls;

        synchronized (this)
        {
            rollbackComponentChanges.clear();

            crls = (CommitRollbackListener[]) crListeners
                    .toArray(new CommitRollbackListener[crListeners.size()]);
            crListeners.clear();
        }

        for (int i = 0; i < crls.length; i++)
        {
            logger.log(Level.INFO, "commitRollbackListeners[" + i
                    + "].commit(): ");

            // System.out.print("*");
            crls[i].commit();
        }

        // System.out.println();
    }

    void addCommitRollbackListener(CommitRollbackListener listener)
    {
        synchronized (this)
        {
            crListeners.add(listener);
        }
    }

    /**
     * Adds a source for a state change. This method is called by {@link Source}.
     * It can be called at any time, by any thread.
     * 
     * @param source
     *            The source to be added.
     */
    void addStateChange(ProcessorSource source)
    {
        if (!queue.isDispatchThread())
        {
            StringBuffer sb = new StringBuffer();
            sb
                    .append("A Processor attempted to change state of event channel '");
            sb.append(source.getEventChannelName());
            sb.append("' outside of the transaction thread.");
            sb.append(" The source for the state change is '");
            sb.append(source.getFullyQualifiedName()).append("'");
            throw new IllegalStateException(sb.toString());
        }

        if (!inTransaction)
        {
            StringBuffer sb = new StringBuffer();
            sb
                    .append("A Processor attempted to change state of event channel '");
            sb.append(source.getEventChannelName());
            sb.append("' outside of an active transaction.");
            sb.append(" The source for the state change is '");
            sb.append(source.getFullyQualifiedName()).append("'");
            throw new IllegalStateException(sb.toString());
        }

        if (!stateChanges.add(source))
        {
            throw new IllegalStateException(source.getTreeComponent().getName()
                    + " attempted to change the state of '"
                    + source.getEventChannelName()
                    + "' twice in the same state change cycle.");
        }
    }

    void addStateChange(InitiatorSource[] source)
    {
        synchronized (this)
        {
            // We can not coalese component changes
            // across state changes so we must make
            // sure that if there are pending component
            // changes that no more are added.
            this.componentChangeTransaction = null;
        }

        queue.add(new StateChangeTransaction(source));
    }

    /**
     * This method is called by {@link Processor}and {@link TriggeredConverter}.
     * It should only be called in the state change phase.
     * 
     * @param processor
     *            the processor to be added.
     */
    void addProcessor(Processor processor)
    {
        if (!executingStateChanges)
        {
            throw new IllegalStateException(
                    "Processors can only be active during state change cycles.");
        }

        if (processors == null)
        {
            processors = new HashSet();
        }

        processors.add(processor);
    }

    /**
     * This method is called by {@link Validator}. It should only be called in
     * the state change phase.
     * 
     * @param validator
     *            the validator to be added.
     */
    void addValidator(Validator validator)
    {
        if (!executingStateChanges)
        {
            throw new IllegalStateException(
                    "Validators can only be active during state change cycles.");
        }

        if (validators == null)
        {
            validators = new HashSet();
        }

        validators.add(validator);
    }

    /**
     * Creates an adds component change. This is called by {@link TreeComponent}.
     * It can be called at any time from any thread.
     * 
     * @param parent
     *            the parent from which a component is to be removed.
     * @param child
     *            the child component to be removed.
     */
    void addComponent(TreeComponent parent, TreeComponent child)
    {
        if (!parent.isRooted())
        {
            throw new IllegalArgumentException(
                    "only rooted parents can have child added inside a "
                            + "transaction");
        }

        AddComponentRunnable acr = new AddComponentRunnable(parent, child);

        componentChange(acr);
    }

    private void componentChange(Runnable change)
    {
        // TODO it looks like this needs to be made thread safe.
        if (componentChangeTransaction == null)
        {
            componentChangeTransaction = new ComponentChangeTransaction(change);
            queue.add(componentChangeTransaction);
        }
        else
        {
            componentChangeTransaction.add(change);
        }
    }

    /**
     * Creates remove component change. This is called by {@link TreeComponent}.
     * It can be called at any time from any thread.
     * 
     * @param parent
     *            the parent to which a component is to be added.
     * @param child
     *            the child component to be added.
     */
    void removeComponent(TreeComponent parent, TreeComponent child)
    {
        if (!parent.isRooted())
        {
            throw new IllegalArgumentException(
                    "only rooted parents can have child removed inside a "
                            + "transaction");
        }

        RemoveComponentRunnable rcr = new RemoveComponentRunnable(parent, child);
        componentChange(rcr);
    }

    void addEventChannelFire(EventChannel eventChannel)
    {
        if (!inTransaction)
        {
            queue.add(new EventChannelFireTransaction(eventChannel));
        }
        else if (!queue.isDispatchThread())
        {
            // This means we are in a transaction but the method call
            // occurred outside of the transaction.
            throw new IllegalStateException(
                    "can't call outside transaction queue thread while a transaction is "
                            + "in progress");
        }
        else
        {
            eventChannelFires.add(eventChannel);
        }
    }

    boolean isDispatchThread()
    {
        return queue.isDispatchThread();
    }

    private class ComponentChangeTransaction implements Runnable
    {
        private final List changes = new ArrayList();

        public ComponentChangeTransaction(Runnable change)
        {
            this.changes.add(change);
        }

        public void run()
        {
            // This method is called in the transaction
            // thread and therefore does not need to be
            // synchronized.
            componentChanges = changes;

            synchronized (TransactionMgr.this)
            {
                if (componentChangeTransaction == ComponentChangeTransaction.this)
                {
                    componentChangeTransaction = null;
                }
            }

            try
            {
                executeTransaction();
            }
            catch (Exception exp)
            {
                exceptionHandler.handle(exp);
            }
        }

        public void add(Runnable change)
        {
            changes.add(change);
        }
    }

    private class StateChangeTransaction implements Runnable
    {
        private final InitiatorSource[] sources;

        public StateChangeTransaction(InitiatorSource[] sources)
        {
            this.sources = sources;
        }

        public void run()
        {
            for (int i = 0; i < sources.length; i++)
            {
                stateChanges.add(sources[i]);
            }

            executeTransaction();
        }
    }

    private class EventChannelFireTransaction implements Runnable
    {
        private final EventChannel eventChannel;

        public EventChannelFireTransaction(EventChannel eventChannel)
        {
            this.eventChannel = eventChannel;
        }

        public void run()
        {
            eventChannelFires.add(eventChannel);
            executeTransaction();
        }
    }

    private static class AddComponentRunnable implements Runnable,
            CommitRollbackListener
    {
        private final TreeComponent parent;

        private final TreeComponent child;

        AddComponentRunnable(final TreeComponent parent,
                final TreeComponent child)
        {
            this.parent = parent;
            this.child = child;
        }

        public void run()
        {
            parent.terminateParentAndLocalConnections(child
                    .terminateChildAndLocalConnections());
        }

        public void rollback()
        {
            child.disconnect();
        }

        public void commit()
        {
        }

        public String toString()
        {
            return "add Component " + child.getName() + " to "
                    + parent.getName();
        }
    }

    private static class RemoveComponentRunnable implements Runnable,
            CommitRollbackListener
    {
        private final TreeComponent parent;

        private final TreeComponent child;

        RemoveComponentRunnable(final TreeComponent parent,
                final TreeComponent child)
        {
            this.parent = parent;
            this.child = child;
        }

        public void run()
        {
            child.disconnect();
        }

        public void rollback()
        {
            parent.terminateParentAndLocalConnections(child
                    .terminateChildAndLocalConnections());
        }

        public void commit()
        {
        }

        public String toString()
        {
            return "remove Component " + child.getName() + " from "
                    + parent.getName();
        }
    }
}
