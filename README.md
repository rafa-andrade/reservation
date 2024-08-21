# Reservation

The Reservation project is a system that processes reservation requests for events, ensuring order and daily availability.

## Features
The main features of the project include:

- Create Reservation: Allows users to make reservations via a POST request. Returns a unique external reference for the created reservation.
- Get Reservation: Allows users to retrieve a specific reservation using a unique external reference via GET.
- List Reservations: Allows users to retrieve all existing reservations through a GET request.

## Technologies Used
- Java 21
- GraalVM
- Spring Boot 3
- Kafka
- PostgreSQL

## Prerequisites
To run the project, you need to have the following installed:

- Java 21
- GraalVM
- Docker
- Docker Compose


## Installation and Execution
Follow the steps below to clone the repository and run the project locally:

Clone the repository:
```bash
git clone https://github.com/rafa-andrade/reservation.git
```

Navigate to the project directory:

```bash
cd reservation
```

Start dependences with Docker Compose:

```bash
docker compose -f compose.override.yml up -d
```

Run the project with Gradle:

```bash
./gradlew bootRun
```

Alternatively, you can build the Docker image and run it:

```bash
./gradlew bootBuildImage
docker compose -f compose.yml up -d
```

Testing
To run the tests, execute:

```bash
./gradlew test
```

## Additional Instructions

Kafka UI access:
```
http://localhost:8090/ui
```

Swagger UI access:
```
http://localhost:8080/swagger-ui/index.html
```

Gatling test:
```bash
./gradlew gatlingRun
```

## Author
Rafael Andrade

GitHub: https://github.com/rafa-andrade
LinkedIn: https://www.linkedin.com/in/rafael-oliveira-andrade/



