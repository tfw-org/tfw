# OSS-Fuzz JVM base image is intentionally pinned by digest.
#
# To update the pinned digest:
#
#   docker pull gcr.io/oss-fuzz-base/base-builder-jvm:latest
#
#   docker image inspect gcr.io/oss-fuzz-base/base-builder-jvm:latest \
#     --format '{{range .RepoDigests}}{{println .}}{{end}}'
#
# Find the line beginning with:
#   gcr.io/oss-fuzz-base/base-builder-jvm@
#
# and replace the digest below with the value after '@'.
FROM gcr.io/oss-fuzz-base/base-builder-jvm@sha256:6fd14efcb9da966372f583ddd3f2a2346adf5d16cf931240b2eadfa96aedcfdd

RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        openjdk-21-jdk \
        maven && \
    rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH=${JAVA_HOME}/bin:${PATH}

RUN java -version && \
    javac -version && \
    mvn -version

COPY . $SRC/tfw
WORKDIR $SRC/tfw
COPY .clusterfuzzlite/build.sh $SRC/
