# Building and Running TFW Fuzzers

This guide describes how to build and run a TFW fuzzer locally using Docker and Jazzer. The build process uses the OSS-Fuzz JVM Docker image, which supplies the Jazzer runtime.

## 1. Verify Docker

Make sure Docker is installed and its daemon is running.

Check the installation:

```bash
docker --version
```

Verify that Docker can run containers, then download the OSS-Fuzz JVM image:

```bash
docker pull gcr.io/oss-fuzz-base/base-builder-jvm
```

## 2. Check the fuzzing environment

Verify that the Docker image provides Java and the Jazzer components.

Check the Java version:

```bash
docker run --rm gcr.io/oss-fuzz-base/base-builder-jvm java -version
```

Check that the Jazzer API location and runtime files are available:

```bash
docker run --rm gcr.io/oss-fuzz-base/base-builder-jvm \
  bash -c 'echo "JAZZER_API_PATH=$JAZZER_API_PATH"; \
           which jazzer_driver; \
           ls -l "$JAZZER_API_PATH"'
```

If you need to inspect the installed Jazzer files, use:

```bash
docker run --rm gcr.io/oss-fuzz-base/base-builder-jvm \
  bash -c 'find /usr/local -name "*jazzer*" -type f \
           -o -name "jazzer_driver" 2>/dev/null | sort'
```

## 3. Build TFW

From the root of the TFW repository, build the project and generate the main TFW JAR using the project's Maven build process.

The JAR must be available under `target/`. Use the main JAR, not the sources or Javadoc JAR.

## 4. Prepare the local fuzzing directories

From the TFW repository root, create the directories used by the local build:

```bash
mkdir -p out work
```

Copy the main TFW JAR into the expected location. Replace the version in the example with the version of the JAR produced by your build:

```bash
cp target/tfw-<version>-SNAPSHOT.jar out/tfw.jar
```

For example, if the build produced `target/tfw-2026.16-SNAPSHOT.jar`, use:

```bash
cp target/tfw-2026.16-SNAPSHOT.jar out/tfw.jar
```

Verify the copied file:

```bash
ls -lh out/tfw.jar
```

## 5. Compile the fuzzers

Run the build script from the TFW repository root:

```bash
./scripts/compile_fuzzers.sh
```

The script starts the OSS-Fuzz JVM container, compiles the Java fuzzing sources against the TFW JAR and Jazzer API, copies the required Jazzer runtime files, and creates an executable wrapper for each discovered fuzzer.

The resulting fuzzer executables are placed in `out/`. The script discovers fuzzer source files by their `*Fuzzer.java` names, so the list of fuzzers does not need to be maintained manually.

See [`scripts/compile_fuzzers.sh`](../../scripts/compile_fuzzers.sh) for the implementation.

## 6. Run a fuzzer

Run an individual fuzzer by invoking its executable in `out/`. For example:

```bash
./out/IntIlaFactoryAddFuzzer -runs=10000
```

The `-runs=10000` option limits the run to 10,000 fuzzing inputs. You can substitute another generated fuzzer executable to run a different target.

A successful smoke test completes the requested number of runs without reporting a crash or assertion failure. Jazzer also reports statistics such as coverage, features, corpus size, and execution throughput.

## 7. Run all fuzzers

To perform a short smoke test of every generated fuzzer, run:

```bash
for fuzzer in ./out/*Fuzzer; do
    "$fuzzer" -runs=10000
done
```

Review each run's output. If a fuzzer reports a failure, investigate it rather than treating the overall sequence as successful.

## Keeping these instructions current

The local build procedure depends on the Docker image and the build script. When the procedure changes, update this guide to match the actual script and the commands that have been verified to work.
