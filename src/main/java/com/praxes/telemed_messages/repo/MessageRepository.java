package com.praxes.telemed_messages.repo;

import com.praxes.telemed_messages.domain.AuthorRole;
import com.praxes.telemed_messages.domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findAllByConsultationIdOrderBySentAtAsc(String consultationId);
    List<Message> findAllByConsultationIdAndAuthorRoleOrderBySentAtAsc(String consultationId, AuthorRole authorRole);
}
