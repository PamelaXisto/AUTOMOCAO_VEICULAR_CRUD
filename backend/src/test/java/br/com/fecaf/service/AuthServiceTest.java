package br.com.fecaf.service;

import br.com.fecaf.dto.request.LoginDTO;
import br.com.fecaf.dto.response.LoginResponseDTO;
import br.com.fecaf.model.RefreshToken;
import br.com.fecaf.model.User;
import br.com.fecaf.security.jwt.JwtService;
import br.com.fecaf.security.jwt.JwtUserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;


    @Test
    @DisplayName("Deve realizar login com sucesso e retornar tokens")
    void loginSucesso() {

        LoginDTO loginDTO = new LoginDTO("user@email.com", "Password@123");

        User user = new User();
        user.setName("User");
        user.setEmail("user@email.com");

        JwtUserDetails userDetails = new JwtUserDetails(user);

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any())).thenReturn(auth);

        when(jwtService.generateToken(user)).thenReturn("token-fake-123");

        RefreshToken fakeRefreshToken = new RefreshToken();
        fakeRefreshToken.setToken("refresh-fake-456");
        when(refreshTokenService.create(user)).thenReturn(fakeRefreshToken);

        LoginResponseDTO response = authService.login(loginDTO);

        assertNotNull(response);
        assertEquals("User", response.name());
        assertEquals("token-fake-123", response.accessToken());
        assertEquals("refresh-fake-456", response.refreshToken());

        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtService, times(1)).generateToken(user);
    }
}
