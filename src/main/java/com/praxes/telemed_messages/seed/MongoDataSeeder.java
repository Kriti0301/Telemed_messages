package com.praxes.telemed_messages.seed;

import com.praxes.telemed_messages.domain.AuthorRole;
import com.praxes.telemed_messages.domain.Consultation;
import com.praxes.telemed_messages.domain.Message;
import com.praxes.telemed_messages.repo.ConsultationRepository;
import com.praxes.telemed_messages.repo.MessageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MongoDataSeeder implements CommandLineRunner {

    private final ConsultationRepository consultations;
    private final MessageRepository messages;

    public MongoDataSeeder(ConsultationRepository consultations, MessageRepository messages) {
        this.consultations = consultations;
        this.messages = messages;
    }

    @Override
    public void run(String... args) {
        boolean seeded = false;

        if (consultations.count() == 0) {
            consultations.save(new Consultation("CONS-1", "P123", "D456",
                    Instant.parse("2025-10-22T14:00:00Z")));
            consultations.save(new Consultation("CONS-2", "P789", "D012",
                    Instant.parse("2025-10-22T15:00:00Z")));
            seeded = true;
        }

        if (messages.count() == 0) {
            messages.save(new Message("CONS-1", "P123", AuthorRole.PATIENT,
                    "Hi Doctor, I have a headache since yesterday.",
                    Instant.parse("2025-10-22T14:02:00Z")));
            messages.save(new Message("CONS-1", "D456", AuthorRole.DOCTOR,
                    "Thanks for reaching out. Can you rate the pain 1–10?",
                    Instant.parse("2025-10-22T14:04:00Z")));
            messages.save(new Message("CONS-1", "P123", AuthorRole.PATIENT,
                    "About 6/10, throbbing near temples.",
                    Instant.parse("2025-10-22T14:06:00Z")));
            messages.save(new Message("CONS-1", "D456", AuthorRole.DOCTOR,
                    "Any nausea, vision changes, or fever?",
                    Instant.parse("2025-10-22T14:08:00Z")));
            messages.save(new Message("CONS-1", "P123", AuthorRole.PATIENT,
                    "No nausea, mild sensitivity to light.",
                    Instant.parse("2025-10-22T14:10:00Z")));
            messages.save(new Message("CONS-1", "D456", AuthorRole.DOCTOR,
                    "Sounds like a tension headache. Hydrate and rest; acetaminophen 500mg if needed.",
                    Instant.parse("2025-10-22T14:12:00Z")));

            messages.save(new Message("CONS-2", "P789", AuthorRole.PATIENT,
                    "Hello, I started a new med and feel drowsy.",
                    Instant.parse("2025-10-22T15:03:00Z")));
            messages.save(new Message("CONS-2", "D012", AuthorRole.DOCTOR,
                    "Which medication and dose are you taking?",
                    Instant.parse("2025-10-22T15:05:00Z")));
            messages.save(new Message("CONS-2", "P789", AuthorRole.PATIENT,
                    "It's loratadine 10mg daily.",
                    Instant.parse("2025-10-22T15:06:30Z")));
            messages.save(new Message("CONS-2", "D012", AuthorRole.DOCTOR,
                    "Drowsiness can happen, though less common. Any driving or machinery work?",
                    Instant.parse("2025-10-22T15:08:30Z")));
            messages.save(new Message("CONS-2", "P789", AuthorRole.PATIENT,
                    "No driving today. I also feel dry mouth.",
                    Instant.parse("2025-10-22T15:10:00Z")));
            messages.save(new Message("CONS-2", "D012", AuthorRole.DOCTOR,
                    "Increase fluids, consider sugar-free lozenges. If persists >48h, let me know.",
                    Instant.parse("2025-10-22T15:12:00Z")));
            messages.save(new Message("CONS-2", "P789", AuthorRole.PATIENT,
                    "Understood, I will monitor symptoms.",
                    Instant.parse("2025-10-22T15:13:30Z")));
            messages.save(new Message("CONS-2", "D012", AuthorRole.DOCTOR,
                    "Thank you. Reach out if anything worsens.",
                    Instant.parse("2025-10-22T15:15:00Z")));

            seeded = true;
        }

        if (seeded) {
            System.out.println("Mongo seed complete.");
        } else {
            System.out.println("Mongo already contains data — skipping seed.");
        }
    }
}
