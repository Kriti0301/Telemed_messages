package com.praxes.telemed_messages.api;

import com.praxes.telemed_messages.domain.AuthorRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.OffsetDateTime;

public class CreateMessageRequest {
    @NotBlank
    private String authorId;

    @NotNull
    private AuthorRole authorRole;

    @NotBlank
    @Size(max = 2000)
    private String content;

    @NotNull
    private Instant sentAt;

    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }

    public AuthorRole getAuthorRole() { return authorRole; }
    public void setAuthorRole(AuthorRole authorRole) { this.authorRole = authorRole; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Instant getSentAt() { return sentAt; }
    public void setSentAt(Instant sentAt) { this.sentAt = sentAt; }
}