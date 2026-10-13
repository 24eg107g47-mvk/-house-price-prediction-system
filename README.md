# House Price Prediction System

A Java full-stack web app that estimates a house's price from its location, built-up area,
bedrooms, bathrooms and parking availability, and keeps a history of past predictions.

## Tech stack
- Frontend: HTML, CSS, JavaScript
- Backend: Java 17, Spring Boot 3
- Database: H2 (file-based, `./data/housedb`)
- Build: Apache Maven
- Communication: REST API

## Run
```bash
mvn spring-boot:run
```
Open http://localhost:8080. H2 console: http://localhost:8080/h2-console
(JDBC URL `jdbc:h2:file:./data/housedb`, user `sa`, empty password).

## REST API
| Method | Endpoint            | Description                          |
|--------|---------------------|--------------------------------------|
| POST   | `/api/predict`      | Estimate a price and save the record |
| GET    | `/api/predictions`  | List previous predictions            |
| DELETE | `/api/predictions`  | Clear prediction history             |

Example:
```bash
curl -X POST localhost:8080/api/predict -H "Content-Type: application/json" \
  -d '{"location":"Hyderabad","builtUpArea":1200,"bedrooms":3,"bathrooms":2,"parking":true}'
```

## Price formula
`price = area × rate(location) + bedrooms × 2,00,000 + bathrooms × 1,00,000 + (parking ? 3,00,000 : 0)`

Per-sq.ft rates live in `PredictionService`; unknown locations use a default rate.
The rates are illustrative, not market data.

## Future enhancement
Replace `PredictionService.estimate()` with a trained regression model (e.g. served via a
Python microservice or exported to PMML/ONNX) using historical housing data.
