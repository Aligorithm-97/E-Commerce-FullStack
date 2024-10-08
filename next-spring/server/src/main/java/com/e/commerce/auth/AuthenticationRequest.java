package com.e.commerce.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

    @Email(message = "Email format required !")
    @NotEmpty(message = "Email required !")
    @NotBlank(message = "Email required !")
    private String email;
    @NotEmpty(message = "Password required !")
    @NotBlank(message = "Password required !")
    @Size(min = 8,message = "Min 8 Characters !")
    private String password;
}
