# ================================================================
# builder step used to download and configure py-spark environment
FROM python:3.9.1 as builder

# author informations

LABEL name="Maestros"
LABEL email="temateuroslynf32@gmail.com"

# packages downloading
RUN apt-get install curl wget
RUN pip install pandas

# Connector installation
RUN pip install mysql-connector-python
RUN pip install cassandra-driver
RUN pip install PyHive
RUN pip install psycopg2


# VERSIONS
ENV SPARK_VERSION=3.3.2 \
HADOOP_VERSION=3 \
JAVA_VERSION=11

# SET JAVA ENV VARIABLES
ENV JAVA_HOME="/home/jdk-${JAVA_VERSION}.0.2"
ENV PATH="${JAVA_HOME}/bin/:${PATH}"

# DOWNLOAD JAVA 11 AND INSTALL
RUN DOWNLOAD_URL="https://download.java.net/java/GA/jdk${JAVA_VERSION}/9/GPL/openjdk-${JAVA_VERSION}.0.2_linux-x64_bin.tar.gz" \
    && TMP_DIR="$(mktemp -d)" \
    && curl -fL "${DOWNLOAD_URL}" --output "${TMP_DIR}/openjdk-${JAVA_VERSION}.0.2_linux-x64_bin.tar.gz" \
    && mkdir -p "${JAVA_HOME}" \
    && tar xzf "${TMP_DIR}/openjdk-${JAVA_VERSION}.0.2_linux-x64_bin.tar.gz" -C "${JAVA_HOME}" --strip-components=1 \
    && rm -rf "${TMP_DIR}" \
    && java --version

# DOWNLOAD SPARK AND INSTALL
                        
RUN DOWNLOAD_URL_SPARK="https://dlcdn.apache.org/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz" \
    && wget --no-verbose -O apache-spark.tgz  "${DOWNLOAD_URL_SPARK}"\
    && mkdir -p /home/spark \
    && tar -xf apache-spark.tgz -C /home/spark --strip-components=1 \
    && rm apache-spark.tgz



# download mysql-connector-java
RUN DOWNLOAD_URL="https://download.jar-download.com/cache_jars/mysql/mysql-connector-java/5.1.40/jar_files.zip" \
    && TMP_DIR="$(mktemp -d)" \
    && curl -fL "${DOWNLOAD_URL}" --output "${TMP_DIR}/mysql-connector-java.zip" \
    && unzip "${TMP_DIR}/mysql-connector-java.zip" -d /usr/local/lib/ \
    && rm -rf "${TMP_DIR}" 

# download postgresql-connector-java
RUN DOWNLOAD_URL="https://jdbc.postgresql.org/download/postgresql-42.5.4.jar" \
    && TMP_DIR="$(mktemp -d)" \
    && curl -fL "${DOWNLOAD_URL}" --output /usr/local/lib/postgresql-42.5.4.jar

# SET libs env path
ENV MYSQL_CONNECTOR_JAR="/usr/local/lib/mysql-connector-java-5.1.40.jar"
ENV POSTGRESQL_CONNECTOR_JAR="/usr/local/lib/postgresql-42.5.4.jar"


# SET SPARK ENV VARIABLES
ENV SPARK_HOME="/home/spark"
ENV PATH="${SPARK_HOME}/bin/:${PATH}"

# SET PYSPARK VARIABLES
ENV PYTHONPATH="${SPARK_HOME}/python/:$PYTHONPATH"
ENV PYTHONPATH="${SPARK_HOME}/python/lib/py4j-0.10.9.5-src.zip:$PYTHONPATH"






# ================================================================
# Apache spark cluster environment

# Apache spark environment
FROM builder as apache-spark

WORKDIR ${SPARK_HOME}

ENV SPARK_MASTER_PORT=7070 \
    SPARK_MASTER_WEBUI_PORT=8080 \
    SPARK_LOG_DIR=${SPARK_HOME}/logs \
    SPARK_MASTER_LOG=${SPARK_HOME}/logs/spark-master.out \
    SPARK_WORKER_LOG=${SPARK_HOME}/logs/spark-worker.out \
    SPARK_WORKER_WEBUI_PORT=8080 \
    SPARK_WORKER_PORT=7000 \
    SPARK_MASTER="spark://spark-master:7070" \
    SPARK_WORKLOAD="master"


RUN mkdir -p $SPARK_LOG_DIR && \
    touch $SPARK_MASTER_LOG && \
    touch $SPARK_WORKER_LOG && \
    ln -sf /dev/stdout $SPARK_MASTER_LOG && \
    ln -sf /dev/stdout $SPARK_WORKER_LOG


# run the project
COPY . ${SPARK_HOME}
RUN python main.py

EXPOSE 8080 7070 6066 8088
# ENTRYPOINT ["/bin/bash" ]
CMD ["/bin/bash", "start-spark.sh"]