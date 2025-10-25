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

### ➤ **Seed Consultations**
#### Consultation 1
POST /consultations

```json
{
"id": "CONS-1",
"patientId": "P123",
"doctorId": "D456",
"startedAt": "2025-10-22T14:00:00Z"
}
```

#### Consultation 2
POST /consultations
```json
{
"id": "CONS-2",
"patientId": "P789",
"doctorId": "D012",
"startedAt": "2025-10-22T15:00:00Z"
}
```


### ➤ **Seed Messages**
#### Message in Consultation 1
POST /consultations/CONS-1/messages
```json
{
"authorId": "P123",
"authorRole": "PATIENT",
"content": "Hi Doctor, I have a headache since yesterday.",
"sentAt": "2025-10-22T14:02:00Z"
}
```
#### Message in Consultation 2
POST /consultations/CONS-2/messages
```json
{
"authorId": "D012",
"authorRole": "DOCTOR",
"content": "Can you describe your side effects in detail?",
"sentAt": "2025-10-22T15:05:00Z"
}
```

# **API Documentation**

Each REST endpoint is fully described with example curl requests and responses.

## **1. Create a Consultation**

POST /consultations

Creates a new consultation record between a doctor and a patient.

Request

```json
{
"id": "CONS-3",
"patientId": "P900",
"doctorId": "D111",
"startedAt": "2025-10-23T11:00:00Z"
}
```

Response

```json
{
"id": "CONS-3",
"patientId": "P900",
"doctorId": "D111",
"startedAt": "2025-10-23T11:00:00Z"
}
```

## **2) Get All Consultations**

GET /consultations

Returns all consultations.

Response

```json
[
{
"id": "CONS-1",
"patientId": "P123",
"doctorId": "D456",
"startedAt": "2025-10-22T14:00:00Z"
},
{
"id": "CONS-2",
"patientId": "P789",
"doctorId": "D012",
"startedAt": "2025-10-22T15:00:00Z"
}
]
```

## **3) Get Messages for a Consultation**

GET /consultations/{consultationId}/messages

Fetches all messages in a specific consultation.
Supports optional query parameter: role=PATIENT or role=DOCTOR.

# All messages

GET /consultations/CONS-1/messages

# Only doctor messages

GET /consultations/CONS-1/messages?role=DOCTOR

Response
```json
[
{
"consultationId": "CONS-1",
"authorId": "P123",
"authorRole": "PATIENT",
"content": "Hi Doctor, I have a headache since yesterday.",
"sentAt": "2025-10-22T14:02:00Z"
},
{
"consultationId": "CONS-1",
"authorId": "D456",
"authorRole": "DOCTOR",
"content": "Can you rate the pain 1–10?",
"sentAt": "2025-10-22T14:04:00Z"
}
]
```

## **4) Send a Message**

POST /consultations/{consultationId}/messages

Adds a new message to a consultation.

Request
```json
{
"authorId": "P789",
"authorRole": "PATIENT",
"content": "I feel sleepy after taking medication.",
"sentAt": "2025-10-22T15:06:00Z"
}
```

Response
```json
{
"consultationId": "CONS-2",
"authorId": "P789",
"authorRole": "PATIENT",
"content": "I feel sleepy after taking medication.",
"sentAt": "2025-10-22T15:06:00Z"
}
```
**Sample Data**
Consultations Collection

```json
[
{
"_id": "CONS-1",
"patientId": "P123",
"doctorId": "D456",
"startedAt": "2025-10-22T14:00:00Z"
},
{
"_id": "CONS-2",
"patientId": "P789",
"doctorId": "D012",
"startedAt": "2025-10-22T15:00:00Z"
}
]
```

Messages Collection
```json
[
{
"_id": "msg1",
"consultationId": "CONS-1",
"authorId": "P123",
"authorRole": "PATIENT",
"content": "Hi Doctor, I have a headache since yesterday.",
"sentAt": "2025-10-22T14:02:00Z"
},
{
"_id": "msg2",
"consultationId": "CONS-1",
"authorId": "D456",
"authorRole": "DOCTOR",
"content": "Can you rate the pain 1–10?",
"sentAt": "2025-10-22T14:04:00Z"
},
{
"_id": "msg3",
"consultationId": "CONS-2",
"authorId": "P789",
"authorRole": "PATIENT",
"content": "Hello, I started a new med and feel drowsy.",
"sentAt": "2025-10-22T15:03:00Z"
}
]
```
# **Architecture Decisions**

## **Data Model**

Entity	Fields	Description
Consultation	id, patientId, doctorId, startedAt	Represents a session between one patient and one doctor
Message	consultationId, authorId, authorRole, content, sentAt	Represents a message exchanged in a consultation

Relationship:
One Consultation → Many Messages

Indexes (recommended for production):

consultationId → for fast message lookups

sentAt → for sorting messages chronologically

## **Technology Stack**

Layer	Technology	Purpose
Backend	Spring Boot 3	REST API framework
Database	MongoDB Embedded (Flapdoodle)	Local, in-memory persistence
Frontend	HTML + JavaScript	Basic UI for testing
Data Seeder	CommandLineRunner	Loads demo consultations and messages automatically

## **Making This Production-Ready**

Category	Improvement	Benefit
Security	Add JWT authentication, HTTPS, and validation	Protects sensitive patient data
Performance	Add pagination for messages	Handles large chat histories
Reliability	Use external MongoDB replica set	Prevents data loss on restart
Scalability	Dockerize + load balance API	Supports many concurrent sessions
Data Integrity	Add schema validation and error logging	Ensures consistent records
Compliance	Implement HIPAA/PIPEDA encryption	Meets healthcare data privacy laws

## **UI Usage**

1. Start the app using mvn spring-boot:run
2. Open src/main/resources/static/index.html
3. Choose a consultation (CONS-1 or CONS-2) from the dropdown
4. Click Load to view messages
5. Type a message and click Send

Messages appear instantly without reloading the page.

## **Project Structure**

telemed-messages/
│
├── src/main/java/com/praxes/telemed_messages/
│   ├── controller/
│   ├── domain/
│   ├── repo/
│   ├── seed/
│   └── TelemedMessagesApplication.java
│
├── src/main/resources/
│   ├── application.yml
│   └── static/index.html
│
└── pom.xml

**Summary**

This project fulfills all technical and documentation criteria:
* RESTful API endpoints with curl examples
* Embedded MongoDB with automatic data seeding
* Simple web UI for testing
* Well-documented architecture and production plan
* Sample data covering both consultations and messages