package tfw.value;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;

/**
 * The base class for a constraint where the value must be of a specifid type.
 * If a value is assignable to the class type of the
 * <code>ClassValueConstraint</code> then it is valid, otherwise it is
 * not valid.
 */
public class ClassValueConstraint<T> extends ValueConstraint<T> {
    /** A value constraint which allows any object. */
    public static final ClassValueConstraint<Object> OBJECT = new ClassValueConstraint<>(Object.class);

    /** A String value constraint which allows any string. */
    public static final ClassValueConstraint<String> STRING = new ClassValueConstraint<>(String.class);

    /** A Boolean value constraint. */
    public static final ClassValueConstraint<Boolean> BOOLEAN = new ClassValueConstraint<>(Boolean.class);

    /** An Integer value constraint that allows any integer value. */
    public static final ClassValueConstraint<Integer> INTEGER = new ClassValueConstraint<>(Integer.class);

    /** An Float value constraint that allows any float value. */
    public static final ClassValueConstraint<Float> FLOAT = new ClassValueConstraint<>(Float.class);

    /** An Double value constraint that allows any double value. */
    public static final ClassValueConstraint<Double> DOUBLE = new ClassValueConstraint<>(Double.class);

    /** A Long value constraint that allows any long value. */
    public static final ClassValueConstraint<Long> LONG = new ClassValueConstraint<>(Long.class);

    /** An Character value constraint that allows any character value. */
    public static final ClassValueConstraint<Character> CHARACTER = new ClassValueConstraint<>(Character.class);

    /** An Short value constraint that allows any short value. */
    public static final ClassValueConstraint<Short> SHORT = new ClassValueConstraint<>(Short.class);

    /** An Byte value constraint that allows any short value. */
    public static final ClassValueConstraint<Byte> BYTE = new ClassValueConstraint<>(Byte.class);

    /** The string used to represent a value which complies with the constraint. */
    public static final String VALID = "Valid";

    private static final Map<Class<?>, ClassValueConstraint<?>> constraints = getInitialConstraints();

    /** The class of the value. */
    protected final Class<T> valueType;

    /**
     * Constructs a constraint
     * @param valueType the type for this constraint.
     */
    protected ClassValueConstraint(Class<T> valueType) {
        Argument.assertNotNull(valueType, "valueType");
        this.valueType = valueType;
    }

    private static Map<Class<?>, ClassValueConstraint<?>> getInitialConstraints() {
        HashMap<Class<?>, ClassValueConstraint<?>> map = new HashMap<>();

        map.put(BOOLEAN.valueType, BOOLEAN);
        map.put(OBJECT.valueType, OBJECT);
        map.put(STRING.valueType, STRING);

        return map;
    }

    /**
     * Returns an instance of a value constraint based on the specified class.
     * @param valueType the class for the value constraint.
     * @return an instance of a value constraint based on the specified class.
     */
    @SuppressWarnings("unchecked")
    public static <T> ClassValueConstraint<T> getInstance(Class<T> valueType) {
        ClassValueConstraint<T> constraint = (ClassValueConstraint<T>) constraints.get(valueType);

        if (constraint == null) {
            constraint = new ClassValueConstraint<>(valueType);
            constraints.put(valueType, constraint);
        }

        return constraint;
    }

    /**
     * Returns <code>true</code> if the specified value complies with the
     * constraint, otherwise returns <code>false</code>.
     *
     * @param value the value to be checked.
     * @return <code>true</code> if the specified value complies with the
     * constraint, otherwise returns <code>false</code>.
     */
    public boolean isValid(T value) {
        String valueCompliance = getValueCompliance(value);

        return VALID.equals(valueCompliance);
    }

    /**
     * Returns {@link #VALID} if the value complies with the constraint,
     * otherwise it returns a string indicating why the value does not comply.
     *
     * @param value The value to be evaluated.
     * @return {@link #VALID} if the value complies with the constraint,
     * otherwise it returns a string indicating why the value does not comply.
     */
    @Override
    public String getValueCompliance(Object value) {
        if (valueType.isInstance(value)) {
            return VALID;
        }

        if (value == null) {
            return "value == null does not meet the constraints on this value";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("The value, of type '");
        sb.append(value.getClass().getName());
        sb.append("', is not assignable to type '");
        sb.append(valueType.getName());
        sb.append("'.");

        return sb.toString();
    }

    /**
     * Returns true if every value which meets the specified constraint
     * also meets this constraint, otherwise returns false. Note that the
     * reverse is not necessarily true.
     *
     * @param constraint the constraint to be checked.
     *
     * @return true if every value which meets the specified constraint
     * also meets this constraint, otherwise returns false.
     */
    @Override
    public boolean isCompatible(ValueConstraint<?> constraint) {
        if (constraint instanceof ClassValueConstraint) {
            return valueType.equals(((ClassValueConstraint<?>) constraint).valueType);
        }

        return false;
    }

    /**
     * Returns a string representation of this constraint.
     * @return a string representation of this constraint.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValueConstraint[");
        sb.append("type = ").append(valueType.getName());
        sb.append("]");

        return sb.toString();
    }
}
