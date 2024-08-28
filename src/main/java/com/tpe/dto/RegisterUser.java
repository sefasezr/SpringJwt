package com.tpe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {

    @NotBlank
    private String firstName;

    @NotBlank
    @Size(min = 5,max =10)
    private String userName;

    @NotBlank
    @Size(min = 4,max =8)
    private String password;
}
