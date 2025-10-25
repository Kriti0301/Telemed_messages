# **Telemed Consultation Messaging System**

Author: Kriti Jodhani
Date: October 2025
Purpose: Praxes Developer Technical Assessment

# **Overview**

This project implements a lightweight telemedicine consultation messaging system that enables patients and doctors to exchange text messages securely within consultation sessions.

It is built using:

Spring Boot 3 (Java 23) — REST API backend

MongoDB (Flapdoodle embedded) — lightweight, in-memory persistence

Vanilla HTML + JavaScript — simple frontend for testing and interaction

The project demonstrates RESTful design, clear data modeling, and a scalable architecture that could easily evolve into a production-grade healthcare communication system.

______

# **Setup Instructions**

## **1) Prerequisites**

Java 17+ (tested with JDK 23)

Maven 3.9+

No external MongoDB or Docker installation required (Flapdoodle embedded DB runs automatically).

## **2) Run the Application**

From the project root, run:

mvn clean spring-boot:run


After startup:

Backend API: http://localhost:8080

Frontend: open src/main/resources/static/index.html in a web browser

## **3) Seed Data**

The database is automatically populated by the MongoDataSeeder class at startup.

If you want to manually seed or reset the data, use the following curl commands.

➤ **Seed Consultations**
# Consultation 1
curl -X POST http://localhost:8080/consultations \
-H "Content-Type: application/json" \
-d '{
"id": "CONS-1",
"patientId": "P123",
"doctorId": "D456",
"startedAt": "2025-10-22T14:00:00Z"
}'

# Consultation 2
curl -X POST http://localhost:8080/consultations \
-H "Content-Type: application/json" \
-d '{
"id": "CONS-2",
"patientId": "P789",
"doctorId": "D012",
"startedAt": "2025-10-22T15:00:00Z"
}'

➤ **Seed Messages**
# Message in Consultation 1
curl -X POST http://localhost:8080/consultations/CONS-1/messages \
-H "Content-Type: application/json" \
-d '{
"authorId": "P123",
"authorRole": "PATIENT",
"content": "Hi Doctor, I have a headache since yesterday.",
"sentAt": "2025-10-22T14:02:00Z"
}'

# Message in Consultation 2
curl -X POST http://localhost:8080/consultations/CONS-2/messages \
-H "Content-Type: application/json" \
-d '{
"authorId": "D012",
"authorRole": "DOCTOR",
"content": "Can you describe your side effects in detail?",
"sentAt": "2025-10-22T15:05:00Z"
}'

