package br.com.fecaf.service;

import br.com.fecaf.dto.request.LoginDTO;
import br.com.fecaf.dto.response.LoginResponseDTO;
import br.com.fecaf.exception.custom.InvalidCredentialsException;
import br.com.fecaf.model.User;
import br.com.fecaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginDTO request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }
        return new LoginResponseDTO(
                user.getName()
        );
    }
}
