package com.praxes.telemed_messages.repo;

import com.praxes.telemed_messages.domain.Consultation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsultationRepository extends MongoRepository<Consultation, String> { }
