# Local tfw Fuzzer Build and Run

This documents the complete process used to build and locally run a tfw Jazzer fuzzer, starting with the OSS-Fuzz Docker container.

The example below uses:

```text
IntIlaFactoryAddFuzzer
```

The same procedure can be used for future fuzzers.

---

## 1. Start the OSS-Fuzz JVM Docker container

From the tfw project directory, start the JVM builder image:

```bash
docker run --rm -it \
  -v "$PWD":/work \
  gcr.io/oss-fuzz-base/base-builder-jvm:latest \
  bash
```

This gives us a shell inside the same general JVM environment used by OSS-Fuzz/ClusterFuzzLite.

The container provides Java 17 and the Jazzer runtime components.

### Verify Java

Inside the container:

```bash
java -version
```

The environment used during this test reported:

```text
openjdk version "17.0.16" 2025-07-15
Temurin-17.0.16+8
```

This is useful because the development machine was running Java 25. Running the fuzzer inside the container therefore avoids depending on the host Java installation.

---

## 2. Verify the Jazzer components

Inside the container, the important Jazzer files are:

```text
/usr/local/bin/jazzer_driver
/usr/local/bin/jazzer_agent_deploy.jar
/usr/local/bin/jazzer_junit.jar
/usr/local/lib/jazzer_api_deploy.jar
```

For example:

```bash
ls -l \
  /usr/local/bin/jazzer_driver \
  /usr/local/bin/jazzer_agent_deploy.jar \
  /usr/local/bin/jazzer_junit.jar \
  /usr/local/lib/jazzer_api_deploy.jar
```

The important distinction is that **we did not need to install Jazzer with Maven or separately on the host**. The OSS-Fuzz image already contains the runtime and API needed by the fuzzer.

---

## 3. Locate the tfw source inside the container

The Docker command mounted the current host directory as:

```text
/work
```

So inside the container:

```bash
cd /work
```

Verify:

```bash
ls
```

The tfw project should be visible.

---

## 4. Build/generate the tfw project

Before compiling the fuzzers, run the normal tfw generation/build process on the host or otherwise make sure the generated classes are current.

For the `IntIlaFactoryAddFuzzer`, the important generated class is:

```text
IntIlaFactoryAdd
```

and the project JAR needs to contain the generated tfw classes used by the fuzzer.

The normal Maven build was used to produce:

```text
target/tfw-2026.16-SNAPSHOT.jar
```

---

## 5. Create the local fuzzing output directory

Inside the project:

```bash
mkdir -p out
```

The `out` directory is used for the locally assembled fuzzing environment.

---

## 6. Copy the actual tfw JAR

Copy the main project JAR:

```bash
cp target/tfw-2026.16-SNAPSHOT.jar out/tfw.jar
```

### Important

Do **not** use:

```bash
cp target/tfw-*.jar out/tfw.jar
```

because Maven creates several JARs, including:

```text
tfw-2026.16-SNAPSHOT.jar
tfw-2026.16-SNAPSHOT-sources.jar
tfw-2026.16-SNAPSHOT-javadoc.jar
```

The wildcard therefore matches multiple files.

Use the main JAR explicitly:

```bash
cp target/tfw-2026.16-SNAPSHOT.jar out/tfw.jar
```

Verify:

```bash
ls -lh out/tfw.jar
```

The successful test produced approximately a 1.4 MB JAR.

---

## 7. Compile the fuzzing Java sources

The fuzzers use the Jazzer API supplied by the Docker image.

The API JAR is:

```text
/usr/local/lib/jazzer_api_deploy.jar
```

The fuzzing source tree is:

```text
src/fuzz/java
```

Compile the fuzzing sources against:

```text
out/tfw.jar
```

and:

```text
/usr/local/lib/jazzer_api_deploy.jar
```

The compilation included all current fuzzers, including:

```text
BooleanIlaFactoryFromArrayFuzzer
ByteIlaFactoryFromArrayFuzzer
CharIlaFactoryFromArrayFuzzer
DoubleIlaFactoryFromArrayFuzzer
FloatIlaFactoryFromArrayFuzzer
IntIlaFactoryAddFuzzer
IntIlaFactoryFromArrayFuzzer
LongIlaFactoryFromArrayFuzzer
ObjectIlaFactoryFromArrayFuzzer
ShortIlaFactoryFromArrayFuzzer
```

