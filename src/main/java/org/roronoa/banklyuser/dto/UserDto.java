package org.roronoa.banklyuser.dto;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.roronoa.banklyuser.entity.Role;

@Data
public class UserDto {
    private Integer id;

    private String uuid;
    @NotNull @NotEmpty
    private String firstName;
    @NotNull @NotEmpty
    private String lastName;
    @NotNull @NotEmpty @Email
    private String email;
    @NotNull @NotEmpty
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
