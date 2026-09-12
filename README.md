# SalesReporter

Command-line tool that reads a CSV file of daily product sales, computes a
summary report, and outputs it to the console or to a text file.

Built for SENG 21222 – Software Construction, Assignment 1 (2026),
University of Kelaniya.

## Requirements

- Java 17+ (JDK)
- Maven 3.8+ (for building and running tests)

## Build

```bash
mvn clean package
```

This produces `target/SalesReporter.jar` and runs all unit tests.

## Run

```bash
java -jar target/SalesReporter.jar <csv-file-path> <output-method> [output-file-path]
```

- `<csv-file-path>` — path to the input CSV file
- `<output-method>` — `console` or `file`
- `[output-file-path]` — required only when output-method is `file`

### Examples

```bash
# Print report to the console
java -jar target/SalesReporter.jar sample-data/sample_sales.csv console

# Write report to a text file
java -jar target/SalesReporter.jar sample-data/sample_sales.csv file report.txt
```

## CSV Format

```
product_id, product_name, category, quantity_sold, unit_price
P001, Wireless Mouse, Electronics, 12, 25.50
...
```

The header row is detected and skipped automatically.

## Running Tests

```bash
mvn test
```

Unit tests cover:
- Revenue calculation (per product, per category, grand total) — `SalesCalculatorTest`
- Best-seller and highest-revenue detection — `SalesCalculatorTest`
- CSV parsing and error handling (missing file, missing columns, invalid numbers) — `CsvProductReaderTest`

## Design Overview

| Package      | Responsibility                                                        |
|--------------|------------------------------------------------------------------------|
| `model`      | `Product` — plain data holder                                          |
| `io`         | `ProductReader` interface + `CsvProductReader` implementation          |
| `report`     | `SalesCalculator` (computation) + `SalesSummary` (result holder)       |
| `formatter`  | `ReportFormatter` — turns a `SalesSummary` into display text           |
| `output`     | `ReportOutput` strategy interface, `ConsoleReportOutput`, `FileReportOutput`, `ReportOutputFactory` |
| `exception`  | Custom checked exceptions for CSV and output-method errors             |
| `service`    | Dependency Inversion by introducing the `SalesReportService` class     |

**Extensibility:** Adding a new output method (e.g. email) only requires a
new class implementing `ReportOutput` and one new case in
`ReportOutputFactory` — no other code changes (Open-Closed Principle).

**Single Responsibility:** reading, calculating, formatting, and delivering
the report are each handled by a separate class, so each class has exactly
one reason to change.
