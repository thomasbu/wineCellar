package io.yocto.lacavedeyocto.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {
    @NotEmpty(message = "message cannot be empty")
    @Email(message = "Invalid email. Please enter a valid email address")
    private String email;
    @NotEmpty(message = "password cannot be empty")
    private String password;
}
