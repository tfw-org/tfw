package tfw.build;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FuzzerGenerator {

    private FuzzerGenerator() {}

    public static void main(final String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: FuzzerGenerator <template-directory> <fuzz-directory>");
        }

        final Path templateDirectory = Paths.get(args[0]);
        final Path fuzzDirectory = Paths.get(args[1]);

        final Configuration configuration = new Configuration(Configuration.VERSION_2_3_34);
        configuration.setDirectoryForTemplateLoading(templateDirectory.toFile());
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);

        final List<FuzzerDefinition> definitions = definitions();

        System.out.println("Generating " + definitions.size() + " fuzzers from " + templateDirectory);
        System.out.println("Writing generated fuzzers to " + fuzzDirectory);

        for (final FuzzerDefinition definition : definitions) {
            generate(configuration, definition, fuzzDirectory);
        }
    }

    private static void generate(
            final Configuration configuration, final FuzzerDefinition definition, final Path fuzzDirectory)
            throws Exception {

        final Template template = configuration.getTemplate(definition.template());

        final Path packageDirectory =
                fuzzDirectory.resolve(definition.packageName().replace('.', File.separatorChar));

        Files.createDirectories(packageDirectory);

        final Path outputFile = packageDirectory.resolve(definition.className() + ".java");

        System.out.println("  " + definition.template() + " -> " + outputFile);

        try (Writer writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8)) {
            template.process(definition.model(), writer);
        }
    }

    /*
     * The generator is organized around two independent concepts:
     *
     *   1. IlaType
     *        Describes a type for which a fuzzer can be generated.
     *
     *   2. FuzzerDefinition
     *        Describes one fuzzer function instantiated for one IlaType.
     *
     * This means that one template can generate a fuzzer for every applicable
     * type. Adding another fuzzer function should add another method below,
     * rather than another copy of the template for every type.
     */
    private static List<FuzzerDefinition> definitions() {
        final List<FuzzerDefinition> definitions = new ArrayList<>();

        for (final IlaType type : ilaTypes()) {
            definitions.add(ilaFactoryFromArray(type));
        }

        return Collections.unmodifiableList(definitions);
    }

    /*
     * These are the types currently represented by the immutable Ila family.
     *
     * The package name is the ilaf package because that is where the generated
     * fuzzer belongs.
     *
     * elementType is the Java element type.
     *
     * arrayType is the Java array type.
     *
     * ilaType is the corresponding Ila interface.
     *
     * generic is only needed by ObjectIla.
     */
    private static List<IlaType> ilaTypes() {
        return Arrays.asList(
                new IlaType("booleanilaf", "boolean", "boolean[]", "BooleanIla", null),
                new IlaType("byteilaf", "byte", "byte[]", "ByteIla", null),
                new IlaType("charilaf", "char", "char[]", "CharIla", null),
                new IlaType("doubleilaf", "double", "double[]", "DoubleIla", null),
                new IlaType("floatilaf", "float", "float[]", "FloatIla", null),
                new IlaType("intilaf", "int", "int[]", "IntIla", null),
                new IlaType("longilaf", "long", "long[]", "LongIla", null),
                new IlaType("objectilaf", "String", "String[]", "ObjectIla", "String"),
                new IlaType("shortilaf", "short", "short[]", "ShortIla", null));
    }

    /*
     * One function -> one template.
     *
     * This method describes IlaFactoryFromArray for one type. The template is
     * shared by every type; all of the type-specific information is supplied
     * through the model.
     *
     * When another fuzzer function is added, create another method following
     * this pattern, for example:
     *
     *   private static FuzzerDefinition ilaFactoryFromSomething(
     *       final IlaType type) {
     *     ...
     *   }
     */
    private static FuzzerDefinition ilaFactoryFromArray(final IlaType type) {

        final String ilaType = type.ilaType();

        return new FuzzerDefinition(
                "tfw/immutable/ilaf/IlaFactoryFromArrayFuzzer.java.ftl",
                "tfw.immutable.ilaf." + type.packageName(),
                ilaType + "FactoryFromArrayFuzzer",
                createModel(
                        type,
                        ilaType + "FactoryFromArrayFuzzer",
                        ilaType + "FactoryFromArray",
                        createExpression(type),
                        type.ilaType() + "::length",
                        type.ilaType() + "::get",
                        initialize(type),
                        assertElementEquals(type)));
    }

    private static Map<String, Object> createModel(
            final IlaType type,
            final String className,
            final String factoryName,
            final String createExpression,
            final String lengthExpression,
            final String getExpression,
            final String initialize,
            final String assertElementEquals) {

        final Map<String, Object> model = new HashMap<>();

        model.put("package", "tfw.immutable.ilaf." + type.packageName());
        model.put("className", className);
        model.put("elementType", type.elementType());
        model.put("arrayType", type.arrayType());
        model.put("ilaPackage", type.ilaPackage());
        model.put("ilaType", type.ilaType());

        if (type.generic() != null) {
            model.put("generic", type.generic());
        }

        model.put("factoryName", factoryName);
        model.put("createExpression", createExpression);
        model.put("lengthExpression", lengthExpression);
        model.put("getExpression", getExpression);
        model.put("initialize", initialize);
        model.put("assertElementEquals", assertElementEquals);

        return model;
    }

    private static String createExpression(final IlaType type) {
        switch (type.ilaType()) {
            case "BooleanIla":
                return "array -> BooleanIlaFactoryFromArray.create(array).create()";

            case "ByteIla":
                return "array -> ByteIlaFactoryFromArray.create(array).create()";

            case "CharIla":
                return "array -> CharIlaFactoryFromArray.create(array).create()";

            case "DoubleIla":
                return "array -> DoubleIlaFactoryFromArray.create(array).create()";

            case "FloatIla":
                return "array -> FloatIlaFactoryFromArray.create(array).create()";

            case "IntIla":
                return "array -> IntIlaFactoryFromArray.create(array).create()";

            case "LongIla":
                return "array -> LongIlaFactoryFromArray.create(array).create()";

            case "ObjectIla":
                return "array -> ObjectIlaFactoryFromArray.<String>create(array).create()";

            case "ShortIla":
                return "array -> ShortIlaFactoryFromArray.create(array).create()";

            default:
                throw new IllegalArgumentException("Unsupported Ila type: " + type.ilaType());
        }
    }

    private static String initialize(final IlaType type) {
        switch (type.ilaType()) {
            case "BooleanIla":
                return "for (int i = 0; i < array.length; i++) {\n" + "    array[i] = (i & 1) != 0;\n" + "}";

            case "ByteIla":
                return "for (int i = 0; i < array.length; i++) {\n" + "    array[i] = (byte) (i * 37 + 11);\n" + "}";

            case "CharIla":
                return "for (int i = 0; i < array.length; i++) {\n"
                        + "    switch (i & 3) {\n"
                        + "        case 0:\n"
                        + "            array[i] = '\\0';\n"
                        + "            break;\n"
                        + "        case 1:\n"
                        + "            array[i] = '\\uffff';\n"
                        + "            break;\n"
                        + "        case 2:\n"
                        + "            array[i] = (char) i;\n"
                        + "            break;\n"
                        + "        default:\n"
                        + "            array[i] = (char) (0xffff - i);\n"
                        + "            break;\n"
                        + "    }\n"
                        + "}";

            case "DoubleIla":
                return "for (int i = 0; i < array.length; i++) {\n"
                        + "    switch (i & 7) {\n"
                        + "        case 0:\n"
                        + "            array[i] = 0.0;\n"
                        + "            break;\n"
                        + "        case 1:\n"
                        + "            array[i] = -0.0;\n"
                        + "            break;\n"
                        + "        case 2:\n"
                        + "            array[i] = Double.NaN;\n"
                        + "            break;\n"
                        + "        case 3:\n"
                        + "            array[i] = Double.POSITIVE_INFINITY;\n"
                        + "            break;\n"
                        + "        case 4:\n"
                        + "            array[i] = Double.NEGATIVE_INFINITY;\n"
                        + "            break;\n"
                        + "        case 5:\n"
                        + "            array[i] = Double.MIN_VALUE;\n"
                        + "            break;\n"
                        + "        case 6:\n"
                        + "            array[i] = Double.MAX_VALUE;\n"
                        + "            break;\n"
                        + "        default:\n"
                        + "            array[i] = i * 1.23456789;\n"
                        + "            break;\n"
                        + "    }\n"
                        + "}";

            case "FloatIla":
                return "for (int i = 0; i < array.length; i++) {\n"
                        + "    switch (i & 7) {\n"
                        + "        case 0:\n"
                        + "            array[i] = 0.0f;\n"
                        + "            break;\n"
                        + "        case 1:\n"
                        + "            array[i] = -0.0f;\n"
                        + "            break;\n"
                        + "        case 2:\n"
                        + "            array[i] = Float.NaN;\n"
                        + "            break;\n"
                        + "        case 3:\n"
                        + "            array[i] = Float.POSITIVE_INFINITY;\n"
                        + "            break;\n"
                        + "        case 4:\n"
                        + "            array[i] = Float.NEGATIVE_INFINITY;\n"
                        + "            break;\n"
                        + "        case 5:\n"
                        + "            array[i] = Float.MIN_VALUE;\n"
                        + "            break;\n"
                        + "        case 6:\n"
                        + "            array[i] = Float.MAX_VALUE;\n"
                        + "            break;\n"
                        + "        default:\n"
                        + "            array[i] = i * 1.2345678f;\n"
                        + "            break;\n"
                        + "    }\n"
                        + "}";

            case "IntIla":
                return "for (int i = 0; i < array.length; i++) {\n"
                        + "    array[i] = i * 0x9e3779b9 ^ 0x12345678;\n"
                        + "}";

            case "LongIla":
                return "for (int i = 0; i < array.length; i++) {\n"
                        + "    array[i] = 0x123456789ABCDEFL ^ ((long) i * 0x100000001L);\n"
                        + "}";

            case "ObjectIla":
                return "for (int i = 0; i < array.length; i++) {\n"
                        + "    switch (i & 3) {\n"
                        + "        case 0:\n"
                        + "            array[i] = null;\n"
                        + "            break;\n"
                        + "        case 1:\n"
                        + "            array[i] = \"tfw-\" + i;\n"
                        + "            break;\n"
                        + "        case 2:\n"
                        + "            array[i] = \"value-\" + i + \"-distinct\";\n"
                        + "            break;\n"
                        + "        default:\n"
                        + "            array[i] = String.valueOf(Integer.MIN_VALUE + i);\n"
                        + "            break;\n"
                        + "    }\n"
                        + "}";

            case "ShortIla":
                return "for (int i = 0; i < array.length; i++) {\n"
                        + "    array[i] = (short) (i * 7919 + 12345);\n"
                        + "}";

            default:
                throw new IllegalArgumentException("Unsupported Ila type: " + type.ilaType());
        }
    }

    private static String assertElementEquals(final IlaType type) {
        switch (type.ilaType()) {
            case "BooleanIla":
            case "ByteIla":
            case "IntIla":
            case "LongIla":
            case "ShortIla":
                return "if (expected[expectedIndex] != actual[actualIndex]) {\n"
                        + "    throw new AssertionError(\n"
                        + "        \"expected=\" + expected[expectedIndex] + \", actual=\" + actual[actualIndex]);\n"
                        + "}";

            case "CharIla":
                return "if (expected[expectedIndex] != actual[actualIndex]) {\n"
                        + "    throw new AssertionError(\n"
                        + "        \"expected=\" + (int) expected[expectedIndex]\n"
                        + "            + \", actual=\" + (int) actual[actualIndex]);\n"
                        + "}";

            case "DoubleIla":
                return "long expectedBits = Double.doubleToRawLongBits(expected[expectedIndex]);\n"
                        + "long actualBits = Double.doubleToRawLongBits(actual[actualIndex]);\n"
                        + "if (expectedBits != actualBits) {\n"
                        + "    throw new AssertionError(\"expectedBits=\"\n"
                        + "        + Long.toHexString(expectedBits)\n"
                        + "        + \", actualBits=\" + Long.toHexString(actualBits));\n"
                        + "}";

            case "FloatIla":
                return "int expectedBits = Float.floatToRawIntBits(expected[expectedIndex]);\n"
                        + "int actualBits = Float.floatToRawIntBits(actual[actualIndex]);\n"
                        + "if (expectedBits != actualBits) {\n"
                        + "    throw new AssertionError(\"expectedBits=\"\n"
                        + "        + Integer.toHexString(expectedBits)\n"
                        + "        + \", actualBits=\" + Integer.toHexString(actualBits));\n"
                        + "}";

            case "ObjectIla":
                return "String expectedValue = expected[expectedIndex];\n"
                        + "String actualValue = actual[actualIndex];\n"
                        + "if (expectedValue == null ? actualValue != null : !expectedValue.equals(actualValue)) {\n"
                        + "    throw new AssertionError(\"expected=\" + expectedValue + \", actual=\" + actualValue);\n"
                        + "}";

            default:
                throw new IllegalArgumentException("Unsupported Ila type: " + type.ilaType());
        }
    }

    private static final class IlaType {

        private final String packageName;
        private final String elementType;
        private final String arrayType;
        private final String ilaType;
        private final String generic;

        private IlaType(
                final String packageName,
                final String elementType,
                final String arrayType,
                final String ilaType,
                final String generic) {
            this.packageName = packageName;
            this.elementType = elementType;
            this.arrayType = arrayType;
            this.ilaType = ilaType;
            this.generic = generic;
        }

        private String packageName() {
            return packageName;
        }

        private String elementType() {
            return elementType;
        }

        private String arrayType() {
            return arrayType;
        }

        private String ilaPackage() {
            return packageName.replace("ilaf", "ila");
        }

        private String ilaType() {
            return ilaType;
        }

        private String generic() {
            return generic;
        }
    }

    private static final class FuzzerDefinition {

        private final String template;
        private final String packageName;
        private final String className;
        private final Map<String, Object> model;

        private FuzzerDefinition(
                final String template,
                final String packageName,
                final String className,
                final Map<String, Object> model) {
            this.template = template;
            this.packageName = packageName;
            this.className = className;
            this.model = model;
        }

        private String template() {
            return template;
        }

        private String packageName() {
            return packageName;
        }

        private String className() {
            return className;
        }

        private Map<String, Object> model() {
            return model;
        }
    }
}
