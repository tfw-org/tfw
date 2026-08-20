package tfw.build;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FuzzerGenerator {

    private FuzzerGenerator() {}

    public static void main(final String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: FuzzerGenerator <template-directory> <fuzz-directory>");
        }

        final Path templateDirectory = Path.of(args[0]);
        final Path fuzzDirectory = Path.of(args[1]);

        final Configuration configuration = new Configuration(Configuration.VERSION_2_3_34);

        configuration.setDirectoryForTemplateLoading(templateDirectory.toFile());
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);

        final Template template = configuration.getTemplate("tfw/immutable/ilaf/IlaFactoryFromArrayFuzzer.java.ftl");

        for (final Map<String, Object> model : definitions()) {
            generate(template, model, fuzzDirectory);
        }
    }

    private static void generate(final Template template, final Map<String, Object> model, final Path fuzzDirectory)
            throws Exception {

        final String packageName = (String) model.get("packageName");
        final String className = (String) model.get("className");

        final Path packageDirectory = fuzzDirectory.resolve(packageName.replace('.', File.separatorChar));

        Files.createDirectories(packageDirectory);

        final Path outputFile = packageDirectory.resolve(className + ".java");

        try (Writer writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8)) {
            template.process(model, writer);
        }
    }

    private static List<Map<String, Object>> definitions() {
        return Arrays.asList(
                definition(
                        "booleanilaf",
                        "BooleanIlaFactoryFromArrayFuzzer",
                        "boolean",
                        "Boolean",
                        "booleanila",
                        "BooleanIla"),
                definition("byteilaf", "ByteIlaFactoryFromArrayFuzzer", "byte", "Byte", "byteila", "ByteIla"),
                definition("charilaf", "CharIlaFactoryFromArrayFuzzer", "char", "Char", "charila", "CharIla"),
                definition(
                        "doubleilaf", "DoubleIlaFactoryFromArrayFuzzer", "double", "Double", "doubleila", "DoubleIla"),
                definition("floatilaf", "FloatIlaFactoryFromArrayFuzzer", "float", "Float", "floatila", "FloatIla"),
                definition("intilaf", "IntIlaFactoryFromArrayFuzzer", "int", "Integer", "intila", "IntIla"),
                definition("longilaf", "LongIlaFactoryFromArrayFuzzer", "long", "Long", "longila", "LongIla"),
                definition(
                        "objectilaf", "ObjectIlaFactoryFromArrayFuzzer", "Object", "Object", "objectila", "ObjectIla"),
                definition("shortilaf", "ShortIlaFactoryFromArrayFuzzer", "short", "Short", "shortila", "ShortIla"));
    }

    private static Map<String, Object> definition(
            final String typePackage,
            final String className,
            final String type,
            final String typeName,
            final String ilaPackage,
            final String ilaType) {

        final Map<String, Object> model = new HashMap<>();

        model.put("packageName", "tfw.immutable.ilaf." + typePackage);
        model.put("className", className);
        model.put("type", type);
        model.put("typeName", typeName);
        model.put("ilaPackage", ilaPackage);
        model.put("ilaType", ilaType);

        return model;
    }
}
