# Software Architecture Coursework – Sprint 2 Report
**Team:** Ahmed & Mostafa
**Sprint:** 2 – Developing Architecture from Requirements

---

## 1. Introduction

This sprint develops a formal software architecture from the requirements specification produced in Sprint 1. The system is a **Share Price Comparison Application** that allows users to retrieve historical share price data, store it locally for offline use, and compare two companies' price performance visually.

The architecture is derived using a structured process: starting from a business concept model, progressing through use cases and system interfaces to a business type model and an initial system architecture, then discovering business interfaces, and finally implementing the result in Java using **Clean Architecture** principles.

---

## 2. Business Concept Model

![Business Concept](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/business-concept.png)

The business concept model captures the real-world entities the system reasons about and how they are related. It is deliberately technology-free — these concepts would exist even without software.

### Entities

| Entity | Description |
|--------|-------------|
| **User** | The person using the application to request and view share data |
| **Share** | A financial asset identified by a ticker symbol (e.g. AAPL, MSFT) |
| **PriceData** | A single daily price record for a share, comprising a date and closing price |
| **MarketDataProvider** | The external source from which historical share prices are obtained |

### Relationships

- A **User** *requests* data for one or more **Shares**
- A **Share** *contains* a collection of **PriceData** records representing its price history
- **PriceData** is associated with exactly one **Share** (composition: a PriceData entry has no meaning without its parent Share)
- The system *retrieves* **Share** data from a **MarketDataProvider**

These four entities and four relationships are the minimum set required to express every functional requirement (FR1–FR8). Each entity is later realised either as a domain class (Share, PriceData), a port (MarketDataProvider), or an actor (User).

---

## 3. Use Case Model

![Use Case Diagram](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/use-case.png)

### Main Actor
- **User** — the primary actor; interacts with the system through the web UI.

### Use Cases

| Use Case | Description | Requirements Covered |
|----------|-------------|----------------------|
| Get Share Data | Retrieve historical price data for a given share symbol and date range | FR1, FR2, FR3 |
| Compare Two Shares | Retrieve and display data for two shares on a single chart | FR5, FR6 |
| View Share Chart | View the rendered graph of share prices over time | FR5 |

---

### Use Case: Get Share Data

| Step | Actor | Action |
|------|-------|--------|
| 1 | User | Enters a share ticker symbol |
| 2 | User | Specifies a start date and end date |
| 3 | System | Validates that start date is before end date |
| 4 | System | Validates that the date range does not exceed two years |
| 5 | System | Calls `MarketDataProvider` to fetch share price data |
| 6 | System | Stores the retrieved data via `ShareRepository` |
| 7 | System | Returns the populated `Share` object to the caller |

**Alternative flow:** If any validation fails (empty symbol, invalid dates, range too long), the system throws `IllegalArgumentException` and processing stops. No data is fetched or stored.

---

### Use Case: Compare Two Shares

| Step | Actor | Action |
|------|-------|--------|
| 1 | User | Enters two share ticker symbols and a date range |
| 2 | System | Executes *Get Share Data* for the first symbol |
| 3 | System | Executes *Get Share Data* for the second symbol |
| 4 | System | Returns both `Share` objects as a comparison result |
| 5 | System | Renders both price lines on the same chart |
| 6 | User | Views and compares the two share performances |

This use case **includes** *Get Share Data* twice, reusing its validation and retrieval logic rather than duplicating it.

---

## 4. System Interfaces

System interfaces are the interfaces between the system and its surrounding environment — they decouple the application from the technology used for external data and persistence.

### MarketDataProvider

Responsible for retrieving historical share price data from an external source.

```java
public interface MarketDataProvider {
    Share fetchShareData(String symbol, LocalDate start, LocalDate end);
}
```

| Operation | Parameters | Returns | Description |
|-----------|-----------|---------|-------------|
| `fetchShareData` | symbol: String, start: LocalDate, end: LocalDate | Share | Retrieves price history for the given symbol over the given range |

### ShareRepository

Responsible for storing and retrieving share data locally, enabling offline functionality (NFR1).

```java
public interface ShareRepository {
    void save(Share share);
    Share findBySymbol(String symbol);
}
```

| Operation | Parameters | Returns | Description |
|-----------|-----------|---------|-------------|
| `save` | share: Share | void | Persists a Share to local storage |
| `findBySymbol` | symbol: String | Share | Retrieves a previously stored Share by symbol (null if not present) |

### Design Rationale

Both interfaces are owned by the **application layer** (package `com.Ga3.application.ports`) — not the infrastructure layer. This is the Clean Architecture port–adapter pattern: high-level modules define what they need; low-level adapters implement it. This means:

- The mock implementations used in this coursework can be swapped for real ones (Yahoo Finance client, SQLite database) without changing any application logic.
- Each layer can be tested in isolation by supplying test doubles for the ports.
- The system satisfies the **Dependency Inversion Principle**: high-level modules do not depend on low-level modules; both depend on abstractions.

---

## 5. Business Type Model

![Class Diagram](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/class-diagram.png)

