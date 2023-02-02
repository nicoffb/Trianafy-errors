package com.salesianostriana.dam.trianafy.validation.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EditUserPasswordDto {

    @NotEmpty(message = "{userDto.password.notempty}")
    private String password;

    @NotEmpty(message = "{userDto.verifypassword.notempty}")
    private String verifyPassword;

}
