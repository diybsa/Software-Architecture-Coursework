# Share Price Comparison Web Application

## Overview
This project is a web-based application developed in Java as part of a Scrum team coursework. The system allows users to compare share prices of two companies over time using a graphical interface.

The focus of this coursework is on software architecture and design rather than UI complexity.

---

## Features
- Input two share symbols (e.g. AAPL, MSFT)
- Compare share performance visually
- Display graph using HTML Canvas
- Simple and interactive web interface
- Works without external APIs (simulated data)
- Supports repeated comparisons

---

## How to Run
1. Open the project in VS Code
2. Navigate to:
   /web/index.html
3. Right click → Open with Live Server
4. Enter two share symbols
5. Click Compare

---

## Architecture

### Compound Components
The system is divided into three main components:
- UI Component: Web interface (HTML and JavaScript)
- Business Component: Application logic and use cases
- Data Component: Data handling and storage

---

### Architectural Styles

#### MVC (Model-View-Controller)
- Model: Share price data
- View: Web interface
- Controller: JavaScript handling user input

#### Layered Architecture
- Presentation Layer (UI)
- Application Layer (logic)
- Domain Layer (data models)
- Infrastructure Layer (data access)

#### N-tier Architecture
- UI Layer
- Business Logic Layer
- Data Layer

---

## Service-Oriented Architecture (SOA)
The system follows SOA principles by separating functionality into independent services. This allows for easier modification, reuse, and scalability of components.

---

## Testing

Test cases are documented in:
docs/testing.md

The system was tested for:
- Correct graph display
- Share comparison
- Input handling
- Stability under repeated usage

---

## Technologies Used
- Java (backend structure)
- HTML / CSS / JavaScript (frontend)
- VS Code
- Live Server extension

---

## Sprint 3 Contributions
- Web interface implemented
- Graph comparison feature added
- Architectural styles applied
- SOA principles introduced
- Test cases defined and documented

---

## Notes
- Share data is simulated for demonstration purposes
- Focus is on architectural design and functionality rather than real-time financial data
