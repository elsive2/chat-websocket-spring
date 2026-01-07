# Chat WebSocket

Small Spring Boot WebSocket chat service with REST/Actuator endpoints and
PostgreSQL persistence.

## Tech Stack
- Java 25
- Spring Boot 4 (WebMVC, WebSocket, Data JPA, Validation, Actuator)
- Hibernate ORM, Liquibase
- PostgreSQL
- Gradle
- Docker, Kubernetes

## Deployment Guides
- [Run locally](#run-locally)
- [Run with Docker](#run-with-docker)
- [Run on Kubernetes](#run-on-kubernetes)

## Run Locally
1. Start PostgreSQL:
   ```bash
   docker compose up -d db
   ```
2. Run the app:
   ```bash
   ./gradlew bootRun
   ```
3. App is available at `http://localhost:8080`.

## Run with Docker
1. Build and run the stack:
   ```bash
   docker compose up --build -d
   ```
2. App is available at `http://localhost:8080`.

## Run on Kubernetes
1. Build the app image:
   ```bash
   docker build -f Dockerfile.jvm -t chat-websocket:latest .
   ```
2. Load the image into your cluster (pick one):
   ```bash
   kind load docker-image chat-websocket:latest
   ```
   ```bash
   minikube image load chat-websocket:latest
   ```
3. Apply the manifests:
   ```bash
   kubectl apply -k k8s
   ```
4. Port-forward the service:
   ```bash
   kubectl port-forward svc/chat-websocket 8080:8080 -n chat-websocket
   ```
5. App is available at `http://localhost:8080`.
