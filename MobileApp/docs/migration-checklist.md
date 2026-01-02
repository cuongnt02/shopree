# Mobile Module Migration Checklist

This checklist maps current classes to target modules. Move in this order to reduce dependency churn.

## 1) Core model
- Move `MobileApp/app/src/main/java/com/ntc/shopree/domain/models/Category.kt` -> `MobileApp/core/model/src/main/java/com/ntc/shopree/core/model/Category.kt`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/domain/models/Product.kt` -> `MobileApp/core/model/src/main/java/com/ntc/shopree/core/model/Product.kt`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/domain/models/User.kt` -> `MobileApp/core/model/src/main/java/com/ntc/shopree/core/model/User.kt`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/domain/models/Session.kt` -> `MobileApp/core/model/src/main/java/com/ntc/shopree/core/model/Session.kt`
- Update imports from `com.ntc.shopree.domain.models.*` to `com.ntc.shopree.core.model.*`

## 2) Core UI
- Move `MobileApp/app/src/main/java/com/ntc/shopree/ui/components/*` -> `MobileApp/core/ui/src/main/java/com/ntc/shopree/core/ui/components/*`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/ui/icons/*` -> `MobileApp/core/ui/src/main/java/com/ntc/shopree/core/ui/icons/*`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/ui/theme/*` -> `MobileApp/core/ui/src/main/java/com/ntc/shopree/core/ui/theme/*`
- Update imports in screens to `com.ntc.shopree.core.ui.*`

## 3) Core network
- Move `MobileApp/app/src/main/java/com/ntc/shopree/data/remote/service/*` -> `MobileApp/core/network/src/main/java/com/ntc/shopree/core/network/service/*`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/data/remote/service/impl/*` -> `MobileApp/core/network/src/main/java/com/ntc/shopree/core/network/service/impl/*`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/data/remote/dto/*` -> `MobileApp/core/network/src/main/java/com/ntc/shopree/core/network/dto/*`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/data/remote/mock/*` -> `MobileApp/core/network/src/main/java/com/ntc/shopree/core/network/mock/*`

## 4) Feature: auth
- Move `MobileApp/app/src/main/java/com/ntc/shopree/ui/screen/auth/*` -> `MobileApp/feature/auth/src/main/java/com/ntc/shopree/feature/auth/ui/*`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/data/remote/service/impl/FirebaseServiceImpl.kt` -> `MobileApp/feature/auth/src/main/java/com/ntc/shopree/feature/auth/data/FirebaseServiceImpl.kt`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/domain/repository/FirebaseRepository.kt` -> `MobileApp/feature/auth/src/main/java/com/ntc/shopree/feature/auth/domain/FirebaseRepository.kt`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/domain/repository/impl/FirebaseRepositoryImpl.kt` -> `MobileApp/feature/auth/src/main/java/com/ntc/shopree/feature/auth/data/FirebaseRepositoryImpl.kt`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/domain/usecase/CheckCurrentUserUseCase.kt` -> `MobileApp/feature/auth/src/main/java/com/ntc/shopree/feature/auth/domain/CheckCurrentUserUseCase.kt`

## 5) Feature: catalog
- Move `MobileApp/app/src/main/java/com/ntc/shopree/ui/screen/products/*` -> `MobileApp/feature/catalog/src/main/java/com/ntc/shopree/feature/catalog/ui/*`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/domain/repository/CategoryRepository.kt` -> `MobileApp/feature/catalog/src/main/java/com/ntc/shopree/feature/catalog/domain/CategoryRepository.kt`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/domain/repository/ProductRepository.kt` -> `MobileApp/feature/catalog/src/main/java/com/ntc/shopree/feature/catalog/domain/ProductRepository.kt`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/domain/repository/impl/CategoryRepositoryImpl.kt` -> `MobileApp/feature/catalog/src/main/java/com/ntc/shopree/feature/catalog/data/CategoryRepositoryImpl.kt`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/domain/repository/impl/ProductRepositoryImpl.kt` -> `MobileApp/feature/catalog/src/main/java/com/ntc/shopree/feature/catalog/data/ProductRepositoryImpl.kt`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/domain/usecase/GetCategoriesUseCase.kt` -> `MobileApp/feature/catalog/src/main/java/com/ntc/shopree/feature/catalog/domain/GetCategoriesUseCase.kt`
- Move `MobileApp/app/src/main/java/com/ntc/shopree/domain/usecase/GetProductsUseCase.kt` -> `MobileApp/feature/catalog/src/main/java/com/ntc/shopree/feature/catalog/domain/GetProductsUseCase.kt`

## 6) App shell
- Keep `MobileApp/app/src/main/java/com/ntc/shopree/ui/ShopreeApp.kt` and `MainActivity.kt` in `:app`.
- Replace `AppContainer` with DI (Hilt/Koin) after features are extracted.
- Update navigation to reference feature entry builders from `:feature:auth` and `:feature:catalog`.

## 7) Deferred features
- Introduce `:feature:cart`, `:feature:checkout`, `:feature:orders`, `:feature:vendor` as new packages when you build those flows.
