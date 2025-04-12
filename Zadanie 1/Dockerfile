FROM ubuntu:latest

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    software-properties-common \
    curl \
    && add-apt-repository ppa:deadsnakes/ppa \
    && apt-get update && apt-get install -y \
    python3.10 \
    python3.10-venv \
    python3.10-dev \
    python3-pip \
    && ln -sf /usr/bin/python3.10 /usr/bin/python3 \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

RUN python3 --version && pip3 --version

CMD [ "python3" ]
