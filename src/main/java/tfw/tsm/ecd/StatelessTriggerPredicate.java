package tfw.tsm.ecd;

import java.util.function.Predicate;

public class StatelessTriggerPredicate implements Predicate<Object> {
    @Override
    public boolean test(Object t) {
        return t == null;
    }
}
