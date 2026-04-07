package tfw.tsm;

import static tfw.check.Arguments.checkNotNull;

import tfw.tsm.ecd.EventChannelDescription;

public class DefaultEventChannelConfig<T extends EventChannelDescription> implements EventChannelConfig<T> {
    public final T ecd;
    public final Object state;
    public final StateChangeRule rule;
    public final String[] tags;

    public DefaultEventChannelConfig(T ecd, StateChangeRule rule, Object state, String[] tags) {
        checkNotNull(ecd, "ecd");
        checkNotNull(rule, "rule");

        this.ecd = ecd;
        this.rule = rule;
        this.state = state;
        this.tags = tags;
    }

    @Override
    public T getEventChannelDescription() {
        return ecd;
    }

    @Override
    public Object getInitialState() {
        return state;
    }

    @Override
    public StateChangeRule getStateChangeRule() {
        return rule;
    }

    @Override
    public String[] getExportTags() {
        return tags;
    }
}
