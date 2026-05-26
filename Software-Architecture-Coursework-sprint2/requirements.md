# Requirements Specification

## 1. Overview

The system is a **Share Price Comparison Web Application** that allows users to retrieve, store, and compare historical share price data for two companies over a specified date range. The application is built in Java with an HTML/JavaScript frontend.

---

## 2. Functional Requirements

| ID | Requirement | Priority |
|----|-------------|----------|
| FR1 | The system shall retrieve daily share price data for a given ticker symbol (e.g. AAPL, MSFT) | High |
| FR2 | The system shall allow users to specify a start and end date for the data range | High |
| FR3 | The system shall enforce a maximum date range of two years | High |
| FR4 | The system shall persistently store retrieved share price data so the system can function without a network connection | High |
| FR5 | The system shall display a line graph of share price data over the selected date range | High |
| FR6 | The system shall allow users to compare the share performance of two different companies on the same chart | High |
| FR7 | The system shall validate user input and display an appropriate error message for invalid entries | Medium |
| FR8 | The system shall allow repeated comparisons without requiring a page reload | Medium |

---

## 3. Non-Functional Requirements

| ID | Requirement | Category |
|----|-------------|----------|
| NFR1 | The system shall remain functional in the absence of a network connection using locally stored data | Availability |
| NFR2 | The system shall follow Clean Architecture principles to ensure separation of concerns | Maintainability |
| NFR3 | The system shall be modular so that individual components (e.g. data source, storage) can be replaced independently | Extensibility |
| NFR4 | The system shall respond to user interactions without noticeable delay | Performance |
| NFR5 | The system shall be structured so that additional share symbols or data sources can be integrated with minimal rework | Scalability |

---

## 4. Scope

### In Scope
- Share data retrieval (simulated via mock provider for this coursework)
- Local data storage via the repository pattern
- Share price chart display using HTML Canvas
- Comparison of two company shares on a single chart
- Input validation (date range, symbol presence)

### Out of Scope
- User accounts and authentication
- Real-time or live data feeds (e.g. live Yahoo Finance API integration)
- Advanced or polished UI design
- Machine learning or predictive analytics
- Mobile application

---

## 5. Requirements Alignment to Project Goals

| Requirement | Project Goal |
|-------------|-------------|
| FR1, FR2, FR3 | Obtain daily price information for a share between two dates, up to a maximum of two years |
| FR4, NFR1 | Persistently store data so the app functions without a network connection |
| FR5, FR6 | Display graphs and allow comparison of two companies |
| NFR2, NFR3 | Apply Clean Architecture and ensure the system is modular and maintainable |
| NFR4, NFR5 | Deliver a robust and scalable application |

All functional and non-functional requirements map directly to the goals stated in the coursework specification.
