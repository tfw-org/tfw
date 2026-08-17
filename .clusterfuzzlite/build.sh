#!/bin/bash -eu

# Build the project using JDK 21 if available, but produce Java 17 bytecode.
#
# Error Prone can still run under JDK 21; --release 17 controls the
# bytecode/API level produced by javac.
export JAVA_HOME="${JAVA_HOME:-/usr/lib/jvm/java-21-openjdk-amd64}"
export PATH="$JAVA_HOME/bin:$PATH"

mvn -DskipTests \
    -Dmaven.compiler.release=17 \
    -Dmaven.compiler.source=17 \
    -Dmaven.compiler.target=17 \
    package

# Find the project JAR produced by Maven.
PROJECT_JAR="$(find target -maxdepth 1 -type f -name 'tfw-*.jar' ! -name '*sources.jar' ! -name '*javadoc.jar' | head -n 1)"

if [ -z "$PROJECT_JAR" ]; then
    echo "ERROR: Could not find the TFW project JAR."
    exit 1
fi

cp "$PROJECT_JAR" "$OUT/tfw.jar"

PROJECT_JARS="tfw.jar"

# Classpath used to compile the fuzz target.
BUILD_CLASSPATH="$(echo "$PROJECT_JARS" | xargs printf -- "$OUT/%s:")$JAZZER_API_PATH"

# Classpath used when Jazzer executes the fuzz target.
RUNTIME_CLASSPATH="$(echo "$PROJECT_JARS" | xargs printf -- '\$this_dir/%s:')\$this_dir"

for fuzzer in $(find "$SRC" -name '*Fuzzer.java'); do
    fuzzer_basename="$(basename -s .java "$fuzzer")"

    echo "Building fuzzer: $fuzzer_basename"

    # Compile the fuzz target as Java 17 bytecode.
    javac \
        --release 17 \
        -cp "$BUILD_CLASSPATH" \
        -d "$OUT" \
        "$fuzzer"

    # Create the executable Jazzer wrapper in $OUT.
    cat > "$OUT/$fuzzer_basename" <<EOF
#!/bin/bash

this_dir=\$(dirname "\$0")

if [[ "\$@" =~ (^| )-runs=[0-9]+($| ) ]]; then
    mem_settings='-Xmx1900m:-Xss900k'
else
    mem_settings='-Xmx2048m:-Xss1024k'
fi

LD_LIBRARY_PATH="\$JVM_LD_LIBRARY_PATH:\$this_dir" \
"\$this_dir/jazzer_driver" \
    --agent_path="\$this_dir/jazzer_agent_deploy.jar" \
    --cp="$RUNTIME_CLASSPATH" \
    --target_class="$fuzzer_basename" \
    --jvm_args="\$mem_settings:-Djava.awt.headless=true" \
    "\$@"
EOF

    chmod +x "$OUT/$fuzzer_basename"
done

echo "Contents of \$OUT:"
ls -la "$OUT"