The important result is that:

```text
IntIlaFactoryAddFuzzer.class
```

was successfully produced.

---

## 8. Copy the compiled classes and Jazzer runtime files

The locally assembled `out` directory needs:

```text
tfw.jar
```

plus the compiled fuzzer classes and the Jazzer runtime components.

In particular:

```text
jazzer_driver
jazzer_agent_deploy.jar
```

are copied from the OSS-Fuzz image into `out`.

The resulting directory is essentially a self-contained local fuzzing environment.

---

## 9. Create a wrapper for the fuzzer

Jazzer needs to know which Java class contains the fuzzing entry point.

For this fuzzer the target class is:

```text
tfw.immutable.ilaf.intilaf.IntIlaFactoryAddFuzzer
```

The wrapper created in:

```text
out/IntIlaFactoryAddFuzzer
```

invokes the Jazzer driver with that target class.

This allows the fuzzer to be run simply as:

```bash
./out/IntIlaFactoryAddFuzzer
```

rather than having to construct the complete Jazzer command each time.

---

## 10. Run a small local smoke test

Before doing a longer fuzzing run, run:

```bash
./out/IntIlaFactoryAddFuzzer -runs=10000
```

This tells Jazzer to execute exactly 10,000 fuzzing inputs.

The successful run ended with:

```text
#10000  DONE   cov: 153 ft: 369 corp: 44/384b lim: 53 exec/s: 10000 rss: 793Mb

Done 10000 runs in 1 second(s)
```

There were no crashes or assertion failures.

---

## 11. Check that the fuzzer is actually reaching the implementation

One useful part of the output was Jazzer's instrumentation list.

The run instrumented classes including:

```text
IntIlaFactoryAdd
IntIla
IntIlaAdd
IntIlaSegment
IntIlaIterator
ImmutableLongArrayUtil
AbstractIntIla
AbstractIla
ClosedManager
```

This confirms that the test was exercising the actual `IntIlaFactoryAdd`/`IntIlaAdd` implementation rather than merely running the fuzzer's own code.

The run reached:

```text
cov: 153
ft: 369
corp: 44
```

The exact numbers will naturally vary with changes to the code and fuzzing environment.

---

## 12. Ignore the non-fuzzer runtime warnings

The local run produced warnings related to:

* Java class sharing
* deprecated `sun.misc.Unsafe`
* `System.load`
* Java native-access restrictions

These are associated with the Jazzer/JVM environment.

They did not indicate a failure of `IntIlaFactoryAddFuzzer`.

The important indicators were:

```text
Done 10000 runs
```

and the absence of a crash or assertion failure.

---

## 13. What the fuzzer itself checks

`IntIlaFactoryAddFuzzer` generates:

* a random source length
* two integer input arrays
* a random destination length
* a random destination offset
* a random ILA start position
* a random requested length

The two input arrays are converted to:

```text
IntIlaFactory
```

instances and then combined with:

```text
IntIlaFactoryAdd.create(leftFactory, rightFactory, 1)
```

The fuzzer verifies:

### Length

The resulting ILA has the expected length.

### Addition

For valid `get()` operations:

```text
result[i] == left[i] + right[i]
```

including values such as:

```text
Integer.MIN_VALUE
Integer.MAX_VALUE
0
-1
1
```

which helps exercise integer overflow behavior.

### Bounds checking

Random valid and invalid `get()` parameters are generated.

Valid operations are checked for the expected result.

Invalid operations must be rejected.

### Destination preservation

Elements of the destination array outside the requested output range must remain unchanged.

### Close behavior

The fuzzer also verifies that:

```text
ila.close()
ila.close()
```

is safe and that subsequent operations fail appropriately.

---

## 14. Test factory argument validation

The next planned improvement to this fuzzer is to explicitly test the factory's argument validation.

These cases should be covered:

```text
leftFactory == null
rightFactory == null
bufferSize < 1
leftFactory.length() != rightFactory.length()
```

The first two are checked directly by:

```text
IntIlaFactoryAdd
```

The latter two are ultimately checked when the resulting factory creates the underlying `IntIlaAdd`.

After adding those checks, rebuild and rerun:

```bash
./out/IntIlaFactoryAddFuzzer -runs=10000
```

---

## 15. Repeat the process for the other factory fuzzers

The first five factories being explored are:

