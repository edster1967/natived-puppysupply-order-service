# natived-puppysupply-order-service

A Spring Boot REST service for looking up puppy supply orders and customers.

- **Spring Boot 3.5 / Java 17+**
- Two database options, selected by Spring profile:
  - `local` (default): **H2 in-memory database**. No install needed; schema and sample data load on startup.
  - `dev`: **PostgreSQL**. Tables are created and seeded on startup (the scripts are safe to re-run).
- Uses Spring Data JPA for orders and `JdbcTemplate` for customers. `PuppySupplyCustomerRepositoryImpl` also has a commented-out example of calling an Oracle stored procedure.
- All credentials come from environment variables or a git-ignored `.env` file. Nothing secret is committed.
- Works on macOS, Linux and Windows.

---

## 1. Prerequisites

| Tool | Version | Check |
|------|---------|-------|
| JDK  | 17 or newer (17, 21 tested) | `java -version` |
| Git  | any | `git --version` |
| PostgreSQL | 13+ (*only for the `dev` profile*) | `psql --version` |

You do **not** need to install Maven. The included Maven Wrapper (`mvnw` / `mvnw.cmd`) downloads the right version on first use.

## 2. Get the code

```bash
git clone <repo-url>
cd natived-puppysupply-order-service
```

## 3. Create your `.env` file

The app reads settings from an optional `.env` file in the project root. Copy the template:

| macOS / Linux | Windows (Command Prompt) | Windows (PowerShell) |
|---|---|---|
| `cp .env.example .env` | `copy .env.example .env` | `Copy-Item .env.example .env` |

Then edit `.env`. The available settings:

| Variable | Default | Used by | Purpose |
|---|---|---|---|
| `APP_PROFILE` | `local` | all | `local` = H2 in-memory, `dev` = PostgreSQL |
| `SERVER_PORT` | `8080` | all | HTTP port |
| `LOG_FILE_PATH` | `logs/puppysupplyservice.log` | all | Log file (relative to the project folder) |
| `DB_URL` | `jdbc:postgresql://localhost:5432/puppysupply` | dev | PostgreSQL JDBC URL |
| `DB_USERNAME` | *(none)* | dev | PostgreSQL user |
| `DB_PASSWORD` | *(none)* | dev | PostgreSQL password |
| `DB_INIT_MODE` | `always` | dev | `always` = create tables and seed data on startup, `never` = skip |
| `H2_USERNAME` / `H2_PASSWORD` | `sa` / *(empty)* | local | H2 credentials |

> `.env` is in `.gitignore`. **Never commit it.** Real environment variables override values in `.env`, which is the recommended approach for CI and servers.

## 4a. Run with the in-memory H2 database (default, easiest)

| macOS / Linux | Windows |
|---|---|
| `./mvnw spring-boot:run` | `mvnw.cmd spring-boot:run` |

The first run downloads Maven and the dependencies, so it takes a few minutes. When you see `Started SupplyOrderServiceApplication`, the app is ready at http://localhost:8080.

H2 web console: http://localhost:8080/h2-console. Use JDBC URL `jdbc:h2:mem:puppysupply`, user `sa`, and an empty password.

Data is lost when the app stops, and it is reloaded from `data-h2.sql` on every start.

## 4b. Run with PostgreSQL

1. **Install and start PostgreSQL.** Pick one:
   - **macOS:** `brew install postgresql@16 && brew services start postgresql@16`
   - **Windows:** use the installer from https://www.postgresql.org/download/windows/. It runs as a Windows service.
   - **Docker (any OS):**
     ```bash
     docker run --name puppysupply-db -e POSTGRES_USER=puppy -e POSTGRES_PASSWORD=<choose-a-password> -e POSTGRES_DB=puppysupply -p 5432:5432 -d postgres:16
     ```
     If you use Docker, the database already exists, so skip step 2.
2. **Create the database and a user** (skip if you used Docker):
   ```bash
   psql -U postgres -c "CREATE USER puppy WITH PASSWORD '<choose-a-password>';"
   psql -U postgres -c "CREATE DATABASE puppysupply OWNER puppy;"
   ```
