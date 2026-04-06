package br.com.fecaf.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

public record UserDTO(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(min=3, message = "O nome deve ter pelo menos 3 caracteres.")
        String name,

        @NotBlank(message = "O sobrenome é obrigatório.")
        @Size(min = 3, message = "O sobrenome deve ter pelo menos 3 caracteres.")
        String surname,

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "Email inválido.")
        String email,

        @NotBlank(message = "O cpf é obrigatório.")
        @CPF(message = "CPF inválido.")
        String cpf,

        @NotBlank(message = "O telefone é obrigatório.")
        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-\\d{4}$",
                message = "Formato de telefone inválido. Preencha novamente (XX) XXXXX-XXXX"
                )
        String phone,

        @NotNull(message = "Data de nascimento é obrigatório.")
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Past(message = "Informe uma data válida")
        LocalDate birthDate,

        @NotBlank(message = "A senhá é obrigatória.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, incluindo letras maiúsculas, letras minúsculas, números e caracteres especiais."
                )
        String password
) {}
