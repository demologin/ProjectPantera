# Project Pantera

Project Pantera is a Java Servlet/JSP application with interactive text quests.

The project was created as an adaptation of the JavaRush Module 4 final project covering MySQL, Hibernate, Redis and Docker.

## Technology stack

- Java 21
- Maven
- Jakarta Servlet API
- JSP / JSTL
- Apache Tomcat 10
- MySQL 8
- Hibernate ORM 6
- Redis 7
- Lettuce
- Jackson
- P6Spy
- SLF4J / Logback
- JUnit 5
- Mockito
- Lombok

## Architecture

```text
Servlet / JSP
      ↓
Service
      ↓
Repository
      ↓
Hibernate / MySQL
```

Quest domain model:

```text
Quest
  └── QuestStep
        └── Choice
```

MySQL is the source of truth. Redis is used only as an optional cache for quest steps.

## Redis cache strategy

Each quest step, including its choices, is stored as a separate Redis entry.

Key format:

```text
quest-step:<questId>:<stepId>
```

Examples:

```text
quest-step:1:1
quest-step:1:2
quest-step:2:1
```

When a quest step is requested:

```text
Redis HIT
→ deserialize cached QuestStep
→ return it to the user
```

```text
Redis MISS
→ load all steps of the selected quest from MySQL
→ cache every step separately
→ return the requested step
```

The complete quest is cached because the current quests are small. This avoids additional SQL queries during a playthrough.

Cache entries have a TTL of five minutes:

```properties
redis.quest-step.ttl-seconds=300
```

If Redis is unavailable, the application continues working through MySQL.

## Prerequisites

Install:

- JDK 21
- Maven
- Docker
- Apache Tomcat 10

Tomcat 10 is required because the application uses the Jakarta Servlet API.

## Start MySQL

Create a MySQL 8 container:

```bash
docker run \
  --name project-pantera-mysql \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=rootpass \
  -e MYSQL_DATABASE=project_pantera \
  -d mysql:8.0
```

For subsequent starts:

```bash
docker start project-pantera-mysql
```

MySQL connection:

```text
Host: localhost
Port: 3306
Database: project_pantera
User: root
Password: rootpass
```

JDBC URL:

```text
jdbc:p6spy:mysql://localhost:3306/project_pantera?createDatabaseIfNotExist=true
```

## Start Redis

Create the Redis container:

```bash
docker run \
  --name project-pantera-redis \
  -p 6379:6379 \
  -d redis:7
```

For subsequent starts:

```bash
docker start project-pantera-redis
```

Redis URL is configured in:

```text
src/main/resources/application.properties
```

```properties
redis.url=redis://localhost:6379
redis.quest-step.ttl-seconds=300
```

## Database initialization

During application startup:

1. Tomcat discovers `ApplicationLifecycleListener`.
2. Hibernate creates the database schema.
3. YAML quest definitions are loaded.
4. Quest, QuestStep and Choice entities are saved to MySQL.
5. Redis infrastructure is initialized.

At shutdown, the application closes:

- the Redis connection;
- the Redis client;
- the Hibernate SessionFactory.

The current development configuration recreates the schema during startup.

> Existing data in the application tables may be deleted when the application restarts.

## Build

Run tests and build the WAR:

```bash
mvn clean package
```

The generated WAR is available in:

```text
target/project-pantera.war
```

## Run with Tomcat

Deploy the generated WAR to Tomcat 10 or configure a local Tomcat server in IntelliJ IDEA.

After deployment, open:

```text
http://localhost:8080/project-pantera/quests
```

The exact context path may depend on the configured WAR artifact name.

## Tests

Run all tests:

```bash
mvn clean test
```

The tests cover:

- YAML quest loading;
- quest graph initialization;
- QuestStep cache hit;
- cache miss and cache warming;
- Redis failure fallback;
- missing quest steps;
- entity relationships created by the initializer.

## Performance comparison

The same ten quest steps with their choices were read from Redis and MySQL.

The comparison included:

- three warm-up rounds;
- ten measurement rounds;
- alternating Redis/MySQL execution order;
- identical quest and step IDs;
- `System.nanoTime()` measurements.

Average result:

| Source | Average time |
|---|---:|
| Redis | 4.622 ms |
| MySQL | 14.422 ms |

In the test environment, Redis was approximately **3.12× faster** than MySQL.

Results may vary depending on hardware, Docker configuration, JVM state and enabled P6Spy SQL logging.

## SQL logging

P6Spy logs SQL statements with real parameter values.

Configuration:

```text
src/main/resources/spy.properties
```

This makes it possible to verify that:

- the first quest-step request loads data from MySQL;
- subsequent requests within the TTL are served from Redis;
- no additional QuestStep SQL queries are executed on cache hits.

## Error handling

The application returns:

- `400 Bad Request` for invalid quest or step IDs;
- `404 Not Found` when a quest or step does not exist.

Redis failures do not stop the game. MySQL remains the source of truth and is used as a fallback.

## Available quests

- How to Tame a Panther
- The Matrix: Before the Chosen One

Quest definitions are stored as YAML resources.