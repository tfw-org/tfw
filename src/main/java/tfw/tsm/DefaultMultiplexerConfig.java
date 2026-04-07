package tfw.tsm;

import static tfw.check.Arguments.checkNotNull;

import tfw.tsm.ecd.EventChannelDescription;

public class DefaultMultiplexerConfig<T extends EventChannelDescription, U extends EventChannelDescription>
        implements MultiplexerConfig<T, U> {
    public final T ecd;
    public final U mecd;
    public final MultiplexerStrategy strategy;
    public final Object initialMultiState;
    public final StateChangeRule rule;
    public final String[] tags;

    public DefaultMultiplexerConfig(
            T ecd,
            U mecd,
            MultiplexerStrategy strategy,
            StateChangeRule rule,
            Object initialMultiState,
            String[] tags) {
        checkNotNull(ecd, "ecd");
        checkNotNull(mecd, "mecd");
        checkNotNull(strategy, "strategy");
        checkNotNull(rule, "rule");

        this.ecd = ecd;
        this.mecd = mecd;
        this.strategy = strategy;
        this.rule = rule;
        this.initialMultiState = initialMultiState;
        this.tags = tags;
    }

    @Override
    public T getEventChannelDescription() {
        return ecd;
    }

    @Override
    public U getMultiEventChannelDescription() {
        return mecd;
    }

    @Override
    public MultiplexerStrategy getMultiplexerStrategy() {
        return strategy;
    }

    @Override
    public Object getInitialMultiState() {
        return initialMultiState;
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
