package br.com.fecaf.mapper;

import br.com.fecaf.dto.request.UserDTO;
import br.com.fecaf.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", source = "email", qualifiedByName = "normalizeEmail")
    @Mapping(target = "cpf", source = "cpf", qualifiedByName = "normalizeCpf")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "normalizePhone")
    User toEntity(UserDTO dto);

    @Mapping(target = "password", ignore = true)
    UserDTO toDTO(User entity);

    @Named("normalizeEmail")
    default String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    @Named("normalizeCpf")
    default String normalizeCpf(String cpf) {
        return cpf == null ? null : cpf.replaceAll("\\D", "");
    }

    @Named("normalizePhone")
    default String normalizePhone(String phone) {
        return phone == null ? null : phone.replaceAll("\\D", "");
    }
}
