# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),  
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Upcoming Changes

Stay tuned for new features and improvements!

## [1.0.0] - 2025-02-17

### Added

- **Initial Release of MicroApply**: A demonstration of microservices architecture using **Spring Boot**.
- **Microservices**:
  - **API Gateway**: Handles authentication and request routing.
  - **Job Service**: Manages job postings.
  - **Application Service**: Processes job applications.
  - **Notification Service**: Sends real-time email notifications.
  - **Common Module**: Contains shared utilities and configurations.
- **Observability Stack**:
  - **Grafana, Prometheus, Loki, Tempo** for monitoring and tracing.
- **Security Integration**:
  - **Keycloak** for authentication and authorization.
- **Messaging System**:
  - **RabbitMQ** for asynchronous communication between services.
- **Database**:
  - **MySQL** for data persistence.
