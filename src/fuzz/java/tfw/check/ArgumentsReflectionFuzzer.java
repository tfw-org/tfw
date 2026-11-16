package tfw.check;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

public final class ArgumentsReflectionFuzzer {
    private static final int EXPECTED_METHOD_COUNT = 108;

    private static final Method[] METHODS = discoverMethods();

    private ArgumentsReflectionFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {

        final int selector = (data.consumeByte() & 0xff) % METHODS.length;
        final Method method = METHODS[selector];

        final Object[] arguments = ArgumentsFuzzerSupport.createArguments(method, data);

        ArgumentsFuzzerSupport.verify(method, arguments);
    }

    private static Method[] discoverMethods() {

        final Method[] methods = Arrays.stream(Arguments.class.getDeclaredMethods())
                .filter(method -> Modifier.isPublic(method.getModifiers()))
                .filter(method -> Modifier.isStatic(method.getModifiers()))
                .toArray(Method[]::new);

        Arrays.sort(methods, Comparator.comparing(Method::toGenericString));

        if (methods.length != EXPECTED_METHOD_COUNT) {
            throw new ExceptionInInitializerError("Expected " + EXPECTED_METHOD_COUNT
                    + " public static void methods in Arguments, found " + methods.length);
        }

        for (final Method method : methods) {
            ArgumentsFuzzerSupport.validateMethod(method);
        }

        return methods;
    }
}
