package br.com.fecaf.service;

import br.com.fecaf.dto.response.RefreshTokenResponseDTO;
import br.com.fecaf.model.RefreshToken;
import br.com.fecaf.model.User;
import br.com.fecaf.repository.RefreshTokenRepository;
import br.com.fecaf.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final JwtService jwtService;

    public RefreshToken create(User user){
        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiresAt(OffsetDateTime.now().plusDays(30))
                .user(user)
                .build();

        return repository.save(token);
    }

    public User validate(String token) {
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));

        if (refreshToken.isRevoked() ||
            refreshToken.getExpiresAt().isBefore(OffsetDateTime.now())) {

            repository.delete(refreshToken);
            throw new RuntimeException("Refresh token expirado ou revogado");

        }

        return refreshToken.getUser();
    }

    public RefreshTokenResponseDTO refresh(String token) {
        User user = validate(token);

        String newAccess = jwtService.generateToken(user);
        RefreshToken newRefresh = create(user);

        repository.findByToken(token).ifPresent(old -> {
            old.setRevoked(true);
            repository.save(old);
        });

        return new RefreshTokenResponseDTO(
                newAccess,
                newRefresh.getToken()
        );
    }

    public void revoke(String token) {
        repository.findByToken(token).ifPresent(t -> {
            t.setRevoked(true);
            repository.save(t);
        });
    }
}
