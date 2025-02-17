# MicroApply 🚀

Welcome to **MicroApply**! A basic demonstration of microservices built with Spring Boot. This project showcases the fundamental principles of microservice architecture but is not a fully-fledged application. 💼

## ✨ Features

- 🌐 **API Gateway**: Central entry point for client requests.
- 📝 **Job Management**: Create and manage job postings effortlessly.
- 📄 **Application Tracking**: Submit applications and monitor progress.
- 📩 **Real-time Notifications**: Get updates on job applications via email.
- 📊 **Observability**: Integrated **Grafana**, **Prometheus**, and **Loki** for monitoring.
- ✅ **Test Coverage**: Ensures code reliability with comprehensive tests.

## 🛠️ Tech Stack

MicroApply is powered by cutting-edge technologies:

- **Java 21** & **Spring Boot** (Core framework)
- **JPA & MySQL** (Database)
- **RabbitMQ** (Messaging)
- **Keycloak** (Authentication & Authorization)
- **Grafana, Prometheus, Loki, Tempo** (Monitoring & Tracing)

## 📦 Microservices Overview

| Service                  | Description                              |
| ------------------------ | ---------------------------------------- |
| **API Gateway**          | Handles authentication & request routing |
| **Job Service**          | Manages job postings                     |
| **Application Service**  | Handles job applications                 |
| **Notification Service** | Sends notifications via email            |
| **Common**               | Shared resources & utilities             |

## 🚀 Getting Started

### 1️⃣ Clone the Repository

```bash
git clone <repository-url>
cd microapply
```

### 2️⃣ Build the Project

```bash
./mvnw clean install
```

### 3️⃣ Start the Services

Using Docker Compose:

```bash
docker-compose up -d
```

Or run individual services:

```bash
./mvnw spring-boot:run -pl <service-name>
```

### 4️⃣ Access the Application

- **API Gateway**: [http://localhost:8080](http://localhost:8080)
- **Grafana**: [http://localhost:3000](http://localhost:3000)
- **Keycloak**: [http://localhost:8081](http://localhost:8081)

## 🤝 Contributing

I’d love your contributions! If you have ideas for improvements or new features, feel free to fork the repo and open a pull request. 🛠️

## 📜 License

This project is licensed under the [MIT License](./LICENSE).
