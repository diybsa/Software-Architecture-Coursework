# Project Management

## Methodology

We followed an **Agile/Scrum** approach, working in three sprints with defined deliverables per sprint. Task tracking was managed using a Kanban-style board with three columns: **To Do**, **In Progress**, and **Done**. Tasks were assigned to team members at the start of each sprint and reviewed at regular team meetings.

---

## Team Members

| Name | Role |
|------|------|
| Mostafa | Requirements, Architectural Design, Diagrams, Documentation |
| Ahmed | GitHub Setup, Java Implementation, Frontend, Testing |

---

## Branching Strategy

| Branch | Purpose |
|--------|---------|
| `main` | Stable, reviewed code only |
| `dev` | Integration branch — features merged here before main |
| `feature/domain` | Domain layer classes (Share, PriceData) |
| `feature/service` | Application layer (ShareService, ShareController) |
| `feature/repository` | Infrastructure interfaces (MarketDataProvider, ShareRepository) |
| `feature/usecases` | Use case layer (GetShareDataUseCase) |
| `feature/frontend` | HTML/JavaScript web frontend |
| `feature/docs` | Documentation, diagrams, reports |

---

## Sprint 1 – Kanban Board

| To Do | In Progress | Done |
|-------|------------|------|
| Define functional requirements | | ✅ Define functional requirements |
| Define non-functional requirements | | ✅ Define non-functional requirements |
| Identify project scope | | ✅ Identify project scope |
| Research Simple Architecture | | ✅ Research Simple Architecture |
| Design component specification diagram | | ✅ Design component specification diagram |
| Set up GitHub repository | | ✅ Set up GitHub repository |
| Create branches | | ✅ Create branches |
| Set up project management tool | | ✅ Set up project management tool |
| Write Code of Conduct | | ✅ Write Code of Conduct |
| Implement Java interfaces | | ✅ Implement Java interfaces |
| Implement domain classes (Share, PriceData) | | ✅ Implement domain classes (Share, PriceData) |
| Write Main.java with mock implementations | | ✅ Write Main.java with mock implementations |
| Comment and clean code | | ✅ Comment and clean code |

---

## Sprint 2 – Kanban Board

| To Do | In Progress | Done |
|-------|------------|------|
| Create Business Concept Model | | ✅ Create Business Concept Model |
| Create Use Case Diagram | | ✅ Create Use Case Diagram |
| Write Use Case steps | | ✅ Write Use Case steps |
| Define System Interfaces | | ✅ Define System Interfaces |
| Create Business Type Model | | ✅ Create Business Type Model |
| Design Initial System Architecture | | ✅ Design Initial System Architecture |
| Map interfaces to components | | ✅ Map interfaces to components |
| Create collaboration/sequence diagram | | ✅ Create collaboration/sequence diagram |
| Implement Clean Architecture layers in Java | | ✅ Implement Clean Architecture layers in Java |
| Implement ShareService with validation | | ✅ Implement ShareService with validation |
| Implement ShareController | | ✅ Implement ShareController |
| Implement GetShareDataUseCase | | ✅ Implement GetShareDataUseCase |
| Write Sprint 2 report | | ✅ Write Sprint 2 report |

---

## Sprint 3 – Kanban Board

| To Do | In Progress | Done |
|-------|------------|------|
| Define compound components | | ✅ Define compound components |
| Select and justify architectural styles | | ✅ Select and justify architectural styles |
| Apply MVC pattern | | ✅ Apply MVC pattern |
| Apply Layered Architecture | | ✅ Apply Layered Architecture |
| Apply N-tier Architecture | | ✅ Apply N-tier Architecture |
| Apply Pipes and Filters | | ✅ Apply Pipes and Filters |
| Design SOA service structure | | ✅ Design SOA service structure |
| Build HTML/JavaScript frontend | | ✅ Build HTML/JavaScript frontend |
| Implement chart rendering (Canvas) | | ✅ Implement chart rendering (Canvas) |
| Define test cases | | ✅ Define test cases |
| Run and document test results | | ✅ Run and document test results |
| Write group report | | ✅ Write group report |
| Final code review and cleanup | | ✅ Final code review and cleanup |
| Submit to GitHub | | ✅ Submit to GitHub |

---

## GitHub Commit Responsibilities

| Team Member | Primary Commit Areas |
|-------------|---------------------|
| Ahmed | Java source files, web frontend, usecases folder, pom.xml |
| Mostafa | docs/diagrams, requirements.md, Architecture Explanation, report files |
| Both | README.md, tasks.md, project-management.md, Code of Conduct |
