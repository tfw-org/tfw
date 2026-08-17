FROM gcr.io/oss-fuzz-base/base-builder-jvm

RUN apt-get update && \
    apt-get install -y --no-install-recommends maven && \
    rm -rf /var/lib/apt/lists/*

RUN java -version && \
    javac -version && \
    mvn -version

COPY . $SRC/tfw

WORKDIR $SRC/tfw

COPY .clusterfuzzlite/build.sh $SRC/build.sh
