package org.example.auth.service;

import org.example.auth.domain.User;
import org.example.auth.domain.PasswordResetToken;
import org.example.auth.repository.PasswordResetTokenRepository;
import org.example.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordResetTokenRepository tokens;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository users, PasswordResetTokenRepository tokens, PasswordEncoder encoder) {
        this.users = users;
        this.tokens = tokens;
        this.encoder = encoder;
    }

    public void register(String email, String rawPassword) {
        if(users.existsByEmail(email)) throw new IllegalArgumentException("E-mail ja existe");
        var u = new User();
        u.setEmail(email.toLowerCase().trim());
        u.setPasswordHash(encoder.encode(rawPassword));
        u.setRoles(Set.of("USER"));
        users.save(u);
    }

    public String createResetToken(String email) {
        var user = users.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new IllegalArgumentException("Email não encontrado"));
        var token = new PasswordResetToken();
        token.setUserId(user.getId());
        token.setToken(UUID.randomUUID().toString());
        token.setExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS));
        token.setUsed(false);
        tokens.save(token);
        return token.getToken();
    }

    public void resetPassword(String tokenValue, String newPassword) {
        var token = tokens.findByToken(tokenValue)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));
        if (token.isUsed() || token.getExpiresAt().isBefore(Instant.now()))
            throw new IllegalArgumentException("Token expirado/indisponível");

        var user = users.findById(token.getUserId()).orElseThrow();
        user.setPasswordHash(encoder.encode(newPassword));
        users.save(user);

        token.setUsed(true);
        tokens.save(token);
    }
}