The business type model takes the entities from the business concept model and gives each one a precise type definition with attributes and operations. This is the boundary at which informal concepts become formal types that the code can implement.

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

### Alignment to the Business Concept Model

Each entity in the business concept model maps directly to a type in the business type model:

| Business Concept | Business Type | Realised As |
|------------------|---------------|-------------|
| Share | `Share` class | Domain entity in `com.Ga3.domain` |
| PriceData | `PriceData` class | Domain entity in `com.Ga3.domain` |
| MarketDataProvider | `MarketDataProvider` interface | Port in `com.Ga3.application.ports` |
| User | (external actor) | Not a type — represented by the human user |

A `Share` **aggregates** multiple `PriceData` objects: the share owns its price history, and a PriceData entry has no independent existence outside its share.

---

## 6. Initial System Architecture

![Architecture Diagram](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/%20architecture-diagram.png)

The system architecture follows **Clean Architecture**, organising the system into four layers. Dependencies point inward only — outer layers know about inner layers, never the reverse.

### Layers

| Layer | Package | Components | Responsibility |
|-------|---------|-----------|----------------|
| Presentation | `presentation/` | `ShareController`, `ShareRequestHandler` (interface) | Receives user requests; delegates to the application layer |
| Application | `application/` | `ShareService`, `GetShareDataUseCase`, `CompareTwoSharesUseCase`, ports | Encodes business rules; coordinates the flow; owns abstractions of what it needs |
| Domain | `domain/` | `Share`, `PriceData` | Core entities; no dependencies on any other layer |
| Infrastructure | `infrastructure/` | `MockMarketDataProvider`, `InMemoryShareRepository` | Concrete adapters that implement the application's ports |

### Interface Allocation

| Component | Implements (provided) | Depends on (required) | # provided | # required |
|-----------|----------------------|----------------------|-----------:|-----------:|
| `ShareController` | `ShareRequestHandler` | `ShareService` | 1 | 1 |
| `ShareService` | (its own facade methods) | `GetShareDataUseCase`, `CompareTwoSharesUseCase` | 1 | 2 |
| `GetShareDataUseCase` | (its `execute` operation) | `MarketDataProvider`, `ShareRepository` | 1 | 2 |
| `CompareTwoSharesUseCase` | (its `execute` operation) | `GetShareDataUseCase` | 1 | 1 |
| `MockMarketDataProvider` | `MarketDataProvider` | — | 1 | 0 |
| `InMemoryShareRepository` | `ShareRepository` | — | 1 | 0 |

### Discussion: which components have more than one interface?

- `ShareService` and `GetShareDataUseCase` are the only components that depend on **more than one** interface. This reflects their coordinator role — they assemble a flow from several smaller capabilities.
- Every other component provides or depends on **exactly one** interface, keeping each class focused on a single responsibility.
- No component both provides multiple interfaces *and* requires multiple interfaces — concentrating that complexity in a single class would have created a coordinator anti-pattern.

The architectural rule "dependencies point inward" is preserved: `presentation` → `application` → (ports defined in application, implemented in `infrastructure`) → `domain`. The infrastructure layer depends on the application layer (it implements interfaces owned by application), not the other way around.

---

## 7. Business Interfaces

System interfaces are about technical boundaries with the outside world. **Business interfaces** are higher-level — they expose the operations the *business components* offer to each other, derived directly from the use cases.

The system has three business components (groupings of related responsibilities) and three corresponding business interfaces:

| Business Component | Business Interface | Operations | Source Use Case |
|--------------------|--------------------|-----------|------------------|
| Presentation Component | `ShareRequestHandler` | `requestShare(symbol, start, end): Share` | Get Share Data |
| Application Component | `ShareService` (façade) | `getShareData(symbol, start, end): Share`<br>`compareShares(a, b, start, end): Result` | Get Share Data, Compare Two Shares |
| Use Case Component | `GetShareDataUseCase`, `CompareTwoSharesUseCase` | `execute(...)` | One per use case |

Each business interface operation traces directly back to a step in a use case:

| Use Case Step | Business Interface Operation |
|---------------|------------------------------|
| Get Share Data step 3–7 | `GetShareDataUseCase.execute(symbol, start, end)` |
| Compare Two Shares steps 1–4 | `ShareService.compareShares(a, b, start, end)` |
| User-facing entry (any use case) | `ShareRequestHandler.requestShare(symbol, start, end)` |

This separation matters: a future change to the *technical* interface for fetching data (e.g. moving from a Java method to an HTTP REST call) would not change the business interface, because the business interface is defined in terms of business operations, not transport mechanics.

---

### Collaboration Diagram — Get Share Data

The collaboration diagram below shows how the business and system interfaces are invoked at runtime for the primary use case. Messages are numbered in execution order.

![Collaboration Diagram](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/collaboration-diagram.png)

