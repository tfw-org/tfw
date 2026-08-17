FROM gcr.io/oss-fuzz-base/base-builder-jvm

RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        maven \
        openjdk-21-jdk && \
    java -version && \
    mvn -version && \
    rm -rf /var/lib/apt/lists/*

COPY . $SRC/tfw

WORKDIR $SRC/tfw

COPY .clusterfuzzlite/build.sh $SRC/build.sh
