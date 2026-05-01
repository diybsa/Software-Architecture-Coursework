# Software Architecture Coursework – Sprint 2 Report
**Team:** Ahmed & Mostafa  
**Sprint:** 2 – Developing Architecture from Requirements

---

## 1. Introduction

This sprint focuses on developing a formal software architecture from the requirements specification produced in Sprint 1. The system is a **Share Price Comparison Application** that allows users to retrieve historical share price data, store it locally, and compare two companies' performance visually.

The architecture is derived using a structured process: starting from a business concept model, progressing through use cases and system interfaces, and arriving at a layered system architecture implemented using **Clean Architecture** principles in Java.

---

## 2. Business Concept Model

![Business Concept](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/business-concept.png)

The business concept model identifies the core domain entities and the relationships between them.

### Entities

| Entity | Description |
|--------|-------------|
| User | The person using the application to request and view share data |
| Share | A financial asset identified by a ticker symbol (e.g. AAPL, MSFT) |
| PriceData | A single daily price record for a Share, containing a date and closing price |
| MarketDataProvider | The external system responsible for supplying historical share price data |

### Relationships

- A **User** requests data for one or more **Shares**
- A **Share** contains a collection of **PriceData** records representing its price history
- The system retrieves **Share** data from a **MarketDataProvider**
- **PriceData** is associated with exactly one **Share**

---

## 3. Use Case Model

![Use Case Diagram](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/use-case.png)

### Main Actor
- **User** — the primary actor who interacts with the system

### Use Cases

| Use Case | Description |
|----------|-------------|
| Get Share Data | Retrieve historical price data for a given share symbol and date range |
| Compare Two Shares | Retrieve and display data for two shares simultaneously |
| View Share Chart | View a graphical representation of share price over time |

---

### Use Case: Get Share Data

| Step | Actor | Action |
|------|-------|--------|
| 1 | User | Enters a share ticker symbol |
| 2 | User | Specifies a start date and end date |
| 3 | System | Validates that start date is before end date |
| 4 | System | Validates that the date range does not exceed two years |
| 5 | System | Calls MarketDataProvider to fetch share price data |
| 6 | System | Stores the retrieved data via ShareRepository |
| 7 | System | Returns the Share object to the caller |

**Alternative flow:** If validation fails (dates invalid or range too long), the system throws an `IllegalArgumentException` and processing stops.

---

### Use Case: Compare Two Shares

| Step | Actor | Action |
|------|-------|--------|
| 1 | User | Enters two share ticker symbols |
| 2 | System | Executes Get Share Data for the first symbol |
| 3 | System | Executes Get Share Data for the second symbol |
| 4 | System | Passes both data sets to the chart renderer |
| 5 | System | Renders both price lines on the same chart |
| 6 | User | Views and compares the two share performances |

---

## 4. System Interfaces

The system defines two interfaces that decouple the application layer from its infrastructure dependencies.

### MarketDataProvider

Responsible for retrieving historical share price data from an external source.

```java
public interface MarketDataProvider {
    Share fetchShareData(String symbol, LocalDate start, LocalDate end);
}
```

| Operation | Parameters | Returns | Description |
|-----------|-----------|---------|-------------|
| `fetchShareData` | symbol: String, start: LocalDate, end: LocalDate | Share | Retrieves price history for the given symbol and date range |

---

### ShareRepository

Responsible for storing and retrieving share data locally, enabling offline functionality.

```java
public interface ShareRepository {
    void save(Share share);
    Share findBySymbol(String symbol);
}
```

| Operation | Parameters | Returns | Description |
|-----------|-----------|---------|-------------|
| `save` | share: Share | void | Persists a Share object to local storage |
| `findBySymbol` | symbol: String | Share | Retrieves a previously stored Share by its ticker symbol |

---

### Design Rationale

By defining `MarketDataProvider` and `ShareRepository` as interfaces, the application layer (`ShareService`) depends only on the interface contracts — not on any specific implementation. This means:

- The mock implementations used in this coursework can be swapped for real implementations (e.g. a Yahoo Finance client, a SQLite database) without changing any application logic
- Each component can be tested independently using mock/stub implementations
- The system satisfies the **Dependency Inversion Principle**: high-level modules do not depend on low-level modules; both depend on abstractions

---

## 5. Business Type Model

![Class Diagram](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/class-diagram.png)

The business type model defines the structure of the domain entities used in the system.

### Share

| Member | Type | Description |
|--------|------|-------------|
| `symbol` | String | The ticker symbol identifying the company (e.g. "AAPL") |
| `priceHistory` | List\<PriceData\> | The list of daily price records for this share |
| `getSymbol()` | String | Returns the ticker symbol |
| `getPriceHistory()` | List\<PriceData\> | Returns the full price history |

