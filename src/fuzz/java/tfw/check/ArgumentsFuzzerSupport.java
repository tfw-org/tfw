package tfw.check;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Objects;

public final class ArgumentsFuzzerSupport {

    private ArgumentsFuzzerSupport() {}

    public static void validateMethod(final Method method) {
        final String name = method.getName();
        final Class<?>[] types = method.getParameterTypes();

        if (method.getReturnType() != void.class) {
            throw new ExceptionInInitializerError("Unsupported Arguments method: " + method);
        }

        if (!isSupportedMethodName(name)) {
            throw new ExceptionInInitializerError("Unsupported Arguments method: " + method);
        }

        if ("checkTrue".equals(name) || "checkFalse".equals(name)) {
            if (types.length != 2 || types[0] != boolean.class || types[1] != String.class) {
                throw new ExceptionInInitializerError("Unsupported Arguments method: " + method);
            }

            return;
        }

        if ("checkNull".equals(name) || "checkNotNull".equals(name)) {
            if (types.length != 2 || types[0] != Object.class || types[1] != String.class) {
                throw new ExceptionInInitializerError("Unsupported Arguments method: " + method);
            }

            return;
        }

        if (types.length != 3 && types.length != 4) {
            throw new ExceptionInInitializerError("Unsupported Arguments method: " + method);
        }

        if (types[1] != String.class || types[0] != types[2]) {
            throw new ExceptionInInitializerError("Unsupported Arguments method: " + method);
        }

        if (types.length == 4 && types[3] != String.class) {
            throw new ExceptionInInitializerError("Unsupported Arguments method: " + method);
        }

        final Class<?> type = types[0];

        if ("checkEquals".equals(name) || "checkNotEquals".equals(name)) {

            if (type != byte.class
                    && type != char.class
                    && type != double.class
                    && type != float.class
                    && type != int.class
                    && type != long.class
                    && type != short.class
                    && type != BigInteger.class
                    && type != boolean.class
                    && type != Object.class) {
                throw new ExceptionInInitializerError("Unsupported Arguments method: " + method);
            }

            return;
        }

        if (type != byte.class
                && type != char.class
                && type != double.class
                && type != float.class
                && type != int.class
                && type != long.class
                && type != short.class
                && type != BigInteger.class) {
            throw new ExceptionInInitializerError("Unsupported Arguments method: " + method);
        }
    }

    private static boolean isSupportedMethodName(final String name) {
        return "checkLessThan".equals(name)
                || "checkLessThanOrEqual".equals(name)
                || "checkEquals".equals(name)
                || "checkTrue".equals(name)
                || "checkFalse".equals(name)
                || "checkNull".equals(name)
                || "checkNotNull".equals(name)
                || "checkNotEquals".equals(name)
                || "checkGreaterThanOrEqual".equals(name)
                || "checkGreaterThan".equals(name);
    }

    public static Object[] createArguments(final Method method, final FuzzedDataProvider data) {

        final Class<?>[] types = method.getParameterTypes();

        if (types.length == 2) {
            final Object value;

            if (types[0] == boolean.class) {
                value = data.consumeBoolean();
            } else {
                value = consumeValue(Object.class, data, true);
            }

            return new Object[] {value, data.consumeString(32)};
        }

        final Class<?> type = types[0];
        final String methodName = method.getName();

        final boolean nullable = type == Object.class
                || (type == BigInteger.class
                        && ("checkEquals".equals(methodName) || "checkNotEquals".equals(methodName)));

        final Object left = consumeValue(type, data, nullable);
        final Object right = consumeValue(type, data, nullable);

        if (types.length == 3) {
            return new Object[] {left, data.consumeString(32), right};
        }

        return new Object[] {left, data.consumeString(32), right, data.consumeString(32)};
    }

