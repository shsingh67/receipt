## Receipt Processor API
A simple RESTful app to process receipts, calculate points based on various criteria, and store receipt data in a  in-memory database. This project is built with Spring Boot and includes Docker configuration for containerized deployment.

## Features
- POST /receipts: Submit a receipt for processing and storing.
- GET /receipts/{id}/points: Retrieve the points for a processed receipt by its ID.

## Instructions
To run the application locally with Docker, follow these steps:

- docker build -t receipt-app .
- docker run -p 9094:9094 receipt-app
