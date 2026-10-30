#!/bin/bash

mkdir -p "$PWD/work" "$PWD/out"

# The build uses shared work and output directories, so serialize invocations.
flock "$PWD/work/compile_fuzzers.lock" docker run --rm \
    -v "$PWD":/work/tfw \
    -v "$PWD/out":/work/out \
    -v "$PWD/work":/work/work \
    -e SRC=/work \
    -e OUT=/work/out \
    -e WORK=/work/work \
    -e JAZZER_API_PATH=/usr/local/lib/jazzer_api_deploy.jar \
    -w /work/tfw \
    gcr.io/oss-fuzz-base/base-builder-jvm \
    bash -c '
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
