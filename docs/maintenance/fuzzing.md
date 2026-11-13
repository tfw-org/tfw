# Building and Running TFW Fuzzers

This guide describes how to build and run a TFW fuzzer locally using Docker and Jazzer. The build process uses the OSS-Fuzz JVM Docker image, which supplies the Jazzer runtime.

## 1. Configure AMD64 emulation on ARM64 systems

The OSS-Fuzz JVM Docker image used by the TFW fuzzer build process targets the AMD64 architecture. On ARM64 systems, such as a Raspberry Pi running a 64-bit operating system, Docker requires AMD64 emulation to run this image.

Install and register the AMD64 emulator using:

```bash
docker run --privileged --rm tonistiigi/binfmt --install amd64
```

This registers QEMU-based AMD64 emulation with the host operating system. The registration is maintained by the running kernel and is lost when the system reboots. Run the command again after each reboot before building the fuzzers.

To verify that AMD64 emulation is working, run:

```bash
docker run --rm --platform linux/amd64 \
    gcr.io/oss-fuzz-base/base-builder-jvm uname -m
```

The expected output is:

```text
x86_64
```

Once this succeeds, the fuzzer build script can be run normally.

**Note:** Running an AMD64 container under emulation can be significantly slower than running it on an AMD64 host. This section is only necessary on ARM64 systems; AMD64 systems do not require additional emulation setup.

## 2. Verify Docker

Make sure Docker is installed and its daemon is running.

Check the installation:

```bash
docker --version
```

Verify that Docker can run containers, then download the OSS-Fuzz JVM image:

```bash
docker pull gcr.io/oss-fuzz-base/base-builder-jvm
```

## 3. Check the fuzzing environment

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

## 4. Build TFW

From the root of the TFW repository, build the project and generate the main TFW JAR using the project's Maven build process.

The JAR must be available under `target/`. Use the main JAR, not the sources or Javadoc JAR.

## 5. Prepare the local fuzzing directories

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

## 6. Compile the fuzzers

Run the build script from the TFW repository root:

```bash
./bin/compile_fuzzers.sh
```

The script starts the OSS-Fuzz JVM container, compiles the Java fuzzing sources against the TFW JAR and Jazzer API, copies the required Jazzer runtime files, and creates an executable wrapper for each discovered fuzzer.

The resulting fuzzer executables are placed in `out/`. The script discovers fuzzer source files by their `*Fuzzer.java` names, so the list of fuzzers does not need to be maintained manually.

See [`bin/compile_fuzzers.sh`](../../bin/compile_fuzzers.sh) for the implementation.

## 7. Run a fuzzer

Run an individual fuzzer by invoking its executable in `out/`. For example:

```bash
./out/IntIlaFactoryAddFuzzer -runs=10000
```

The `-runs=10000` option limits the run to 10,000 fuzzing inputs. You can substitute another generated fuzzer executable to run a different target.

A successful smoke test completes the requested number of runs without reporting a crash or assertion failure. Jazzer also reports statistics such as coverage, features, corpus size, and execution throughput.

## 8. Run all fuzzers

To perform a short smoke test of every generated fuzzer, run:

```bash
for fuzzer in ./out/*Fuzzer; do
    "$fuzzer" -runs=10000
done
```

Review each run's output. If a fuzzer reports a failure, investigate it rather than treating the overall sequence as successful.

## Keeping these instructions current

The local build procedure depends on the Docker image and the build script. When the procedure changes, update this guide to match the actual script and the commands that have been verified to work.
