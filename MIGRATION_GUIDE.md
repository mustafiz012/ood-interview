# Migration Guide: Language-Based Modular Restructure

This guide documents the restructuring of the Object-Oriented Design (OOD) repository from a flat Java-only structure into language-partitioned workspaces (`java/` and `kotlin/`) with independent multi-project Gradle builds and complete idiomatic Kotlin implementations.

## Architectural Changes

### 1. Partitioned Language Workspaces
The root directory is partitioned into two language-specific directories:
- **`java/`**: Houses all 11 Java modules with an independent Gradle multi-project build (`java/settings.gradle` and `java/build.gradle`).
- **`kotlin/`**: Houses all 11 Kotlin modules with an independent Gradle multi-project build (`kotlin/settings.gradle` and `kotlin/build.gradle`).

### 2. Independent Multi-Project Builds
Each language workspace can be built and tested independently without cross-language compiler plugin overhead:
- Java build uses Java 17 toolchain and JUnit 5.
- Kotlin build uses Kotlin JVM 2.0.21 with JVM 17 toolchain and JUnit 5.

### 3. Root Build Orchestration
The root build scripts (`settings.gradle` and `build.gradle`) provide delegating tasks for cross-language convenience:
```bash
./gradlew testAll   # Runs all Java and Kotlin test suites
./gradlew buildAll  # Compiles and builds all Java and Kotlin modules
```

## Directory Structure Comparison

### Before
```
ood-interview/
├── build.gradle
├── settings.gradle
├── atm/
├── blackjack/
├── elevator_system/
├── file_search/
├── grocery_store/
├── movie_ticket/
├── parking_lot/
├── restaurant/
├── shipping_locker/
├── tic_tac_toe/
└── vending_machine/
```

### After
```
ood-interview/
├── build.gradle                        # Root orchestration / convenience tasks
├── settings.gradle                     # Root project settings
├── gradlew, gradlew.bat, gradle/       # Gradle wrapper shared across root
├── README.md                           # Main repo documentation
├── MIGRATION_GUIDE.md                  # Restructuring and migration guide
│
├── java/                               # Java independent workspace
│   ├── build.gradle                    # Java subproject build configuration
│   ├── settings.gradle                 # Java module declarations
│   ├── atm/
│   ├── blackjack/
│   ├── elevator_system/
│   ├── file_search/
│   ├── grocery_store/
│   ├��─ movie_ticket/
│   ├── parking_lot/
│   ├── restaurant/
│   ├── shipping_locker/
│   ├── tic_tac_toe/
│   └── vending_machine/
│
└── kotlin/                             # Kotlin independent workspace
    ├── build.gradle                    # Kotlin subproject build configuration
    ├── settings.gradle                 # Kotlin module declarations
    ├── atm/
    ├── blackjack/
    ├── elevator_system/
    ├── file_search/
    ├── grocery_store/
    ├── movie_ticket/
    ├── parking_lot/
    ├── restaurant/
    ├── shipping_locker/
    ├── tic_tac_toe/
    └── vending_machine/
```

## Build & Test Workflows

### Testing from Root
- Run all tests across both Java and Kotlin:
  ```bash
  ./gradlew testAll
  ```
- Build everything:
  ```bash
  ./gradlew buildAll
  ```

### Testing Java Modules Only
- Run all Java tests:
  ```bash
  ./gradlew -p java test
  ```
- Run a specific Java module test:
  ```bash
  ./gradlew -p java :atm:test
  ./gradlew -p java :vendingmachine:test
  ```

### Testing Kotlin Modules Only
- Run all Kotlin tests:
  ```bash
  ./gradlew -p kotlin test
  ```
- Run a specific Kotlin module test:
  ```bash
  ./gradlew -p kotlin :atm:test
  ./gradlew -p kotlin :vendingmachine:test
  ```

## Kotlin Design Principles Applied
The Kotlin modules follow modern idiomatic Kotlin conventions:
- **Data Classes**: Value-based domain models (`Item`, `Product`, `Card`, `Player`, `Ticket`) leverage concise `data class` definitions with automatic `equals`, `hashCode`, and `toString`.
- **Property Access & Immutability**: Elimination of boilerplate getters/setters in favor of `val` and `var` properties with encapsulated setters (`private set`).
- **Null Safety**: Strict compile-time null safety with safe calls (`?.`) and explicit elvis operators (`?:`) replacing runtime null pointer checks.
- **Concise Functional Expressions**: Stream operations converted to Kotlin standard library collections methods (`map`, `fold`, `all`, `any`, `flatten`).
- **Standard Project Layout**: Each Kotlin subproject follows the standard Gradle directory conventions (`src/main/kotlin` and `src/test/kotlin`).
