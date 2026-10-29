# Public Service Request System: Backend API

RESTful API for the **Murang'a County Public Service Request System**. Residents report issues such as broken streetlights, water problems and uncollected garbage, and administrators update the status until each one is resolved.

**Live API:** https://public-service-request-backend.onrender.com
*(Free Render instance: the first request after idle time may take 50+ seconds.)*

## Features

- JWT authentication with resident and admin roles
- Submit service requests with category, location, description and photo
- Status tracking with full history and notes
- Admin account seeded automatically on first startup

## Tech Stack

Java 17 · Spring Boot · Spring Security · Spring Data JPA · MySQL (Aiven) · Maven · Docker · Render

## Quick Start

```bash
git clone https://github.com/YOUR-GITHUB-USERNAME/public-service-request-system.git
cd public-service-request-system
mvn spring-boot:run
```

The API runs at `http://localhost:8080`.

## Configuration

| Variable | Description |
|---|---|
| `DATABASE_URL` | JDBC URL of the MySQL database |
| `DB_USERNAME`, `DB_PASSWORD` | Database credentials |
| `JWT_SECRET` | Token signing key (32+ characters) |
| `ADMIN_EMAIL`, `ADMIN_PASSWORD` | Seeded admin account |
| `CORS_ORIGINS` | Allowed frontend origin(s) |
| `PORT` | Server port (default `8080`) |

> **Aiven MySQL:** append `&sessionVariables=sql_require_primary_key=0` to `DATABASE_URL` so Hibernate can create the `request_history` table.

## Deployment

Deployed as a Docker web service on Render. Set the variables above, push to GitHub, and Render redeploys automatically.

## Related

- Frontend: [public-service-request-frontend](https://github.com/YOUR-GITHUB-USERNAME/public-service-request-frontend)

## Author

**Gideon Sang**
