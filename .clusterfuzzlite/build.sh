#!/bin/bash
set -eux

cd "$SRC/tfw"

# Build TFW without compiling or running its normal test suite.
mvn -B -Dmaven.test.skip=true package

CURRENT_VERSION=$(mvn \
    org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate \
    -Dexpression=project.version \
    -q \
    -DforceStdout)

# Give the TFW JAR a stable name.
cp "target/tfw-${CURRENT_VERSION}.jar" "$OUT/tfw.jar"

# TFW currently has SLF4J as its runtime dependency.
mvn -B dependency:copy-dependencies \
    -DincludeScope=runtime \
    -DoutputDirectory="$OUT/deps"

PROJECT_JARS="tfw.jar"

# Jazzer API is supplied by the ClusterFuzzLite JVM image.
BUILD_CLASSPATH=$(echo "$PROJECT_JARS" | \
    xargs printf -- "$OUT/%s:")"$JAZZER_API_PATH"

# Compile every fuzz target.
for fuzzer in $(find "$SRC" -name '*Fuzzer.java'); do
    fuzzer_basename=$(basename -s .java "$fuzzer")

    javac \
        -cp "$BUILD_CLASSPATH" \
        -d "$OUT" \
        "$fuzzer"

    cat > "$OUT/$fuzzer_basename" <<EOF
#!/bin/sh

this_dir=\$(dirname "\$0")

LD_LIBRARY_PATH="\$JVM_LD_LIBRARY_PATH:\$this_dir" \
"\$this_dir/jazzer_driver" \
    --agent_path="\$this_dir/jazzer_agent_deploy.jar" \
    --cp="\$this_dir/tfw.jar:\$this_dir/deps/*:\$this_dir" \
    --target_class="$fuzzer_basename" \
    --jvm_args="-Xmx2048m -Djava.awt.headless=true" \
    "\$@"
EOF

    chmod +x "$OUT/$fuzzer_basename"
done
