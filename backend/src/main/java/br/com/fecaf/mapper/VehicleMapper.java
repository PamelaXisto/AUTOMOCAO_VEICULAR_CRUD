package br.com.fecaf.mapper;

import br.com.fecaf.dto.request.VehicleDTO;
import br.com.fecaf.model.Vehicle;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle toEntity(VehicleDTO dto);

    VehicleDTO toDTO(Vehicle entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateVehicleFromDto(VehicleDTO dto, @MappingTarget Vehicle entity);
}
