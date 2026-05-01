# Share Price Comparison – Group Report
**Team Members:** Ahmed & Mostafa  
**Module:** Software Architecture and Design  

---

## 1. Introduction

This report documents the full design and development of a Share Price Comparison web application, completed across three sprints. The system allows users to retrieve historical share price data, store it locally, and compare the performance of two companies visually through a chart.

The project was developed as a two-person Scrum team by Ahmed and Mostafa. Throughout the project we applied key software architecture and design principles including Clean Architecture, compound components, domain-independent architectural styles, and Service-Oriented Architecture (SOA). The focus was on architectural quality and design rigour rather than UI complexity.

---

## 2. Team Meetings Log

| # | Date | Attendees | What Was Discussed | Decisions Made |
|---|------|-----------|-------------------|----------------|
| 1 | 28/01/2026 | Ahmed, Mostafa | Reviewed the coursework brief, discussed scope and what the system needs to do | Agreed to build a share price comparison app using Java and HTML; split initial responsibilities |
| 2 | 03/02/2026 | Ahmed, Mostafa | Discussed requirements — functional and non-functional; reviewed what Clean Architecture means for this project | Agreed on the 5 functional requirements; Mostafa to write requirements doc, Ahmed to set up GitHub |
| 3 | 10/02/2026 | Ahmed, Mostafa | Reviewed the component specification diagram draft; checked Java interface stubs | Finalised the component diagram; Ahmed to implement interfaces before Sprint 1 deadline |
| 4 | 24/02/2026 | Ahmed, Mostafa | Sprint 2 planning — discussed the models needed (business concept, use case, type model, system architecture) | Mostafa to handle all diagrams and models; Ahmed to implement Clean Architecture in Java |
| 5 | 10/03/2026 | Ahmed, Mostafa | Reviewed Sprint 2 implementation; checked layers matched the diagrams; discussed interface allocation | Fixed layer dependency issues; agreed ShareService depends on interfaces not concrete classes |
| 6 | 25/03/2026 | Ahmed, Mostafa | Sprint 3 planning — discussed compound components, which architectural styles to apply, and SOA | Agreed on MVC, Layered, N-tier, and Pipes & Filters; Ahmed to implement compound components and frontend |
| 7 | 10/04/2026 | Ahmed, Mostafa | Reviewed the full system end to end; discussed test cases; reviewed the report draft | Finalised test cases; agreed on final report structure; divided writing responsibilities |
| 8 | 20/04/2026 | Ahmed, Mostafa | Final review of code, report, and GitHub before submission | Confirmed everything was committed; final checks on test results and report |

---

## 3. Sprint 1 – Requirements and Setup

### 3.1 Objective

The aim of Sprint 1 was to set up the project workflow, identify the system requirements and scope, define the architectural approach, and produce an abstract implementation of the key architectural elements in Java.

---

### 3.2 Requirements

#### Functional Requirements

| ID | Requirement |
|----|-------------|
| FR1 | The system shall retrieve daily share price data for a given symbol |
| FR2 | The system shall allow users to specify a date range with a maximum of two years |
| FR3 | The system shall persistently store retrieved share price data locally |
| FR4 | The system shall display a graph of share price data over the selected date range |
| FR5 | The system shall allow comparison of two different company shares on the same chart |

#### Non-Functional Requirements

| ID | Requirement |
|----|-------------|
| NFR1 | The system should remain functional in the absence of a network connection using stored data |
| NFR2 | The system should be modular and maintainable, following Clean Architecture principles |
| NFR3 | The system should respond to user requests within a reasonable time |
| NFR4 | The system should be extensible so that real data sources can be substituted without major rework |

#### Scope

**In scope:**
- Share data retrieval (simulated for this coursework)
- Local data storage via repository pattern
- Share price chart display
- Comparison of two company shares

**Out of scope:**
- User accounts and authentication
- Real-time data feeds
- Advanced UI design
- Machine learning or predictive analysis

---

### 3.3 Architectural Concepts – Simple Architecture

For Sprint 1 we identified and applied **Simple Architecture** principles. The system was structured around clearly defined components that communicate through well-defined interfaces. Each component has a single responsibility and depends on abstractions rather than concrete implementations.

The key architectural elements identified at this stage were:

- **ShareController** — receives user requests (provided interface: `requestShare`)
- **ShareService** — coordinates application logic (required interfaces: `MarketDataProvider`, `ShareRepository`)
- **MarketDataProvider** — interface for external data retrieval
- **ShareRepository** — interface for local data persistence
- **Share / PriceData** — domain entities representing core business data

