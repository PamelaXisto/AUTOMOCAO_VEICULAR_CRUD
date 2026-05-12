package br.com.fecaf.service;

import br.com.fecaf.dto.request.LoginDTO;
import br.com.fecaf.dto.response.LoginResponseDTO;
import br.com.fecaf.model.User;
import br.com.fecaf.security.jwt.JwtService;
import br.com.fecaf.security.jwt.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginDTO request) {

        JwtUserDetails userDetails = (JwtUserDetails)
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            ).getPrincipal();

        User user = userDetails.getUser();

        String accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.create(user);

        return new LoginResponseDTO(
                user.getName(),
                accessToken,
                refreshToken.getToken()
        );
    }
}
