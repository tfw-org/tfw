package tfw.tsm.ecd;

import java.util.function.Predicate;

public final class StatelessTriggerPredicate implements Predicate<Object> {
    @Override
    public boolean test(Object t) {
        return t == null;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof StatelessTriggerPredicate;
    }

    @Override
    public int hashCode() {
        return StatelessTriggerPredicate.class.hashCode();
    }
}
