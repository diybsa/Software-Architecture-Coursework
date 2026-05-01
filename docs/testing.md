# Test Cases and Results

## 1. Overview

Testing was carried out across the frontend (HTML/JavaScript) and the backend (Java) to verify that the system meets its functional requirements. Frontend tests were conducted manually using Live Server in VS Code. Backend validation logic was verified by code review and trace analysis of the `ShareService` class.

---

## 2. Frontend Test Cases

| ID | Test Case | Input | Expected Result | Actual Result | Status |
|----|-----------|-------|----------------|---------------|--------|
| TC1 | Load webpage | Open `index.html` with Live Server | Page loads with title, two input fields, Compare button, and empty canvas | Page displayed correctly | ✅ Passed |
| TC2 | Compare two different shares | Symbol 1: AAPL, Symbol 2: MSFT | Two differently shaped lines displayed in blue and red | Two distinct lines rendered on the chart | ✅ Passed |
| TC3 | Compare alternative shares | Symbol 1: TSLA, Symbol 2: GOOG | Chart updates with different line values for the new symbols | Chart re-rendered with different values | ✅ Passed |
| TC4 | Both fields empty | Leave both fields blank, click Compare | Alert: "Please enter both share symbols" | Alert displayed correctly | ✅ Passed |
| TC5 | One field empty | Symbol 1: AAPL, Symbol 2: blank | Alert: "Please enter both share symbols" | Alert displayed correctly | ✅ Passed |
| TC6 | Same symbol twice | Symbol 1: AAPL, Symbol 2: AAPL | Two identical lines overlaid on the chart | Two identical lines displayed | ✅ Passed |
| TC7 | Repeated comparison | Compare AAPL/MSFT, then compare TSLA/GOOG | Chart clears and re-renders correctly for second comparison | Chart cleared and re-rendered successfully | ✅ Passed |
| TC8 | Lowercase input | Symbol 1: aapl, Symbol 2: msft | Input converted to uppercase; chart renders as AAPL/MSFT | Symbols uppercased correctly, chart rendered | ✅ Passed |

---

## 3. Backend Validation Test Cases

These test cases verify the validation logic in `ShareService.getShareData()`.

| ID | Test Case | Input | Expected Result | Actual Result | Status |
|----|-----------|-------|----------------|---------------|--------|
| TC9 | Valid date range | start: 2024-01-01, end: 2024-06-01 | Data returned successfully with no exception | Share object returned correctly | ✅ Passed |
| TC10 | Start date after end date | start: 2024-06-01, end: 2024-01-01 | `IllegalArgumentException`: "Start date must be before end date" | Exception thrown as expected | ✅ Passed |
| TC11 | Date range exceeds 2 years | start: 2022-01-01, end: 2024-06-01 | `IllegalArgumentException`: "Date range cannot exceed 2 years" | Exception thrown as expected | ✅ Passed |
| TC12 | Exactly 2 year range | start: 2022-01-01, end: 2024-01-01 | Data returned successfully (boundary case) | Share object returned correctly | ✅ Passed |

---

## 4. Test Summary

| Category | Total Tests | Passed | Failed |
|----------|------------|--------|--------|
| Frontend | 8 | 8 | 0 |
| Backend Validation | 4 | 4 | 0 |
| **Total** | **12** | **12** | **0** |

All test cases passed. The system correctly handles valid inputs, edge cases (same symbol, exact 2-year boundary), and invalid inputs (empty fields, reversed dates, excessive date ranges). The frontend input validation provides clear user feedback, and the backend enforces business rules through explicit exception handling.
