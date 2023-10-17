# Roberto Shopping Cart Proof-of-Concept Project Summary

**Objective**: Develop a shopping cart system allowing users to add products, complete orders, and store order data.
1. [Architecture](#architecture)
2. [Features](#features)
3. [User Interface](#user-interface)
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


## User Interface

### Product List
![Product List](https://github.com/Rebolos/rob-products-app/assets/67263430/a085bab3-6bcb-49e8-abbc-7cd923a67df3)

### Cart List
![Cart List](https://github.com/Rebolos/rob-products-app/assets/67263430/053b4368-712c-4748-8d9b-4527d9756581)

### Order Confirmation
![Order Confirmation](https://github.com/Rebolos/rob-products-app/assets/67263430/3599d470-bfbc-4f93-9a65-329ccf341421)

**TechStack**:
- Programming Language: Kotlin
- **Dagger Hilt**: Simplifies dependency injection in Android by generating code at compile time.
- **Room**: Android database library that streamlines database operations and object mapping.
- **Navigation Component**: Part of Android's Jetpack for managing in-app navigation.
- **Coroutines**: Facilitates asynchronous tasks in Kotlin, enhancing code readability.
- **Shared Preferences**: Stores small key-value data for app preferences and settings.
- **Timber**: Logging library for Android, improving debugging.
- **Flow Binding**: Simplifies UI component interaction using Kotlin Flow and more. To find the library in the `buildSrc` module, open [Libs.kt](https://github.com/Rebolos/rob-products-app/blob/develop/buildSrc/src/main/java/Libs.kt).


## Project Structure

- **App Module**: Presentation layer that contains:
  - Activities
  - Fragments
  - ViewModels
  - Resources (e.g. layout, navigation, strings, menu, styles, etc.)

- **Data Module**: Supplies data to the app from both local and network modules. See examples below:
  - [`ProductRepository`](https://github.com/Rebolos/rob-products-app/blob/develop/data/src/main/java/com/rob_products_data/feature/product/source/ProductRepository.kt)
  - [`ProductRepositoryImpl`](https://github.com/Rebolos/rob-products-app/blob/develop/data/src/main/java/com/rob_products_data/feature/product/source/impl/ProductRepositoryImpl.kt)

- **Common Module**: Common modules, also known as core modules, contain code frequently used across modules. They reduce redundancy and don't represent any specific layer in an app's architecture. See example below:
  - [`Common Utils`](https://github.com/Rebolos/rob-products-app/tree/develop/common/src/main/java/com/rob_product_common)

- **BuildSrc Module**: This module serves as a way to abstract and simplify the process of managing dependencies, especially when working on large multi-module projects. See example below:
  - [`BuildSrc`](https://github.com/Rebolos/rob-products-app/tree/develop/buildSrc/src/main/java)

- **Network Module**: This module is responsible for supplying data from network sources, including DTOs (Data Transfer Objects) and all APIs. See examples below:
  - [`ProductRemoteSource`](https://github.com/Rebolos/rob-products-app/blob/develop/network/src/main/java/com/example/network/feature/products/model/ProductRemoteSource.kt)
  - [`ProductRemoteSourceImpl`](https://github.com/Rebolos/rob-products-app/blob/develop/network/src/main/java/com/example/network/feature/products/model/ProductRemoteSourceImpl.kt)

- **Local Module**: Supplies data from local sources, such as a database, shared preferences, DAOs (Data Access Objects), and more. See examples below:
  - [`ProductLocalSource`](https://github.com/Rebolos/rob-products-app/blob/develop/local/src/main/java/com/roberto_product/local/feature/product/ProductLocalSource.kt)
  - [`ProductLocalSourceImpl`](https://github.com/Rebolos/rob-products-app/blob/develop/local/src/main/java/com/roberto_product/local/feature/product/ProductLocalSourceImpl.kt)

- **Domain Module**: This module holds use cases responsible for managing business logic, promoting clean code organization, and adhering to the Single Responsibility principle in the SOLID framework. For a deeper understanding of use cases, you can refer to this [link](https://developer.android.com/topic/architecture/domain-layer). See examples below in the actual code:
  - [`UseCases`](https://github.com/Rebolos/rob-products-app/tree/develop/domain/src/main/java/com/rob_product_domain/usecase/feature/product)

- **Common Styles Module**: Manages app theming and styling, ensuring a consistent source for fonts and enhancing project maintainability and scalability.
