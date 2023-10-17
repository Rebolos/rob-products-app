# Roberto Shopping Cart Proof-of-Concept Project Summary

**Objective**: Develop a shopping cart system allowing users to add products, complete orders, and store order data.
1. [Architecture](#architecture)
2. [Features](#features)
3. [UI](#ui)
4. [Teck stack](#technology-used)

## Architecture

- **MVVM + Clean Architecture**:
  - Combines Clean Architecture's separation of concerns with MVVM's UI-focused structure.
  - Ensures clear separation of concerns for testability and maintainability.
  - MVVM separates UI logic from data and business logic through ViewModels, promoting loose coupling and testability.

## Features
- Fetch and display products from `products.json`.
- Real-time cart updates.
- Persistent cart contents.
- Generate unique order identifiers.
- Save order data as `.json` files (e.g., `order_2948281.json`) on checkout screen.


## UI

**TechStack**:
- Programming Language: Kotlin
- **Dagger Hilt**: Simplifies dependency injection in Android by generating code at compile time.
- **Room**: Android database library that streamlines database operations and object mapping.
- **Navigation Component**: Part of Android's Jetpack for managing in-app navigation.
- **Coroutines**: Facilitates asynchronous tasks in Kotlin, enhancing code readability.
- **Shared Preferences**: Stores small key-value data for app preferences and settings.
- **Timber**: Logging library for Android, improving debugging.
- **Flow Binding**: Simplifies UI component interaction using Kotlin Flow and etc  find `buildSrc module open Libs`


## Project Structure

- **App Module** - is the pesentation layer that contains the following:
  - Activies
  - Fragments
  - ViewModels
  - resources (e.g. layout, navigation, strings, menu, styles, etc.)
- **Data Module** - Supplying data to the app sourced from both local and network modules. See example below:
- [`ProductRepository`](https://github.com/Rebolos/rob-products-app/blob/develop/data/src/main/java/com/rob_products_data/feature/product/source/ProductRepository.kt)
- [`ProductRepositoryImpl`](https://github.com/Rebolos/rob-products-app/blob/develop/data/src/main/java/com/rob_products_data/feature/product/source/impl/ProductRepositoryImpl.kt)
- **Common Module**- Common modules, also known as core modules, contain code that other modules frequently use. They reduce redundancy and don't represent any specific layer in an app's architecture suc ash utils, helper,extensions. See example below
-  [`Common Utils`]([`ProductRemoteSource`](https://github.com/Rebolos/rob-products-app/blob/develop/network/src/main/java/com/example/network/feature/products/model/ProductRemoteSource.kt))
-
- **BuildSrc Module** - This module it serves as a way to abstract and simplify the process of managing dependencies, especially when working on large projects with multi module projects. See Example below
- [`BuildSrc`](https://github.com/Rebolos/rob-products-app/tree/develop/buildSrc/src/main/java)
- **Network Module** - This module is responsible for supplying data from network sources, including DTO (_Data Transfer Object_) and all APIs. See example below:
- [`ProductRemoteSource`](https://github.com/Rebolos/rob-products-app/blob/develop/network/src/main/java/com/example/network/feature/products/model/ProductRemoteSource.kt)
- [`ProductRemoteSourceImpl`](https://github.com/Rebolos/rob-products-app/blob/develop/network/src/main/java/com/example/network/feature/products/model/ProductRemoteSourceImpl.kt)
- **Local Module** - Supplying data from local sources, such as a database, shared preferences, dao (_Data Access Object_), and more. See example below:
  - [`ProductLocalSource`](https://github.com/Rebolos/rob-products-app/blob/develop/local/src/main/java/com/roberto_product/local/feature/product/ProductLocalSource.kt)
  - [`ProductLocalSourceImpl`](https://github.com/Rebolos/rob-products-app/blob/develop/local/src/main/java/com/roberto_product/local/feature/product/ProductLocalSourceImpl.kt)
- **Domain Module**: This module holds use cases responsible for managing business logic, promoting clean code organization, and adhering to the Single Responsibility principle in the SOLID framework. For a deeper understanding of use cases, you can refer to this [link](https://developer.android.com/topic/architecture/domain-layer). See example below in the actual code
  - [`UseCases`](https://github.com/Rebolos/rob-products-app/tree/develop/domain/src/main/java/com/rob_product_domain/usecase/feature/product)

- **Common Styles Module** - This Manages app theming and styling, ensuring a consistent source for fonts and enhancing project maintainability and scalability.