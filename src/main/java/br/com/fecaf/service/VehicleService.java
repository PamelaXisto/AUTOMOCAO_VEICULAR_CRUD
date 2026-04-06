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
            veiculoAtualizado.setModelo(veiculo.getModelo());
            veiculoAtualizado.setAno(veiculo.getAno());
            veiculoAtualizado.setMarca(veiculo.getMarca());
            veiculoAtualizado.setQuilometragem(veiculo.getQuilometragem());
            veiculoAtualizado.setCombustivel(veiculo.getCombustivel());
            veiculoAtualizado.setDisponibilidade(veiculo.getDisponibilidade());
            veiculoAtualizado.setPreco(veiculo.getPreco());
            veiculoAtualizado.setFoto(veiculo.getFoto());
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
