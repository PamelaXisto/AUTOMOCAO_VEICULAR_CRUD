package br.com.fecaf.service;


import br.com.fecaf.model.Vehicle;
import br.com.fecaf.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Validações

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository veiculoRepository;

    // Metodo para listar Veículos
    public List<Vehicle> listarVeiculos() {
        return veiculoRepository.findAll();
    }


    // Metodo para salvar/criar novo Veículo
    public Vehicle cadastrarVeiculo(Vehicle veiculo) {
        return veiculoRepository.save(veiculo);
    }


    public Vehicle editarVeiculo(Integer id, Vehicle veiculo) {
        Optional<Vehicle> veiculoExistente = veiculoRepository.findById(id);

        if (veiculoExistente.isPresent()) {
            Vehicle veiculoAtualizado = veiculoExistente.get();
            veiculoAtualizado.setModel(veiculo.getModel());
            veiculoAtualizado.setYear(veiculo.getYear());
            veiculoAtualizado.setBrand(veiculo.getBrand());
            veiculoAtualizado.setMileage(veiculo.getMileage());
            veiculoAtualizado.setFuelType(veiculo.getFuelType());
            veiculoAtualizado.setAvailability(veiculo.getAvailability());
            veiculoAtualizado.setPrice(veiculo.getPrice());
            veiculoAtualizado.setImageUrl(veiculo.getImageUrl());
            return veiculoRepository.save(veiculoAtualizado);
        } else {
            return null;
        }
    }

    // Metodo para deletar um veículo
    public void deletarVeiculo(int id) {
        veiculoRepository.deleteById(id);
    }
}
