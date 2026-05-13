package br.com.fecaf.service;

import br.com.fecaf.dto.request.UserDTO;
import br.com.fecaf.dto.response.UserResponseDTO;
import br.com.fecaf.exception.custom.DuplicateCpfException;
import br.com.fecaf.exception.custom.DuplicateEmailException;
import br.com.fecaf.exception.custom.ResourceNotFoundException;
import br.com.fecaf.mapper.UserMapper;
import br.com.fecaf.model.Role;
import br.com.fecaf.model.User;
import br.com.fecaf.repository.RoleRepository;
import br.com.fecaf.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Deve registrar um usuário com sucesso")
    void registerUserSucesso() {
        UserDTO dto = new UserDTO(
                "User",
                "Teste",
                "user@email.com",
                "123.456.789-00",
                "(11) 99999-9999",
                LocalDate.of(1995, 5, 15),
                "Senha@123"
        );

        User userEntity = new User();
        Role roleUser = new Role();
        roleUser.setId(1L);
        roleUser.setName("USER");

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userRepository.existsByCpf(dto.cpf())).thenReturn(false);
        when(userMapper.toEntity(dto)).thenReturn(userEntity);
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(roleUser));
        when(passwordEncoder.encode(any())).thenReturn("hash-senha");
        when(userRepository.save(any())).thenReturn(userEntity);

        UserResponseDTO responseDTO = new UserResponseDTO(
                1L,
                "User",
                "Teste",
                "user@email.com",
                "(11) 99999-9999",
                "ENABLED"
        );
        when(userMapper.toResponseDTO(any())).thenReturn(responseDTO);

        UserResponseDTO response = userService.registerUser(dto);

        assertNotNull(response);
        assertEquals("User", response.name());
        verify(userRepository, times(1)).save(any());
        verify(passwordEncoder).encode("Senha@123");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar e-mail duplicado")
    void registerUserEmailDuplicado() {
        UserDTO dto = new UserDTO(
                "User", "Teste", "user@email.com", "123.456.789-00", "(11) 99999-9999",
                LocalDate.of(1995, 5, 15), "Senha@123"
        );
        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> userService.registerUser(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar CPF duplicado")
    void registerUserCpfDuplicado() {
        UserDTO dto = new UserDTO(
                "User", "Teste", "user@email.com", "123.456.789-00", "(11) 99999-9999",
                LocalDate.of(1995, 5, 15), "Senha@123"
        );
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByCpf(dto.cpf())).thenReturn(true);

        assertThrows(DuplicateCpfException.class, () -> userService.registerUser(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a Role USER não for encontrada")
    void registerUserRoleNotFound() {
        UserDTO dto = new UserDTO(
                "User", "Teste", "user@email.com", "123.456.789-00", "(11) 99999-9999",
                LocalDate.of(1995, 5, 15), "Senha@123"
        );
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByCpf(any())).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.registerUser(dto));
    }
}