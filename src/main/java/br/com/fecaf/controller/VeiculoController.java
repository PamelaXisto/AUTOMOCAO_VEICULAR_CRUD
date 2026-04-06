// CONTROLLER irá receber as requisições/HTTP

package br.com.fecaf.controller;

import br.com.fecaf.model.Vehicle;
import br.com.fecaf.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501", allowedHeaders = "*")
@RequestMapping("/api/veiculos") //Caminho
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;


    // Endpoint para listar os veiculos
    @GetMapping("/listarVeiculos")
    public List<Vehicle> listarVeiculos() {
        return veiculoService.listarVeiculos();
    }


    // Endpoint para cadastrar um novo veículo
    @PostMapping("/cadastrarVeiculos")
    public ResponseEntity<Vehicle> cadastrarVeiculo(@RequestBody Vehicle veiculo) {
        Vehicle newVeiculo = veiculoService.cadastrarVeiculo(veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVeiculo);
    }


    // Endpoint para editar um veículo existente
    @PutMapping("/editarVeiculo/{id}")
    public ResponseEntity<Vehicle> editarVeiculo(@PathVariable Integer id, @RequestBody Vehicle veiculo) {
        Vehicle veiculoAtualizado = veiculoService.editarVeiculo(id, veiculo);

        if (veiculoAtualizado != null) {
            return ResponseEntity.ok(veiculoAtualizado);
        } else {
            return ResponseEntity.notFound().build();

        }
    }


    // Endpoint para deletar um veículo
    @DeleteMapping("/deletarVeiculo/{id}")
    public ResponseEntity<Void> deletarVeiculo(@PathVariable int id) {
        veiculoService.deletarVeiculo(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