These components and their relationships were captured in the component specification diagram.

---

### 3.4 Component Specification Diagram

The component specification diagram was created to show the system's components, their provided and required interfaces, and the connectors between them.

![Architecture Diagram](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/%20architecture-diagram.png)

**Components and their interfaces:**

| Component | Provided Interface | Required Interface |
|-----------|-------------------|-------------------|
| ShareController | requestShare() | ShareService |
| ShareService | getShareData() | MarketDataProvider, ShareRepository |
| MarketDataProvider | fetchShareData() | — |
| ShareRepository | save(), findBySymbol() | — |

---

### 3.5 Abstract Java Implementation

The Sprint 1 Java implementation defined the core interfaces and domain classes:

- `MarketDataProvider` — interface with `fetchShareData(symbol, start, end)`
- `ShareRepository` — interface with `save(share)` and `findBySymbol(symbol)`
- `Share` — domain class holding a symbol and list of `PriceData`
- `PriceData` — domain class holding a date and closing price
- `Main` — wires together mock implementations to demonstrate the system runs correctly

Mock implementations in `Main.java` simulate real data retrieval and storage, demonstrating that the architectural structure functions correctly end to end.

---

### 3.6 Task Allocation – Sprint 1

| Task | Owner |
|------|-------|
| Define and document requirements | Mostafa |
| Research Simple Architecture principles | Mostafa |
| Design component specification diagram (first draft) | Mostafa |
| Set up GitHub repository and branches | Ahmed |
| Set up project management tool and task board | Ahmed |
| Write and finalise Code of Conduct | Ahmed |
| Refine component diagram (final version) | Ahmed |
| Implement Java interfaces and domain classes | Ahmed |
| Code cleanup and commenting | Ahmed |

---

## 4. Sprint 2 – Developing Architecture from Requirements

### 4.1 Objective

Sprint 2 focused on developing a formal software architecture from the requirements defined in Sprint 1. We created a series of architectural models and implemented the system using Clean Architecture principles in Java.

---

### 4.2 Business Concept Model

![Business Concept](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/business-concept.png)

The business concept model identifies the core domain entities and their relationships:

- **User** — initiates requests for share price data
- **Share** — represents a financial asset identified by a ticker symbol (e.g. AAPL, MSFT)
- **PriceData** — represents a single day's closing price for a share
- **MarketDataProvider** — the external system responsible for supplying share data

**Relationships:**
- A User requests data for a Share
- A Share contains one or more PriceData records
- The system retrieves Share data from a MarketDataProvider

---

### 4.3 Use Case Model

![Use Case Diagram](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/use-case.png)

**Main Actor:** User

**Use Cases:**
- Get Share Data
- Compare Two Shares
- View Share Chart

#### Use Case: Get Share Data

| Step | Actor | Action |
|------|-------|--------|
| 1 | User | Enters a share symbol |
| 2 | User | Selects a start and end date |
| 3 | System | Validates that start date is before end date and range does not exceed 2 years |
| 4 | System | Retrieves share data from the MarketDataProvider |
| 5 | System | Stores retrieved data via ShareRepository |
| 6 | System | Returns the data to the user |

#### Use Case: Compare Two Shares

| Step | Actor | Action |
|------|-------|--------|
| 1 | User | Enters two share symbols |
| 2 | System | Retrieves data for both shares |
| 3 | System | Renders both price lines on the same chart |
| 4 | User | Views and compares the two share performances |

---

### 4.4 System Interfaces

The system defines two key interfaces that decouple the application layer from infrastructure concerns.

#### MarketDataProvider
Responsible for retrieving share price data from an external source.

```java
public interface MarketDataProvider {
    Share fetchShareData(String symbol, LocalDate start, LocalDate end);
}
```

#### ShareRepository
Responsible for storing and retrieving share data locally.

```java
public interface ShareRepository {
    void save(Share share);
    Share findBySymbol(String symbol);
}
```

These interfaces mean the application layer (`ShareService`) is never directly coupled to any specific data source or storage mechanism. This supports the **Dependency Inversion Principle** — high-level modules depend on abstractions, not concrete implementations.

---

### 4.5 Business Type Model

![Class Diagram](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/class-diagram.png)

The business type model defines the domain entities used throughout the system.

