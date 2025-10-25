package com.praxes.telemed_messages.api;

import com.praxes.telemed_messages.domain.Consultation;
import com.praxes.telemed_messages.repo.ConsultationRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;


@RestController
@RequestMapping("/consultations")
public class ConsultationController {

    private final ConsultationRepository consultations;

    public ConsultationController(ConsultationRepository consultations) {
        this.consultations = consultations;
    }

    @PostMapping
    public ResponseEntity<Consultation> create(@Valid @RequestBody Consultation request) {
        if (consultations.existsById(request.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if (request.getStartedAt() == null) {
            request.setStartedAt(Instant.now());
        }
        Consultation saved = consultations.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping
    public List<Consultation> getAllConsultations() {
        return consultations.findAll();
    }

}
