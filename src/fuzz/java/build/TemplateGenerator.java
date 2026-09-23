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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TemplateGenerator {
    private TemplateGenerator() {}

    private static final class TypeDefinition {
        private final String name;
        private final String template;
        private final String templateSpace;
        private final String typeOrTemplate;
        private final String diamond;
        private final String type;
        private final String lowercase;
        private final String suppress;

        private final String randomValue;
        private final String assertEqualsDelta;
        private final String createImmutableStart;
        private final String createImmutableEnd;
        private final String castFromInt;
        private final String castFromIntPre;
        private final String castFromIntPost;
        private final String castFromLong;
        private final String castFromLongPre;
        private final String castFromLongPost;
        private final String castFromDouble;

        private TypeDefinition(
                final String name,
                final String template,
                final String templateSpace,
                final String typeOrTemplate,
                final String diamond,
                final String type,
                final String lowercase) {
            this(
                    name,
                    template,
                    templateSpace,
                    typeOrTemplate,
                    diamond,
                    type,
                    lowercase,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "");
        }

        private TypeDefinition(
                final String name,
                final String template,
                final String templateSpace,
                final String typeOrTemplate,
                final String diamond,
                final String type,
                final String lowercase,
                final String suppress) {
            this(
                    name,
                    template,
                    templateSpace,
                    typeOrTemplate,
                    diamond,
                    type,
                    lowercase,
                    suppress,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "");
        }

        private TypeDefinition(
                final String name,
                final String template,
                final String templateSpace,
                final String typeOrTemplate,
                final String diamond,
                final String type,
                final String lowercase,
                final String suppress,
                final String randomValue,
                final String assertEqualsDelta,
                final String createImmutableStart,
                final String createImmutableEnd,
                final String castFromInt,
                final String castFromIntPre,
                final String castFromIntPost,
                final String castFromLong,
                final String castFromLongPre,
                final String castFromLongPost,
                final String castFromDouble) {
            this.name = name;
            this.template = template;
            this.templateSpace = templateSpace;
            this.typeOrTemplate = typeOrTemplate;
            this.diamond = diamond;
            this.type = type;
            this.lowercase = lowercase;
            this.suppress = suppress;
            this.randomValue = randomValue;
            this.assertEqualsDelta = assertEqualsDelta;
            this.createImmutableStart = createImmutableStart;
            this.createImmutableEnd = createImmutableEnd;
            this.castFromInt = castFromInt;
            this.castFromIntPre = castFromIntPre;
            this.castFromIntPost = castFromIntPost;
            this.castFromLong = castFromLong;
            this.castFromLongPre = castFromLongPre;
            this.castFromLongPost = castFromLongPost;
            this.castFromDouble = castFromDouble;
        }
    }

    private static final Map<String, TypeDefinition> TYPES = createTypes();

    private static Map<String, TypeDefinition> createTypes() {
        final Map<String, TypeDefinition> types = new HashMap<>();

        types.put("booleaniba", new TypeDefinition("Boolean", "", "", "boolean", "", "boolean", "boolean"));
        types.put("byteiba", new TypeDefinition("Byte", "", "", "byte", "", "byte", "byte"));
        types.put("chariba", new TypeDefinition("Char", "", "", "char", "", "char", "char"));
        types.put("doubleiba", new TypeDefinition("Double", "", "", "double", "", "double", "double"));
        types.put("floatiba", new TypeDefinition("Float", "", "", "float", "", "float", "float"));
        types.put("intiba", new TypeDefinition("Int", "", "", "int", "", "int", "int"));
        types.put("longiba", new TypeDefinition("Long", "", "", "long", "", "long", "long"));
        types.put("objectiba", new TypeDefinition("Object", "<T>", "<T> ", "T", "<>", "Object", "object"));
        types.put("shortiba", new TypeDefinition("Short", "", "", "short", "", "short", "short"));

        types.put("booleaniis", new TypeDefinition("Boolean", "", "", "boolean", "", "boolean", "boolean"));
        types.put("byteiis", new TypeDefinition("Byte", "", "", "byte", "", "byte", "byte"));
        types.put("chariis", new TypeDefinition("Char", "", "", "char", "", "char", "char"));
        types.put("doubleiis", new TypeDefinition("Double", "", "", "double", "", "double", "double"));
        types.put("floatiis", new TypeDefinition("Float", "", "", "float", "", "float", "float"));
        types.put("intiis", new TypeDefinition("Int", "", "", "int", "", "int", "int"));
        types.put("longiis", new TypeDefinition("Long", "", "", "long", "", "long", "long"));
        types.put("objectiis", new TypeDefinition("Object", "<T>", "<T> ", "T", "<>", "Object", "object"));
        types.put("shortiis", new TypeDefinition("Short", "", "", "short", "", "short", "short"));

        types.put("booleaniisf", new TypeDefinition("Boolean", "", "", "boolean", "", "boolean", "boolean"));
        types.put("byteiisf", new TypeDefinition("Byte", "", "", "byte", "", "byte", "byte"));
        types.put("chariisf", new TypeDefinition("Char", "", "", "char", "", "char", "char"));
        types.put("doubleiisf", new TypeDefinition("Double", "", "", "double", "", "double", "double"));
        types.put("floatiisf", new TypeDefinition("Float", "", "", "float", "", "float", "float"));
        types.put("intiisf", new TypeDefinition("Int", "", "", "int", "", "int", "int"));
        types.put("longiisf", new TypeDefinition("Long", "", "", "long", "", "long", "long"));
        types.put("objectiisf", new TypeDefinition("Object", "<T>", "<T> ", "T", "<>", "Object", "object"));
        types.put("shortiisf", new TypeDefinition("Short", "", "", "short", "", "short", "short"));

        types.put("booleanilmf", new TypeDefinition("Boolean", "", "", "boolean", "", "boolean", "boolean"));
        types.put("byteilmf", new TypeDefinition("Byte", "", "", "byte", "", "byte", "byte"));
        types.put("charilmf", new TypeDefinition("Char", "", "", "char", "", "char", "char"));
        types.put("doubleilmf", new TypeDefinition("Double", "", "", "double", "", "double", "double"));
        types.put("floatilmf", new TypeDefinition("Float", "", "", "float", "", "float", "float"));
        types.put("intilmf", new TypeDefinition("Int", "", "", "int", "", "int", "int"));
        types.put("longilmf", new TypeDefinition("Long", "", "", "long", "", "long", "long"));
        types.put("objectilmf", new TypeDefinition("Object", "<T>", "<T> ", "T", "<>", "Object", "object"));
        types.put("shortilmf", new TypeDefinition("Short", "", "", "short", "", "short", "short"));

        types.put("booleanilm", new TypeDefinition("Boolean", "", "", "boolean", "", "boolean", "boolean"));
        types.put("byteilm", new TypeDefinition("Byte", "", "", "byte", "", "byte", "byte"));
        types.put("charilm", new TypeDefinition("Char", "", "", "char", "", "char", "char"));
        types.put("doubleilm", new TypeDefinition("Double", "", "", "double", "", "double", "double"));
        types.put("floatilm", new TypeDefinition("Float", "", "", "float", "", "float", "float"));
        types.put("intilm", new TypeDefinition("Int", "", "", "int", "", "int", "int"));
        types.put("longilm", new TypeDefinition("Long", "", "", "long", "", "long", "long"));
        types.put("objectilm", new TypeDefinition("Object", "<T>", "<T> ", "T", "<>", "Object", "object"));
        types.put("shortilm", new TypeDefinition("Short", "", "", "short", "", "short", "short"));

        types.put("booleanilaf", new TypeDefinition("Boolean", "", "", "boolean", "", "boolean", "boolean"));
        types.put("byteilaf", new TypeDefinition("Byte", "", "", "byte", "", "byte", "byte"));
        types.put("charilaf", new TypeDefinition("Char", "", "", "char", "", "char", "char"));
        types.put("doubleilaf", new TypeDefinition("Double", "", "", "double", "", "double", "double"));
        types.put("floatilaf", new TypeDefinition("Float", "", "", "float", "", "float", "float"));
        types.put("intilaf", new TypeDefinition("Int", "", "", "int", "", "int", "int"));
        types.put("longilaf", new TypeDefinition("Long", "", "", "long", "", "long", "long"));
        types.put(
                "objectilaf",
                new TypeDefinition(
                        "Object",
                        "<T>",
                        "<T> ",
                        "T",
                        "<>",
                        "Object",
                        "object",
                        "@SuppressWarnings(\"unchecked\")\n        "));
        types.put("shortilaf", new TypeDefinition("Short", "", "", "short", "", "short", "short"));

        /*
         * ILA types.
         *
         * These values are the former contents of the nine
         * src/main/template/tfw/immutable/ila/*.mapping files.
         */
        types.put(
                "booleanila",
                new TypeDefinition(
                        "Boolean",
                        "",
                        "",
                        "boolean",
                        "",
                        "boolean",
                        "boolean",
                        "",
                        "random.nextBoolean()",
                        "",
                        "new Boolean(",
                        ")",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        ""));

        types.put(
                "byteila",
                new TypeDefinition(
                        "Byte",
                        "",
                        "",
                        "byte",
                        "",
                        "byte",
                        "byte",
                        "",
                        "(byte)random.nextInt()",
                        "",
                        "new Byte(",
                        ")",
                        " (byte)",
                        "(byte) (",
                        ")",
                        " (byte)",
                        "(byte) (",
                        ")",
                        ""));

        types.put(
                "charila",
                new TypeDefinition(
                        "Char",
                        "",
                        "",
                        "char",
                        "",
                        "char",
                        "char",
                        "",
                        "(char)random.nextInt()",
                        "",
                        "new Character(",
                        ")",
                        " (char)",
                        "(char) (",
                        ")",
                        " (char)",
                        "(char) (",
                        ")",
                        ""));

        types.put(
                "doubleila",
                new TypeDefinition(
                        "Double",
                        "",
                        "",
                        "double",
                        "",
                        "double",
                        "double",
                        "",
                        "random.nextDouble()",
                        ", 0",
                        "new Double(",
                        ")",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        ""));

        types.put(
                "floatila",
                new TypeDefinition(
                        "Float",
                        "",
                        "",
                        "float",
                        "",
                        "float",
                        "float",
                        "",
                        "random.nextFloat()",
                        ", 0f",
                        "new Float(",
                        ")",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        " (float)"));

        types.put(
                "intila",
                new TypeDefinition(
                        "Int",
                        "",
                        "",
                        "int",
                        "",
                        "int",
                        "int",
                        "",
                        "random.nextInt()",
                        "",
                        "new Integer(",
                        ")",
                        "",
                        "",
                        "",
                        " (int)",
                        "(int) (",
                        ")",
                        ""));

        types.put(
                "longila",
                new TypeDefinition(
                        "Long",
                        "",
                        "",
                        "long",
                        "",
                        "long",
                        "long",
                        "",
                        "random.nextLong()",
                        "",
                        "new Long(",
                        ")",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        ""));

        types.put(
                "objectila",
                new TypeDefinition(
                        "Object",
                        "<T>",
                        "<T> ",
                        "T",
                        "<>",
                        "Object",
                        "object",
                        "",
                        "new Object()",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        ""));

        types.put(
                "shortila",
                new TypeDefinition(
                        "Short",
                        "",
                        "",
                        "short",
                        "",
                        "short",
                        "short",
                        "",
                        "(short)random.nextInt()",
                        "",
                        "new Short(",
                        ")",
                        " (short)",
                        "(short) (",
                        ")",
                        " (short)",
                        "(short) (",
                        ")",
                        ""));

        return types;
    }

    public static void main(final String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: TemplateGenerator <template-root>");
        }

        final Path templateRoot = Paths.get(args[0]);

        generate(templateRoot.resolve("main"), Paths.get("src", "main", "template"), Paths.get("src", "main", "java"));

        generate(templateRoot.resolve("test"), Paths.get("src", "test", "template"), Paths.get("src", "test", "java"));
    }

    private static void generate(final Path templateRoot, final Path mappingRoot, final Path outputRoot)
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
            generate(configuration, templateRoot, mappingRoot, outputRoot, templatePath);
        }
    }

    private static void generate(
            final Configuration configuration,
            final Path templateRoot,
            final Path mappingRoot,
            final Path outputRoot,
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

        final Path templateMappingDirectory = mappingRoot.resolve(relativeDirectory);

        for (final String mappingName : mappingLine.split(",")) {
            generateMapping(
                    configuration,
                    mappingName.trim(),
                    parts[1],
                    templateMappingDirectory,
                    outputRoot,
                    relativeDirectory,
                    templatePath);
        }
    }

    private static void generateMapping(
            final Configuration configuration,
            final String mappingName,
            final String templateSource,
            final Path mappingDirectory,
            final Path outputRoot,
            final Path relativeDirectory,
            final Path templatePath)
            throws Exception {
        final Map<String, Object> model = new HashMap<>();
        final TypeDefinition type = TYPES.get(mappingName);

        final boolean isMain = mappingDirectory.startsWith(Paths.get("src", "main", "template"));

        final boolean isTest = mappingDirectory.startsWith(Paths.get("src", "test", "template"));

        final boolean isIba = relativeDirectory.equals(Paths.get("tfw", "immutable", "iba"));

        final boolean isIis = relativeDirectory.equals(Paths.get("tfw", "immutable", "iis"));

        final boolean isIisf = relativeDirectory.equals(Paths.get("tfw", "immutable", "iisf"));

        final boolean isIlmf = relativeDirectory.equals(Paths.get("tfw", "immutable", "ilmf"));

        final boolean isIlm = relativeDirectory.equals(Paths.get("tfw", "immutable", "ilm"));

        final boolean isIlaf = relativeDirectory.equals(Paths.get("tfw", "immutable", "ilaf"));

        final boolean isIla = relativeDirectory.equals(Paths.get("tfw", "immutable", "ila"));

        final boolean isMigratedMainType = isIba || isIis || isIisf || isIlmf || isIlm || isIlaf || isIla;

        final boolean isMigratedTestType = isIba || isIis || isIisf || isIlm || isIla || isIlaf;

        if (type != null && ((isMain && isMigratedMainType) || (isTest && isMigratedTestType))) {

            model.put("NAME", type.name);
            model.put("TEMPLATE", type.template);
            model.put("TEMPLATE_SPACE", type.templateSpace);
            model.put("TYPE_OR_TEMPLATE", type.typeOrTemplate);
            model.put("DIAMOND", type.diamond);
            model.put("TYPE", type.type);
            model.put("LOWERCASE", type.lowercase);
            model.put("LOWER_NAME", type.lowercase);
            model.put("SUPPRESS", type.suppress);

            if (isMain && isIla) {
                model.put("RANDOM_VALUE", type.randomValue);
                model.put("ASSERT_EQUALS_DELTA", type.assertEqualsDelta);
                model.put("CREATE_IMMUTABLE_START", type.createImmutableStart);
                model.put("CREATE_IMMUTABLE_END", type.createImmutableEnd);
                model.put("CAST_FROM_INT", type.castFromInt);
                model.put("CAST_FROM_INT_PRE", type.castFromIntPre);
                model.put("CAST_FROM_INT_POST", type.castFromIntPost);
                model.put("CAST_FROM_LONG", type.castFromLong);
                model.put("CAST_FROM_LONG_PRE", type.castFromLongPre);
                model.put("CAST_FROM_LONG_POST", type.castFromLongPost);
                model.put("CAST_FROM_DOUBLE", type.castFromDouble);
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

            final String packageName =
                    relativeDirectory.resolve(mappingName).toString().replace(File.separatorChar, '.');

            model.put("PACKAGE", packageName);
        }

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
}
