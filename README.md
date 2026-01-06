# Shopree

Shopree is a mobile-first, multi-vendor commerce platform tailored for Vietnam retail and F&B. It combines a Spring Boot backend, a Next.js web frontend, and an Android mobile app to deliver catalog browsing, cart, checkout, and vendor operations with a clear roadmap toward payments, offline-first, and scale.

## Table of Contents
- Overview
- Core Capabilities
- Architecture
- Project Structure
- Tech Stack
- Getting Started
- Configuration
- Key Flows
- Roadmap
- Development Standards
- Testing
- Security and Compliance
- Contribution Guidelines

## Overview
Shopree focuses on practical, high-impact commerce features with an execution plan that prioritizes auth and payments first, then offline catalog and multi-vendor operations, followed by reliability and scale. The product direction is mobile-first with support for vendor storefronts, localized payment methods (VietQR/MoMo), and a roadmap toward POS and B2B extensions.

## Core Capabilities
Current and planned capabilities (see Roadmap for sequencing):
- Authentication with Firebase verification and JWT session issuance.
- Catalog, categories, and vendor storefronts.
- Cart and order draft creation with multi-vendor support.
- Payment flows with VietQR and MoMo, including webhook updates.
- Offline-first browsing with local cache and stale-while-revalidate refresh.
- Vendor onboarding, KYC, payout ledger, and commission tracking.
- Basic analytics, loyalty, and growth features (referrals, bundles).

## Architecture
- Backend: multi-module Kotlin/Spring Boot system (Shopree API) plus a separate auth service.
- Frontend: Next.js web app.
- Mobile: Android app with layered modules and offline-first data access.
- Payment flow: order draft -> payment pending -> paid -> confirmed, with idempotent status updates.

## Project Structure
- `Backend/shopree-server` (multi-module Spring Boot)
  - `Application`: boot app, config, migrations
  - `Api`: controllers and API payloads
  - `Service`: business services and DTOs
  - `Data`: repositories
  - `Domain`: entities
  - `Security`: auth
- `Backend/auth-server`: separate Spring Boot auth service
- `Frontend`: Next.js app (`Frontend/src/app`)
- `MobileApp`: Android app (`MobileApp/app/src/main`)

## Tech Stack
- Backend: Kotlin, Spring Boot (Spring 4.0 per project guidelines), Spring Security, JPA
- Frontend: TypeScript, Next.js
- Mobile: Android, Kotlin, Hilt, Room (planned), Ktor client (planned)
- DB & migrations: Flyway (backend)

## Getting Started
Prerequisites:
- JDK 17+
- Node.js 18+
- Android Studio (for MobileApp)
- Gradle (wrapper included)

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

Frontend:
```
cd Frontend
npm install
npm run dev
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

## Key Flows
Auth flow (Week 1 target):
- Client login -> Firebase ID token -> backend verifies -> JWT session issued.
- JWT stored in DataStore; attached to API calls.
- On app start, refresh session and route by role.

Cart flow (Week 2 target):
- Product list -> product detail -> add to cart.
- Cart grouped by vendor; supports quantity updates and removal.
- Local persistence for offline use; merge with server cart on login.

Order and payment flow (Week 3 target):
- Create order draft -> payment pending.
- Initiate VietQR/MoMo payment.
- Webhook updates -> order status updated -> client shows success/failure.

## Roadmap
Phase 0 (now -> 4 weeks):
- Firebase auth verification and JWT session issuance.
- Payment MVP (VietQR/MoMo) and idempotent updates.

Phase 1 (1-2 months):
- Offline-first catalog with cache and background refresh.
- Vendor storefronts and availability hours.

Phase 2 (2-4 months):
- Vendor onboarding, KYC, and payout ledger.
- Vendor admin tooling for inventory and orders.

Phase 3 (ongoing):
- Rate limiting, caching, async jobs, read replicas/optimization.

MVP (1 month to production):
- Week 1: login -> catalog -> product detail flow; backend auth verification.
- Week 2: catalog API, pagination, and offline cache.
- Week 3: checkout MVP with payments and webhooks.
- Week 4: vendor onboarding basics, reliability, release checklist.

## Development Standards
- Kotlin/Java: 4-space indentation, PascalCase classes, camelCase functions.
- Frontend: follow ESLint rules in `Frontend/eslint.config.mjs`.
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

Frontend lint:
```
cd Frontend
npm run lint
```

## Security and Compliance
- Enforce verified email/phone for authentication.
- JWT validation with issuer and expiry checks.
- Role-based access control for vendor endpoints.
- Standardized error responses and centralized CORS configuration.

## Contribution Guidelines
- Use commit messages like `[SH-11] Short summary`.
- Include screenshots for UI changes (Frontend/MobileApp).
- Keep PRs concise with linked issues and migration/config notes.
