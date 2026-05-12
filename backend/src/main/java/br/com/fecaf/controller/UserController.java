package br.com.fecaf.controller;

import br.com.fecaf.dto.request.UserDTO;
import br.com.fecaf.dto.response.UserResponseDTO;
import br.com.fecaf.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Endpoints para gestão de usuários do sistema")
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @Operation(summary = "Registrar um novo usuário", description = "Cria um usuário comum no sistema. O CPF e E-mail devem ser únicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação ou CPF/Email já cadastrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserDTO userDTO) {

        UserResponseDTO createdUser = userService.registerUser(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários. Requer perfil ADMIN.")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.listAllUsers());
    }


    @Operation(summary = "Desativar um usuário (Soft Delete)", description = "Apenas perfil ADMIN altera o status do usuário para 'DISABLED'. O registro permanece no banco para fins de auditoria, mas perde acesso ao sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para realizar esta operação")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (   @Parameter(description = "ID do usuário a ser desativado", example = "1")
                                               @PathVariable Long id){

        userService.softDeleteUser(id);

        return ResponseEntity.noContent().build();
    }

}
