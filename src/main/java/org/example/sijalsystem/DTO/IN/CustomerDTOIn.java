package org.example.sijalsystem.DTO.IN;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTOIn {

    @NotBlank(message = "username must not be empty" )
    private String username;

    @NotBlank(message = "password must not be empty" )
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$",message = "Password must contain lowercase, uppercase, number, and symbol")
    private String password;

    @NotBlank(message = "full name must not be empty" )
    private String fullName;

    @Email(message = "email must be valid")
    @NotBlank(message = "email must not be empty" )
    private String email;

    @NotBlank(message = "phone number must not be empty" )
    private String phoneNumber;

    @NotEmpty(message = "age must not be null" )
    private String age;



}
