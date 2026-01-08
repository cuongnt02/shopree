# Shopree

Shopree is a mobile-first, multi-vendor commerce platform tailored for retail and F&B. It includes a Spring Boot backend and an Android mobile app.

## Motivation
Most local vendors going through other e-commerce platform currently have to go through a big and complex process of shipping, handling orders, cancelling orders and having to go through a lot of unnecessary risk of losing money because of slow and overload inventory system.

We aim to remove the inventory and shipping layer of these systems, leaving the only choice of buying in-place, we believe removing the shipping layer in one side proved to be inconvenient, but on the other side might turn into an unexpectedly productive outcomes.

## Project Structure
- `Backend/shopree-server` (multi-module Spring Boot)
  - `Application`: boot app, config, migrations
  - `Api`: controllers and API payloads
  - `Service`: business services and DTOs
  - `Data`: repositories
  - `Domain`: entities
  - `Security`: auth
- `Backend/auth-server`: separate Spring Boot auth service
- `MobileApp`: Android app (`MobileApp/app/src/main`)

## Tech Stack
- Backend: Kotlin, Spring Boot (Spring 4.0 per project guidelines), Spring Security, JPA
- Mobile: Android, Kotlin, Hilt, Firebase (Auth)
- DB & migrations: Flyway (backend), PostgresSQL with PostGIS extension.

## Project Setup
1. Clone the repository
  1. For backend: pull a docker postgis image and start one with your own username and password
  ```
  docker pull postgis/postgis
  docker run -d --name my-postgres -p 5432:5432 -e POSTGRES_PASSWORD=your_password -v pgdata:/var/lib/postgresql postgres
  ```
  
  Environment setup: set DATABASE_URL, DATABASE_PASSWORD, DATABASE_USERNAME as your local environment

  Run the app as specified in the "Getting Started" section.

  2. For mobile: Set the IP address and api host in the build.gradle.kts of the app module as your local ip address. This is taken as a technical debt and will be resolved in the future

## Getting Started
Backend (Shopree API):
```
cd Backend/shopree-server
./gradlew :Application:bootRun
```

Auth server:
```
cd Backend/auth-server
./gradlew bootRun
```

Mobile app (debug APK):
```
cd MobileApp
./gradlew :app:assembleDebug
```

## Configuration
- Backend app config: `Backend/shopree-server/Application/src/main/resources/application.yml`
- Flyway migrations: `Backend/shopree-server/Application/src/main/resources/db/migration`
- Keep secrets out of git; use local `local.properties` files where present.

## Development Standards
- Kotlin/Java: 4-space indentation, PascalCase classes, camelCase functions.
- SQL migrations: `V#__description.sql` in backend migration folder.

## Testing
Backend:
```
cd Backend/shopree-server
./gradlew test
```

Mobile (unit tests):
```
cd MobileApp
./gradlew :app:test
```

## Security and Compliance
- Enforce verified email/phone for authentication.
- JWT validation with issuer and expiry checks.
- Role-based access control for vendor endpoints.
- Standardized error responses and centralized CORS configuration.

## Build Checklist
- [ ] Backend API runs locally
- [ ] Auth server runs locally
- [ ] Mobile app builds in debug
- [ ] Mobile login flow works end-to-end
- [ ] Catalog fetch works with auth token
- [ ] Tests pass for backend and mobile

## Features checklist
- [x] Backend server runs with initial config
- [x] Mobile application architecture set up and runs
- [x] Backend server handle basic authentication (username, password)
- [ ] Backend server handle other types of authentication (phone number, email, remember me)
- [ ] Email verification flow and reset password feature
- [ ] Backend process payment
- [ ] Tracking orders and bills
- [?] Tracking and managing inventory (marking for removal/modify)
- [ ] Cart flow
- [ ] Quick buy
