package org.roronoa.banklyuser.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthDto {
    @NotNull @NotEmpty @Email
    private String email;
    @NotNull @NotEmpty
    private String password;
}