```text
IlaFactoryAdd
IlaFactoryBound
IlaFactoryConcatenate
IlaFactoryDecimate
IlaFactoryDivide
```

The intention is to implement and test these individually before changing `TemplateGenerator`.

This lets us see which patterns genuinely occur across the different factory types.

Only after several real examples exist should we decide whether common fuzzer infrastructure or additional template-generation logic is warranted.

---

# Quick-reference procedure

For future use, the overall sequence is:

```bash
# 1. Start OSS-Fuzz JVM environment
docker run --rm -it \
  -v "$PWD":/work \
  gcr.io/oss-fuzz-base/base-builder-jvm:latest \
  bash

# 2. Inside container
cd /work

# 3. Make sure the project has been generated/built
#    (done through the normal tfw Maven workflow)

# 4. Create output directory
mkdir -p out

# 5. Copy the MAIN project JAR
cp target/tfw-2026.16-SNAPSHOT.jar out/tfw.jar

# 6. Compile the fuzzing sources against:
#    out/tfw.jar
#    /usr/local/lib/jazzer_api_deploy.jar

# 7. Copy the required Jazzer runtime files
#    into out/

# 8. Create the target-class wrapper

# 9. Run the fuzzer
./out/IntIlaFactoryAddFuzzer -runs=10000
```

The key idea is:

```text
tfw Maven build
       ↓
target/tfw-2026.16-SNAPSHOT.jar
       ↓
OSS-Fuzz JVM Docker container
       ↓
compile fuzzers with Jazzer API
       ↓
assemble out/ directory
       ↓
Jazzer wrapper
       ↓
./out/IntIlaFactoryAddFuzzer -runs=10000
```

This is a **local reproduction of the ClusterFuzzLite/Jazzer environment**, intended primarily to catch compilation problems, runtime problems, incorrect fuzzer assumptions, and obvious bugs before pushing the fuzzer to the actual ClusterFuzzLite workflow.




   32  clear
   33  docker pull gcr.io/oss-fuzz-base/base-builder-jvm
   34  docker run --rm gcr.io/oss-fuzz-base/base-builder-jvm   bash -c 'echo "JAZZER_API_PATH=$JAZZER_API_PATH"; \
           which jazzer_driver; \
           ls -l "$JAZZER_API_PATH"'
   35  clear
   36  docker run --rm gcr.io/oss-fuzz-base/base-builder-jvm   bash -c 'find /usr/local -name "*jazzer*" -type f -o -name "jazzer_driver" 2>/dev/null | sort'
   37  docker run --rm gcr.io/oss-fuzz-base/base-builder-jvm   java -version
   38  pwd
   39  clear
   40  docker run --rm   -v "$PWD":/work/tfw   -e SRC=/work   -e OUT=/work/out   -e WORK=/work/work   -e JAZZER_API_PATH=/usr/local/lib/jazzer_api_deploy.jar   -w /work/tfw   gcr.io/oss-fuzz-base/base-builder-jvm   bash -c '
    rm -rf /work/out /work/work
    mkdir -p /work/out /work/work
    ./.clusterfuzzlite/build.sh
  '
   41  clear
   42  docker run --rm   -v "$PWD":/work/tfw   -e SRC=/work   -e OUT=/work/out   -e WORK=/work/work   -e JAZZER_API_PATH=/usr/local/lib/jazzer_api_deploy.jar   -w /work/tfw   gcr.io/oss-fuzz-base/base-builder-jvm   bash -c '
    rm -rf /work/out /work/work
    mkdir -p /work/out /work/work
    ./.clusterfuzzlite/build.sh
  '
   43  clear
   44  docker run --rm   -v "$PWD":/work/tfw   -e SRC=/work   -e OUT=/work/out   -e WORK=/work/work   -e JAZZER_API_PATH=/usr/local/lib/jazzer_api_deploy.jar   -w /work/tfw   gcr.io/oss-fuzz-base/base-builder-jvm   bash -c '
    rm -rf /work/out /work/work
    mkdir -p /work/out /work/work
    ./.clusterfuzzlite/build.sh
  '
   45  clear
   46  docker run --rm   -v "$PWD":/work/tfw   -e SRC=/work   -e OUT=/work/out   -e WORK=/work/work   -e JAZZER_API_PATH=/usr/local/lib/jazzer_api_deploy.jar   -w /work/tfw   gcr.io/oss-fuzz-base/base-builder-jvm   bash -c '
    rm -rf /work/out /work/work
    mkdir -p /work/out /work/work
    bash ./.clusterfuzzlite/build.sh
  '
   47  clear
   48  mvn -DskipTests   -Dmaven.compiler.release=17   -Dmaven.compiler.source=17   -Dmaven.compiler.target=17   package
   49  rm -rf out work
   50  mkdir -p out work
   51  cp target/tfw-*.jar out/tfw.jar
   52  docker run --rm   -v "$PWD":/work/tfw   -v "$PWD/out":/work/out   -v "$PWD/work":/work/work   -e SRC=/work   -e OUT=/work/out   -e WORK=/work/work   -e JAZZER_API_PATH=/usr/local/lib/jazzer_api_deploy.jar   -w /work/tfw   gcr.io/oss-fuzz-base/base-builder-jvm   bash -c '
    set -e

    FUZZ_SRC="$SRC/tfw/src/fuzz/java"
    FUZZ_CLASSES="$WORK/fuzz-classes"

    rm -rf "$FUZZ_CLASSES"
    mkdir -p "$FUZZ_CLASSES"

    mapfile -t FUZZ_SOURCES < <(
        find "$FUZZ_SRC" \
            -type f \
            -name "*.java" \
            ! -path "$FUZZ_SRC/build/*" \
            -print
    )

    echo "Compiling ${#FUZZ_SOURCES[@]} fuzz source files."

    javac \
        --release 17 \
        -cp "$OUT/tfw.jar:$JAZZER_API_PATH" \
        -d "$FUZZ_CLASSES" \
        "${FUZZ_SOURCES[@]}"

    cp -R "$FUZZ_CLASSES"/. "$OUT/"

    cp /usr/local/bin/jazzer_driver "$OUT/"
    cp /usr/local/bin/jazzer_agent_deploy.jar "$OUT/"

    mapfile -t FUZZERS < <(
        find "$FUZZ_SRC" \
            -type f \
            -name "*Fuzzer.java" \
            -print
    )

    for fuzzer in "${FUZZERS[@]}"; do
        relative="${fuzzer#"$FUZZ_SRC"/}"
        class_name="${relative%.java}"
        class_name="${class_name//\//.}"
        fuzzer_basename="$(basename -s .java "$fuzzer")"

        echo "Building fuzzer: $class_name"

        cat > "$OUT/$fuzzer_basename" <<EOF
