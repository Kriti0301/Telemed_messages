package com.praxes.telemed_messages.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Document(collection = "messages")
@CompoundIndex(name = "consult_ts", def = "{'consultationId': 1, 'sentAt': 1}")
@CompoundIndex(name = "consult_role_ts", def = "{'consultationId': 1, 'authorRole': 1, 'sentAt': 1}")
public class Message {

    @Id
    private String id;

    @Indexed
    private String consultationId;

    private String authorId;

    @Indexed
    private AuthorRole authorRole;

    private String content;

    @Indexed
    private Instant sentAt;

    public Message() {}

    public Message(String consultationId, String authorId, AuthorRole authorRole, String content, Instant  sentAt) {
        this.consultationId = consultationId;
        this.authorId = authorId;
        this.authorRole = authorRole;
        this.content = content;
        this.sentAt = sentAt;
    }

    // getters/setters
    public String getId() { return id; }
    public String getConsultationId() { return consultationId; }
    public void setConsultationId(String consultationId) { this.consultationId = consultationId; }
    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }
    public AuthorRole getAuthorRole() { return authorRole; }
    public void setAuthorRole(AuthorRole authorRole) { this.authorRole = authorRole; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Instant  getSentAt() { return sentAt; }
    public void setSentAt(Instant  sentAt) { this.sentAt = sentAt; }
}
