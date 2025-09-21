package org.example.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.example.auth.domain.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {
    Optional<PasswordResetToken> findByToken(String token);
}
