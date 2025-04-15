FROM ubuntu:24.04


RUN apt-get update && apt-get install -y \
    software-properties-common \
    curl \
    wget \
    git \
    unzip \
    zip \
    gnupg2 \
    ca-certificates \
    && rm -rf /var/lib/apt/lists/*


RUN add-apt-repository ppa:openjdk-r/ppa && \
    apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"


RUN add-apt-repository ppa:deadsnakes/ppa && \
    apt-get update && \
    apt-get install -y python3.10 python3.10-venv python3.10-distutils && \
    ln -sf /usr/bin/python3.10 /usr/bin/python3 && \
    rm -rf /var/lib/apt/lists/*


ENV KOTLIN_VERSION=1.9.23

RUN curl -s https://get.sdkman.io | bash && \
    bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install kotlin $KOTLIN_VERSION"

ENV PATH="/root/.sdkman/candidates/kotlin/current/bin:$PATH"


RUN java -version && python3 --version && kotlinc -version
