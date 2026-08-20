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
      throw new IllegalArgumentException(
          "Usage: FuzzerGenerator <template-directory> <fuzz-directory>");
    }

    final Path templateDirectory = Path.of(args[0]);
    final Path fuzzDirectory = Path.of(args[1]);

    final Configuration configuration =
        new Configuration(Configuration.VERSION_2_3_34);

    configuration.setDirectoryForTemplateLoading(templateDirectory.toFile());
    configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
    configuration.setTemplateExceptionHandler(
        TemplateExceptionHandler.RETHROW_HANDLER);
    configuration.setLogTemplateExceptions(false);
    configuration.setWrapUncheckedExceptions(true);

    final Template template =
        configuration.getTemplate(
            "tfw/immutable/ilaf/IlaFactoryFromArrayFuzzer.java.ftl");

    for (final Map<String, Object> model : definitions()) {
      generate(template, model, fuzzDirectory);
    }
  }

  private static void generate(
      final Template template,
      final Map<String, Object> model,
      final Path fuzzDirectory)
      throws Exception {

    final String packageName = (String) model.get("package");
    final String className = (String) model.get("className");

    final Path packageDirectory =
        fuzzDirectory.resolve(
            packageName.replace('.', File.separatorChar));

    Files.createDirectories(packageDirectory);

    final Path outputFile =
        packageDirectory.resolve(className + ".java");

    try (Writer writer =
        Files.newBufferedWriter(
            outputFile,
            StandardCharsets.UTF_8)) {
      template.process(model, writer);
    }
  }

  private static List<Map<String, Object>> definitions() {
    return Arrays.asList(
        definition(
            "booleanilaf",
            "BooleanIlaFactoryFromArrayFuzzer",
            "boolean",
            "BooleanIla",
            null),

        definition(
            "byteilaf",
            "ByteIlaFactoryFromArrayFuzzer",
            "byte",
            "ByteIla",
            null),

        definition(
            "charilaf",
            "CharIlaFactoryFromArrayFuzzer",
            "char",
            "CharIla",
            null),

        definition(
            "doubleilaf",
            "DoubleIlaFactoryFromArrayFuzzer",
            "double",
            "DoubleIla",
            null),

        definition(
            "floatilaf",
            "FloatIlaFactoryFromArrayFuzzer",
            "float",
            "FloatIla",
            null),

        definition(
            "intilaf",
            "IntIlaFactoryFromArrayFuzzer",
            "int",
            "IntIla",
            null),

        definition(
            "longilaf",
            "LongIlaFactoryFromArrayFuzzer",
            "long",
            "LongIla",
            null),

        definition(
            "objectilaf",
            "ObjectIlaFactoryFromArrayFuzzer",
            "Object",
            "ObjectIla",
            "Object"),

        definition(
            "shortilaf",
            "ShortIlaFactoryFromArrayFuzzer",
            "short",
            "ShortIla",
            null));
  }

  private static Map<String, Object> definition(
      final String typePackage,
      final String className,
      final String arrayType,
      final String ilaType,
      final String generic) {

    final Map<String, Object> model = new HashMap<>();

    model.put(
        "package",
        "tfw.immutable.ilaf." + typePackage);

    model.put("className", className);
    model.put("arrayType", arrayType);
    model.put("ilaType", ilaType);

    if (generic != null) {
      model.put("generic", generic);
    }

    return model;
  }
}
