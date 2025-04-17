FROM jenkins/jenkins:lts
USER root

# 필수 패키지 설치
RUN apt-get update && \
    apt-get -y install apt-transport-https \
    ca-certificates \
    curl \
    gnupg2 \
    software-properties-common

# Docker GPG 키 추가 및 리포지토리 설정
RUN curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg | apt-key add - && \
    add-apt-repository \
      "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

# Docker 설치
RUN apt-get update && \
    apt-get -y install docker.io

# Docker Compose 설치
RUN curl -L "https://github.com/docker/compose/releases/download/v2.34.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && \
    chmod +x /usr/local/bin/docker-compose

# Docker 그룹 추가 및 Jenkins 사용자 그룹에 추가
RUN groupadd -f docker && \
    usermod -aG docker jenkins

# Jenkins 사용자로 돌아가기
USER jenkins
