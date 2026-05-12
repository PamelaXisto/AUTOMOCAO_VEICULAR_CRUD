package br.com.fecaf.repository;

import br.com.fecaf.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByExpiresAtBeforeOrRevokedIsTrue(OffsetDateTime now);
}
