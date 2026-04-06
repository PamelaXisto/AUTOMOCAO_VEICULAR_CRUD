package br.com.fecaf.repository;

import br.com.fecaf.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Vehicle, Integer> {


}