**Share**
- `symbol : String`
- `priceHistory : List<PriceData>`
- `getSymbol() : String`
- `getPriceHistory() : List<PriceData>`

**PriceData**
- `date : LocalDate`
- `closePrice : double`
- `getDate() : LocalDate`
- `getClosePrice() : double`

A `Share` aggregates multiple `PriceData` objects, representing its full price history over the requested date range. These domain classes are kept in the `domain` package, independent of all other layers.

---

### 4.6 Initial System Architecture

![Sequence Diagram](https://raw.githubusercontent.com/diybsa/Software-Architecture-Coursework/main/docs/diagrams/sequence-diagram.png)

The system is organised into four layers following Clean Architecture:

| Layer | Components | Responsibility |
|-------|-----------|----------------|
| Presentation | ShareController | Receives user input, delegates to application layer |
| Application | ShareService | Validates input, coordinates data retrieval and storage |
| Domain | Share, PriceData | Core business entities, no external dependencies |
| Infrastructure | MarketDataProvider, ShareRepository | Data access and persistence |

**Interface Allocation:**
- `ShareController` implements one interface: `requestShare()`
- `ShareService` implements one interface (`getShareData()`) but depends on two: `MarketDataProvider` and `ShareRepository`
- `MarketDataProvider` implements one interface: `fetchShareData()`
- `ShareRepository` implements two operations: `save()` and `findBySymbol()`

Dependencies always point inward — infrastructure depends on domain, application depends on domain, presentation depends on application. The domain layer has no outward dependencies.

---

### 4.7 Business Interfaces and Collaboration

The interaction flow for the primary use case (Get Share Data) is as follows:

1. The User sends a request to `ShareController` with a symbol and date range
2. `ShareController` calls `ShareService.getShareData(symbol, start, end)`
3. `ShareService` validates the input (checks dates are valid and range ≤ 2 years)
4. `ShareService` calls `MarketDataProvider.fetchShareData()` to retrieve data
5. `ShareService` calls `ShareRepository.save()` to persist the data
6. The `Share` object is returned up through the layers to the user

This collaboration pattern is consistent across all use cases. Each interface operation maps to a clearly defined step in the interaction sequence.

---

### 4.8 Clean Architecture Implementation

The Java package structure directly reflects the Clean Architecture layers:

```
com.Ga3
 ├── domain/
 │   ├── Share.java
 │   └── PriceData.java
 ├── application/
 │   └── ShareService.java
 ├── infrastructure/
 │   ├── MarketDataProvider.java
 │   └── ShareRepository.java
 ├── presentation/
 │   └── ShareController.java
 └── Main.java
```

And separately, the use case layer:

```
usecases/
 └── GetShareDataUseCase.java
```

`GetShareDataUseCase` encapsulates the application-specific rules for retrieving share data independently of the service layer, demonstrating the use case layer concept from Clean Architecture.

---

### 4.9 Task Allocation – Sprint 2

| Task | Owner |
|------|-------|
| Create Business Concept Model | Mostafa |
| Create Use Case Diagram and write use case steps | Mostafa |
| Define System Interfaces and operations | Mostafa |
| Create Business Type Model | Mostafa |
| Design Initial System Architecture diagram | Mostafa |
| Decide interface-to-component allocation | Mostafa |
| Create Business Interfaces and collaboration diagrams | Ahmed |
| Implement Clean Architecture structure in Java | Ahmed |
| Review and ensure architecture consistency | Ahmed |

---

## 5. Sprint 3 – Compound Components, Architectural Styles, and SOA

### 5.1 Objective

Sprint 3 focused on decomposing the system into compound components, applying domain-independent architectural styles, introducing SOA principles, and producing a fully tested system.

---

### 5.2 Compound Components

The system is divided into three compound components. Each compound component is a high-level grouping that contains internal sub-components and exposes external interfaces via delegation connectors.

#### UI Component
**Internal sub-components:** HTML page, CSS styles, JavaScript controller logic, Canvas chart renderer  
**External interface:** User interaction (input fields, Compare button)  
**Responsibility:** Presents the application to the user, captures share symbols, renders the comparison chart  

The JavaScript `loadChart()` function acts as the controller within this component, coordinating `makeData()` (data generation), `drawAxes()` (view rendering), and `drawLine()` (data visualisation).

#### Business Component
**Internal sub-components:** `ShareService`, `GetShareDataUseCase`, `ShareController`  
**External interface:** `getShareData(symbol, start, end)`  
**Responsibility:** Validates input, enforces business rules (2-year limit, date ordering), and coordinates data retrieval and storage  

#### Data Component
**Internal sub-components:** `MarketDataProvider` (interface + mock), `ShareRepository` (interface + mock), `Share`, `PriceData`  
**External interface:** `fetchShareData()`, `save()`, `findBySymbol()`  
**Responsibility:** Handles all data access — retrieval from external sources and local persistence  

These three components interact through assembly connectors. The UI Component delegates to the Business Component, which in turn delegates to the Data Component.

---

### 5.3 Domain-Independent Architectural Styles

#### Model-View-Controller (MVC)

MVC is applied both in the Java backend and the HTML/JavaScript frontend.

**In the frontend:**
- **Model:** The share price data generated by `makeData()`
- **View:** The HTML Canvas chart rendered by `drawAxes()` and `drawLine()`
- **Controller:** The JavaScript `loadChart()` function, which handles user input and coordinates the model and view

**In the Java backend:**
- **Model:** `Share` and `PriceData` domain classes
- **View:** Console output in `Main.java`
- **Controller:** `ShareController`, which receives requests and delegates to `ShareService`

This separation ensures that changes to the user interface do not affect business logic, and vice versa.

---

#### Layered Architecture

The system is organised into four hierarchical layers, each providing services only to the layer directly above it:

| Layer | Java Package | Responsibility |
|-------|-------------|----------------|
| Presentation | `presentation/` | User interaction and request handling |
| Application | `application/` | Business logic and use case coordination |
| Domain | `domain/` | Core entities (Share, PriceData) |
| Infrastructure | `infrastructure/` | Data access and external integrations |

Each layer communicates with the layer below it only through defined interfaces. This enforces loose coupling and makes each layer independently testable and replaceable.

---

#### N-Tier Architecture

At a higher level, the system follows an N-tier pattern with three tiers:

- **UI Tier:** The HTML/JavaScript frontend (`web/index.html`)
- **Business Logic Tier:** The Java application and domain layers
- **Data Tier:** The repository and market data provider

This separation means the frontend, backend logic, and data handling are all independently modifiable. In a production version, each tier could run on a separate server.

---

#### Pipes and Filters

The data processing pipeline within `ShareService` follows a Pipes and Filters style. When a user requests share data, the data passes through a sequence of discrete processing steps:

1. **Filter 1 – Input Validation:** `ShareService.getShareData()` validates the symbol is not null, the start date is before the end date, and the range does not exceed two years. Invalid data is rejected at this stage.
2. **Pipe:** Validated input is passed to the market data provider
3. **Filter 2 – Data Retrieval:** `MarketDataProvider.fetchShareData()` retrieves the raw share data
4. **Pipe:** Retrieved data is passed to the repository
5. **Filter 3 – Persistence:** `ShareRepository.save()` stores the data locally
6. **Output:** The processed `Share` object is returned to the caller

Each filter is independent and has a single responsibility. This makes the pipeline easy to extend — for example, a normalisation filter or a caching filter could be inserted without modifying existing filters.

---

### 5.4 Service-Oriented Architecture (SOA)

The system applies SOA principles by structuring its functionality as a set of discrete, loosely coupled services. Each service has a well-defined interface and can operate independently of the others.

**Services identified in the system:**

| Service | Interface | Responsibility |
|---------|-----------|----------------|
| Share Data Service | `getShareData(symbol, start, end)` | Retrieves and validates share price data |
| Market Data Service | `fetchShareData(symbol, start, end)` | Fetches data from an external market source |
| Storage Service | `save(share)`, `findBySymbol(symbol)` | Persists and retrieves share data locally |
| Chart Service | `loadChart()` (JavaScript) | Renders the visual comparison chart |

**SOA principles applied:**

- **Service Modularity:** Each service has a single, well-defined responsibility. `ShareService` handles business logic independently of `MarketDataProvider`, which handles data retrieval independently of `ShareRepository`.
- **Loose Coupling:** Services communicate through interfaces rather than concrete implementations. The application layer never directly references an infrastructure class — only the interface contracts.
- **Interoperability:** The `MarketDataProvider` interface is designed so that any data source — whether a mock, a CSV file, or a live Yahoo Finance API — can be substituted without changing any other part of the system.
- **Reusability:** `ShareRepository` and `MarketDataProvider` are reusable across any feature that requires share data, not just the comparison use case.

---

### 5.5 Testing

#### Test Cases

| ID | Test Case | Input | Expected Result | Actual Result |
|----|-----------|-------|----------------|---------------|
| TC1 | Load webpage | Open `index.html` with Live Server | Webpage displays correctly with input fields and Compare button | Passed |
| TC2 | Compare two shares | AAPL, MSFT | Two different coloured lines displayed on the chart | Passed |
| TC3 | Compare different shares | TSLA, GOOG | Chart updates with different line values for the new symbols | Passed |
| TC4 | Empty input | Leave both fields blank, click Compare | Alert displayed: "Please enter both share symbols" | Passed |
| TC5 | Single empty field | Enter AAPL, leave second field blank | Alert displayed asking for both symbols | Passed |
| TC6 | Same symbol twice | AAPL, AAPL | Two identical lines displayed (same data) | Passed |
| TC7 | Date validation (Java) | Start date after end date | `IllegalArgumentException` thrown by `ShareService` | Passed |
| TC8 | Date range validation (Java) | Range exceeding 2 years | `IllegalArgumentException` thrown by `ShareService` | Passed |

#### Test Summary

The frontend was tested manually using Live Server in VS Code. All user-facing interactions — including chart rendering, input validation, and repeated comparisons — behaved as expected across multiple symbol combinations.

The Java backend validation logic in `ShareService` was verified by reviewing the conditional checks in the `getShareData()` method and confirming that both the date ordering check and the two-year limit check would throw appropriate exceptions for invalid inputs.

---

### 5.6 Task Allocation – Sprint 3

| Task | Owner |
|------|-------|
| Define compound component structure and boundaries | Ahmed |
| Select and justify architectural styles | Ahmed |
| Design SOA service structure | Ahmed |
| Implement compound components in code | Ahmed |
| Implement Pipes and Filters in ShareService | Ahmed |
| Build HTML/JavaScript frontend | Ahmed |
| Define and document test cases | Ahmed |
| Run all test cases and document results | Ahmed |
| Write design rationale section of report | Ahmed |
| Final code cleanup and integration | Ahmed |
| Contribute to group report write-up | Mostafa |

---

## 6. Design Rationale

### Why Clean Architecture?

Clean Architecture was chosen because it enforces a strict separation of concerns through layering. The domain layer — `Share` and `PriceData` — contains no dependencies on any framework, database, or external system. This means the core business logic can be tested in isolation and the infrastructure can be changed without touching the domain. This directly supports the non-functional requirement for modularity and maintainability.

### Why Interfaces for MarketDataProvider and ShareRepository?

Using interfaces rather than concrete classes for these components means the system is not locked into any specific data source or storage mechanism. In this coursework, mock implementations are used for demonstration. In a real deployment, the `MockMarketDataProvider` could be replaced with a Yahoo Finance API client and the `MockShareRepository` with a SQLite implementation, without any changes to `ShareService` or any other layer.

### Why MVC in the Frontend?

The HTML/JavaScript frontend separates the data generation (`makeData()`), rendering (`drawAxes()`, `drawLine()`), and user interaction handling (`loadChart()`) into distinct responsibilities. This makes the frontend easier to maintain — for example, switching from Canvas to a charting library would only require changing the view functions.

### Why Pipes and Filters in ShareService?

The data processing flow in `ShareService` is naturally sequential — validate, fetch, store, return. Applying a Pipes and Filters style makes each step explicit, independent, and extensible. A caching step or a data transformation step could be added to the pipeline without modifying the existing filters.

### Why SOA Principles?

Applying SOA principles ensures each functional concern — data retrieval, storage, business logic, chart rendering — is encapsulated as an independently operable service. This supports reusability and means future features (e.g. a portfolio tracking service) could reuse the existing `Share Data Service` without modification.

---

## 7. Conclusion

Over three sprints, the team progressed from requirements identification through formal architectural modelling to a fully implemented and tested system. The architecture was designed to be modular, maintainable, and extensible — guided consistently by Clean Architecture principles, compound component design, and a set of domain-independent architectural styles.

Ahmed led the implementation work across all three sprints, delivering the Java architecture, the HTML/JavaScript frontend, and the test cases. Mostafa led the requirements, design modelling, and documentation work, delivering the requirements specification, all UML diagrams, and the architectural models.

Both team members collaborated on code reviews, report writing, and GitHub management throughout the project.
