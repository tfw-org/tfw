package tfw.tsm.ecd;

import java.util.function.Predicate;
import tfw.check.Arguments;

public class IsAssignableFromPredicate implements Predicate<Object> {
    private final Class<?> clazz;

    public IsAssignableFromPredicate(final Class<?> clazz) {
        Arguments.checkNotNull(clazz, "clazz");

        this.clazz = clazz;
    }

    @Override
    public boolean test(Object object) {
        if (object == null) {
            return false;
        }

        return clazz.isAssignableFrom(object.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        IsAssignableFromPredicate other = (IsAssignableFromPredicate) obj;

        return clazz.equals(other.clazz);
    }

    @Override
    public int hashCode() {
        return clazz.hashCode();
    }
}
