# Object-Oriented Design Examples

Companion code repository for "Object-Oriented Design Interview" by ByteByteGo.

This repository is organized into language-based directories at the root level, containing independent multi-project Gradle builds for both **Java** and **Kotlin**:
- **`java/`**: Original Java implementations using Java 17 and JUnit 5.
- **`kotlin/`**: Idiomatic Kotlin implementations leveraging modern features (data classes, null safety, expression bodies, and standard Gradle layout).

## Quick Start

```bash
git clone <repo-url>
cd ood-interview

# Run all tests across both Java and Kotlin
./gradlew testAll

# Build everything
./gradlew buildAll
```

## Running Language-Specific Builds

Each language directory functions as an independent Gradle multi-project build:

### Java Workspace (`java/`)
```bash
# Run all Java tests
./gradlew -p java test

# Test a specific Java project
./gradlew -p java :atm:test
./gradlew -p java :blackjack:test
./gradlew -p java :vendingmachine:test
```

### Kotlin Workspace (`kotlin/`)
```bash
# Run all Kotlin tests
./gradlew -p kotlin test

# Test a specific Kotlin project
./gradlew -p kotlin :atm:test
./gradlew -p kotlin :blackjack:test
./gradlew -p kotlin :vendingmachine:test
```

## Available Projects

| Project | Java Test Command | Kotlin Test Command |
|---|---|---|
| ATM System | `./gradlew -p java :atm:test` | `./gradlew -p kotlin :atm:test` |
| Blackjack | `./gradlew -p java :blackjack:test` | `./gradlew -p kotlin :blackjack:test` |
| Elevator System | `./gradlew -p java :elevator:test` | `./gradlew -p kotlin :elevator:test` |
| File Search | `./gradlew -p java :filesearch:test` | `./gradlew -p kotlin :filesearch:test` |
| Grocery Store | `./gradlew -p java :grocerystore:test` | `./gradlew -p kotlin :grocerystore:test` |
| Movie Ticket | `./gradlew -p java :movieticket:test` | `./gradlew -p kotlin :movieticket:test` |
| Parking Lot | `./gradlew -p java :parkinglot:test` | `./gradlew -p kotlin :parkinglot:test` |
| Restaurant | `./gradlew -p java :restaurant:test` | `./gradlew -p kotlin :restaurant:test` |
| Shipping Locker | `./gradlew -p java :shippinglocker:test` | `./gradlew -p kotlin :shippinglocker:test` |
| Tic-Tac-Toe | `./gradlew -p java :tictactoe:test` | `./gradlew -p kotlin :tictactoe:test` |
| Vending Machine | `./gradlew -p java :vendingmachine:test` | `./gradlew -p kotlin :vendingmachine:test` |

## Repository Structure

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
│   ├── movie_ticket/
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

## Requirements

- **Java 17+** (Java 21 LTS recommended)
