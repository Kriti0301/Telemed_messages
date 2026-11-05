# Telemed Consultation Messaging API

A REST API for text messaging between patients and doctors inside telemedicine consultations, built with **Spring Boot** and **MongoDB**. A small web page is included for trying it out.

Built by Kriti Jodhani (October 2025) as a developer technical assessment.

Front end: [Telemed Messages – Angular UI](https://github.com/Kriti0301/telemed-angular-ui), an Angular app that consumes this API.

---

## Features

- Create and list consultations (one patient and one doctor per consultation)
- Send messages in a consultation, and list them in chronological order
- Filter messages by author role (`?role=PATIENT` or `?role=DOCTOR`)
- Input validation: required fields, and a 2,000-character limit on messages
- Clear errors: `404` for an unknown consultation, `409` for a duplicate consultation ID, and `400` with field details for invalid JSON or missing fields
- MongoDB indexes for fast lookups by consultation, role and time
- Embedded MongoDB, so no database install is needed, and demo data is loaded at startup

---

## Tech stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.5 (Web, Validation, Data MongoDB) |
| Database | MongoDB, embedded at runtime with Flapdoodle |
| Frontend | Plain HTML + JavaScript (`src/main/resources/static/index.html`) |
| Build | Maven (wrapper included) |

---

## Getting started

### Prerequisites

- **Java 21** or newer
- No MongoDB or Docker is needed. An embedded MongoDB is downloaded and started automatically on first run.

### Run

```bash
./mvnw spring-boot:run        # macOS / Linux
mvnw.cmd spring-boot:run      # Windows
```

- API: `http://localhost:8080`
- Web UI: open **`http://localhost:8080/`** in a browser. Spring serves it, so it can call the API without CORS problems.

On startup, `MongoDataSeeder` inserts **2 consultations** (`CONS-1`, `CONS-2`) and **14 demo messages**.

### Using the web UI

1. Choose a consultation from the dropdown and click **Load**.
2. Optionally filter by role.
3. Type a message and click **Send**. It appears without reloading the page.

---

## API

### `POST /consultations`: create a consultation

```bash
curl -X POST http://localhost:8080/consultations \
  -H "Content-Type: application/json" \
  -d '{"id":"CONS-3","patientId":"P900","doctorId":"D111","startedAt":"2025-10-23T11:00:00Z"}'
```

Response `201 Created`:

```json
{ "id": "CONS-3", "patientId": "P900", "doctorId": "D111", "startedAt": "2025-10-23T11:00:00Z" }
```

If `startedAt` is omitted, it defaults to the current time. If the `id` already exists, the response is `409 Conflict`.

### `GET /consultations`: list consultations

```bash
curl http://localhost:8080/consultations
```

```json
[
  { "id": "CONS-1", "patientId": "P123", "doctorId": "D456", "startedAt": "2025-10-22T14:00:00Z" },
  { "id": "CONS-2", "patientId": "P789", "doctorId": "D012", "startedAt": "2025-10-22T15:00:00Z" }
]
```

### `POST /consultations/{consultationId}/messages`: send a message

```bash
curl -X POST http://localhost:8080/consultations/CONS-2/messages \
  -H "Content-Type: application/json" \
  -d '{"authorId":"P789","authorRole":"PATIENT","content":"I feel sleepy after taking medication.","sentAt":"2025-10-22T15:06:00Z"}'
```

Response `201 Created` returns the saved message. If the consultation doesn't exist, the response is `404 Not Found`.

### `GET /consultations/{consultationId}/messages`: list messages

```bash
curl http://localhost:8080/consultations/CONS-1/messages
curl "http://localhost:8080/consultations/CONS-1/messages?role=DOCTOR"
```

Messages come back sorted by `sentAt`, oldest first:

```json
[
  { "consultationId": "CONS-1", "authorId": "P123", "authorRole": "PATIENT",
    "content": "Hi Doctor, I have a headache since yesterday.", "sentAt": "2025-10-22T14:02:00Z" },
  { "consultationId": "CONS-1", "authorId": "D456", "authorRole": "DOCTOR",
    "content": "Can you rate the pain 1–10?", "sentAt": "2025-10-22T14:04:00Z" }
]
```

### Validation errors

A missing or invalid field returns `400 Bad Request` (handled by `GlobalExceptionHandler`):

```json
{ "error": "ValidationError", "details": [ { "field": "content", "message": "must not be blank" } ] }
```

---

## Design decisions

**Data model.** One consultation has many messages.

| Collection | Fields |
|---|---|
| `consultations` | `id`, `patientId`, `doctorId`, `startedAt` |
| `messages` | `id`, `consultationId`, `authorId`, `authorRole` (`PATIENT` or `DOCTOR`), `content`, `sentAt` |

**Indexes.** The `messages` collection has compound indexes on `(consultationId, sentAt)` and `(consultationId, authorRole, sentAt)`. These match the two queries the API runs: all messages in time order, and messages by role in time order.

**Separate request DTO.** `CreateMessageRequest` carries the validation rules, so the API never binds request bodies directly to the stored entity.

**Embedded MongoDB.** This keeps setup to one command for reviewers. In production it would be replaced by a managed MongoDB instance.

---

## Project structure

```text
src/main/java/com/praxes/telemed_messages/
├── api/        # REST controllers, request DTO, global exception handler
├── domain/     # Consultation, Message, AuthorRole
├── repo/       # Spring Data MongoDB repositories
├── seed/       # Demo data loaded at startup
└── TelemedMessagesApplication.java
src/main/resources/
├── application.yml
└── static/index.html   # Web UI
```

---

## Testing

```bash
./mvnw test
```

Currently this includes a Spring context-load test only.

---

## Next steps for production

| Area | Improvement |
|---|---|
| Security | Authentication (for example JWT), with checks that only the consultation's patient and doctor can read or send messages; HTTPS |
| Privacy | Encrypt message content at rest, to support PIPEDA/HIPAA requirements |
| Scale | Pagination for long conversations; external MongoDB replica set |
| Quality | Controller and repository tests (MockMvc, Testcontainers) |
| Real-time | WebSocket push instead of manual reload |