#!/bin/bash

this_dir=\$(dirname "\$0")

"\$this_dir/jazzer_driver" \
    --agent_path=\$this_dir/jazzer_agent_deploy.jar \
    --cp=\$this_dir/tfw.jar:\$this_dir \
    --target_class=$class_name \
    "\$@"
EOF

        chmod +x "$OUT/$fuzzer_basename"
    done

    echo
    echo "Fuzzers:"
    find "$OUT" -maxdepth 1 -type f -name "*Fuzzer" -printf "%f\n" | sort
  '
   53  clear
   54  rm -rf out work
   55  mkdir -p out work
   56  cp target/tfw-*.jar out/tfw.jar
   57  docker run --rm   -v "$PWD":/work/tfw   -v "$PWD/out":/work/out   -v "$PWD/work":/work/work   -e SRC=/work   -e OUT=/work/out   -e WORK=/work/work   -e JAZZER_API_PATH=/usr/local/lib/jazzer_api_deploy.jar   -w /work/tfw   gcr.io/oss-fuzz-base/base-builder-jvm   bash -c '
    set -e

    FUZZ_SRC="$SRC/tfw/src/fuzz/java"
    FUZZ_CLASSES="$WORK/fuzz-classes"

    rm -rf "$FUZZ_CLASSES"
    mkdir -p "$FUZZ_CLASSES"

    mapfile -t FUZZ_SOURCES < <(
        find "$FUZZ_SRC" \
            -type f \
            -name "*.java" \
            ! -path "$FUZZ_SRC/build/*" \
            -print
    )

    echo "Compiling ${#FUZZ_SOURCES[@]} fuzz source files."

    javac \
        --release 17 \
        -cp "$OUT/tfw.jar:$JAZZER_API_PATH" \
        -d "$FUZZ_CLASSES" \
        "${FUZZ_SOURCES[@]}"

    cp -R "$FUZZ_CLASSES"/. "$OUT/"

    cp /usr/local/bin/jazzer_driver "$OUT/"
    cp /usr/local/bin/jazzer_agent_deploy.jar "$OUT/"

    mapfile -t FUZZERS < <(
        find "$FUZZ_SRC" \
            -type f \
            -name "*Fuzzer.java" \
            -print
    )

    for fuzzer in "${FUZZERS[@]}"; do
        relative="${fuzzer#"$FUZZ_SRC"/}"
        class_name="${relative%.java}"
        class_name="${class_name//\//.}"
        fuzzer_basename="$(basename -s .java "$fuzzer")"

        echo "Building fuzzer: $class_name"

        cat > "$OUT/$fuzzer_basename" <<EOF
