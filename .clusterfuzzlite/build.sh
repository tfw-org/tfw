#!/bin/bash
set -eux

cd "$SRC/tfw"

# Build the project.
mvn -B -Dmaven.test.skip=true package

# Get the Maven project version.
CURRENT_VERSION=$(mvn \
    org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate \
    -Dexpression=project.version \
    -q \
    -DforceStdout)

# Copy the project JAR to $OUT using a stable name.
cp "target/tfw-${CURRENT_VERSION}.jar" "$OUT/tfw.jar"

# Copy runtime dependencies.
mvn -B dependency:copy-dependencies \
    -DincludeScope=runtime \
    -DoutputDirectory="$OUT/deps"

# Project JARs used by the fuzzers.
PROJECT_JARS="tfw.jar"

# Build-time classpath: project JAR + Jazzer API.
BUILD_CLASSPATH=$(echo "$PROJECT_JARS" | \
    xargs printf -- "$OUT/%s:")"$JAZZER_API_PATH"

# Build every *Fuzzer.java file.
for fuzzer in $(find "$SRC" -name '*Fuzzer.java'); do
    fuzzer_basename=$(basename -s .java "$fuzzer")

    # Compile the fuzz target into $OUT.
    javac \
        -cp "$BUILD_CLASSPATH" \
        -d "$OUT" \
        "$fuzzer"

    # Create the executable Jazzer launcher.
    cat > "$OUT/$fuzzer_basename" <<EOF
#!/bin/sh

# LLVMFuzzerTestOneInput for fuzzer detection.
this_dir=\$(dirname "\$0")

LD_LIBRARY_PATH="\$JVM_LD_LIBRARY_PATH":\$this_dir \
\$this_dir/jazzer_driver \
    --agent_path=\$this_dir/jazzer_agent_deploy.jar \
    --cp=\$this_dir/tfw.jar:\$this_dir/deps/*:\$this_dir \
    --target_class=$fuzzer_basename \
    --jvm_args="-Xmx2048m:-Djava.awt.headless=true" \
    "\$@"
EOF

    chmod +x "$OUT/$fuzzer_basename"
done