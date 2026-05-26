# Task Allocation

## Team Members
- **Ahmed** — Implementation lead (Java backend, frontend, testing)
- **Mostafa** — Design lead (requirements, diagrams, architectural models, documentation)

---

## Sprint 1 – Foundations and Setup

| Task | Owner | Description |
|------|-------|-------------|
| Define system requirements and scope | Mostafa | Write detailed functional and non-functional requirements; ensure they align with the coursework goals |
| Research Simple Architecture principles | Mostafa | Investigate component-based design, provided/required interfaces, connectors |
| Design component specification diagram (first draft) | Mostafa | Produce initial UML component diagram showing components and interfaces |
| Refine component diagram (final version) | Ahmed | Review and finalise the diagram based on the implemented architecture |
| Set up GitHub repository and branches | Ahmed | Create repo, set up main, dev, and feature branches |
| Set up project management tool and task board | Ahmed | Set up Kanban board; add sprint tasks and assign owners |
| Write and finalise Code of Conduct | Ahmed | Draft and agree Code of Conduct with Mostafa |
| Implement Java interfaces (`MarketDataProvider`, `ShareRepository`) | Ahmed | Write interface definitions as abstract contracts |
| Implement domain classes (`Share`, `PriceData`) | Ahmed | Write core domain entities with fields and getters |
| Write `Main.java` with mock implementations | Ahmed | Wire together mock provider and repository; demonstrate system runs |
| Clean, comment, and ensure code builds | Ahmed | Review all code for clarity and correctness |

---

## Sprint 2 – Architecture Design

| Task | Owner | Description |
|------|-------|-------------|
| Create Business Concept Model | Mostafa | Identify domain entities (User, Share, PriceData, MarketDataProvider) and relationships |
| Create Use Case Diagram | Mostafa | Draw use case diagram with User actor and three main use cases |
| Write Use Case steps | Mostafa | Define step-by-step actor and system actions for each use case |
| Define System Interfaces and operations | Mostafa | Document interface contracts with method signatures |
| Create Business Type Model | Mostafa | Define domain classes with attributes and operations |
| Design Initial System Architecture diagram | Mostafa | Produce architecture diagram showing layers and component allocation |
| Decide interface-to-component allocation | Mostafa | Document which components implement which interfaces |
| Create Business Interfaces and collaboration diagrams | Ahmed | Identify business-level operations; create sequence/collaboration diagrams |
| Implement Clean Architecture structure in Java | Ahmed | Create package structure (domain, application, infrastructure, presentation) |
| Implement `ShareService` with validation logic | Ahmed | Enforce date ordering and 2-year limit; coordinate data retrieval and storage |
| Implement `ShareController` | Ahmed | Create presentation layer entry point delegating to `ShareService` |
| Implement `GetShareDataUseCase` | Ahmed | Demonstrate use case layer with encapsulated business rules |
| Review and ensure architecture consistency | Ahmed | Check that layers and dependencies match the architectural diagrams |

---

## Sprint 3 – Advanced Implementation and Testing

| Task | Owner | Description |
|------|-------|-------------|
| Define compound component boundaries | Ahmed | Identify UI, Business, and Data compound components and their sub-components |
| Select and justify architectural styles | Ahmed | Choose MVC, Layered, N-tier, Pipes and Filters; document rationale |
| Apply Pipes and Filters to `ShareService` | Ahmed | Structure data pipeline: validate → fetch → store → return |
| Design SOA service structure | Ahmed | Define discrete services (Share Data, Market Data, Storage, Chart) with interfaces |
| Build HTML/JavaScript frontend | Ahmed | Implement `index.html` with input fields, Compare button, and Canvas chart |
| Implement chart rendering | Ahmed | Write `makeData()`, `drawAxes()`, `drawLine()` functions |
| Define test cases | Ahmed | Write 12 test cases covering frontend and backend validation |
| Run and document test results | Ahmed | Execute all tests; record actual results |
| Write design rationale for report | Ahmed | Explain architectural decisions and justify style choices |
| Final code cleanup and integration | Ahmed | Review all files; ensure everything is committed and consistent |
| Contribute to group report (Sprint 3 sections) | Mostafa | Write up compound components, styles, SOA, and testing sections |
| Final report review and editing | Both | Review complete group report together before submission |

---

## Shared Responsibilities (Both Sprints)

| Task | Owner |
|------|-------|
| Regular commits to GitHub | Both |
| Code reviews before sprint deadlines | Both |
| Updating the project management board | Both |
| Attending and contributing to team meetings | Both |
| Writing the final group report | Both |
| Preparing for code review sessions | Both |
