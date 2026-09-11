package tfw.build;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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

        private TypeDefinition(
                final String name,
                final String template,
                final String templateSpace,
                final String typeOrTemplate,
                final String diamond) {
            this.name = name;
            this.template = template;
            this.templateSpace = templateSpace;
            this.typeOrTemplate = typeOrTemplate;
            this.diamond = diamond;
        }
    }

    private static final Map<String, TypeDefinition> IBA_TYPES = createIbaTypes();

    private static Map<String, TypeDefinition> createIbaTypes() {
        final Map<String, TypeDefinition> types = new HashMap<>();

        types.put("booleaniba", new TypeDefinition("Boolean", "", "", "boolean", ""));
        types.put("byteiba", new TypeDefinition("Byte", "", "", "byte", ""));
        types.put("chariba", new TypeDefinition("Char", "", "", "char", ""));
        types.put("doubleiba", new TypeDefinition("Double", "", "", "double", ""));
        types.put("floatiba", new TypeDefinition("Float", "", "", "float", ""));
        types.put("intiba", new TypeDefinition("Int", "", "", "int", ""));
        types.put("longiba", new TypeDefinition("Long", "", "", "long", ""));
        types.put("objectiba", new TypeDefinition("Object", "<T>", "<T> ", "T", "<>"));
        types.put("shortiba", new TypeDefinition("Short", "", "", "short", ""));

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
        final TypeDefinition type = IBA_TYPES.get(mappingName);

        if (type != null && relativeDirectory.equals(Paths.get("tfw", "immutable", "iba"))) {
            model.put("NAME", type.name);
            model.put("TEMPLATE", type.template);
            model.put("TEMPLATE_SPACE", type.templateSpace);
            model.put("TYPE_OR_TEMPLATE", type.typeOrTemplate);
            model.put("DIAMOND", type.diamond);

            final String packageName =
                    relativeDirectory.resolve(mappingName).toString().replace(File.separatorChar, '.');

            model.put("PACKAGE", packageName);
        } else {
            final Path mappingPath = mappingDirectory.resolve(mappingName + ".mapping");

            final Properties properties = new Properties();
            properties.load(new StringReader(new String(Files.readAllBytes(mappingPath), StandardCharsets.UTF_8)));

            for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
                final String propertyName = ((String) entry.getKey()).trim();
                if (!propertyName.startsWith("%%") || !propertyName.endsWith("%%")) {
                    throw new IllegalArgumentException("Invalid mapping property: " + propertyName);
                }

                final String modelName = propertyName.substring(2, propertyName.length() - 2);

                model.put(modelName, entry.getValue());
            }
        }

        final Template template = new Template(templatePath.toString(), templateSource, configuration);

        final StringWriter writer = new StringWriter();
        template.process(model, writer);

        final String generated = writer.toString() + "// AUTO GENERATED FROM TEMPLATE" + System.lineSeparator();

        final Path packageDirectory =
                outputRoot.resolve(((String) model.get("PACKAGE")).replace('.', File.separatorChar));

        Files.createDirectories(packageDirectory);

        final String templateName = templatePath.getFileName().toString();

        final String outputName = templateName.replace(".ftl", "").replace("__", (String) model.get("NAME"));

        final Path outputFile = packageDirectory.resolve(outputName);

        System.out.println("  " + templatePath + " [" + mappingName + "] -> " + outputFile);

        Files.write(outputFile, generated.getBytes(StandardCharsets.UTF_8));
    }
}
