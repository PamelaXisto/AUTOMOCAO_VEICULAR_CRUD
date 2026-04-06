package br.com.fecaf.services;


import br.com.fecaf.model.Veiculo;
import br.com.fecaf.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    // Metodo para listar Veículos
    public List<Veiculo> listarVeiculos() { return veiculoRepository.findAll(); }


    // Metodo para salvar/criar novo Veículo
    public Veiculo cadastrarVeiculo(Veiculo veiculo) {return veiculoRepository.save(veiculo); }


    public Veiculo editarVeiculo(Integer id, Veiculo veiculo) {
        Optional<Veiculo> veiculoExistente = veiculoRepository.findById(id);

        if (veiculoExistente.isPresent()) {
            Veiculo veiculoAtualizado = veiculoExistente.get();
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
