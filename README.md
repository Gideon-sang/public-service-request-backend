# Murang'a County - Public Service Request System (Backend)

Spring Boot 3.3 / Java 17 REST API with MySQL, JWT login, citizen and admin roles, request tracking.

## Run (Eclipse)
1. File > Import > Maven > Existing Maven Projects > select this folder.
2. Open `src/main/resources/application.properties` and set `YOUR_MYSQL_PASSWORD` (and the username if not root).
3. Make sure MySQL is running. The `psrs` database and all tables are created automatically.
4. Right-click the project > Maven > Update Project (Force Update).
5. Run `PublicServiceRequestApplication` as a Java Application. API: http://localhost:8080/api

Admin login: admin@demo.com / Admin@123 (change with ADMIN_EMAIL / ADMIN_PASSWORD).

## API
| Method | Path | Access |
|--------|------|--------|
| POST | /api/register | public |
| POST | /api/login | public |
| GET | /api/me | logged in |
| GET | /api/requests | logged in (admin sees all) |
| POST | /api/requests | logged in |
| PATCH | /api/requests/{id} | admin |

Protected calls need `Authorization: Bearer <token>`.

## Environment variables (production)
DATABASE_URL (JDBC), DB_USERNAME, DB_PASSWORD, ADMIN_EMAIL, ADMIN_PASSWORD, JWT_SECRET, CORS_ORIGINS, PORT.
Never commit your real database password: keep the placeholder in the file and use environment variables when deploying.
