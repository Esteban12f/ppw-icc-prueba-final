package ec.edu.ups.icc.labevaluation.users.dtos;

import java.util.Set;

import ec.edu.ups.icc.labevaluation.security.entities.RoleEntity;

public record UserResponseDto(
    Long id, 
    String name, 
    String email, 
    Integer age, 
    Boolean active, 
    Set<RoleEntity> roles) {
}