### PriceData

| Member | Type | Description |
|--------|------|-------------|
| `date` | LocalDate | The date of this price record |
| `closePrice` | double | The closing price on that date |
| `getDate()` | LocalDate | Returns the date |
| `getClosePrice()` | double | Returns the closing price |

### Relationship

A `Share` **aggregates** multiple `PriceData` objects. The `Share` is the parent — it owns the list of `PriceData` records. Removing a `Share` would remove all its associated `PriceData`.

---

## 6. Initial System Architecture

![Architecture Diagram](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/%20architecture-diagram.png)

The system architecture follows **Clean Architecture**, organising the system into four layers. Dependencies always point inward — outer layers depend on inner layers, never the other way around.

### Layers

| Layer | Package | Components | Responsibility |
|-------|---------|-----------|----------------|
| Presentation | `presentation/` | ShareController | Receives user requests; delegates to application layer |
| Application | `application/` | ShareService | Validates input; coordinates data retrieval and storage |
| Domain | `domain/` | Share, PriceData | Core business entities; no external dependencies |
| Infrastructure | `infrastructure/` | MarketDataProvider, ShareRepository | Data access and external integrations |

### Interface Allocation

| Component | Implements | Depends On |
|-----------|-----------|------------|
| ShareController | `requestShare()` | ShareService |
| ShareService | `getShareData()` | MarketDataProvider, ShareRepository |
| MarketDataProvider | `fetchShareData()` | — |
| ShareRepository | `save()`, `findBySymbol()` | — |

`ShareService` is the only component that depends on more than one interface, reflecting its role as the coordinator of the application logic. All other components implement or depend on a single interface, keeping responsibilities narrow and focused.

---

## 7. Business Interfaces and Collaboration

The sequence diagram below illustrates how components collaborate to handle the primary use case.

![Sequence Diagram](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/sequence-diagram.png)

### Interaction Flow

| Step | From | To | Operation | Notes |
|------|------|----|-----------|-------|
| 1 | User | ShareController | `requestShare(symbol, start, end)` | Entry point |
| 2 | ShareController | ShareService | `getShareData(symbol, start, end)` | Delegates request |
| 3 | ShareService | ShareService | Validate input | Checks date order and 2-year limit |
| 4 | ShareService | MarketDataProvider | `fetchShareData(symbol, start, end)` | Retrieve data |
| 5 | ShareService | ShareRepository | `save(share)` | Persist data locally |
| 6 | ShareService | ShareController | return Share | Return result |
| 7 | ShareController | User | return Share | Deliver to user |

All communication is synchronous. Each interface operation corresponds directly to a step in this sequence, ensuring the business interface design is fully traceable to the use case model.

---

## 8. Clean Architecture Implementation

### Package Structure

```
com.Ga3/
 ├── domain/
 │   ├── Share.java             — Core entity: symbol + price history
 │   └── PriceData.java         — Core entity: date + closing price
 ├── application/
 │   └── ShareService.java      — Business logic: validate, fetch, store
 ├── infrastructure/
 │   ├── MarketDataProvider.java — Interface: external data retrieval
 │   └── ShareRepository.java   — Interface: local data persistence
 ├── presentation/
 │   └── ShareController.java   — Entry point: delegates to ShareService
 └── Main.java                  — Wires mock implementations; runs system

usecases/
 └── GetShareDataUseCase.java   — Use case layer: encapsulates retrieval rules
```

### Key Implementation Details

**ShareService.java** enforces two business rules:
1. Start date must be before end date — throws `IllegalArgumentException` if violated
2. Date range must not exceed two years — throws `IllegalArgumentException` if violated

**Main.java** uses inner classes (`MockMarketDataProvider`, `MockShareRepository`) to provide concrete implementations of the interfaces, demonstrating that the architecture is wired correctly and the system runs end to end without a real data source.

**GetShareDataUseCase.java** demonstrates the use case layer concept from Clean Architecture — a separate class that encapsulates the application-specific rules for data retrieval, independently of `ShareService`.

---

## 9. Conclusion

Sprint 2 successfully transformed the requirements specification into a structured, formally documented software architecture. The models produced — business concept, use cases, system interfaces, business type model, and system architecture — are all consistent with each other and directly traceable to the original requirements.

The Clean Architecture implementation in Java reflects the architectural design precisely, with each layer, interface, and component matching its specification in the models. The use of interfaces for `MarketDataProvider` and `ShareRepository` ensures the system is extensible, maintainable, and independently testable.
