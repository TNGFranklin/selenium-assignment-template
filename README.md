# Selenium Assignment — XYZ Bank (GlobalSQA)



---

## About the Website

XYZ Bank is a practice banking application built with AngularJS, hosted on GlobalSQA. It simulates a simple banking system with two user roles:

- **Bank Manager** — can add customers, open accounts, and view/search the customer list
- **Customer** — can log in via dropdown, deposit money, withdraw money, and view transactions

---

## Project Structure

```
src/
├── main/java/
│   └── config/
│       └── ConfigReader.java          # Reads settings from config.properties
└── test/java/
    ├── base/
    │   └── BaseTest.java              # WebDriver setup and teardown
    ├── listeners/
    │   └── ScreenshotListener.java    # Auto-screenshots on test failure
    ├── pages/
    │   ├── BasePage.java              # Shared utilities for all page classes
    │   ├── LoginPage.java             # Login page interactions
    │   ├── CustomerAccountPage.java   # Customer account dashboard
    │   └── ManagerPage.java           # Bank Manager dashboard
    ├── tests/
    │   ├── LoginTest.java             # Login, logout, page title tests
    │   ├── ManagerTest.java           # Add customer, open account, customer list
    │   ├── CustomerTest.java          # Deposit, withdraw, transactions, cookies
    │   └── NavigationTest.java        # Static page, multiple pages, XPath tests
    └── utils/
        └── RandomDataGenerator.java   # Generates random test data
```

---

## How to Run the Tests

### Prerequisites

- Java 11 or higher
- Chrome browser installed
- Internet connection (tests run against live site)

### Run all tests

```bash
./gradlew test
```

On Windows:

```
gradlew.bat test
```

### Run in headless mode

Edit `src/test/resources/config.properties` and set:

```
headless=true
```

Then run:

```bash
./gradlew test
```

### View test report

After running, open the HTML report in your browser:

```
build/reports/tests/test/index.html
```

### View screenshots on failure

Failed test screenshots are saved to:

```
build/screenshots/
```

---

## Test Suites

| Suite | Tests | Description |
|---|---|---|
| LoginTest | 6 | Customer login, manager login, logout, page title |
| ManagerTest | 8 | Add customer, open account, customer list, search, sort, XPath |
| CustomerTest | 7 | Deposit, transactions, JS scroll, cookies, browser history |
| NavigationTest | 5 | Static content, multiple pages, complex XPath, page title |

**Total: 30 tests**

---

## Key Features Implemented

### Basic Tasks
- Login form (customer dropdown + submit)
- Form with logged-in user (deposit, withdrawal)
- Logout verification
- 8 different input field types filled
- 5 distinct forms submitted
- Static page content verification
- Multiple page iteration with loop
- 6 complex XPath expressions
- Dropdown selection (Select class)
- Page title verification with `getTitle()`
- Page Object pattern (LoginPage, CustomerAccountPage, ManagerPage)
- BasePage class extended by all page classes
- 8+ Java classes

### Advanced Tasks
- **WebDriver config** — ChromeOptions with window size, notifications disabled
- **Cookie manipulation** — add, read, delete cookies
- **Browser history** — `back()` and `forward()` navigation
- **Random data generation** — unique customer names, post codes, deposit amounts per run
- **Config file** — `config.properties` stores base URL, browser, timeouts
- **JavascriptExecutor** — `scrollIntoView()` used on account page
- **Screenshot on failure** — `ScreenshotListener` implements `ITestListener`
- **Headless execution** — controlled via `config.properties`

---

## Configuration

All settings are in `src/test/resources/config.properties`:

```properties
base.url=https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login
browser=chrome
headless=false
implicit.wait=10
explicit.wait=15
```

---

