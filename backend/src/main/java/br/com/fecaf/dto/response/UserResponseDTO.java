package br.com.fecaf.dto.response;

public record UserResponseDTO (

        Long id_user,
        String name,
        String surname,
        String email,
        String phone
) {}
