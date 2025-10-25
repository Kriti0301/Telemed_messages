package com.praxes.telemed_messages.api;

import com.praxes.telemed_messages.domain.AuthorRole;
import com.praxes.telemed_messages.domain.Message;
import com.praxes.telemed_messages.repo.ConsultationRepository;
import com.praxes.telemed_messages.repo.MessageRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultations/{consultationId}/messages")
public class MessageController {

    private final MessageRepository messages;
    private final ConsultationRepository consultations;

    public MessageController(MessageRepository messages, ConsultationRepository consultations) {
        this.messages = messages;
        this.consultations = consultations;
    }

    @PostMapping
    public ResponseEntity<?> addMessage(@PathVariable String consultationId,
                                        @Valid @RequestBody CreateMessageRequest req) {
        if (!consultations.existsById(consultationId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Consultation not found: " + consultationId);
        }

        Message m = new Message(
                consultationId,
                req.getAuthorId(),
                req.getAuthorRole(),
                req.getContent(),
                req.getSentAt()
        );
        messages.save(m);
        return ResponseEntity.status(HttpStatus.CREATED).body(m);
    }

    @GetMapping
    public ResponseEntity<List<Message>> listMessages(@PathVariable String consultationId,
                                                      @RequestParam(value = "role", required = false) AuthorRole role) {
        if (!consultations.existsById(consultationId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Message> result = (role == null)
                ? messages.findAllByConsultationIdOrderBySentAtAsc(consultationId)
                : messages.findAllByConsultationIdAndAuthorRoleOrderBySentAtAsc(consultationId, role);

        return ResponseEntity.ok(result);
    }
}
