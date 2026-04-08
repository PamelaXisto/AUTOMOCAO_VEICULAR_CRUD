package br.com.fecaf.dto.response;

public record UserResponseDTO (

        Long id,
        String name,
        String surname,
        String email,
        String phone
) {}
