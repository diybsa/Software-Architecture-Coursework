# Software Architecture Coursework – Sprint 2 Report

## 1. Introduction

This sprint focuses on deriving a software architecture from the requirements defined in Sprint 1. The system developed is a **Share Price Comparison Application**, which allows users to retrieve historical share price data within a specified date range, store the data locally, and support comparison and charting.

The architecture follows **Clean Architecture principles**, ensuring separation of concerns, maintainability, scalability, and testability.

---

## 2. Business Concept Model

![Business Concept Model](docs/diagrams/business-concept.png)

### Description

The business concept model identifies the core entities in the system:

- **User**: Initiates requests for share data  
- **Share**: Represents a financial asset identified by a symbol  
- **PriceData**: Represents daily price information for a share  
- **MarketDataProvider**: External system used to retrieve share data  

### Relationships

- A **User** requests data for a **Share**  
- A **Share** contains multiple **PriceData** records  
- The system retrieves data from a **MarketDataProvider**  

---

## 3. Use Case Model

![Use Case Diagram](docs/diagrams/use-case.png)

### Main Actor
- **User**

### Use Cases
- Get Share Data  
- Compare Two Shares  
- View Share Chart  

### Use Case: Get Share Data

1. User enters a share symbol  
2. User selects a date range  
3. System validates the input  
4. System retrieves share data from the market data provider  
5. System stores the data locally  
6. System returns the data to the user  

---

## 4. System Interfaces

The system uses interfaces to decouple business logic from infrastructure components.

### MarketDataProvider
- Responsible for retrieving share data from external sources  

**Operation:**
- `fetchShareData(String symbol, LocalDate start, LocalDate end): Share`

---

### ShareRepository
- Responsible for storing and retrieving share data  

**Operations:**
- `save(Share share): void`  
- `findBySymbol(String symbol): Share`  

---

### Description

These interfaces allow the application layer to remain independent of specific implementations, improving flexibility and enabling easier testing.

---

## 5. Business Type Model

![Class Diagram](docs/diagrams/class-diagram.png)

### Classes

**Share**
- symbol : String  
- priceHistory : List<PriceData>  

**PriceData**
- date : LocalDate  
- closePrice : double  

### Description

The business type model defines the domain entities used in the system. A **Share** aggregates multiple **PriceData** objects to represent historical price information.

---

## 6. Initial System Architecture

![System Architecture](docs/diagrams/architecture-diagram.png)

### Layers

1. **Presentation Layer**
   - ShareController  
   - Handles user input and forwards requests  

2. **Application Layer**
   - ShareService  
   - Contains application logic and coordinates operations  

3. **Domain Layer**
   - Share  
   - PriceData  
   - Core business entities  

4. **Infrastructure Layer**
   - MarketDataProvider  
   - ShareRepository  
   - Handles external data access and persistence  

---

### Interface Allocation

- **ShareController** depends on **ShareService**  
- **ShareService** depends on **MarketDataProvider** and **ShareRepository** interfaces  
- Infrastructure components provide implementations of these interfaces  

This demonstrates the **Dependency Inversion Principle**, where the application layer depends on abstractions rather than concrete implementations.

---

## 7. Business Interfaces and Collaboration

![Sequence Diagram](docs/diagrams/sequence-diagram.png)

### Interaction Flow

1. User sends a request to ShareController  
2. ShareController calls ShareService  
3. ShareService validates input  
4. ShareService calls MarketDataProvider to fetch data  
5. ShareService calls ShareRepository to store data  
6. Data is returned to the controller and then to the user  

---

## 8. Clean Architecture Implementation

### Package Structure

```
com.Ga3
 ├── domain
 ├── usecases
 ├── infrastructure
 └── presentation
```

---

### Description:

The system is structured to ensure that high-level business logic is independent of frameworks and external systems. The use case layer encapsulates application-specific rules, while infrastructure concerns are isolated.

---

# 9. Conclusion

The developed architecture successfully transforms the requirements into a structured system using Clean Architecture principles.

The design ensures:

* Maintainability
* Scalability
* Clear separation between components

All models and implementation are aligned, providing a cohesive and robust system design.
