#!/bin/bash -eu

# Build the project using JDK 21 if available, but produce Java 17 bytecode.

export JAVA_HOME="${JAVA_HOME:-/usr/lib/jvm/java-21-openjdk-amd64}"
export PATH="$JAVA_HOME/bin:$PATH"

mvn -DskipTests \
    -Dmaven.compiler.release=17 \
    -Dmaven.compiler.source=17 \
    -Dmaven.compiler.target=17 \
    package


# Find the project JAR produced by Maven.

PROJECT_JAR="$(
    find target \
        -maxdepth 1 \
        -type f \
        -name 'tfw-*.jar' \
        ! -name '*sources.jar' \
        ! -name '*javadoc.jar' \
        | head -n 1
)"

if [ -z "$PROJECT_JAR" ]; then
    echo "ERROR: Could not find the TFW project JAR."
    exit 1
fi

cp "$PROJECT_JAR" "$OUT/tfw.jar"

PROJECT_JARS="tfw.jar"


# Classpath used to compile the fuzz targets.
#
# tfw.jar contains the production classes.
# JAZZER_API_PATH contains FuzzedDataProvider and the other Jazzer API
# classes.  The fuzzing sources are intentionally not part of Maven's
# production compilation.

BUILD_CLASSPATH="$(
    echo "$PROJECT_JARS" |
        xargs printf -- "$OUT/%s:"
)$JAZZER_API_PATH"


# Classpath used when Jazzer executes the fuzz target.
#
# The fuzz classes are copied into $OUT preserving their package
# hierarchy, so $this_dir is sufficient to find them.

RUNTIME_CLASSPATH="$(
    echo "$PROJECT_JARS" |
        xargs printf -- "\$this_dir/%s:"
):\$this_dir"


# Find all Java sources under src/fuzz.
#
# This includes both:
#
#   shared fuzzing support classes
#
# and:
#
#   *Fuzzer.java classes
#
# Everything is compiled together because the individual fuzz targets
# depend on the shared classes.

FUZZ_SRC="$SRC/tfw/src/fuzz"

FUZZ_CLASSES="$WORK/fuzz-classes"

rm -rf "$FUZZ_CLASSES"
mkdir -p "$FUZZ_CLASSES"

mapfile -t FUZZ_SOURCES < <(
    find "$FUZZ_SRC" \
        -type f \
        -name '*.java' \
        -print
)

if [ "${#FUZZ_SOURCES[@]}" -eq 0 ]; then
    echo "ERROR: No fuzz source files found under $FUZZ_SRC"
    exit 1
fi

echo "Compiling ${#FUZZ_SOURCES[@]} fuzz source files."

javac \
    --release 17 \
    -cp "$BUILD_CLASSPATH" \
    -d "$FUZZ_CLASSES" \
    "${FUZZ_SOURCES[@]}"


# Copy the compiled fuzz classes into $OUT.
#
# The directory hierarchy is preserved, so for example:
#
#   tfw.immutable.ilaf.longilaf.LongIlaFactoryFromArrayFuzzer
#
# is stored as:
#
#   $OUT/tfw/immutable/ilaf/longilaf/
#       LongIlaFactoryFromArrayFuzzer.class

cp -R "$FUZZ_CLASSES"/. "$OUT/"


# Find the actual fuzz target source files.
#
# These are the files for which we create executable Jazzer wrappers.
# Shared fuzzing support classes do not get wrappers.

mapfile -t FUZZERS < <(
    find "$FUZZ_SRC" \
        -type f \
        -name '*Fuzzer.java' \
        -print
)

if [ "${#FUZZERS[@]}" -eq 0 ]; then
    echo "ERROR: No fuzz targets found under $FUZZ_SRC"
    exit 1
fi


# Create one executable Jazzer wrapper for each fuzz target.

for fuzzer in "${FUZZERS[@]}"; do

    # Get the path relative to src/fuzz.
    #
    # Example:
    #
    #   tfw/immutable/ilaf/longilaf/
    #       LongIlaFactoryFromArrayFuzzer.java

    relative="${fuzzer#"$FUZZ_SRC"/}"


    # Convert the source path into the fully-qualified Java class name.
    #
    # Example:
    #
    #   tfw/immutable/ilaf/longilaf/
    #       LongIlaFactoryFromArrayFuzzer.java
    #
    # becomes:
    #
    #   tfw.immutable.ilaf.longilaf.LongIlaFactoryFromArrayFuzzer

    class_name="${relative%.java}"
    class_name="${class_name//\//.}"


    # The executable itself uses the simple class name.
    #
    # Example:
    #
    #   LongIlaFactoryFromArrayFuzzer

    fuzzer_basename="$(basename -s .java "$fuzzer")"

    echo "Building fuzzer: $class_name"


    # Create the executable Jazzer wrapper in $OUT.

    cat > "$OUT/$fuzzer_basename" <<EOF
#!/bin/bash

# LLVMFuzzerTestOneInput for fuzzer detection.

this_dir=\$(dirname "\$0")

if [[ "\$@" =~ (^| )-runs=[0-9]+(\$| ) ]]; then
    mem_settings='-Xmx1900m:-Xss900k'
else
    mem_settings='-Xmx2048m:-Xss1024k'
fi

LD_LIBRARY_PATH="\$JVM_LD_LIBRARY_PATH":\$this_dir \
\$this_dir/jazzer_driver \
    --agent_path=\$this_dir/jazzer_agent_deploy.jar \
    --cp=$RUNTIME_CLASSPATH \
    --target_class=$class_name \
    --jvm_args="\$mem_settings:-Djava.awt.headless=true" \
    "\$@"
EOF

    chmod +x "$OUT/$fuzzer_basename"

done


echo "Contents of \$OUT:"
ls -la "$OUT"
