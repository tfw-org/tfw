package tfw.build;

import com.google.common.flogger.FluentLogger;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SearchAndReplace {
    private static final FluentLogger LOGGER = FluentLogger.forEnclosingClass();

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }

    private SearchAndReplace() {}

    public static void main(final String[] args) throws Exception {
        if (args.length < 1) {
            LOGGER.atSevere().log("Usage: SearchAndReplace <template/mapping root directory>");

            System.exit(-1);
        }

        for (int a = 0; a < args.length; a++) {
            List<Path> templateList;
            try (Stream<Path> paths =
                    Files.find(Paths.get(args[a]), Integer.MAX_VALUE, (p, basicFileAttributes) -> p.getFileName()
                            .toString()
                            .endsWith(".template")); ) {
                templateList = paths.collect(Collectors.toList());
            }

            for (final Path p : templateList) {
                LOGGER.atInfo().log("Template Path =%s", p);

                final String templateString = new String(Files.readAllBytes(p), StandardCharsets.UTF_8);
                final String[] mappingTemplate = templateString.split("\\R", 2);

                LOGGER.atInfo().log("  mapping = %s", mappingTemplate[0]);
                LOGGER.atFine().log("  template.l = %n%s", mappingTemplate[1].length());

                final int commentIndex = mappingTemplate[0].indexOf("//");

                if (commentIndex != 0) {
                    LOGGER.atSevere().log("Mapping comment not at beginning of first line!");

                    System.exit(-1);
                }

                final String mapping = mappingTemplate[0].substring(2).trim();

                LOGGER.atFine().log("  mapping = %s", mapping);

                final String[] mappings = mapping.split(",");

                LOGGER.atInfo().log("  mappings = %s", mappings.length);

                for (int i = 0; i < mappings.length; i++) {
                    final Path mappingPath = Paths.get(p.getParent().toString(), mappings[i] + ".mapping");

                    LOGGER.atFine().log("  mappingPath = %s", mappingPath);

                    final String mappingString = new String(Files.readAllBytes(mappingPath), StandardCharsets.UTF_8);

                    LOGGER.atFine().log("  mappingString = %n%s", mappingString);

                    final Properties properties = new Properties();
                    properties.load(new StringReader(mappingString));

                    String template = mappingTemplate[1];
                    for (final Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
                        final String propertyName = (String) e.nextElement();

                        template = template.replaceAll(propertyName, properties.getProperty(propertyName));
                    }
                    template = template + "// AUTO GENERATED FROM TEMPLATE\n";

                    // DELETE THIS!!!
                    template = template.replace("\r\n", System.lineSeparator());

                    LOGGER.atFine().log("  template = %n%s", template);

                    final Path outputPath = Paths.get(
                            p.getParent().toString().replace("template", "java"),
                            mappings[i],
                            p.getName(p.getNameCount() - 1)
                                    .toString()
                                    .replace("template", "java")
                                    .replace("__", properties.getProperty("%%NAME%%"))
                                    .replaceAll("\\.\\..+\\.", "."));

                    LOGGER.atFine().log("  outputPath = %s", outputPath);

                    if (outputPath.toFile().exists()) {
                        final String originalFileString =
                                new String(Files.readAllBytes(outputPath), StandardCharsets.UTF_8);

                        if (originalFileString.equals(template)) {
                            LOGGER.atInfo().log("  same as %s", outputPath);
                        } else {
                            Files.write(outputPath, template.getBytes(StandardCharsets.UTF_8));

                            LOGGER.atInfo().log("  writing %s", outputPath);
                        }
                    } else {
                        Files.write(outputPath, template.getBytes(StandardCharsets.UTF_8));

                        LOGGER.atInfo().log("  writing %s", outputPath);
                    }
                }
            }
        }
    }
}
