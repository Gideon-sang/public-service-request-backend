-- Optional: run in MySQL Workbench. The app also creates the database automatically.
CREATE DATABASE IF NOT EXISTS psrs CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- Tables (users, service_request, request_history) are created by Spring Boot on first run.
-- Handy checks:
--   SELECT id, name, email, role FROM psrs.users;
--   SELECT id, ref, category, status, created_at FROM psrs.service_request;
--   SELECT * FROM psrs.request_history;
