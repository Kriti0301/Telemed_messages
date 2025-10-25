**Telemed Consultation Messaging System**

Author: Kriti Jodhani
Date: October 2025
Purpose: Praxes Developer Technical Assessment

**Overview**

This project implements a lightweight telemedicine consultation messaging system that enables patients and doctors to exchange text messages securely within consultation sessions.

It is built using:

Spring Boot 3 (Java 23) — REST API backend

MongoDB (Flapdoodle embedded) — lightweight, in-memory persistence

Vanilla HTML + JavaScript — simple frontend for testing and interaction

The project demonstrates RESTful design, clear data modeling, and a scalable architecture that could easily evolve into a production-grade healthcare communication system.

**Setup Instructions**
**1) Prerequisites**

Java 17+ (tested with JDK 23)

Maven 3.9+

No external MongoDB or Docker installation required (Flapdoodle embedded DB runs automatically).

**2) Run the Application**

From the project root, run:

mvn clean spring-boot:run


After startup:

Backend API: http://localhost:8080

Frontend: open src/main/resources/static/index.html in a web browser
