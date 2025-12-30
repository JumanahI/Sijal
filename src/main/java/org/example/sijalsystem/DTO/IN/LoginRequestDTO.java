package org.example.sijalsystem.DTO.IN;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDTO {

    @NotEmpty(message = "The username is required")
    private String username;
    @NotEmpty(message = "The password is required")
    private String password;
}