#!/bin/bash

this_dir=\$(dirname "\$0")

"\$this_dir/jazzer_driver" \
    --agent_path=\$this_dir/jazzer_agent_deploy.jar \
    --cp=\$this_dir/tfw.jar:\$this_dir \
    --target_class=$class_name \
    "\$@"
EOF

        chmod +x "$OUT/$fuzzer_basename"
    done

    echo
    echo "Fuzzers:"
    find "$OUT" -maxdepth 1 -type f -name "*Fuzzer" -printf "%f\n" | sort
  '
   58  clear
   59  ls -lh target/tfw-*.jar
   60  rm -rf out work
   61  mkdir -p out work
   62  cp target/tfw-*.jar out/tfw.jar
   63  ls -lh out/tfw.jar
   64  clear
   65  cp target/tfw-2026.16-SNAPSHOT.jar out/tfw.jar
   66  ls -lh out/tfw.jar
   67  clear
   68  docker run --rm   -v "$PWD":/work/tfw   -v "$PWD/out":/work/out   -v "$PWD/work":/work/work   -e SRC=/work   -e OUT=/work/out   -e WORK=/work/work   -e JAZZER_API_PATH=/usr/local/lib/jazzer_api_deploy.jar   -w /work/tfw   gcr.io/oss-fuzz-base/base-builder-jvm   bash -c '
    set -e

    FUZZ_SRC="$SRC/tfw/src/fuzz/java"
    FUZZ_CLASSES="$WORK/fuzz-classes"

    rm -rf "$FUZZ_CLASSES"
    mkdir -p "$FUZZ_CLASSES"

    mapfile -t FUZZ_SOURCES < <(
        find "$FUZZ_SRC" \
            -type f \
            -name "*.java" \
            ! -path "$FUZZ_SRC/build/*" \
            -print
    )

    echo "Compiling ${#FUZZ_SOURCES[@]} fuzz source files."

    javac \
        --release 17 \
        -cp "$OUT/tfw.jar:$JAZZER_API_PATH" \
        -d "$FUZZ_CLASSES" \
        "${FUZZ_SOURCES[@]}"

    cp -R "$FUZZ_CLASSES"/. "$OUT/"

    cp /usr/local/bin/jazzer_driver "$OUT/"
    cp /usr/local/bin/jazzer_agent_deploy.jar "$OUT/"

    mapfile -t FUZZERS < <(
        find "$FUZZ_SRC" \
            -type f \
            -name "*Fuzzer.java" \
            -print
    )

    for fuzzer in "${FUZZERS[@]}"; do
        relative="${fuzzer#"$FUZZ_SRC"/}"
        class_name="${relative%.java}"
        class_name="${class_name//\//.}"
        fuzzer_basename="$(basename -s .java "$fuzzer")"

        echo "Building fuzzer: $class_name"

        cat > "$OUT/$fuzzer_basename" <<EOF
#!/bin/bash

this_dir=\$(dirname "\$0")

"\$this_dir/jazzer_driver" \
    --agent_path=\$this_dir/jazzer_agent_deploy.jar \
    --cp=\$this_dir/tfw.jar:\$this_dir \
    --target_class=$class_name \
    "\$@"
EOF

        chmod +x "$OUT/$fuzzer_basename"
    done

    echo
    echo "Fuzzers:"
    find "$OUT" -maxdepth 1 -type f -name "*Fuzzer" -printf "%f\n" | sort
  '
   69  clear
   70  ./out/IntIlaFactoryAddFuzzer   -runs=10000
   71  clear
   72  history