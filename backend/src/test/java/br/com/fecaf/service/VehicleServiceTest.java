package br.com.fecaf.service;

import br.com.fecaf.dto.request.VehicleDTO;
import br.com.fecaf.exception.custom.ResourceNotFoundException;
import br.com.fecaf.mapper.VehicleMapper;
import br.com.fecaf.model.Vehicle;
import br.com.fecaf.repository.VehicleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    @DisplayName("Deve criar um veículo com sucesso")
    void createVehicleSucesso() {
        VehicleDTO dto = new VehicleDTO(
                "User",
                2024,
                "Teste",
                0.0,
                "Flex",
                true,
                100000.0,
                "http://foto.com"
        );
        Vehicle vehicle = new Vehicle();

        when(vehicleMapper.toEntity(dto)).thenReturn(vehicle);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle result = vehicleService.createVehicle(dto);

        assertNotNull(result);
        verify(vehicleRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve atualizar um veículo com sucesso")
    void updateVehicleSucesso() {
        Integer id = 1;
        VehicleDTO dto = new VehicleDTO(
                "User",
                2024,
                "Teste",
                100.0,
                "Flex",
                true,
                95000.0,
                "http://foto.com"
        );
        Vehicle existingVehicle = new Vehicle();

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(existingVehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(existingVehicle);

        Vehicle result = vehicleService.updateVehicle(id, dto);

        assertNotNull(result);
        verify(vehicleMapper, times(1)).updateVehicleFromDto(dto, existingVehicle);
        verify(vehicleRepository, times(1)).save(existingVehicle);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar veículo inexistente")
    void updateVehicleNotFound() {
        Integer id = 99;
        VehicleDTO dto = new VehicleDTO(
                "User", 2024, "Teste", 0.0, "Flex", true, 0.0, "url"
        );

        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vehicleService.updateVehicle(id, dto));
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar um veículo com sucesso")
    void deleteVehicleSucesso() {
        int id = 1;
        when(vehicleRepository.existsById(id)).thenReturn(true);

        vehicleService.deleteVehicle(id);

        verify(vehicleRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar veículo inexistente")
    void deleteVehicleNotFound() {
        int id = 99;
        when(vehicleRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> vehicleService.deleteVehicle(id));
        verify(vehicleRepository, never()).deleteById(anyInt());
    }
}