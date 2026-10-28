package tfw.check;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public final class ArgumentsReflectionFuzzer {

    private static final Method LESS_THAN_INT_3 = findMethod("checkLessThan", int.class, String.class, int.class);

    private static final Method LESS_THAN_INT_4 =
            findMethod("checkLessThan", int.class, String.class, int.class, String.class);

    private static final Method LESS_THAN_DOUBLE_3 =
            findMethod("checkLessThan", double.class, String.class, double.class);

    private static final Method LESS_THAN_DOUBLE_4 =
            findMethod("checkLessThan", double.class, String.class, double.class, String.class);

    private static final Method EQUALS_OBJECT_3 = findMethod("checkEquals", Object.class, String.class, Object.class);

    private static final Method EQUALS_OBJECT_4 =
            findMethod("checkEquals", Object.class, String.class, Object.class, String.class);

    private static final Method NOT_NULL = findMethod("checkNotNull", Object.class, String.class);

    private ArgumentsReflectionFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {

        switch (data.consumeInt(0, 3)) {
            case 0:
                testLessThanInt(data);
                break;

            case 1:
                testLessThanDouble(data);
                break;

            case 2:
                testEqualsObject(data);
                break;

            case 3:
                testNotNull(data);
                break;

            default:
                throw new AssertionError("Unexpected operation");
        }
    }

    private static void testLessThanInt(FuzzedDataProvider data) {

        final int left = data.consumeInt();
        final int right = data.consumeInt();
        final boolean fourArguments = data.consumeBoolean();
        final Method method = fourArguments ? LESS_THAN_INT_4 : LESS_THAN_INT_3;

        ArgumentsFuzzerSupport.verify(
                left >= right,
                () -> invoke(
                        method,
                        fourArguments
                                ? new Object[] {left, "left", right, "right"}
                                : new Object[] {left, "left", right}),
                "reflective checkLessThan(int)");
    }

    private static void testLessThanDouble(FuzzedDataProvider data) {

        final double left = data.consumeDouble();
        final double right = data.consumeDouble();
        final boolean fourArguments = data.consumeBoolean();
        final Method method = fourArguments ? LESS_THAN_DOUBLE_4 : LESS_THAN_DOUBLE_3;

        ArgumentsFuzzerSupport.verify(
                left >= right,
                () -> invoke(
                        method,
                        fourArguments
                                ? new Object[] {left, "left", right, "right"}
                                : new Object[] {left, "left", right}),
                "reflective checkLessThan(double)");
    }

    private static void testEqualsObject(FuzzedDataProvider data) {

        final String left = ArgumentsFuzzerSupport.consumeNullableString(data);
        final String right = ArgumentsFuzzerSupport.consumeNullableString(data);
        final boolean fourArguments = data.consumeBoolean();
        final Method method = fourArguments ? EQUALS_OBJECT_4 : EQUALS_OBJECT_3;

        ArgumentsFuzzerSupport.verify(
                !Objects.equals(left, right),
                () -> invoke(
                        method,
                        fourArguments
                                ? new Object[] {left, "left", right, "right"}
                                : new Object[] {left, "left", right}),
                "reflective checkEquals(Object)");
    }

    private static void testNotNull(FuzzedDataProvider data) {

        final String value = ArgumentsFuzzerSupport.consumeNullableString(data);

        ArgumentsFuzzerSupport.verify(
                value == null, () -> invoke(NOT_NULL, value, "value"), "reflective checkNotNull(Object)");
    }

    private static Method findMethod(String name, Class<?>... parameterTypes) {

        try {
            return Arguments.class.getMethod(name, parameterTypes);

        } catch (ReflectiveOperationException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    private static void invoke(Method method, Object... arguments) {

        try {
            method.invoke(null, arguments);

        } catch (InvocationTargetException exception) {

            final Throwable cause = exception.getCause();

            if (cause instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) cause;
            }

            throw new AssertionError("Unexpected exception from " + method.getName(), cause);

        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("Could not invoke " + method.getName(), exception);
        }
    }
}
