# Noon.com Test Automation (Guest User) — Selenium + TestNG (Java)

Automated end‑to‑end tests for **Noon.com** as a **guest user** (not logged in).  
This project implements the first two required scenarios from the assignment document and is structured using the **Page Object Model (POM)** with **Data‑Driven Testing (DDT)**.

---

## Table of Contents
- [Project Goals](#project-goals)
- [Implemented Scenarios](#implemented-scenarios)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Test Data (DDT)](#test-data-ddt)
- [How to Run](#how-to-run)
- [Architecture & Design Notes](#architecture--design-notes)
- [Troubleshooting (Common Errors)](#troubleshooting-common-errors)
- [Next Steps](#next-steps)

---

## Project Goals

1. Automate realistic guest-user flows on Noon.com.
2. Keep tests maintainable using **POM** (locators/actions inside `pages`).
3. Support **multiple DDT sources** (Excel / JSON / Properties / CSV / TestNG DataProvider).
4. Write tests that are resilient to dynamic web content (explicit waits, fallback locators).

---

## Implemented Scenarios

### Scenario 1 — Browse and Filter Products ✅
**Steps**
1. Open Noon.com.
2. Navigate to **Electronics** category.
3. Apply filters:
   - **Brand = Samsung**
   - **Price range = 1000–3000 EGP**
4. Sort results by **Customer Ratings**.
5. Verify that listed items match the selected filters and sorting.

**DDT**: Excel, CSV, JSON, Properties, TestNG DataProvider

---

### Scenario 2 — Add Multiple Products to Cart (Guest) ✅
**Steps**
1. Search for **“Headphones”** (or any provided term).
2. Select **3 different items** from results (by indexes from test data).
3. Add each item to the shopping cart.
4. Navigate to the cart page.
5. Verify the cart contains the expected items with correct **name / price / quantity**.

**DDT**: Excel, JSON, Properties

---

## Project Structure

The repository is organized around a classic POM layout.

```
src/
  main/
    java/
      pages/
        BasePage.java
        HomePage.java
        ElectronicsPage.java
        SamsungPage.java
        VerifyingItems.java
        (Scenario 2 pages: SearchPage, ProductPage, CartPage, CartItem)*
    resources/
      testData/
        Provider.json
        Providers.properties
        Providerss.xlsx
  test/
    java/
      tests/
        BaseTest.java
        ConfigLoader.java
        FirstScenarioPositive.java
        SecondScenario.java* (or similar name)
      utils/
        CSVFileManager.java
        EXcelFileManager.java
        JsonFileManager.java
        PropertiesFileManager.java
```

\* Files marked with an asterisk are part of Scenario 2 implementation in your working project (may not exist in older exports).

---

## Configuration

### Base URL
The driver starts by navigating to `url` loaded by `ConfigLoader`.

Typical config file example (create one if missing):
```
url=https://www.noon.com/egypt-en/
```

Where it is loaded from depends on your `ConfigLoader` usage. Common patterns:
- `src/test/resources/config.properties`
- `src/main/resources/config.properties`

### Browser / Driver
`BaseTest` initializes `ChromeDriver` with some Chrome options and preferences (e.g., disabling password manager popups).

Recommendations:
- Prefer **Selenium Manager** (ships with Selenium 4.6+) or ensure ChromeDriver matches your Chrome version.
- Use explicit waits (already used in `BasePage`).

---

## Test Data (DDT)

All test data lives under:

`src/main/resources/testData/`

### 1) Excel — `Providerss.xlsx`
Used by `EXcelFileManager`.

**Scenario 2 note:**  
Make sure the sheet name passed in code exists **exactly** (case-sensitive).  
Example: `Scenario2`

Recommended sheet format (row 1 as header):
| searchTerm | index1 | index2 | index3 |
|---|---:|---:|---:|
| Headphones | 1 | 2 | 3 |

### 2) JSON — `Provider.json`
Recommended structure for Scenario 2 (multiple runs):

```json
{
  "scenario2": [
    { "searchTerm": "Headphones",  "indexes": [1, 2, 3] },
    { "searchTerm": "Laptop",      "indexes": [4, 5, 6] },
    { "searchTerm": "Smartphone",  "indexes": [7, 8, 9] }
  ]
}
```

Your `scenario2FromJson()` data provider should flatten each object into:
`searchTerm, index1, index2, index3`.

### 3) Properties — `Providers.properties`
Example keys:

```
SecondScenarioSearchTerm=Headphones
SecondScenarioIndexes=1,2,3
```

### 4) CSV
If you implement it, a typical CSV could be:

```
searchTerm,index1,index2,index3
Headphones,1,2,3
Laptop,4,5,6
```

---

## How to Run

### Run from IntelliJ IDEA (Recommended)
1. Open the project.
2. Ensure dependencies are available (Maven/Gradle or jars configured).
3. Run the test class:
   - `tests.FirstScenarioPositive`
   - `tests.SecondScenario` (Scenario 2)

### Run from Maven (If you have a `pom.xml`)
Common commands:
```bash
mvn clean test
```

Or run a specific suite:
```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

## Architecture & Design Notes

### Page Object Model (POM)
- **Pages** contain:
  - Locators (`By`)
  - Actions (click/filter/sort/search)
  - Safe element interactions (explicit waits)
- **Tests** contain:
  - Scenario flow
  - Assertions
  - DataProviders

### Waiting Strategy
Use **explicit waits** (`WebDriverWait`) instead of `Thread.sleep` where possible.

### Scenario 2 Search Submission
Avoid `element.submit()` unless the search input is inside a `<form>`.  
Prefer:
- `sendKeys(Keys.ENTER)`
- clicking a search button
- or direct navigation to `.../search?q=<term>` as a robust fallback.

### Assertions
Prefer TestNG assertions:
```java
Assert.assertEquals(actual, expected);
```
Over Java `assert` (often disabled unless JVM is run with `-ea`).

---

## Troubleshooting (Common Errors)

### 1) Excel Sheet is null
**Error:** `Sheet is null. Check sheetName and file path.`
- Ensure the sheet name passed in code exists (e.g., `"Scenario2"`).
- Improve the exception message to print available sheet names.

### 2) Search input not found (TimeoutException)
**Error:** waiting for visibility of element located by a search CSS selector.
- Noon DOM can change by locale/device.
- Use broader locators (`data-qa`, `type='text'`, header search container).
- Click the search icon first on mobile/responsive layouts.

### 3) `UnsupportedOperationException` on submit
**Error:** `To submit an element, it must be nested inside a form element`
- Replace `.submit()` with `Keys.ENTER` or a button click.

### 4) SLF4J / Log4j “no provider”
- Add a logging backend (Logback or log4j-core) to enable logs.

### 5) CDP version mismatch warning
**Warning:** “CDP version 143 … closest 139”
- Update Selenium to a newer version to better match your Chrome version.
- This warning usually does not break basic UI automation.


