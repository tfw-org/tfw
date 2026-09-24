package tfw.build;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TemplateGenerator {
    private TemplateGenerator() {}

    private enum TemplateProperty {
        TEMPLATE,
        TEMPLATE_SPACE,
        TYPE_OR_TEMPLATE,
        DIAMOND,
        SUPPRESS
    }

    private enum IlaProperty {
        RANDOM_VALUE,
        ASSERT_EQUALS_DELTA,
        CREATE_IMMUTABLE_START,
        CREATE_IMMUTABLE_END,
        CAST_FROM_INT,
        CAST_FROM_INT_PRE,
        CAST_FROM_INT_POST,
        CAST_FROM_LONG,
        CAST_FROM_LONG_PRE,
        CAST_FROM_LONG_POST,
        CAST_FROM_DOUBLE
    }

    private enum FuzzProperty {
        CREATE_EXPRESSION,
        LENGTH_EXPRESSION,
        GET_EXPRESSION,
        INITIALIZE,
        ASSERT_ELEMENT_EQUALS
    }

    private static final class TypeDefinition {
        private final String name;
        private final String type;
        private final String lowercase;

        private final EnumMap<TemplateProperty, String> templateProperties;
        private final EnumMap<IlaProperty, String> ilaProperties;
        private final EnumMap<FuzzProperty, String> fuzzProperties;

        private boolean fuzzSingleLineAssertElementEquals;

        private TypeDefinition(final String name, final String type, final String lowercase) {
            this.name = name;
            this.type = type;
            this.lowercase = lowercase;

            this.templateProperties = new EnumMap<>(TemplateProperty.class);
            this.ilaProperties = new EnumMap<>(IlaProperty.class);
            this.fuzzProperties = new EnumMap<>(FuzzProperty.class);

            templateProperties.put(TemplateProperty.TEMPLATE, "");
            templateProperties.put(TemplateProperty.TEMPLATE_SPACE, "");
            templateProperties.put(TemplateProperty.TYPE_OR_TEMPLATE, type);
            templateProperties.put(TemplateProperty.DIAMOND, "");

            this.fuzzSingleLineAssertElementEquals = false;
        }

        private TypeDefinition(final TypeDefinition source) {
            this.name = source.name;
            this.type = source.type;
            this.lowercase = source.lowercase;

            this.templateProperties = new EnumMap<>(source.templateProperties);
            this.ilaProperties = new EnumMap<>(source.ilaProperties);
            this.fuzzProperties = new EnumMap<>(source.fuzzProperties);

            this.fuzzSingleLineAssertElementEquals = source.fuzzSingleLineAssertElementEquals;
        }

        private TypeDefinition template(final TemplateProperty property, final String value) {
            templateProperties.put(property, value);
            return this;
        }

        private TypeDefinition ila(final IlaProperty property, final String value) {
            ilaProperties.put(property, value);
            return this;
        }

        private TypeDefinition fuzz(final FuzzProperty property, final String value) {
            fuzzProperties.put(property, value);
            return this;
        }

        private String template(final TemplateProperty property) {
            return templateProperties.getOrDefault(property, "");
        }

        private String ila(final IlaProperty property) {
            return ilaProperties.getOrDefault(property, "");
        }

        private String fuzz(final FuzzProperty property) {
            return fuzzProperties.getOrDefault(property, "");
        }
    }

    private static final Map<String, TypeDefinition> TYPES = createTypes();

    private static final List<Path> MIGRATED_MAIN_DIRECTORIES = Arrays.asList(
            Paths.get("tfw", "immutable", "iba"),
            Paths.get("tfw", "immutable", "iis"),
            Paths.get("tfw", "immutable", "iisf"),
            Paths.get("tfw", "immutable", "ilmf"),
            Paths.get("tfw", "immutable", "ilm"),
            Paths.get("tfw", "immutable", "ilaf"),
            Paths.get("tfw", "immutable", "ila"));

    private static final List<Path> MIGRATED_TEST_DIRECTORIES = Arrays.asList(
            Paths.get("tfw", "immutable", "iba"),
            Paths.get("tfw", "immutable", "iis"),
            Paths.get("tfw", "immutable", "iisf"),
            Paths.get("tfw", "immutable", "ilm"),
            Paths.get("tfw", "immutable", "ila"),
            Paths.get("tfw", "immutable", "ilaf"));

    private static Map<String, TypeDefinition> createTypes() {
        final Map<String, TypeDefinition> types = new HashMap<>();

        addStandardTypes(types, "iba");
        addStandardTypes(types, "iis");
        addStandardTypes(types, "iisf");
        addStandardTypes(types, "ilmf");
        addStandardTypes(types, "ilm");
        addStandardTypes(types, "ilaf");

        addIlaTypes(types);
        addFuzzTypes(types);

        return types;
    }

    private static void addStandardTypes(final Map<String, TypeDefinition> types, final String suffix) {
        addType(types, suffix, new TypeDefinition("Boolean", "boolean", "boolean"));
        addType(types, suffix, new TypeDefinition("Byte", "byte", "byte"));
        addType(types, suffix, new TypeDefinition("Char", "char", "char"));
        addType(types, suffix, new TypeDefinition("Double", "double", "double"));
        addType(types, suffix, new TypeDefinition("Float", "float", "float"));
        addType(types, suffix, new TypeDefinition("Int", "int", "int"));
        addType(types, suffix, new TypeDefinition("Long", "long", "long"));

        TypeDefinition object = new TypeDefinition("Object", "Object", "object")
                .template(TemplateProperty.TEMPLATE, "<T>")
                .template(TemplateProperty.TEMPLATE_SPACE, "<T> ")
                .template(TemplateProperty.TYPE_OR_TEMPLATE, "T")
                .template(TemplateProperty.DIAMOND, "<>");

        if ("ilaf".equals(suffix)) {
            object.template(TemplateProperty.SUPPRESS, "@SuppressWarnings(\"unchecked\")\n        ");
        }

        addType(types, suffix, object);
        addType(types, suffix, new TypeDefinition("Short", "short", "short"));
    }

    private static void addType(
            final Map<String, TypeDefinition> types, final String suffix, final TypeDefinition type) {
        types.put(type.lowercase + suffix, type);
    }

    /*
     * ILA types.
     *
     * These values are the former contents of the nine
     * src/main/template/tfw/immutable/ila/*.mapping files.
     */
    private static void addIlaTypes(final Map<String, TypeDefinition> types) {
        addIlaType(
                types,
                new TypeDefinition("Boolean", "boolean", "boolean")
                        .ila(IlaProperty.RANDOM_VALUE, "random.nextBoolean()")
                        .ila(IlaProperty.CREATE_IMMUTABLE_START, "new Boolean(")
                        .ila(IlaProperty.CREATE_IMMUTABLE_END, ")"));

        addIlaType(
                types,
                new TypeDefinition("Byte", "byte", "byte")
                        .ila(IlaProperty.RANDOM_VALUE, "(byte)random.nextInt()")
                        .ila(IlaProperty.CREATE_IMMUTABLE_START, "new Byte(")
                        .ila(IlaProperty.CREATE_IMMUTABLE_END, ")")
                        .ila(IlaProperty.CAST_FROM_INT, " (byte)")
                        .ila(IlaProperty.CAST_FROM_INT_PRE, "(byte) (")
                        .ila(IlaProperty.CAST_FROM_INT_POST, ")")
                        .ila(IlaProperty.CAST_FROM_LONG, " (byte)")
                        .ila(IlaProperty.CAST_FROM_LONG_PRE, "(byte) (")
                        .ila(IlaProperty.CAST_FROM_LONG_POST, ")"));

        addIlaType(
                types,
                new TypeDefinition("Char", "char", "char")
                        .ila(IlaProperty.RANDOM_VALUE, "(char)random.nextInt()")
                        .ila(IlaProperty.CREATE_IMMUTABLE_START, "new Character(")
                        .ila(IlaProperty.CREATE_IMMUTABLE_END, ")")
                        .ila(IlaProperty.CAST_FROM_INT, " (char)")
                        .ila(IlaProperty.CAST_FROM_INT_PRE, "(char) (")
                        .ila(IlaProperty.CAST_FROM_INT_POST, ")")
                        .ila(IlaProperty.CAST_FROM_LONG, " (char)")
                        .ila(IlaProperty.CAST_FROM_LONG_PRE, "(char) (")
                        .ila(IlaProperty.CAST_FROM_LONG_POST, ")"));

        addIlaType(
                types,
                new TypeDefinition("Double", "double", "double")
                        .ila(IlaProperty.RANDOM_VALUE, "random.nextDouble()")
                        .ila(IlaProperty.ASSERT_EQUALS_DELTA, ", 0")
                        .ila(IlaProperty.CREATE_IMMUTABLE_START, "new Double(")
                        .ila(IlaProperty.CREATE_IMMUTABLE_END, ")"));

        addIlaType(
                types,
                new TypeDefinition("Float", "float", "float")
                        .ila(IlaProperty.RANDOM_VALUE, "random.nextFloat()")
                        .ila(IlaProperty.ASSERT_EQUALS_DELTA, ", 0f")
                        .ila(IlaProperty.CREATE_IMMUTABLE_START, "new Float(")
                        .ila(IlaProperty.CREATE_IMMUTABLE_END, ")")
                        .ila(IlaProperty.CAST_FROM_DOUBLE, " (float)"));

        addIlaType(
                types,
                new TypeDefinition("Int", "int", "int")
                        .ila(IlaProperty.RANDOM_VALUE, "random.nextInt()")
                        .ila(IlaProperty.CREATE_IMMUTABLE_START, "new Integer(")
                        .ila(IlaProperty.CREATE_IMMUTABLE_END, ")")
                        .ila(IlaProperty.CAST_FROM_LONG, " (int)")
                        .ila(IlaProperty.CAST_FROM_LONG_PRE, "(int) (")
                        .ila(IlaProperty.CAST_FROM_LONG_POST, ")"));

        addIlaType(
                types,
                new TypeDefinition("Long", "long", "long")
                        .ila(IlaProperty.RANDOM_VALUE, "random.nextLong()")
                        .ila(IlaProperty.CREATE_IMMUTABLE_START, "new Long(")
                        .ila(IlaProperty.CREATE_IMMUTABLE_END, ")"));

        addIlaType(
                types,
                new TypeDefinition("Object", "Object", "object")
                        .template(TemplateProperty.TEMPLATE, "<T>")
                        .template(TemplateProperty.TEMPLATE_SPACE, "<T> ")
                        .template(TemplateProperty.TYPE_OR_TEMPLATE, "T")
                        .template(TemplateProperty.DIAMOND, "<>")
                        .ila(IlaProperty.RANDOM_VALUE, "new Object()"));

        addIlaType(
                types,
                new TypeDefinition("Short", "short", "short")
                        .ila(IlaProperty.RANDOM_VALUE, "(short)random.nextInt()")
                        .ila(IlaProperty.CREATE_IMMUTABLE_START, "new Short(")
                        .ila(IlaProperty.CREATE_IMMUTABLE_END, ")")
                        .ila(IlaProperty.CAST_FROM_INT, " (short)")
                        .ila(IlaProperty.CAST_FROM_INT_PRE, "(short) (")
                        .ila(IlaProperty.CAST_FROM_INT_POST, ")")
                        .ila(IlaProperty.CAST_FROM_LONG, " (short)")
                        .ila(IlaProperty.CAST_FROM_LONG_PRE, "(short) (")
                        .ila(IlaProperty.CAST_FROM_LONG_POST, ")"));
    }

    private static void addIlaType(final Map<String, TypeDefinition> types, final TypeDefinition type) {
        types.put(type.lowercase + "ila", type);
    }

    private static void addFuzzTypes(final Map<String, TypeDefinition> types) {
        addFuzzType(
                types,
                "boolean",
                "array -> BooleanIlaFactoryFromArray.create(array).create()",
                "BooleanIla::length",
                "BooleanIla::get",
                "for (int i = 0; i < array.length; i++) {\n" + "    array[i] = (i & 1) != 0;\n" + "}",
                "if (expected[expectedIndex] != actual[actualIndex]) {\n"
                        + "    throw new AssertionError(\n"
                        + "            \"expected=\" + expected[expectedIndex] + \", actual=\" + actual[actualIndex]);\n"
                        + "}",
                false);

        addFuzzType(
                types,
                "byte",
                "array -> ByteIlaFactoryFromArray.create(array).create()",
                "ByteIla::length",
                "ByteIla::get",
                "for (int i = 0; i < array.length; i++) {\n" + "    array[i] = (byte) (i * 37 + 11);\n" + "}",
                "if (expected[expectedIndex] != actual[actualIndex]) {\n"
                        + "    throw new AssertionError(\n"
                        + "            \"expected=\" + expected[expectedIndex] + \", actual=\" + actual[actualIndex]);\n"
                        + "}",
                true);

        addFuzzType(
                types,
                "char",
                "array -> CharIlaFactoryFromArray.create(array).create()",
                "CharIla::length",
                "CharIla::get",
                "for (int i = 0; i < array.length; i++) {\n"
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
                        + "}",
                "if (expected[expectedIndex] != actual[actualIndex]) {\n"
                        + "    throw new AssertionError(\n"
                        + "            \"expected=\" + (int) expected[expectedIndex] + \", actual=\" + (int) actual[actualIndex]);\n"
                        + "}",
                true);

        addFuzzType(
                types,
                "double",
                "array -> DoubleIlaFactoryFromArray.create(array).create()",
                "DoubleIla::length",
                "DoubleIla::get",
                "for (int i = 0; i < array.length; i++) {\n"
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
                        + "}",
                "long expectedBits = Double.doubleToRawLongBits(expected[expectedIndex]);\n"
                        + "long actualBits = Double.doubleToRawLongBits(actual[actualIndex]);\n"
                        + "if (expectedBits != actualBits) {\n"
                        + "    throw new AssertionError(\"expectedBits=\"\n"
                        + "            + Long.toHexString(expectedBits)\n"
                        + "            + \", actualBits=\" + Long.toHexString(actualBits));\n"
                        + "}",
                false);

        addFuzzType(
                types,
                "float",
                "array -> FloatIlaFactoryFromArray.create(array).create()",
                "FloatIla::length",
                "FloatIla::get",
                "for (int i = 0; i < array.length; i++) {\n"
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
                        + "}",
                "int expectedBits = Float.floatToRawIntBits(expected[expectedIndex]);\n"
                        + "int actualBits = Float.floatToRawIntBits(actual[actualIndex]);\n"
                        + "if (expectedBits != actualBits) {\n"
                        + "    throw new AssertionError(\"expectedBits=\"\n"
                        + "            + Integer.toHexString(expectedBits)\n"
                        + "            + \", actualBits=\" + Integer.toHexString(actualBits));\n"
                        + "}",
                true);

        addFuzzType(
                types,
                "int",
                "array -> IntIlaFactoryFromArray.create(array).create()",
                "IntIla::length",
                "IntIla::get",
                "for (int i = 0; i < array.length; i++) {\n" + "    array[i] = i * 0x9e3779b9 ^ 0x12345678;\n" + "}",
                "if (expected[expectedIndex] != actual[actualIndex]) {\n"
                        + "    throw new AssertionError(\n"
                        + "            \"expected=\" + expected[expectedIndex] + \", actual=\" + actual[actualIndex]);\n"
                        + "}",
                true);

        addFuzzType(
                types,
                "long",
                "array -> LongIlaFactoryFromArray.create(array).create()",
                "LongIla::length",
                "LongIla::get",
                "for (int i = 0; i < array.length; i++) {\n"
                        + "    array[i] = 0x123456789ABCDEFL ^ ((long) i * 0x100000001L);\n"
                        + "}",
                "if (expected[expectedIndex] != actual[actualIndex]) {\n"
                        + "    throw new AssertionError(\n"
                        + "            \"expected=\" + expected[expectedIndex] + \", actual=\" + actual[actualIndex]);\n"
                        + "}",
                true);

        addFuzzType(
                types,
                "object",
                "array -> ObjectIlaFactoryFromArray.<Object>create(array).create()",
                "ObjectIla::length",
                "ObjectIla::get",
                "for (int i = 0; i < array.length; i++) {\n"
                        + "    switch (i & 3) {\n"
                        + "        case 0:\n"
                        + "            array[i] = null;\n"
                        + "            break;\n"
                        + "        case 1:\n"
                        + "            array[i] = \"tfw-\" + i;\n"
                        + "            break;\n"
                        + "        case 2:\n"
                        + "            array[i] = Integer.valueOf(i);\n"
                        + "            break;\n"
                        + "        default:\n"
                        + "            array[i] = Long.valueOf(i);\n"
                        + "            break;\n"
                        + "    }\n"
                        + "}",
                "Object expectedValue = expected[expectedIndex];\n"
                        + "Object actualValue = actual[actualIndex];\n"
                        + "if (expectedValue == null ? actualValue != null : !expectedValue.equals(actualValue)) {\n"
                        + "    throw new AssertionError(\"expected=\" + expectedValue + \", actual=\" + actualValue);\n"
                        + "}",
                false);

        addFuzzType(
                types,
                "short",
                "array -> ShortIlaFactoryFromArray.create(array).create()",
                "ShortIla::length",
                "ShortIla::get",
                "for (int i = 0; i < array.length; i++) {\n" + "    array[i] = (short) (i * 7919 + 12345);\n" + "}",
                "if (expected[expectedIndex] != actual[actualIndex]) {\n"
                        + "    throw new AssertionError(\n"
                        + "            \"expected=\" + expected[expectedIndex] + \", actual=\" + actual[actualIndex]);\n"
                        + "}",
                true);
    }

    private static void addFuzzType(
            final Map<String, TypeDefinition> types,
            final String lowercase,
            final String createExpression,
            final String lengthExpression,
            final String getExpression,
            final String initialize,
            final String assertElementEquals,
            final boolean singleLineAssertElementEquals) {

        final String key = lowercase + "ilaf";
        final TypeDefinition base = types.get(key);

        if (base == null) {
            throw new IllegalArgumentException("Unknown fuzz type: " + key);
        }

        types.put(
                key,
                withFuzz(
                        base,
                        createExpression,
                        lengthExpression,
                        getExpression,
                        initialize,
                        assertElementEquals,
                        singleLineAssertElementEquals));
    }

    private static TypeDefinition withFuzz(
            final TypeDefinition base,
            final String createExpression,
            final String lengthExpression,
            final String getExpression,
            final String initialize,
            final String assertElementEquals,
            final boolean singleLineAssertElementEquals) {
        final TypeDefinition result = new TypeDefinition(base);

        result.fuzz(FuzzProperty.CREATE_EXPRESSION, createExpression);
        result.fuzz(FuzzProperty.LENGTH_EXPRESSION, lengthExpression);
        result.fuzz(FuzzProperty.GET_EXPRESSION, getExpression);
        result.fuzz(FuzzProperty.INITIALIZE, initialize);
        result.fuzz(FuzzProperty.ASSERT_ELEMENT_EQUALS, assertElementEquals);
        result.fuzzSingleLineAssertElementEquals = singleLineAssertElementEquals;

        return result;
    }

    public static void main(final String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: TemplateGenerator <template-root>");
        }

        final Path templateRoot = Paths.get(args[0]);

        generate(
                templateRoot.resolve("main"),
                Paths.get("src", "main", "template"),
                Paths.get("src", "main", "java"),
                SourceKind.MAIN);

        generate(
                templateRoot.resolve("test"),
                Paths.get("src", "test", "template"),
                Paths.get("src", "test", "java"),
                SourceKind.TEST);

        generate(templateRoot.resolve("fuzz"), null, Paths.get("src", "fuzz", "java"), SourceKind.FUZZ);
    }

    private static void generate(
            final Path templateRoot, final Path mappingRoot, final Path outputRoot, final SourceKind sourceKind)
            throws Exception {
        if (!Files.exists(templateRoot)) {
            return;
        }

        final Configuration configuration = new Configuration(Configuration.VERSION_2_3_34);

        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);

        final List<Path> templates;

        try (Stream<Path> paths = Files.find(
                templateRoot,
                Integer.MAX_VALUE,
                (path, attributes) -> attributes.isRegularFile()
                        && path.getFileName().toString().endsWith(".ftl"))) {

            templates = paths.collect(Collectors.toList());
        }

        for (final Path templatePath : templates) {
            generate(configuration, templateRoot, mappingRoot, outputRoot, sourceKind, templatePath);
        }
    }

    private static void generate(
            final Configuration configuration,
            final Path templateRoot,
            final Path mappingRoot,
            final Path outputRoot,
            final SourceKind sourceKind,
            final Path templatePath)
            throws Exception {
        final String source = new String(Files.readAllBytes(templatePath), StandardCharsets.UTF_8);

        final String[] parts = source.split("\\R", 2);

        if (parts.length != 2 || !parts[0].startsWith("//")) {
            throw new IllegalArgumentException(
                    "Template does not have a mapping comment as its first line: " + templatePath);
        }

        final String mappingLine = parts[0].substring(2).trim();

        final Path relativeDirectory = templateRoot.relativize(templatePath.getParent());

        final Path templateMappingDirectory = mappingRoot == null ? null : mappingRoot.resolve(relativeDirectory);

        for (final String mappingName : mappingLine.split(",")) {
            generateMapping(
                    configuration,
                    mappingName.trim(),
                    parts[1],
                    templateMappingDirectory,
                    outputRoot,
                    relativeDirectory,
                    sourceKind,
                    templatePath);
        }
    }

    private static void addCommonModel(final Map<String, Object> model, final TypeDefinition type) {

        model.put("NAME", type.name);
        model.put("TEMPLATE", type.template(TemplateProperty.TEMPLATE));
        model.put("TEMPLATE_SPACE", type.template(TemplateProperty.TEMPLATE_SPACE));
        model.put("TYPE_OR_TEMPLATE", type.template(TemplateProperty.TYPE_OR_TEMPLATE));
        model.put("DIAMOND", type.template(TemplateProperty.DIAMOND));
        model.put("TYPE", type.type);
        model.put("LOWERCASE", type.lowercase);
        model.put("LOWER_NAME", type.lowercase);
        model.put("SUPPRESS", type.template(TemplateProperty.SUPPRESS));
    }

    private static void generateMapping(
            final Configuration configuration,
            final String mappingName,
            final String templateSource,
            final Path mappingDirectory,
            final Path outputRoot,
            final Path relativeDirectory,
            final SourceKind sourceKind,
            final Path templatePath)
            throws Exception {
        final Map<String, Object> model = new HashMap<>();
        final TypeDefinition type = TYPES.get(mappingName);

        if (type == null) {
            throw new IllegalArgumentException("Unknown type mapping: " + mappingName);
        }

        final boolean isMain = sourceKind == SourceKind.MAIN;
        final boolean isTest = sourceKind == SourceKind.TEST;
        final boolean isFuzz = sourceKind == SourceKind.FUZZ;

        final boolean isIla = relativeDirectory.equals(Paths.get("tfw", "immutable", "ila"));
        final boolean isIlaf = relativeDirectory.equals(Paths.get("tfw", "immutable", "ilaf"));

        if (isMain && !MIGRATED_MAIN_DIRECTORIES.contains(relativeDirectory)) {
            return;
        }

        if (isTest && !MIGRATED_TEST_DIRECTORIES.contains(relativeDirectory)) {
            return;
        }

        if (isFuzz && !isIlaf) {
            return;
        }

        addCommonModel(model, type);

        if (isMain && isIla) {
            model.put("RANDOM_VALUE", type.ila(IlaProperty.RANDOM_VALUE));
            model.put("ASSERT_EQUALS_DELTA", type.ila(IlaProperty.ASSERT_EQUALS_DELTA));
            model.put("CREATE_IMMUTABLE_START", type.ila(IlaProperty.CREATE_IMMUTABLE_START));
            model.put("CREATE_IMMUTABLE_END", type.ila(IlaProperty.CREATE_IMMUTABLE_END));
            model.put("CAST_FROM_INT", type.ila(IlaProperty.CAST_FROM_INT));
            model.put("CAST_FROM_INT_PRE", type.ila(IlaProperty.CAST_FROM_INT_PRE));
            model.put("CAST_FROM_INT_POST", type.ila(IlaProperty.CAST_FROM_INT_POST));
            model.put("CAST_FROM_LONG", type.ila(IlaProperty.CAST_FROM_LONG));
            model.put("CAST_FROM_LONG_PRE", type.ila(IlaProperty.CAST_FROM_LONG_PRE));
            model.put("CAST_FROM_LONG_POST", type.ila(IlaProperty.CAST_FROM_LONG_POST));
            model.put("CAST_FROM_DOUBLE", type.ila(IlaProperty.CAST_FROM_DOUBLE));
        }

        if (isTest) {
            model.put("TEMPLATE", testTemplate(type.type));
            model.put("DEFAULT_VALUE", defaultValue(type.type));
            model.put("DEFAULT_VALUE_2", defaultValue2(type.type));
            model.put("RANDOM_VALUE", randomValue(type.type));
            model.put("RANDOM_INCLUDE", randomInclude(type.type));
            model.put("RANDOM_INIT", randomInit(type.type));
            model.put("RANDOM_INCLUDE_2", randomInclude2(type.type));
            model.put("RANDOM_INIT_0", randomInit0(type.type));
            model.put("RANDOM_INIT_12", randomInit12(type.type));
            model.put("UTIL", util(type.type));
            model.put("FULL_CAST", fullCast(type));
            model.put("CHAR_CAST_TO_INT", charCastToInt(type.type));
            model.put("FP_ZEROS", fpZeros(type.type));
            model.put("IS_EQUALS_START", isEqualsStart(type.type));
            model.put("IS_EQUALS_END", isEqualsEnd(type.type));
            model.put("CAST_FROM_INT", testCastFromInt(type.type));
            model.put("CAST_FROM_INT_PAREN", testCastFromIntParen(type.type));
            model.put("CAST_FROM_INT_PAREN_END", testCastFromIntParenEnd(type.type));
            model.put("CAST_FROM_DOUBLE", testCastFromDouble(type.type));
            model.put("SUPPRESS", testSuppress(type.type));
        }

        if (isFuzz) {
            model.put("FUZZ_ARRAY_TYPE", type.type + "[]");
            model.put("FUZZ_ELEMENT_TYPE", type.type);
            model.put("FUZZ_ILA_PACKAGE", type.lowercase + "ila");
            model.put("FUZZ_ILA_TYPE", type.name + "Ila");
            model.put("FUZZ_FACTORY_NAME", type.name + "IlaFactoryFromArray");
            model.put("FUZZ_CREATE_EXPRESSION", type.fuzz(FuzzProperty.CREATE_EXPRESSION));
            model.put("FUZZ_LENGTH_EXPRESSION", type.fuzz(FuzzProperty.LENGTH_EXPRESSION));
            model.put("FUZZ_GET_EXPRESSION", type.fuzz(FuzzProperty.GET_EXPRESSION));
            model.put("FUZZ_INITIALIZE", indent(type.fuzz(FuzzProperty.INITIALIZE), 20));
            model.put("FUZZ_ASSERT_ELEMENT_EQUALS", indent(type.fuzz(FuzzProperty.ASSERT_ELEMENT_EQUALS), 20));
            model.put("FUZZ_SINGLE_LINE_ASSERT_ELEMENT_EQUALS", type.fuzzSingleLineAssertElementEquals);

            if ("Object".equals(type.type)) {
                model.put("FUZZ_GENERIC", "Object");
            }
        }

        final String packageName =
                relativeDirectory.resolve(mappingName).toString().replace(File.separatorChar, '.');

        model.put("PACKAGE", packageName);

        final Template template = new Template(templatePath.toString(), templateSource, configuration);

        final StringWriter writer = new StringWriter();

        template.process(model, writer);

        final String generated = writer.toString() + "// AUTO GENERATED FROM TEMPLATE" + System.lineSeparator();

        final Path packageDirectory =
                outputRoot.resolve(((String) model.get("PACKAGE")).replace('.', File.separatorChar));

        Files.createDirectories(packageDirectory);

        final String templateName = templatePath.getFileName().toString();

        final String outputName = templateName
                .replace(".ftl", "")
                .replace("__", (String) model.get("NAME"))
                .replaceAll("\\.\\..+\\.", ".");

        final Path outputFile = packageDirectory.resolve(outputName);

        System.out.println("  " + templatePath + " [" + mappingName + "] -> " + outputFile);

        Files.write(outputFile, generated.getBytes(StandardCharsets.UTF_8));
    }

    private static String indent(final String text, final int spaces) {
        final String indentation = String.join("", java.util.Collections.nCopies(spaces, " "));

        final String[] lines = text.split("\\n", -1);

        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            if (i > 0) {
                result.append('\n');
            }

            if (!lines[i].isEmpty()) {
                result.append(indentation);
                result.append(lines[i]);
            }
        }

        return result.toString();
    }

    private static String testTemplate(final String type) {
        return "Object".equals(type) ? "<Object>" : "";
    }

    private static String defaultValue(final String type) {
        switch (type) {
            case "boolean":
                return "false";
            case "byte":
                return "(byte) 0";
            case "char":
                return "(char) 0";
            case "double":
                return "0.0";
            case "float":
                return "0.0f";
            case "int":
                return "0";
            case "long":
                return "0L";
            case "Object":
                return "Object.class";
            case "short":
                return "(short) 0";
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    private static String defaultValue2(final String type) {
        switch (type) {
            case "boolean":
                return "true";
            case "byte":
                return "(byte) 1";
            case "char":
                return "(char) 1";
            case "double":
                return "1.0";
            case "float":
                return "1.0f";
            case "int":
                return "1";
            case "long":
                return "1L";
            case "Object":
                return "String.class";
            case "short":
                return "(short) 1";
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    private static String randomValue(final String type) {
        switch (type) {
            case "boolean":
                return "random.nextBoolean()";
            case "byte":
                return "(byte) random.nextInt()";
            case "char":
                return "(char) random.nextInt()";
            case "double":
                return "random.nextDouble()";
            case "float":
                return "random.nextFloat()";
            case "int":
                return "random.nextInt()";
            case "long":
                return "random.nextLong()";
            case "Object":
                return "new Object()";
            case "short":
                return "(short) random.nextInt()";
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    private static String randomInclude2(final String type) {
        if ("Object".equals(type)) {
            return "\n";
        }

        return "import java.util.Random;\n\n";
    }

    private static String randomInit0(final String type) {
        if ("Object".equals(type)) {
            return "";
        }

        return "final Random random = new Random(0);\n";
    }

    private static String randomInit12(final String type) {
        if ("Object".equals(type)) {
            return "";
        }

        return "final Random random = new Random(0);\n        ";
    }

    private static String util(final String type) {
        if ("boolean".equals(type)) {
            return "BooleanIlaUtilCheck.checkAll(actual, epsilon);\n        ";
        }

        return "";
    }

    private static String fullCast(final TypeDefinition type) {
        final String template = "Object".equals(type.type) ? "<Object>" : "";
        return "(" + type.name + "Ila" + template + "[]) ";
    }

    private static String charCastToInt(final String type) {
        if ("char".equals(type)) {
            return "(int) ";
        }

        return "";
    }

    private static String fpZeros(final String type) {
        if ("double".equals(type) || "float".equals(type)) {
            return "00000";
        }

        return "";
    }

    private static String isEqualsStart(final String type) {
        if ("Object".equals(type)) {
            return ".equals(";
        }

        if ("boolean".equals(type)
                || "byte".equals(type)
                || "char".equals(type)
                || "int".equals(type)
                || "long".equals(type)
                || "short".equals(type)) {
            return " == ";
        }

        return "";
    }

    private static String isEqualsEnd(final String type) {
        if ("Object".equals(type)) {
            return ")";
        }

        return "";
    }

    private static String testCastFromInt(final String type) {
        switch (type) {
            case "byte":
                return "(byte) ";
            case "char":
                return "(char) ";
            case "short":
                return "(short) ";
            default:
                return "";
        }
    }

    private static String testCastFromIntParen(final String type) {
        switch (type) {
            case "byte":
                return "(byte) (";
            case "char":
                return "(char) (";
            case "short":
                return "(short) (";
            default:
                return "";
        }
    }

    private static String testCastFromIntParenEnd(final String type) {
        switch (type) {
            case "byte":
            case "char":
            case "short":
                return ")";
            default:
                return "";
        }
    }

    private static String testCastFromDouble(final String type) {
        switch (type) {
            case "byte":
                return "(byte) ";
            case "char":
                return "(char) ";
            case "float":
                return "(float) ";
            case "int":
                return "(int) ";
            case "long":
                return "(long) ";
            case "short":
                return "(short) ";
            default:
                return "";
        }
    }

    private static String testSuppress(final String type) {
        if ("Object".equals(type)) {
            return "@SuppressWarnings(\"unchecked\")\n    ";
        }

        return "";
    }

    private static String randomInclude(final String type) {
        if ("Object".equals(type)) {
            return "";
        }

        return "import java.util.Random;\n";
    }

    private static String randomInit(final String type) {
        if ("Object".equals(type)) {
            return "";
        }

        return "final Random random = new Random(0);\n        ";
    }

    private enum SourceKind {
        MAIN,
        TEST,
        FUZZ
    }
}
