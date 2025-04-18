# CI/CD 초보자 가이드

## 목차
1. [CI/CD란 무엇인가?](#1-cicd란-무엇인가)
2. [CI/CD의 주요 개념](#2-cicd의-주요-개념)
3. [CI/CD 아키텍처](#3-cicd-아키텍처)
4. [CI/CD 파이프라인 흐름](#4-cicd-파이프라인-흐름)
5. [주요 용어 설명](#5-주요-용어-설명)
6. [Docker 이해하기](#6-docker-이해하기)
7. [실제 프로젝트 예시](#7-실제-프로젝트-예시)
8. [자주 묻는 질문](#8-자주-묻는-질문)

## 1. CI/CD란 무엇인가?

### CI (Continuous Integration, 지속적 통합)
개발자들이 코드 변경사항을 자주 통합하는 개발 방식입니다. 코드를 메인 브랜치에 병합할 때마다 자동으로 빌드하고 테스트하여 문제를 빠르게 발견하고 해결할 수 있게 합니다.

**쉽게 말하면**: 여러 개발자가 작업한 코드를 자주 합치고, 합칠 때마다 자동으로 테스트해서 문제가 없는지 확인하는 방식입니다.

### CD (Continuous Delivery/Deployment, 지속적 배포)
- **Continuous Delivery(지속적 전달)**: 코드 변경사항이 테스트를 통과하면 자동으로 배포 가능한 상태로 준비하는 것입니다. 실제 배포는 수동으로 진행합니다.
- **Continuous Deployment(지속적 배포)**: 코드 변경사항이 테스트를 통과하면 자동으로 프로덕션 환경에 배포하는 것입니다.

**쉽게 말하면**: 테스트를 통과한 코드를 자동으로 서버에 배포할 수 있게 준비하거나(Delivery), 아예 자동으로 서버에 배포까지 해버리는(Deployment) 방식입니다.

## 2. CI/CD의 주요 개념

### 자동화 (Automation)
CI/CD의 핵심은 자동화입니다. 코드 통합, 테스트, 빌드, 배포 과정을 자동화하여 수동 작업을 최소화합니다.

### 파이프라인 (Pipeline)
코드가 개발 환경에서 프로덕션 환경까지 이동하는 자동화된 단계들의 집합입니다. 각 단계는 이전 단계가 성공적으로 완료된 후에만 실행됩니다.

### 피드백 루프 (Feedback Loop)
개발자가 코드를 변경한 후 빠르게 결과를 확인할 수 있는 시스템입니다. 문제가 발생하면 즉시 알림을 받아 수정할 수 있습니다.

### 인프라스트럭처 as 코드 (Infrastructure as Code, IaC)
서버, 네트워크, 데이터베이스 등의 인프라를 코드로 정의하고 관리하는 방식입니다. 이를 통해 인프라 구성을 버전 관리하고 자동화할 수 있습니다.

## 3. CI/CD 아키텍처

### 일반적인 CI/CD 아키텍처
```
[개발자] → [버전 관리 시스템] → [CI 서버] → [아티팩트 저장소] → [배포 도구] → [운영 환경]
```

### 우리 프로젝트의 CI/CD 아키텍처
```
[개발자] → [GitHub] → [GitHub Actions(CI)] → [GitHub Container Registry] → [Jenkins(CD)] → [운영 서버]
```

### 주요 구성 요소

1. **버전 관리 시스템 (GitHub)**
   - 코드 변경사항을 추적하고 관리합니다.
   - 여러 개발자의 작업을 통합합니다.

2. **CI 도구 (GitHub Actions)**
   - 코드 변경이 발생할 때마다 자동으로 빌드 및 테스트를 실행합니다.
   - 테스트 결과를 개발자에게 알립니다.
   - 성공적인 빌드 결과물(아티팩트)을 생성합니다.

3. **아티팩트 저장소 (GitHub Container Registry)**
   - 빌드된 애플리케이션(Docker 이미지 등)을 저장합니다.
   - 버전 관리와 배포를 위한 중앙 저장소 역할을 합니다.

4. **CD 도구 (Jenkins)**
   - CI 과정이 성공적으로 완료되면 배포 프로세스를 시작합니다.
   - 다양한 환경(개발, 테스트, 프로덕션)에 애플리케이션을 배포합니다.

5. **컨테이너화 도구 (Docker)**
   - 애플리케이션과 그 의존성을 패키징합니다.
   - 일관된 환경에서 애플리케이션을 실행할 수 있게 합니다.

## 4. CI/CD 파이프라인 흐름

### 1단계: 코드 변경 및 커밋
- 개발자가 코드를 작성하고 GitHub 저장소에 푸시합니다.
- 이 과정에서 코드 리뷰나 풀 리퀘스트를 통해 코드 품질을 검토할 수 있습니다.

### 2단계: 자동 테스트 (CI)
- GitHub Actions가 코드 변경을 감지하고 자동으로 테스트를 실행합니다.
- 단위 테스트, 통합 테스트 등 다양한 테스트를 수행합니다.
- 테스트가 실패하면 개발자에게 알림이 가고, 파이프라인이 중단됩니다.

### 3단계: 빌드 및 패키징 (CI)
- 테스트가 성공하면 GitHub Actions가 애플리케이션을 빌드합니다.
- Docker 이미지를 생성하고 GitHub Container Registry에 푸시합니다.

### 4단계: 배포 트리거 (CI→CD 연결)
- GitHub Actions가 Jenkins에 웹훅을 전송하여 배포 프로세스를 시작합니다.

### 5단계: 배포 (CD)
- Jenkins가 GitHub Container Registry에서 최신 Docker 이미지를 가져옵니다.
- 기존 컨테이너를 중지하고 새 이미지로 컨테이너를 실행합니다.
- 배포 결과를 모니터링하고 성공/실패 여부를 알립니다.

## 5. 주요 용어 설명

### 워크플로우 (Workflow)
자동화된 프로세스의 정의입니다. GitHub Actions에서는 YAML 파일로 워크플로우를 정의합니다.

### 파이프라인 (Pipeline)
CI/CD 과정의 각 단계를 연결한 자동화된 프로세스입니다. Jenkins에서는 Jenkinsfile로 파이프라인을 정의합니다.

### 빌드 (Build)
소스 코드를 실행 가능한 애플리케이션으로 변환하는 과정입니다.

### 아티팩트 (Artifact)
빌드 과정에서 생성된 결과물입니다. JAR 파일, Docker 이미지 등이 해당됩니다.

### 컨테이너 (Container)
애플리케이션과 그 의존성을 포함하는 독립적인 실행 환경입니다. Docker가 대표적인 컨테이너 기술입니다.

### 레지스트리 (Registry)
Docker 이미지와 같은 아티팩트를 저장하고 관리하는 저장소입니다. GitHub Container Registry, Docker Hub 등이 있습니다.

### 웹훅 (Webhook)
특정 이벤트가 발생했을 때 다른 시스템에 HTTP 요청을 보내는 방식입니다. CI와 CD 도구 간의 연동에 사용됩니다.

### 에이전트 (Agent)
CI/CD 작업을 실행하는 서버 또는 컨테이너입니다. Jenkins에서는 마스터-에이전트 구조를 사용합니다.

## 6. Docker 이해하기

### Docker란 무엇인가?
Docker는 애플리케이션을 개발, 배포, 실행하기 위한 오픈소스 플랫폼입니다. Docker를 사용하면 애플리케이션과 그 의존성을 컨테이너라는 표준화된 단위로 패키징하여 어떤 환경에서도 동일하게 실행할 수 있습니다.

**쉽게 말하면**: Docker는 애플리케이션과 그 실행에 필요한 모든 것을 하나의 상자(컨테이너)에 담아서, 어디서든 그 상자만 있으면 똑같이 실행할 수 있게 해주는 도구입니다.

### Docker가 CI/CD에서 중요한 이유
1. **환경 일관성**: 개발, 테스트, 프로덕션 환경을 동일하게 유지할 수 있습니다.
2. **격리**: 각 애플리케이션이 독립적인 환경에서 실행되어 충돌을 방지합니다.
3. **이식성**: 어떤 시스템에서도 동일하게 실행할 수 있습니다.
4. **확장성**: 컨테이너를 쉽게 복제하여 부하를 분산할 수 있습니다.
5. **버전 관리**: 이미지 태그를 통해 애플리케이션 버전을 관리할 수 있습니다.

### Docker의 주요 구성 요소

#### 1. Docker 엔진
Docker의 핵심 구성 요소로, 컨테이너를 생성하고 관리하는 역할을 합니다.

#### 2. Docker 이미지
컨테이너를 생성하기 위한 템플릿입니다. 애플리케이션 코드, 라이브러리, 의존성, 도구 등을 포함합니다.

**쉽게 말하면**: 이미지는 애플리케이션과 그 실행 환경이 담긴 '설계도'입니다.

#### 3. Docker 컨테이너
이미지의 실행 가능한 인스턴스입니다. 각 컨테이너는 독립적으로 실행되며, 호스트 시스템과 격리되어 있습니다.

**쉽게 말하면**: 컨테이너는 이미지를 바탕으로 실제로 실행 중인 '상자'입니다.

#### 4. Docker 레지스트리
Docker 이미지를 저장하고 공유하는 저장소입니다. Docker Hub, GitHub Container Registry(GHCR) 등이 있습니다.

**쉽게 말하면**: 레지스트리는 이미지를 보관하고 공유하는 '창고'입니다.

#### 5. Dockerfile
Docker 이미지를 생성하기 위한 스크립트입니다. 이미지를 어떻게 구성할지 정의합니다.

**쉽게 말하면**: Dockerfile은 이미지를 만들기 위한 '조리법'입니다.

#### 6. Docker Compose
여러 컨테이너로 구성된 애플리케이션을 정의하고 실행하기 위한 도구입니다.

**쉽게 말하면**: Docker Compose는 여러 컨테이너를 한 번에 관리하는 '지휘자'입니다.

### Dockerfile 이해하기

Dockerfile은 Docker 이미지를 생성하기 위한 텍스트 파일입니다. 다음은 우리 프로젝트에서 사용하는 Dockerfile의 예시입니다:

```dockerfile
# 빌드 전용 이미지
FROM gradle:8.5.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test

# 실행용 이미지
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# 환경 변수 설정
ENV SPRING_PROFILES_ACTIVE=prod

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Dockerfile 주요 명령어 설명

1. **FROM**: 기본 이미지를 지정합니다. 여기서는 두 단계로 나누어 빌드 단계에서는 Gradle이 포함된 이미지를, 실행 단계에서는 JDK만 포함된 가벼운 이미지를 사용합니다.

2. **WORKDIR**: 작업 디렉토리를 설정합니다. 이후 명령어들이 이 디렉토리에서 실행됩니다.

3. **COPY**: 호스트 시스템의 파일이나 디렉토리를 컨테이너로 복사합니다. `COPY --from=builder`는 멀티 스테이지 빌드에서 이전 단계의 파일을 가져옵니다.

4. **RUN**: 이미지 빌드 중에 실행할 명령어를 지정합니다. 여기서는 Gradle을 사용하여 애플리케이션을 빌드합니다.

5. **ENV**: 환경 변수를 설정합니다. 여기서는 Spring 프로필을 'prod'로 설정합니다.

6. **ENTRYPOINT**: 컨테이너가 시작될 때 실행할 명령어를 지정합니다. 여기서는 Java 애플리케이션을 실행합니다.

**쉽게 말하면**: Dockerfile은 "이 기본 이미지에서 시작해서, 이 파일들을 복사하고, 이 명령어를 실행한 다음, 컨테이너가 시작되면 이 명령어를 실행해"라고 Docker에게 알려주는 지시서입니다.

### 멀티 스테이지 빌드

위 Dockerfile은 멀티 스테이지 빌드 방식을 사용합니다. 이 방식의 장점은:

1. **이미지 크기 감소**: 빌드에 필요한 도구들(Gradle 등)은 최종 이미지에 포함되지 않습니다.
2. **보안 강화**: 빌드 도구와 관련된 취약점이 최종 이미지에 포함되지 않습니다.
3. **명확한 관심사 분리**: 빌드 단계와 실행 단계가 명확히 구분됩니다.

**쉽게 말하면**: 멀티 스테이지 빌드는 "요리를 위한 모든 도구와 재료로 요리를 완성한 다음, 완성된 요리만 깨끗한 접시에 담아 제공하는 것"과 같습니다.

### Docker Compose 이해하기

Docker Compose는 여러 컨테이너로 구성된 애플리케이션을 정의하고 실행하기 위한 도구입니다. YAML 파일을 사용하여 서비스, 네트워크, 볼륨 등을 정의합니다.

다음은 우리 프로젝트에서 Jenkins를 실행하기 위한 Docker Compose 파일의 예시입니다:

```yaml
services:
  jenkins:
    build: .
    user: root
    container_name: jenkins
    ports:
      - "8080:8080"       # Jenkins 웹 인터페이스
      - "50000:50000"     # 에이전트 연결용 포트
    volumes:
      - jenkins_home:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
    restart: always
    environment:
      - JAVA_OPS=-Xmx1g

volumes:
  jenkins_home:
```

#### Docker Compose 주요 설정 설명

1. **services**: 실행할 컨테이너들을 정의합니다.
   - **build**: Dockerfile의 위치를 지정합니다.
   - **user**: 컨테이너 내에서 사용할 사용자를 지정합니다.
   - **container_name**: 컨테이너의 이름을 지정합니다.
   - **ports**: 호스트와 컨테이너 간의 포트 매핑을 설정합니다.
   - **volumes**: 호스트와 컨테이너 간의 볼륨 매핑을 설정합니다.
   - **restart**: 컨테이너 재시작 정책을 설정합니다.
   - **environment**: 환경 변수를 설정합니다.

2. **volumes**: 컨테이너 데이터를 영구적으로 저장하기 위한 볼륨을 정의합니다.

**쉽게 말하면**: Docker Compose는 "이런 컨테이너들을 이런 설정으로 함께 실행해줘"라고 Docker에게 알려주는 설계도입니다.

### Docker Registry와 GitHub Container Registry(GHCR)

Docker Registry는 Docker 이미지를 저장하고 공유하는 저장소입니다. 우리 프로젝트에서는 GitHub Container Registry(GHCR)를 사용합니다.

#### GitHub Container Registry의 장점

1. **GitHub와의 통합**: GitHub 저장소와 직접 연결되어 있어 관리가 용이합니다.
2. **접근 제어**: GitHub의 권한 시스템을 활용하여 이미지 접근을 제어할 수 있습니다.
3. **워크플로우 통합**: GitHub Actions와 원활하게 통합됩니다.
4. **가시성 옵션**: 공개, 비공개, 내부 가시성 옵션을 제공합니다.

#### CI/CD 파이프라인에서의 Docker Registry 활용

1. **빌드 단계**: GitHub Actions가 애플리케이션을 빌드하고 Docker 이미지를 생성합니다.
2. **푸시 단계**: 생성된 이미지를 GHCR에 푸시합니다.
3. **배포 단계**: Jenkins가 GHCR에서 이미지를 가져와 배포합니다.

**쉽게 말하면**: GitHub Container Registry는 우리가 만든 Docker 이미지를 GitHub에 안전하게 보관하고, 필요할 때 가져다 쓸 수 있게 해주는 창고입니다.

### Docker 명령어 기본

Docker를 사용할 때 알아두면 유용한 기본 명령어들입니다:

1. **이미지 관련**
   - `docker build -t 이미지이름 .`: 현재 디렉토리의 Dockerfile로 이미지를 빌드합니다.
   - `docker images`: 로컬에 있는 이미지 목록을 확인합니다.
   - `docker pull 이미지이름`: 레지스트리에서 이미지를 가져옵니다.
   - `docker push 이미지이름`: 이미지를 레지스트리에 업로드합니다.
   - `docker rmi 이미지이름`: 이미지를 삭제합니다.

2. **컨테이너 관련**
   - `docker run -d --name 컨테이너이름 이미지이름`: 이미지로부터 컨테이너를 생성하고 백그라운드에서 실행합니다.
   - `docker ps`: 실행 중인 컨테이너 목록을 확인합니다.
   - `docker ps -a`: 모든 컨테이너 목록을 확인합니다.
   - `docker stop 컨테이너이름`: 컨테이너를 중지합니다.
   - `docker start 컨테이너이름`: 중지된 컨테이너를 시작합니다.
   - `docker rm 컨테이너이름`: 컨테이너를 삭제합니다.
   - `docker logs 컨테이너이름`: 컨테이너의 로그를 확인합니다.

3. **Docker Compose 관련**
   - `docker-compose up -d`: docker-compose.yml 파일에 정의된 서비스를 백그라운드에서 실행합니다.
   - `docker-compose down`: 서비스를 중지하고 컨테이너를 삭제합니다.
   - `docker-compose logs`: 서비스의 로그를 확인합니다.

**쉽게 말하면**: 이 명령어들은 Docker를 사용할 때의 기본적인 "리모컨 버튼들"입니다.

## 7. 실제 프로젝트 예시

우리 프로젝트에서는 다음과 같은 CI/CD 파이프라인을 사용하고 있습니다:

### GitHub Actions 워크플로우

#### 1. 테스트 워크플로우 (test.yml)
```yaml
name: 1. Run Tests

# main 브랜치에 push될 때 실행
on:
  push:
    branches:
      - main
    paths:
      - 'src/**'

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
      # 1. 코드 가져오기
      - name: Checkout repository
        uses: actions/checkout@v4

      # 2. JDK 설정
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      # 3. Gradle 캐시 설정
      - name: Cache Gradle packages
        uses: actions/cache@v3
        with:
          path: |
            ~/.gradle/caches
            ~/.gradle/wrapper
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
          restore-keys: |
            ${{ runner.os }}-gradle-

      # 4. 테스트 실행
      - name: Run tests
        run: chmod +x ./gradlew && ./gradlew test
```

#### 2. 이미지 빌드 워크플로우 (build_image.yml)
```yaml
name: 2. Build Docker Image

# 테스트 워크플로우가 성공적으로 완료되면 실행
on:
  workflow_run:
    workflows: ["1. Run Tests"]
    types:
      - completed
    branches:
      - main

jobs:
  build:
    # 테스트 워크플로우가 성공한 경우에만 실행
    if: ${{ github.event.workflow_run.conclusion == 'success' }}
    runs-on: ubuntu-latest

    # GHCR에 푸시하려면 권한 필요
    permissions:
      contents: read
      packages: write

    steps:
      # 1. 코드 가져오기
      - name: Checkout repository
        uses: actions/checkout@v4

      # 2. GHCR 로그인
      - name: Log in to GitHub Container Registry
        uses: docker/login-action@v3
        with:
          registry: ghcr.io
          username: ${{ github.actor }}
          password: ${{ secrets.GITHUB_TOKEN }}

      # 3. Docker 태그/레이블 메타데이터 추출
      - name: Extract metadata for Docker
        id: meta
        uses: docker/metadata-action@v5
        with:
          images: ghcr.io/${{ github.repository }}

      # 4. Docker 이미지 빌드 및 GHCR로 푸시
      - name: Build and push Docker image
        uses: docker/build-push-action@v5
        with:
          context: .
          file: ./docker/Dockerfile
          push: true
          tags: ${{ steps.meta.outputs.tags }}
          labels: ${{ steps.meta.outputs.labels }}
```

#### 3. Jenkins 웹훅 워크플로우 (webhook.yml)
```yaml
name: 3. Trigger Jenkins Webhook

# 이미지 빌드 워크플로우가 성공적으로 완료되면 실행
on:
  workflow_run:
    workflows: ["2. Build Docker Image"]
    types:
      - completed
    branches:
      - main

jobs:
  webhook:
    # 이미지 빌드 워크플로우가 성공한 경우에만 실행
    if: ${{ github.event.workflow_run.conclusion == 'success' }}
    runs-on: ubuntu-latest

    steps:
      # Jenkins로 웹훅 전달
      - name: Trigger Jenkins Build with Crumb
        run: |
          set +x
          CRUMB=$(curl -s --user "${{ secrets.JENKINS_USER }}:${{ secrets.JENKINS_API_TOKEN }}" \
            "${{ secrets.JENKINS_URL }}/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,\":\",//crumb)")

          curl -X POST "${{ secrets.JENKINS_URL }}/job/practice/buildWithParameters?token=${{ secrets.JENKINS_AUTH_TOKEN }}" \
            --user "${{ secrets.JENKINS_USER }}:${{ secrets.JENKINS_API_TOKEN }}" \
            -H "$CRUMB"
```

### Jenkins 파이프라인 (Jenkinsfile)

```groovy
pipeline {
    agent any

    parameters {
        string(name: 'REGISTRY', defaultValue: 'ghcr.io', description: 'Docker Registry 주소')
        string(name: 'IMAGE_NAME', defaultValue: 'lucky-0111/pr-practice', description: 'Docker 이미지 이름')
        string(name: 'IMAGE_TAG', defaultValue: 'main', description: 'Docker 이미지 태그')
        string(name: 'CONTAINER_NAME', defaultValue: 'myapp-container', description: '실행할 컨테이너 이름')
        string(name: 'PORT', defaultValue: '8443', description: '컨테이너 포트')
    }

    environment {
        IMAGE = "${params.REGISTRY}/${params.IMAGE_NAME}:${params.IMAGE_TAG}"
    }

    stages {
        stage('Authenticate with GHCR') {
            steps {
                echo 'GHCR에 인증 중...'
                withCredentials([string(credentialsId: 'github_access_token', variable: 'GITHUB_TOKEN')]) {
                    sh 'echo $GITHUB_TOKEN | docker login ghcr.io -u Lucky-0111 --password-stdin'
                }
            }
        }

        stage('Pull Docker Image') {
            steps {
                echo "Docker 이미지 가져오는 중..."
                sh "docker pull $IMAGE"
            }
        }

        stage('Stop Previous Container') {
            steps {
                echo '기존 컨테이너 중지 중...'
                sh "docker stop $CONTAINER_NAME || true"
                sh "docker rm $CONTAINER_NAME || true"
            }
        }

        stage('Run New Container') {
            steps {
                echo '새 컨테이너 실행 중...'
                sh """
                docker run -d \
                  --name $CONTAINER_NAME \
                  -p $PORT:$PORT \
                  $IMAGE
                """
            }
        }
    }

    post {
        failure {
            echo '배포 실패 ❌'
        }
        success {
            echo '배포 성공 ✅'
        }
    }
}
```

### Docker 컨테이너화 (Dockerfile)

```dockerfile
# 빌드 전용 이미지
FROM gradle:8.5.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test

# 실행용 이미지
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# 환경 변수 설정
ENV SPRING_PROFILES_ACTIVE=prod

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## 7. 자주 묻는 질문

### Q: CI/CD를 도입하면 어떤 이점이 있나요?
**A**: CI/CD를 도입하면 다음과 같은 이점이 있습니다:
- 개발 속도 향상: 자동화된 테스트와 배포로 개발 주기가 단축됩니다.
- 버그 감소: 자주 통합하고 테스트하여 문제를 조기에 발견합니다.
- 안정적인 배포: 검증된 코드만 배포되므로 배포 실패 위험이 줄어듭니다.
- 개발자 생산성 향상: 반복적인 수동 작업이 줄어들어 개발자가 코드 작성에 집중할 수 있습니다.

### Q: GitHub Actions와 Jenkins의 차이점은 무엇인가요?
**A**: 두 도구 모두 CI/CD를 위한 도구이지만 몇 가지 차이점이 있습니다:
- GitHub Actions: GitHub와 통합되어 있어 GitHub 저장소와 함께 사용하기 쉽습니다. 클라우드 기반으로 별도의 서버 설정이 필요 없습니다.
- Jenkins: 오픈소스로 더 오래된 역사를 가지고 있으며, 더 많은 플러그인과 커스터마이징 옵션을 제공합니다. 자체 서버에 설치해야 합니다.

우리 프로젝트에서는 두 도구의 장점을 결합하여 GitHub Actions는 CI(테스트 및 빌드)에, Jenkins는 CD(배포)에 사용하고 있습니다.

### Q: Docker를 사용하는 이유는 무엇인가요?
**A**: Docker를 사용하면 다음과 같은 이점이 있습니다:
- 일관된 환경: "내 컴퓨터에서는 작동합니다"라는 문제를 해결합니다.
- 격리: 애플리케이션과 그 의존성을 격리하여 충돌을 방지합니다.
- 확장성: 컨테이너를 쉽게 복제하여 부하를 분산할 수 있습니다.
- 이식성: 개발, 테스트, 프로덕션 환경 간에 쉽게 이동할 수 있습니다.

### Q: CI/CD 파이프라인을 어떻게 시작해야 할까요?
**A**: CI/CD 파이프라인을 시작하는 단계는 다음과 같습니다:
1. 버전 관리 시스템(Git 등) 설정
2. 자동화된 테스트 작성
3. CI 도구(GitHub Actions, Jenkins 등) 설정
4. 빌드 프로세스 자동화
5. 배포 프로세스 자동화
6. 모니터링 및 피드백 루프 구축

처음에는 간단한 파이프라인부터 시작하여 점진적으로 확장하는 것이 좋습니다.
