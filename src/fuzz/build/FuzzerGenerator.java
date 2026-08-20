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
            null,
            "BooleanIlaFactoryFromArray",
            "array -> BooleanIlaFactoryFromArray.create(array).create()",
            "BooleanIla::length",
            "BooleanIla::get",
            "for (int i = 0; i < array.length; i++) {\n"
                + "    array[i] = (i & 1) != 0;\n"
                + "}",
            "if (expected[expectedIndex] != actual[actualIndex]) {\n"
                + "    throw new AssertionError(\n"
                + "        \"expected=\" + expected[expectedIndex] + \", actual=\" + actual[actualIndex]);\n"
                + "}"),

        definition(
            "byteilaf",
            "ByteIlaFactoryFromArrayFuzzer",
            "byte",
            "ByteIla",
            null,
            "ByteIlaFactoryFromArray",
            "array -> ByteIlaFactoryFromArray.create(array).create()",
            "ByteIla::length",
            "ByteIla::get",
            "for (int i = 0; i < array.length; i++) {\n"
                + "    array[i] = (byte) (i * 37 + 11);\n"
                + "}",
            "if (expected[expectedIndex] != actual[actualIndex]) {\n"
                + "    throw new AssertionError(\n"
                + "        \"expected=\" + expected[expectedIndex] + \", actual=\" + actual[actualIndex]);\n"
                + "}"),

        definition(
            "charilaf",
            "CharIlaFactoryFromArrayFuzzer",
            "char",
            "CharIla",
            null,
            "CharIlaFactoryFromArray",
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
                + "        \"expected=\" + (int) expected[expectedIndex]\n"
                + "            + \", actual=\" + (int) actual[actualIndex]);\n"
                + "}"),

        definition(
            "doubleilaf",
            "DoubleIlaFactoryFromArrayFuzzer",
            "double",
            "DoubleIla",
            null,
            "DoubleIlaFactoryFromArray",
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
                + "        + Long.toHexString(expectedBits)\n"
                + "        + \", actualBits=\" + Long.toHexString(actualBits));\n"
                + "}"),

        definition(
            "floatilaf",
            "FloatIlaFactoryFromArrayFuzzer",
            "float",
            "FloatIla",
            null,
            "FloatIlaFactoryFromArray",
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
                + "        + Integer.toHexString(expectedBits)\n"
                + "        + \", actualBits=\" + Integer.toHexString(actualBits));\n"
                + "}"),

        definition(
            "intilaf",
            "IntIlaFactoryFromArrayFuzzer",
            "int",
            "IntIla",
            null,
            "IntIlaFactoryFromArray",
            "array -> IntIlaFactoryFromArray.create(array).create()",
            "IntIla::length",
            "IntIla::get",
            "for (int i = 0; i < array.length; i++) {\n"
                + "    array[i] = i * 0x9e3779b9 ^ 0x12345678;\n"
                + "}",
            "if (expected[expectedIndex] != actual[actualIndex]) {\n"
                + "    throw new AssertionError(\n"
                + "        \"expected=\" + expected[expectedIndex] + \", actual=\" + actual[actualIndex]);\n"
                + "}"),

        definition(
            "longilaf",
            "LongIlaFactoryFromArrayFuzzer",
            "long",
            "LongIla",
            null,
            "LongIlaFactoryFromArray",
            "array -> LongIlaFactoryFromArray.create(array).create()",
            "LongIla::length",
            "LongIla::get",
            "for (int i = 0; i < array.length; i++) {\n"
                + "    array[i] = 0x123456789ABCDEFL ^ ((long) i * 0x100000001L);\n"
                + "}",
            "if (expected[expectedIndex] != actual[actualIndex]) {\n"
                + "    throw new AssertionError(\n"
                + "        \"expected=\" + expected[expectedIndex] + \", actual=\" + actual[actualIndex]);\n"
                + "}"),

        definition(
            "objectilaf",
            "ObjectIlaFactoryFromArrayFuzzer",
            "String",
            "ObjectIla",
            "String",
            "ObjectIlaFactoryFromArray",
            "array -> ObjectIlaFactoryFromArray.<String>create(array).create()",
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
                + "            array[i] = \"value-\" + i + \"-distinct\";\n"
                + "            break;\n"
                + "        default:\n"
                + "            array[i] = String.valueOf(Integer.MIN_VALUE + i);\n"
                + "            break;\n"
                + "    }\n"
                + "}",
            "String expectedValue = expected[expectedIndex];\n"
                + "String actualValue = actual[actualIndex];\n"
                + "if (expectedValue == null ? actualValue != null : !expectedValue.equals(actualValue)) {\n"
                + "    throw new AssertionError(\"expected=\" + expectedValue + \", actual=\" + actualValue);\n"
                + "}"),

        definition(
            "shortilaf",
            "ShortIlaFactoryFromArrayFuzzer",
            "short",
            "ShortIla",
            null,
            "ShortIlaFactoryFromArray",
            "array -> ShortIlaFactoryFromArray.create(array).create()",
            "ShortIla::length",
            "ShortIla::get",
            "for (int i = 0; i < array.length; i++) {\n"
                + "    array[i] = (short) (i * 7919 + 12345);\n"
                + "}",
            "if (expected[expectedIndex] != actual[actualIndex]) {\n"
                + "    throw new AssertionError(\n"
                + "        \"expected=\" + expected[expectedIndex] + \", actual=\" + actual[actualIndex]);\n"
                + "}"));
  }

  private static Map<String, Object> definition(
      final String typePackage,
      final String className,
      final String arrayType,
      final String ilaType,
      final String generic,
      final String factoryName,
      final String createExpression,
      final String lengthExpression,
      final String getExpression,
      final String initialize,
      final String assertElementEquals) {

    final Map<String, Object> model = new HashMap<>();

    model.put(
        "package",
        "tfw.immutable.ilaf." + typePackage);
    model.put("className", className);
    model.put("arrayType", arrayType);
    model.put("ilaPackage", typePackage.replace("ilaf", "ila"));
    model.put("ilaType", ilaType);

    if (generic != null) {
      model.put("generic", generic);
    }

    model.put("factoryName", factoryName);
    model.put("createExpression", createExpression);
    model.put("lengthExpression", lengthExpression);
    model.put("getExpression", getExpression);
    model.put("initialize", initialize);
    model.put("assertElementEquals", assertElementEquals);

    return model;
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
