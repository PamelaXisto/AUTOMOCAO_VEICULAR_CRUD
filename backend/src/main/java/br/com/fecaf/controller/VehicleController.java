package br.com.fecaf.controller;

import br.com.fecaf.dto.request.VehicleDTO;
import br.com.fecaf.model.Vehicle;
import br.com.fecaf.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501", allowedHeaders = "*")
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody VehicleDTO dto) {
        Vehicle newVehicle = vehicleService.createVehicle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Integer id, @RequestBody VehicleDTO dto) {
        Vehicle updateVehicle = vehicleService.updateVehicle(id, dto);
        return ResponseEntity.ok(updateVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable int id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
