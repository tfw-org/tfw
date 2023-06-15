package tfw.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.LoggerFactory;
import tfw.check.Argument;

public class StateMachine {
    public static interface Guard {
        boolean isTrue(Object event);
    }

    public static interface Action {
        void act(Object event);
    }

    public static class Transition {
        public final Object trigger;
        public final Object fromState;
        public final Object toState;
        public final Guard guard;
        public final List<Action> actions;

        public Transition(Object trigger, Object fromState, Object toState, Guard guard, List<Action> actions) {
            Argument.assertNotNull(trigger, "trigger");
            Argument.assertNotNull(fromState, "fromState");
            Argument.assertNotNull(toState, "toState");

            this.trigger = trigger;
            this.fromState = fromState;
            this.toState = toState;
            this.guard = guard;
            this.actions = (actions == null) ? null : Collections.unmodifiableList(new ArrayList<Action>(actions));
        }
    }

    private final Object lock = new Object();
    private final List<Transition> transitions;

    private Object state = null;
    private boolean processingEvent = false;

    public StateMachine(List<Transition> transitions, Object initialState) {
        Argument.assertNotNull(transitions, "transitions");
        Argument.assertNotNull(initialState, "initialState");

        this.transitions = Collections.unmodifiableList(new ArrayList<Transition>(transitions));
        this.state = initialState;
    }

    public void fireEvent(Object event) {
        synchronized (lock) {
            if (processingEvent) {
                throw new IllegalStateException("Already processing event!");
            }

            processingEvent = true;

            int i = 0;
            for (; i < transitions.size(); i++) {
                Transition t = transitions.get(i);
                final boolean e = (t.trigger == event)
                        || t.trigger instanceof Class<?> && ((Class<?>) t.trigger).isInstance(event);
                final boolean g = t.guard == null || (t.guard != null && t.guard.isTrue(event));
                final boolean s = t.fromState == state;

                if (e && g && s) {
                    for (int j = 0; j < t.actions.size(); j++) {
                        try {
                            t.actions.get(j).act(event);
                        } catch (Exception exception) {
                            LoggerFactory.getLogger(StateMachine.class)
                                    .error("Exception thrown while executing action!", exception);
                        }
                    }

                    state = t.toState;

                    break;
                }
            }

            if (i == transitions.size()) {
                throw new IllegalStateException("No transitions for event!");
            }

            processingEvent = false;
        }
    }
}