| # | From | To | Message | Notes |
|---|------|----|---------| ----- |
| 1 | `:User` | `:ShareController` | `requestShare(symbol, start, end)` | Business interface (Presentation) |
| 2 | `:ShareController` | `:ShareService` | `getShareData(symbol, start, end)` | Business interface (Application) |
| 3 | `:ShareService` | `:GetShareDataUseCase` | `execute(symbol, start, end)` | Business interface (Use Case) |
| 4 | `:GetShareDataUseCase` | `:GetShareDataUseCase` | `validate(input)` | Internal — enforces date and range rules |
| 5 | `:GetShareDataUseCase` | `:MockMarketDataProvider` | `fetchShareData(symbol, start, end)` | System interface (port) |
| 6 | `:GetShareDataUseCase` | `:InMemoryShareRepository` | `save(share)` | System interface (port) |

The diagram makes the inward direction of control explicit: the `User` calls into Presentation, which calls into the Application façade, which delegates to a use case object, which finally invokes ports implemented by infrastructure adapters. Returns flow back along the reverse path.

### Sequence Diagram (alternative view of the same collaboration)

![Sequence Diagram](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/sequence-diagram.png)

---

## 8. Clean Architecture Implementation

### Package Structure

```
com.Ga3/
 ├── domain/
 │   ├── Share.java                       — entity: symbol + price history
 │   └── PriceData.java                   — entity: date + closing price
 │
 ├── application/                          — business rules & abstractions
 │   ├── ports/                              — interfaces owned by application
 │   │   ├── MarketDataProvider.java
 │   │   └── ShareRepository.java
 │   ├── usecases/
 │   │   ├── GetShareDataUseCase.java     — validate → fetch → save → return
 │   │   └── CompareTwoSharesUseCase.java — composes two GetShareData calls
 │   └── ShareService.java                — application façade over use cases
 │
 ├── infrastructure/                       — adapters: implement ports
 │   ├── MockMarketDataProvider.java
 │   └── InMemoryShareRepository.java
 │
 ├── presentation/                         — entry point
 │   ├── ShareRequestHandler.java         — provided interface
 │   └── ShareController.java             — implements ShareRequestHandler
 │
 └── Main.java                             — composition root: wires it all up
```

### Where business rules live

`GetShareDataUseCase` is the single source of truth for the Get Share Data business rules:

1. Symbol must be non-empty
2. Start and end dates must both be supplied
3. Start date must be before end date
4. Date range must not exceed two years

`ShareService` and `ShareController` contain **no** business rules — they delegate. This makes the use cases independently testable and prevents the rules from drifting out of sync if the same logic were duplicated in multiple places.

### Dependency direction

| Layer | Depends on |
|-------|-----------|
| `presentation` | `application` |
| `application` | `domain` |
| `infrastructure` | `application` (implements its ports), `domain` |
| `domain` | nothing |

Critically, the infrastructure layer depends on the application layer — not the other way around. The application layer never `import`s from `infrastructure`. This is the dependency inversion at the heart of Clean Architecture, and it is the change from earlier drafts where the ports incorrectly lived in `infrastructure/`.

### Composition

`Main.java` is the composition root. It is the **only** place in the system that instantiates infrastructure adapters. Anywhere else, code talks to a port, not a concrete class:

```java
MarketDataProvider marketData = new MockMarketDataProvider();
ShareRepository    repository  = new InMemoryShareRepository();

GetShareDataUseCase getShareData =
        new GetShareDataUseCase(marketData, repository);
CompareTwoSharesUseCase compareShares =
        new CompareTwoSharesUseCase(getShareData);

ShareService          service     = new ShareService(getShareData, compareShares);
ShareRequestHandler   controller  = new ShareController(service);
```

To swap the mock data provider for a real Yahoo Finance client, only this one block of wiring code would change.

---

## 9. Traceability

Each requirement from Sprint 1 traces to a concrete artefact in Sprint 2:

| Requirement | Realised by |
|-------------|-------------|
| FR1 (retrieve share data) | `GetShareDataUseCase.execute()` via `MarketDataProvider` port |
| FR2 (specify date range) | Parameters on `GetShareDataUseCase.execute()` |
| FR3 (max 2-year range) | Validation rule inside `GetShareDataUseCase` |
| FR4 (persistent local storage) | `ShareRepository` port + `InMemoryShareRepository` adapter |
| FR5 / FR6 (compare two shares) | `CompareTwoSharesUseCase` + `ShareService.compareShares` |
| FR7 (validation, error messages) | `GetShareDataUseCase` validation block |
| NFR1 (works offline) | Repository adapter is local-only |
| NFR2 (Clean Architecture) | Package structure, port–adapter pattern, dependency inversion |
| NFR3 (modular, replaceable components) | Ports + Composition root pattern |

---

## 10. Conclusion

Sprint 2 takes the requirements specification from Sprint 1 and develops it into a full software architecture through a structured chain of models: business concept → use cases → system interfaces → business types → initial system architecture → business interfaces → implementation. Each model justifies the next, and the implementation realises every interface and operation discovered along the way.

The Clean Architecture structure (domain, application, infrastructure, presentation, with ports owned by application) ensures that the business rules can evolve independently of the technology used to deliver them, and that the system meets its non-functional requirements for modularity, extensibility, and offline availability.
