package br.com.fecaf.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Schema(description = "Dados para criação/atualização de usuário")
public record UserDTO(

        @Schema(description = "Nome do usuário", example = "Ana")
        @NotBlank(message = "O nome é obrigatório.")
        @Size(min=3, message = "O nome deve ter pelo menos 3 caracteres.")
        String name,

        @Schema(description = "Sobrenome do usuário", example = "Silva")
        @NotBlank(message = "O sobrenome é obrigatório.")
        @Size(min = 3, message = "O sobrenome deve ter pelo menos 3 caracteres.")
        String surname,

        @Schema(description = "E-mail único para acesso", example = "anasilva@email.com")
        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "Email inválido.")
        String email,

        @Schema(description = "CPF válido (apenas números ou formatado)", example = "123.456.789-00")
        @NotBlank(message = "O cpf é obrigatório.")
        @CPF(message = "CPF inválido.")
        String cpf,

        @Schema(description = "Telefone de contato", example = "(11) 99999-9999")
        @NotBlank(message = "O telefone é obrigatório.")
        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-\\d{4}$",
                message = "Formato de telefone inválido. Preencha novamente (XX) XXXXX-XXXX"
                )
        String phone,

        @Schema(description = "Data de nascimento no formato DD/MM/AAAA", example = "15/05/1995")
        @NotNull(message = "Data de nascimento é obrigatório.")
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Past(message = "Informe uma data válida")
        LocalDate birthDate,

        @Schema(description = "Senha forte: min 8 caracteres, maiúscula, minúscula, número e especial",
                example = "Senha@123", format = "password")
        @NotBlank(message = "A senhá é obrigatória.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, incluindo letras maiúsculas, letras minúsculas, números e caracteres especiais."
                )
        String password
) {}
