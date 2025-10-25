package com.praxes.telemed_messages.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.OffsetDateTime;

@Document(collection = "consultations")
public class Consultation {

    @Id
    private String id;

    @Indexed
    private String patientId;

    @Indexed
    private String doctorId;

    private Instant  startedAt;
    private Instant endedAt;

    public Consultation() {}

    public Consultation(String id, String patientId, String doctorId, Instant  startedAt) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.startedAt = startedAt;
    }

    // getters/setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public Instant  getStartedAt() { return startedAt; }
    public void setStartedAt(Instant  startedAt) { this.startedAt = startedAt; }
    public Instant  getEndedAt() { return endedAt; }
    public void setEndedAt(Instant  endedAt) { this.endedAt = endedAt; }
}
