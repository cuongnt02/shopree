# Shopree

Shopree is a mobile-first, multi-vendor commerce platform tailored for Vietnam retail and F&B. It includes a Spring Boot backend and an Android mobile app.

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
- Mobile: Android, Kotlin, Hilt
- DB & migrations: Flyway (backend)

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

## Checklist
- [ ] Backend API runs locally
- [ ] Auth server runs locally
- [ ] Mobile app builds in debug
- [ ] Mobile login flow works end-to-end
- [ ] Catalog fetch works with auth token
- [ ] Tests pass for backend and mobile
