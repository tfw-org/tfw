package tfw.demo;

import java.util.function.Predicate;
import tfw.tsm.Synchronizer;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.StringRollbackECD;

/**
 * Converts between <code>java.lang.String</code> and <code>
 * java.lang.Integer</code>
 */
public class IntegerStringConverter extends Synchronizer {
    private final StringECD stringECD;
    private final IntegerECD integerECD;
    private final StringRollbackECD errorECD;
    private final Predicate<Object> integerPredicate;

    public IntegerStringConverter(String name, StringECD stringECD, IntegerECD integerECD, StringRollbackECD errorECD) {
        super(
                "IntegerStringConverter[" + name + "]",
                new ObjectECD[] {stringECD},
                new ObjectECD[] {integerECD},
                null,
                new ObjectECD[] {errorECD});
        this.stringECD = stringECD;
        this.integerECD = integerECD;
        this.errorECD = errorECD;
        this.integerPredicate = integerECD.getPredicate();
    }

    @Override
    public void convertBToA() {
        set(stringECD, ((Integer) get(integerECD)).toString());
    }

    @Override
    public void convertAToB() {
        final String stringValue = (String) get(stringECD);
        final Integer intValue;

        try {
            intValue = Integer.valueOf(stringValue);
        } catch (NumberFormatException nfe) {
            rollback(errorECD, "Invalid integer value '" + stringValue + "'");
            return;
        }

        if (!this.integerPredicate.test(intValue)) {
            rollback(errorECD, "Integer value '" + intValue + "' is not valid");
            return;
        }

        set(integerECD, intValue);
    }
}
