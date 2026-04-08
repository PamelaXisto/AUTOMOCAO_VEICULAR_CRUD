package br.com.fecaf.service;

import br.com.fecaf.dto.request.UserDTO;
import br.com.fecaf.mapper.UserMapper;
import br.com.fecaf.model.User;
import br.com.fecaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO registerUser(UserDTO userDTO) {

        User user = userMapper.toEntity(userDTO);

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        if (userRepository.existsByCpf(user.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        user.setPasswordHash(userDTO.password());

        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }
}
