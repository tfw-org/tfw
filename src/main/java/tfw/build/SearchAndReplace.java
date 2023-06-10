package tfw.build;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SearchAndReplace {
    private static final Logger LOGGER;
    private static final String REGEX = "\\s*//\\s*([\\w+\\,?]+)";
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private static final Matcher MATCHER = PATTERN.matcher("");

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOGGER = LoggerFactory.getLogger(SearchAndReplace.class);
    }

    private SearchAndReplace() {}

    public static void main(final String[] args) throws Exception {
        if (args.length < 1) {
            LOGGER.error("Usage: SearchAndReplace <template/mapping root directory>");

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
                LOGGER.info("Template Path ={}", p);

                final String templateString = new String(Files.readAllBytes(p), StandardCharsets.UTF_8);
                final String[] mappingTemplate = templateString.split("\\R", 2);

                LOGGER.info("  mapping = {}", mappingTemplate[0]);
                LOGGER.debug("  template.l = \n{}", mappingTemplate[1].length());

                MATCHER.reset(mappingTemplate[0]);

                final boolean mappingFound = MATCHER.find();
                final String mapping = MATCHER.group(1);

                LOGGER.debug("  mappingFound = {}", mappingFound);
                LOGGER.debug("  mapping = {}", mapping);

                final String[] mappings = mapping.split(",");

                LOGGER.info("  mappings = {}", mappings.length);

                for (int i = 0; i < mappings.length; i++) {
                    final Path mappingPath = Paths.get(p.getParent().toString(), mappings[i] + ".mapping");

                    LOGGER.debug("  mappingPath = {}", mappingPath);

                    final String mappingString = new String(Files.readAllBytes(mappingPath), StandardCharsets.UTF_8);

                    LOGGER.debug("  mappingString = \n{}", mappingString);

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

                    LOGGER.debug("  template = \n{}", template);

                    final Path outputPath = Paths.get(
                            p.getParent().toString().replace("template", "java"),
                            mappings[i],
                            p.getName(p.getNameCount() - 1)
                                    .toString()
                                    .replace("template", "java")
                                    .replace("__", properties.getProperty("%%NAME%%"))
                                    .replaceAll("\\.\\..+\\.", "."));

                    LOGGER.debug("  outputPath = {}", outputPath);

                    if (outputPath.toFile().exists()) {
                        final String originalFileString =
                                new String(Files.readAllBytes(outputPath), StandardCharsets.UTF_8);

                        if (originalFileString.equals(template)) {
                            LOGGER.info("  same as {}", outputPath);
                        } else {
                            Files.write(outputPath, template.getBytes(StandardCharsets.UTF_8));

                            LOGGER.info("  writing {}", outputPath);
                        }
                    } else {
                        Files.write(outputPath, template.getBytes(StandardCharsets.UTF_8));

                        LOGGER.info("  writing {}", outputPath);
                    }
                }
            }
        }
    }
}
