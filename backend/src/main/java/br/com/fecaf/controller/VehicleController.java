package br.com.fecaf.controller;

import br.com.fecaf.dto.request.VehicleDTO;
import br.com.fecaf.model.Vehicle;
import br.com.fecaf.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Veículos", description = "Gerenciamento do catálogo de veículos")
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501", allowedHeaders = "*")
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(summary = "Listar veículos", description = "Acesso público para visualizar o catálogo.")
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @Operation(summary = "Cadastrar veículo", description = "Permite o cadastro de novos veículos. Requer ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Veículo cadastrado"),
            @ApiResponse(responseCode = "403", description = "Token inválido ou sem permissão de ADMIN")
    })
    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody VehicleDTO dto) {
        Vehicle newVehicle = vehicleService.createVehicle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVehicle);
    }

    @Operation(summary = "Atualizar dados de um veículo", description = "Atualiza as informações de um veículo existente com base no ID. Requer privilégios de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - Requer ROLE_ADMIN")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle (          @Parameter(description = "ID do veículo", example = "1")
                                                            @PathVariable Integer id, @RequestBody VehicleDTO dto){
        Vehicle updateVehicle = vehicleService.updateVehicle(id, dto);
        return ResponseEntity.ok(updateVehicle);
    }

    @Operation(summary = "Remover veículo", description = "Exclui permanentemente um veículo da base de dados. Requer privilégios de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Veículo removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - Requer ROLE_ADMIN")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(      @Parameter(description = "ID do veículo a ser deletado", example = "1")
                                                    @PathVariable int id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