    private static Object consumeValue(final Class<?> type, final FuzzedDataProvider data, final boolean nullable) {
        if (type == byte.class) {
            return data.consumeByte();
        }

        if (type == char.class) {
            return (char) data.consumeShort();
        }

        if (type == double.class) {
            return data.consumeDouble();
        }

        if (type == float.class) {
            return data.consumeFloat();
        }

        if (type == int.class) {
            return data.consumeInt();
        }

        if (type == long.class) {
            return data.consumeLong();
        }

        if (type == short.class) {
            return data.consumeShort();
        }

        if (type == boolean.class) {
            return data.consumeBoolean();
        }

        if (type == BigInteger.class) {
            if (nullable && data.consumeBoolean()) {
                return null;
            }

            final byte[] bytes = data.consumeBytes(16);

            return bytes.length == 0 ? BigInteger.ZERO : new BigInteger(bytes);
        }

        if (type == Object.class) {
            return nullable && data.consumeBoolean() ? null : data.consumeString(64);
        }

        throw new AssertionError("Unsupported argument type: " + type);
    }

    public static void verify(final Method method, final Object[] arguments) {
        final boolean shouldThrow = shouldThrow(method, arguments);

        try {
            invoke(method, arguments);

        } catch (IllegalArgumentException exception) {

            if (!shouldThrow) {
                throw new AssertionError(method + " threw unexpectedly", exception);
            }

            return;
        }

        if (shouldThrow) {
            throw new AssertionError(method + " did not throw");
        }
    }

    private static boolean shouldThrow(final Method method, final Object[] arguments) {
        final String name = method.getName();

        if ("checkTrue".equals(name)) {
            return !((Boolean) arguments[0]);
        }

        if ("checkFalse".equals(name)) {
            return (Boolean) arguments[0];
        }

        if ("checkNull".equals(name)) {
            return arguments[0] != null;
        }

        if ("checkNotNull".equals(name)) {
            return arguments[0] == null;
        }

        final Class<?> type = method.getParameterTypes()[0];
        final Object left = arguments[0];
        final Object right = arguments[2];

        if ("checkEquals".equals(name)) {
            return !equal(type, left, right);
        }

        if ("checkNotEquals".equals(name)) {
            return equal(type, left, right);
        }

        return relationalFailure(name, type, left, right);
    }

    private static boolean equal(final Class<?> type, final Object left, final Object right) {
        if (type == float.class) {
            return ((Float) left).floatValue() == ((Float) right).floatValue();
        }

        if (type == double.class) {
            return ((Double) left).doubleValue() == ((Double) right).doubleValue();
        }

        return Objects.equals(left, right);
    }

    private static boolean relationalFailure(
            final String operation, final Class<?> type, final Object left, final Object right) {
        if (type == BigInteger.class) {
            final int comparison = ((BigInteger) left).compareTo((BigInteger) right);

            return compare(operation, comparison);
        }

        if (type == char.class) {
            final int comparison = Character.compare((Character) left, (Character) right);

            return compare(operation, comparison);
        }

        if (type == float.class) {
            return floatingFailure(operation, ((Float) left).floatValue(), ((Float) right).floatValue());
        }

        if (type == double.class) {
            return floatingFailure(operation, ((Double) left).doubleValue(), ((Double) right).doubleValue());
        }

        final int comparison = Long.compare(((Number) left).longValue(), ((Number) right).longValue());

        return compare(operation, comparison);
    }

    private static boolean compare(final String operation, final int comparison) {
        switch (operation) {
            case "checkLessThan":
                return comparison >= 0;

            case "checkLessThanOrEqual":
                return comparison > 0;

            case "checkGreaterThanOrEqual":
                return comparison < 0;

            case "checkGreaterThan":
                return comparison <= 0;

            default:
                throw new AssertionError("Unexpected relational operation: " + operation);
        }
    }

    private static boolean floatingFailure(final String operation, final double left, final double right) {
        switch (operation) {
            case "checkLessThan":
                return left >= right;

            case "checkLessThanOrEqual":
                return left > right;

            case "checkGreaterThanOrEqual":
                return left < right;

            case "checkGreaterThan":
                return left <= right;

            default:
                throw new AssertionError("Unexpected relational operation: " + operation);
        }
    }

    private static void invoke(final Method method, final Object[] arguments) {
        try {
            method.invoke(null, arguments);

        } catch (InvocationTargetException exception) {

            final Throwable cause = exception.getCause();

            if (cause instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) cause;
            }

            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }

            if (cause instanceof Error) {
                throw (Error) cause;
            }

            throw new AssertionError("Unexpected exception from " + method, cause);

        } catch (ReflectiveOperationException | IllegalArgumentException exception) {
            throw new AssertionError("Could not invoke " + method, exception);
        }
    }
}