3. **Set these values in `.env`:**
   ```properties
   APP_PROFILE=dev
   DB_URL=jdbc:postgresql://localhost:5432/puppysupply
   DB_USERNAME=puppy
   DB_PASSWORD=<the-password-you-chose>
   ```
4. **Run** (same command as before):

   | macOS / Linux | Windows |
   |---|---|
   | `./mvnw spring-boot:run` | `mvnw.cmd spring-boot:run` |

   On startup, `schema-postgres.sql` creates the tables if they don't exist, and `data-postgres.sql` inserts the sample rows (`ON CONFLICT DO NOTHING`). Set `DB_INIT_MODE=never` once you manage the schema yourself.

**Switching profile without editing `.env`:** pass it on the command line, which overrides `.env`:

| macOS / Linux | Windows |
|---|---|
| `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev` | `mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev` |

## 5. Try the API

| Method | URL | Description |
|---|---|---|
| GET | `/puppysupply/puppyorder/{orderNumber}` | Order by order number, with customer (404 if not found) |
| GET | `/puppysupply/puppyorder/id/{puppyOrderId}` | Order by internal id, with customer (404 if not found) |
| GET | `/puppysupply/puppyorders?lastName={lastName}` | All orders for customers with that last name (case-insensitive) |
| GET | `/puppysupply/customer/{customerId}` | Customer by id (404 if not found) |
| GET | `/actuator/health` | Health check |
| GET | `/swagger-ui.html` | Interactive API docs (OpenAPI JSON at `/v3/api-docs`) |

Examples using the sample data (`curl` ships with macOS and Windows 10+; in PowerShell type `curl.exe`):

```bash
curl http://localhost:8080/puppysupply/puppyorder/1122
curl http://localhost:8080/puppysupply/puppyorder/id/21
curl "http://localhost:8080/puppysupply/puppyorders?lastName=smith"
curl http://localhost:8080/puppysupply/customer/13
curl http://localhost:8080/actuator/health
```

Sample response:

```json
{
  "puppyOrderId": 2, "orderNumber": 1122, "orderDate": "2026-09-28", "customerId": 11,
  "subTotal": 123.00, "shippingCost": 0.00, "tax": 1.00, "total": 200.00,
  "customer": { "first_name": "John", "last_name": "smith", "address_1": "1231 test drive",
                "address_2": null, "city": "brunswick", "state": "OH", "zip": "44212" }
}
```

## 6. Run the tests

| macOS / Linux | Windows |
|---|---|
| `./mvnw test` | `mvnw.cmd test` |

The tests use the H2 profile, so no database install is needed:

- `controllers/PuppyOrderControllerIntegrationTest`: every URL end to end (success, 404 and 400 cases, health, OpenAPI).
- `services/PuppyOrderServiceImplTest`: service logic, as unit tests with Mockito.
- `repositories/*RepositoryTest`: SQL and JPA queries against the H2 schema and data.

## 7. Build a runnable jar

| macOS / Linux | Windows |
|---|---|
| `./mvnw clean package` then `java -jar target/supply-order-service-0.0.1-SNAPSHOT.jar` | `mvnw.cmd clean package` then `java -jar target\supply-order-service-0.0.1-SNAPSHOT.jar` |

Run the jar from the project root so it finds `.env`, or set the variables in your environment instead.

## Project layout

```
src/main/java/.../controllers    REST endpoints
src/main/java/.../services       business logic
src/main/java/.../repositories   JPA (orders) + JdbcTemplate (customers)
src/main/resources
  application.properties         shared settings, loads .env, picks profile
  application-local.properties   H2 in-memory settings
  application-dev.properties     PostgreSQL settings
  schema-h2.sql / data-h2.sql              H2 schema + sample data
  schema-postgres.sql / data-postgres.sql  PostgreSQL schema + sample data
```

## Troubleshooting

- **`./mvnw: Permission denied` (macOS/Linux):** run `chmod +x mvnw`.
- **`Port 8080 already in use`:** set `SERVER_PORT=8081` in `.env`.
- **`password authentication failed` / `Connection refused` on the dev profile:** check that PostgreSQL is running and that `DB_URL`, `DB_USERNAME` and `DB_PASSWORD` in `.env` are correct.
- **`Unsupported class file major version`:** you're on a JDK older than 17. Check `java -version` and `JAVA_HOME`.
