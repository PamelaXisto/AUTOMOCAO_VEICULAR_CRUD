package br.com.fecaf.service;

import br.com.fecaf.dto.request.UserDTO;
import br.com.fecaf.exception.custom.DuplicateCpfException;
import br.com.fecaf.exception.custom.DuplicateEmailException;
import br.com.fecaf.mapper.UserMapper;
import br.com.fecaf.model.User;
import br.com.fecaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO registerUser(UserDTO userDTO) {

        User user = userMapper.toEntity(userDTO);

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException();
        }

        if (userRepository.existsByCpf(user.getCpf())) {
            throw new DuplicateCpfException();
        }

        String encryptedPassword = passwordEncoder.encode(userDTO.password());
        user.setPasswordHash(encryptedPassword);
        log.debug("User registered successfully: {}", user.getEmail());

        user = userRepository.save(user);

        return userMapper.toDTO(user);
    }
}
