package br.com.fecaf.service;

import br.com.fecaf.dto.request.UserDTO;
import br.com.fecaf.dto.response.UserResponseDTO;
import br.com.fecaf.enums.UserStatus;
import br.com.fecaf.exception.custom.DuplicateCpfException;
import br.com.fecaf.exception.custom.DuplicateEmailException;
import br.com.fecaf.exception.custom.ResourceNotFoundException;
import br.com.fecaf.mapper.UserMapper;
import br.com.fecaf.model.Role;
import br.com.fecaf.model.User;
import br.com.fecaf.repository.RoleRepository;
import br.com.fecaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO registerUser(UserDTO userDTO) {

        validateUser(userDTO);

        User user = userMapper.toEntity(userDTO);

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role USER not found"));

        user.setRole(role);
        user.setPasswordHash(passwordEncoder.encode(userDTO.password()));

        User savedUser = userRepository.save(user);

        log.info("User created successfully. Email: {}", savedUser.getEmail());

        return userMapper.toResponseDTO(savedUser);
    }

    public List<UserResponseDTO> listAllUsers() {
        log.info("Request to list all users");

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }


    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setStatusUser(UserStatus.DISABLED);

        userRepository.save(user);

        log.info("User soft deleted. ID: {}", id);
    }

    private void validateUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new DuplicateEmailException();
        }

        if (userRepository.existsByCpf(userDTO.cpf())) {
            throw new DuplicateCpfException();
        }
    }
}
