package br.com.fecaf.service;


import br.com.fecaf.dto.request.VehicleDTO;
import br.com.fecaf.exception.custom.ResourceNotFoundException;
import br.com.fecaf.mapper.VehicleMapper;
import br.com.fecaf.model.Vehicle;
import br.com.fecaf.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }


    public Vehicle createVehicle(VehicleDTO dto) {
        Vehicle vehicle = vehicleMapper.toEntity(dto);
        return vehicleRepository.save(vehicle);
    }


    public Vehicle updateVehicle(Integer id, VehicleDTO dto) {
        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));

        vehicleMapper.updateVehicleFromDto(dto, existingVehicle);

        return vehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(int id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);
        }
        vehicleRepository.deleteById(id);
    }
}
