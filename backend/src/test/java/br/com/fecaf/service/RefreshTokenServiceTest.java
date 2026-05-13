package br.com.fecaf.service;

import br.com.fecaf.model.RefreshToken;
import br.com.fecaf.model.User;
import br.com.fecaf.repository.RefreshTokenRepository;
import br.com.fecaf.security.jwt.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository repository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Test
    @DisplayName("Deve validar um token com sucesso e retornar o usuário")
    void validateSucesso() {
        String tokenStr = "token-valido";
        User user = new User();
        user.setName("User");

        RefreshToken tokenEntity = RefreshToken.builder()
                .token(tokenStr)
                .expiresAt(OffsetDateTime.now().plusDays(1))
                .revoked(false)
                .user(user)
                .build();

        when(repository.findByToken(tokenStr)).thenReturn(Optional.of(tokenEntity));

        User result = refreshTokenService.validate(tokenStr);

        assertNotNull(result);
        assertEquals("User", result.getName());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o token esitver expirado")
    void validateTokenExpirado() {
        String tokenStr = "token-expirado";
        RefreshToken tokenEntity = RefreshToken.builder()
                .token(tokenStr)
                .expiresAt(OffsetDateTime.now().minusDays(1))
                .revoked(false)
                .build();

        when(repository.findByToken(tokenStr)).thenReturn(Optional.of(tokenEntity));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            refreshTokenService.validate(tokenStr);
        });

        assertEquals("Refresh token expirado ou revogado", exception.getMessage());
        verify(repository, times(1)).delete(tokenEntity);
    }

    @Test
    @DisplayName("Deve criar um novo Refresh Token para o usuário")
    void createSucesso(){
        User user = new User();

        when(repository.save(any(RefreshToken.class))).thenAnswer(i -> i.getArguments()[0]);

        RefreshToken created = refreshTokenService.create(user);

        assertNotNull(created.getToken());
        assertEquals(user, created.getUser());
        assertTrue(created.getExpiresAt().isAfter(OffsetDateTime.now()));
        verify(repository, times(1)).save(any());
    }
}
